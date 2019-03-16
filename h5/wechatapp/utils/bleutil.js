var constants = require('/constants.js');
var errorInfo = require('/errorInfo.js');
var util = require('/util.js');
var crc = require('/crc16.js');


const targetDeviceId = 'FF:FC:9D:E9:B0:33' //目标设备
// var mByteList=[]
const commandHead = 'fefe'
const commandVersion = '0000'
const commandEnd = '3b'
var commandNum = 0 //指令包序号


//当前连接设备
var currentBle = null
// page的实例对象 用来回调处理结果回去 
var _this = null

var timeOut = null

/** 
 * 
 * 只需要最简单的配置 傻瓜式使用 只要通过配置文件修改uuid 即可发送自己的数据至设备 √
 * 兼容ios android  √
 * 对当前用户的手机系统进行判断是否支持ble   √
 *   获取系统  及系统版本  √
 *   获取微信版本   √
 * 判断用户手机蓝牙是否打开  √
 * 错误码定义 √
 * 所有可变的配置抽取出来 统一配置参数  config文件编写√
 * 连接函数需要抽取出来   √
 * 扫描方法抽取 √
 * ble 操作过程中希望所有状态都抽取出来 通过一个函数来分发 而不是在代码中到处修改 √
 * 希望能对ui有最小的侵入 用户可以定义显示的ui 这边只采用最简单的显示在dialog中 √
 * 如果用户的场景不是手动点击连接 而是代码自动进行连接设备 可以调用_connectBle()传入device即可 √
 * 如果用户的场景不需要扫描 则不调用startScanBle()方法即可 这个方法只是断开当前正在连接的设备 开始扫描附近的外围设备 如果对你的逻辑有侵入 请自行修改 √
 * ios扫描同一个设备出现了两个  × 有瑕疵
 * 扫描时间配置  √
 * 将ble所有的操作都抽取为一个js  √
 *  1.之后也可以拷贝到其他地方使用,耦合低
 *  2.如果和业务逻辑都写在一起,代码很乱,阅读性差,不好维护
 *  3.每个用户可能自己的业务逻辑都不同,所以ble部分可能还需要自己再稍加修改，可以更快更容易定位到需要修改的地方
 *  4.更方便测试
 */


/**
 * 写入数据至设备 
 * data 16进制数组
 */
function write(data) {
  let buffer = new ArrayBuffer(data.length)
  let dataView = new DataView(buffer)
  for (var i = 0; i < data.length; i++) {
    dataView.setUint8(i, data[i])
  }
  //写数据
  wx.writeBLECharacteristicValue({
    deviceId: currentBle,
    serviceId: constants.SERUUID,
    characteristicId: constants.WRITEUUID,
    value: buffer,
    success: res => {
      _this.writeListener(true)
    },
    fail: res => {
      _this.writeListener(false, errorInfo.getErrorInfo(res.errCode))
    }
  })
}

/**
 * 获取系统及系统版本及微信版本
 *   iOS 微信客户端 6.5.6 版本开始支持，Android 6.5.7 版本开始支持  ble
 *    Android 8.0.0 -> res.system
 *    6.7.3 -> res.version
 *    android -> res.platform
 */
function initBle(that) {
  _this = that
  try {
    // 同步获取系统信息 反之有异步 自己根据情况使用
    const res = wx.getSystemInfoSync()
    var tempPlatform = res.platform
    var tempVersion = res.version
    var tempSystem = res.system
    //判断用户当前的微信版本是否支持ble
    _checkPermission(tempPlatform, tempVersion, tempSystem)
  } catch (e) {
    // Do something when catch error
  }
}
/**
 * 扫描蓝牙  扫描时先断开连接 避免在连接时同时扫描 因为他们都比较消耗性能 容易导致卡顿等不良体验
 */
function startScanBle(obj) {
  disconnect()
  //监听寻找到新设备的事件
  wx.onBluetoothDeviceFound((devices) => {
    var bleDevice = devices.devices[0]
    //获取名称 效验名称
    var deviceName = bleDevice.name;
    // 过滤 
    console.log(bleDevice.deviceId) //打印扫描到的mac地址
    if (deviceName.toUpperCase().startsWith(constants.CONDITION1) || deviceName.toUpperCase().startsWith(constants.CONDITION2)) {
      // 扫描结果暴露出去 
      obj.success(bleDevice)
    }
  })
  //扫描附近的外围设备
  wx.startBluetoothDevicesDiscovery({})
  if (timeOut != null) {
    console.log('有扫描任务在进行 先清除任务')
    clearTimeout(timeOut)
  }
  //时间到停止扫描
  timeOut = setTimeout(function() {
    //停止搜寻附近的蓝牙外围设备
    wx.stopBluetoothDevicesDiscovery({})
    timeOut = null
  }, constants.SCANTIME)
}

/**
 * 初始化蓝牙适配器
 */
function _initBleTools() {
  //初始化蓝牙适配器
  wx.openBluetoothAdapter({
    success: function(res) {
      console.log("初始化蓝牙适配器成功")
    },
    fail: function(err) {
      //在用户蓝牙开关未开启或者手机不支持蓝牙功能的情况下，调用 wx.openBluetoothAdapter 会返回错误（errCode=10001），表示手机蓝牙功能不可用
      if (err.errCode == 10001) {
        _this.bleStateListener(constants.STATE_CLOSE_BLE)
      }
    }
  })
  //监听蓝牙适配器状态变化事件
  wx.onBluetoothAdapterStateChange(res => {
    if (res.discovering) {
      _this.bleStateListener(constants.STATE_SCANNING)
    } else {
      _this.bleStateListener(constants.STATE_SCANNED)
    }
  })
}

/**
 * 连接设备的函数 传入对象device即可 在该函数中连接成功后 就会启动监听特征变化用来获取数据
 */
function connectBle(device) {
  disconnect()
  //获取到设备的deviceId地址
  var deviceId = device.deviceId
  //连接时停止扫描 避免连接与扫描在同时进行消耗性能 可能会导致卡顿等影响 如果你需要扫描请注释此代码 对逻辑不会有影响
  wx.stopBluetoothDevicesDiscovery({})
  //记录本次连接的设备 当再次扫描时 本次连接就需要断开 因为蓝牙的扫描和连接都需要高消耗 避免两个操作同时进行
  currentBle = deviceId
  //开始本次连接
  _this.bleStateListener(constants.STATE_CONNECTING)
  wx.createBLEConnection({
    deviceId: deviceId,
    timeOut: constants.CONNECTTIME,
    fail: err => {
      _this.bleStateListener(constants.STATE_CONNECTING_ERROR)
      //蓝牙已经断开连接了  那么当前连接设备要取消掉
      currentBle = null
      console.log('连接失败 下面是连接失败原因')
      console.dir(err)
    }
  })
  //监听低功耗蓝牙连接状态的改变事件。包括开发者主动连接或断开连接，设备丢失，连接异常断开等等
  wx.onBLEConnectionStateChange(res => {
    // 该方法回调中可以用于处理连接意外断开等异常情况
    if (res.connected) {
      _this.bleStateListener(constants.STATE_CONNECTED)
      //获取所有的服务 不获取不影响Android的蓝牙通信 但是官方文档说会影响ios 所以按照文档来 
      _getDevices(deviceId)
    } else {
      _this.bleStateListener(constants.STATE_DISCONNECTED)
      //蓝牙已经断开连接了  那么当前连接设备要取消掉
      currentBle = null
    }
  })
}

//停止搜寻附近的蓝牙外围设备
function stopBluetoothDevicesDiscovery() {
  wx.stopBluetoothDevicesDiscovery({})
}

/**
 * 释放资源
 */
function clear() {
  wx.closeBluetoothAdapter({
    success: function(res) {
      console.log("销毁页面 释放适配器资源")
    }
  })
}

/**
 * 断开当前连接
 */
function disconnect() {
  // 1.断开连接(如果有连接的话) 
  //当前正在连接的设备 当前也可能没有设备连接
  var ble = currentBle
  if (ble != null) {
    console.log("有设备在连接中")
    //说明当前有设备在连接 需要执行断开操作
    wx.closeBLEConnection({
      deviceId: ble
    })
  } else {
    console.log("没有设备在连接中")
  }
}

/**
 * 获取已连接设备的所有服务
 */
function _getDevices(deviceId) {
  wx.getBLEDeviceServices({
    // 这里的 deviceId 需要已经通过 createBLEConnection 与对应设备建立链接
    deviceId: deviceId,
    success: res => {
      var servicesArray = res.services
      for (var i = 0; i < servicesArray.length; i++) {
        var services = servicesArray[i].uuid
        if (constants.SERUUID.toUpperCase() === services) {
          console.log('查找服务成功')
          constants.SERUUID = services
          _getCharacteristic(deviceId, services)
          break;
        }
      }
    },
    fail(err) {
      console.log("没有找到服务")
    }
  })
}


//服务uuid已经找到  
//获取蓝牙设备某个服务中所有特征值(characteristic)。
function _getCharacteristic(deviceId, services) {
  wx.getBLEDeviceCharacteristics({
    // 这里的 deviceId 需要已经通过 createBLEConnection 与对应设备建立链接
    deviceId: deviceId,
    // 这里的 serviceId 需要在 getBLEDeviceServices 接口中获取
    serviceId: services,
    success: (res) => {
      var characteristicsArray = res.characteristics
      for (var i = 0; i < characteristicsArray.length; i++) {
        var characteristics = characteristicsArray[i].uuid
        if (constants.NOTIFYUUID.toUpperCase() === characteristics) {
          console.log('查找通知服务成功')
          constants.NOTIFYUUID = characteristics
        } else if (constants.WRITEUUID.toUpperCase() === characteristics) {
          console.log('查找写服务成功')
          constants.WRITEUUID = characteristics
        }
      }
      _startNotifyListener(deviceId)
    }
  })
}
//启用低功耗蓝牙设备特征值变化时的 notify 功能，订阅特征值
function _startNotifyListener(deviceId) {
  wx.notifyBLECharacteristicValueChange({
    deviceId: deviceId,
    serviceId: constants.SERUUID,
    characteristicId: constants.NOTIFYUUID,
    state: true,
    success: res => {
      //启动成功后 监听数据变化
      _onNotifyListener()
      _this.bleStateListener(constants.STATE_NOTIFY_SUCCESS)
    },
    fail: res => {
      _this.bleStateListener(constants.STATE_NOTIFY_FAIL)
      console.log("开启监听失败 下面是开启监听失败的原因")
      console.dir(res)
    }
  })
}
//监听低功耗蓝牙设备的特征值变化。必须先启用 notifyBLECharacteristicValueChange 接口才能接收到设备推送的 notification。
function _onNotifyListener() {
  wx.onBLECharacteristicValueChange(res => {
    //转换数据
    let buffer = res.value
    let dataView = new DataView(buffer)
    let dataResult = []
    for (let i = 0; i < dataView.byteLength; i++) {
      dataResult.push(dataView.getUint8(i).toString(16))
    }
    const result = dataResult
    _this.notifyListener(result)
  })
}
/**
 * 判断微信客户端是否支持使用蓝牙API
 */
function _checkPermission(platform, version, tempSystem) {
  if (platform === 'android') {
    //android 4.3才开始支持ble Android 8.0.0
    var systemVersion = tempSystem.substring(8, tempSystem.length)
    if (systemVersion >= '4.3.0') {
      //系统支持
      if (version >= '6.5.7') {
        //支持ble 初始化蓝牙适配器
        _initBleTools()
      } else {
        //不支持ble  微信版本过低
        _this.bleStateListener(constants.STATE_NOTBLE_WCHAT_VERSION)
      }
    } else {
      //不支持ble 系统版本过低
      _this.bleStateListener(constants.STATE_NOTBLE_SYSTEM_VERSION)
    }
  } else if (platform === 'ios') {
    if (version >= '6.5.6') {
      //支持ble 初始化蓝牙适配器
      _initBleTools()
    } else {
      //不支持ble  微信版本过低
      _this.bleStateListener(constants.STATE_NOTBLE_WCHAT_VERSION)
    }
  } else {
    console.log('未知系统 请自行探索')
  }
}
//-----------------------自定义内容-------------------------------------------------------------------------

//是否是选中的设备
function isTargetDevice(device) {
  // console.log(device.deviceId.length + 'deviceID--->' + device.deviceId)
  // console.log('targetDeviceId--->' + targetDeviceId)
  // console.log(targetDeviceId == device.deviceId)
  return device.deviceId == targetDeviceId;

}


module.exports = {
  write, //写数据
  startScanBle, //开始扫描
  clear, //退出释放资源
  stopBluetoothDevicesDiscovery, //停止扫描
  connectBle, //连接设备
  initBle, //初始化蓝牙模块
  disconnect, //断开连接
  isTargetDevice, //是否是选中的设备
}


//获取 指令 body长度
function getCommandLength(commandStr) {
  let realLenStr = commandStr.charAt(14) + commandStr.charAt(15) + commandStr.charAt(12) + commandStr.charAt(13)

  //16进制转10进制
  // console.log('getCommandBodyLength realLenStr-->' + realLenStr + '  to int-->' + parseInt(realLenStr, 16))
  return parseInt(realLenStr, 16) + 9;
}

//获取该指令是什么命令
function getCommand(valueStr) {
  let command = valueStr.charAt(24) + valueStr.charAt(25) + valueStr.charAt(22) + valueStr.charAt(23)
  // console.log('commandStr-->' + command)
  return command;
}

//根据指令和body创建指令
//前导码（fefe）+校验字(crc16校验后面所有 2B)+协议版本号(2B)+body长度(2B)+body+foot(3b)
function createCommand(commandBody) {
  // let verionBytes = CRC.stringToByte(commandVersion)

  let bodyLenStr = shortNum2Hex(commandBody.length / 2, true)

  let str = `${commandVersion}${bodyLenStr}${commandBody}${commandEnd}`;
  // let checkedList = [verionBytes, bodyLengthByte, bodyBytes, footBytes]; //要CRC的list
  let crc = CRC.CRC.ToModbusCRC16(str) //arr

  // console.log(`commandVersion--- ${commandVersion}`)
  // console.log(`bodyLenStr--- ${bodyLenStr}`)
  // console.log(`body String ${commandBody}`)
  // console.log(`协议版本号后面--- ${str}`) //000014000000111160694e41484f2d484a303031000000003b
  // console.log(`crc--- ${crc}`)  

  let commandStr = `${commandHead}${crc}${str}`;
  return commandStr;
}

//body = 包序号(1B)+ctrl(2B--SF加密表示1B ,payload长度len 1B--)+payload(command+data)
function createBody(command, data) {
  let payload = command + data //payload
  let commandNumStr = num2HexString(commandNum)
  let securetyFlag = '00' //SF 1为加密，0为不加密。

  //payload转成byte数组时的长度
  let len = num2HexString(payload.length / 2)
  let ctrl = securetyFlag + len; //必须 是 0x xx的格式

  // console.log(`create body num ${commandNumStr}  ctrl  ${ctrl}`)
  return `${commandNumStr}${ctrl}${payload}`
}



// data: {
//   devices: [], //设备列表
//   connected: false,
//   chs: [], //设备特征值 数据列表
//   log: 'log',
// }
// test() {
//   let test = hexStr2ab('000014000000111160694e41484f2d484a303031000000003b')
//   console.log('a?' + test.byteLength)
//   console.log(ab2hex(test.slice(0, 4)))

//   let dataView = new DataView(test)
//   dataView.setUint8(1, 100)
//   console.log(ab2hex(test.slice(0, 4)))
//   // let a = 15001
//   // console.log(`test ${num2HexString(a)}`)
//   // console.log(CRC.crc16(CRC.stringToByte('12345678')));
//   // crc目标 B278
//   // this.operateLock(true)  
//   // console.log(CRC.CRC.ToModbusCRC16('000014000000111160694e41484f2d484a303031000000003b'.toUpperCase()));
//   // console.log(CRC.CRC.ToModbusCRC16('000014000000111160694E41484F2D484A303031000000003B'));
//   // console.log(CRC.CRC.ToModbusCRC16('000014000000111160694e41484f2d484a303031000000003b'));
//   // console.log(`shortNum2Hex: ${shortNum2Hex(33,true)}`)
//   // console.log(CRC.CRC.ToCRC16('12345678', true));
//   // console.log(CRC.CRC.ToCRC16('12345678', false));

//   // console.log(CRC.CRC.ToModbusCRC16('12345678', true));


//   // console.log(CRC.CRC.ToCRC16('你好，我们测试一下CRC16算法', true));
// }

// //打开蓝牙，开始扫描
// openBluetoothAdapter() {

//   this.setData({
//     log: '点击开始扫描'
//   })
//   let self = this
//   wx.openBluetoothAdapter({

//     success: (res) => {
//       console.log('openBluetoothAdapter success', res)
//       this.startBluetoothDevicesDiscovery()
//     },
//     fail: (res) => {
//       this.setData({
//         log: '请先打开蓝牙'
//       })
//       if (res.errCode === 10001) {
//         wx.onBluetoothAdapterStateChange(function(res) {
//           console.log('onBluetoothAdapterStateChange', res)
//           console.log('onBluetoothAdapterStateChange this', this)
//           if (res.available) {
//             self.startBluetoothDevicesDiscovery()
//           }
//         })
//       }
//     }
//   })
// }

// //获取蓝牙适配器是否打开
// getBluetoothAdapterState() {
//   wx.getBluetoothAdapterState({
//     success: (res) => {
//       console.log('getBluetoothAdapterState', res)
//       this.setData({
//         log: '蓝牙打开着'
//       });
//       if (res.discovering) {
//         this.onBluetoothDeviceFound()
//       } else if (res.available) {
//         this.startBluetoothDevicesDiscovery()
//       }
//     }
//   })
// }

// //开始蓝牙扫描
// startBluetoothDevicesDiscovery() {
//   if (this._discoveryStarted) {
//     return
//   }
//   this.setData({
//     log: '开始蓝牙扫描'
//   })
//   this._discoveryStarted = true
//   wx.startBluetoothDevicesDiscovery({
//     allowDuplicatesKey: true,
//     success: (res) => {
//       console.log('startBluetoothDevicesDiscovery success', res)
//       this.onBluetoothDeviceFound()
//     },
//   })
// }

// //停止扫描
// stopBluetoothDevicesDiscovery() {
//   this.setData({
//     log: this.data.log + '--停止扫描'
//   })
//   wx.stopBluetoothDevicesDiscovery()
// }

// //发现蓝牙设备
// onBluetoothDeviceFound() {
//   wx.onBluetoothDeviceFound((res) => {
//     res.devices.forEach(device => {
//       if (!device.name && !device.localName) {
//         return
//       }
//       this.setData({
//         log: '发现设备！'
//       })
//       const foundDevices = this.data.devices
//       console.log('foundDevices', foundDevices)
//       const idx = inArray(foundDevices, 'deviceId', device.deviceId)
//       const data = {}
//       if (idx === -1) {
//         data[`devices[${foundDevices.length}]`] = device
//       } else {
//         data[`devices[${idx}]`] = device
//       }
//       this.setData(data)
//       if (isTargetDevice(device)) {
//         console.log('目标设备，准备连接')
//         this.connectDevice(device)
//       }
//     })
//   })
// }

// //开始连接蓝牙设备
// connectDevice(device) {
//   const deviceId = device.deviceId
//   const name = device.name

//   this.setData({
//     log: '开始连接设备：' + deviceId
//   })
//   wx.createBLEConnection({
//     deviceId,
//     success: (res) => {
//       this.setData({
//         connected: true,
//         name,
//         deviceId,
//       })
//       this.getBLEDeviceServices(deviceId)

//       wx.onBLEConnectionStateChange(function(res) {
//         // 该方法回调中可以用于处理连接意外断开等异常情况
//         console.log(`device ${res.deviceId} state has changed, connected: ${res.connected}`)
//       })
//     }
//   })
//   this.stopBluetoothDevicesDiscovery()
// }

// //开始连接蓝牙设备
// createBLEConnection(e) {
//   const ds = e.currentTarget.dataset
//   const deviceId = ds.deviceId
//   const name = ds.name

//   this.setData({
//     log: '开始连接设备：' + deviceId
//   })
//   wx.createBLEConnection({
//     deviceId,
//     success: (res) => {
//       this.setData({
//         connected: true,
//         name,
//         deviceId,
//       })
//       this.getBLEDeviceServices(deviceId)

//       wx.onBLEConnectionStateChange(function(res) {
//         // 该方法回调中可以用于处理连接意外断开等异常情况
//         console.log(`device ${res.deviceId} state has changed, connected: ${res.connected}`)
//       })
//     }
//   })
//   this.stopBluetoothDevicesDiscovery()
// }

// //断开蓝牙连接
// closeBLEConnection() {

//   this.setData({
//     log: '断开连接'
//   })
//   wx.closeBLEConnection({
//     deviceId: this.data.deviceId
//   })
//   this.setData({
//     connected: false,
//     chs: [],
//     canWrite: false,
//   })
// }

// //获取扫描到的蓝牙设备的 service uuid
// getBLEDeviceServices(deviceId) {
//   wx.getBLEDeviceServices({
//     deviceId,
//     success: (res) => {
//       for (let i = 0; i < res.services.length; i++) {
//         if (res.services[i].isPrimary) {
//           this.getBLEDeviceCharacteristics(deviceId, res.services[i].uuid)
//           return
//         }
//       }
//     }
//   })
// }

// //获取蓝牙设备特征值
// getBLEDeviceCharacteristics(deviceId, serviceId) {
//   console.log('+++++++++++++++++++++++++++开始获取设备特征值！！！+++++++++')
//   wx.getBLEDeviceCharacteristics({
//     deviceId,
//     serviceId,
//     success: (res) => {
//       for (let i = 0; i < res.characteristics.length; i++) {
//         let item = res.characteristics[i]
//         console.log('000 getBLEDeviceCharacteristics success', item)
//         if (item.properties.read) {
//           console.log('start 111---------------readBLECharacteristicValue---')
//           wx.readBLECharacteristicValue({
//             deviceId,
//             serviceId,
//             characteristicId: item.uuid,
//             success(res) {
//               console.log('readBLECharacteristicValue:', res.errCode)
//             }
//           })
//         }
//         if (item.properties.write) {
//           writeCharacteristic = item
//           console.log('start 222---------------writeBLECharacteristicValue---')
//           this.setData({
//             canWrite: true
//           })
//           this._deviceId = deviceId
//           this._serviceId = serviceId
//           this._characteristicId = item.uuid
//           // this.writeBLECharacteristicValue()
//         }
//         if (item.properties.notify || item.properties.indicate) {
//           console.log('start 333--------------notifyBLECharacteristicValueChange---')
//           wx.notifyBLECharacteristicValueChange({
//             deviceId,
//             serviceId,
//             characteristicId: item.uuid,
//             state: true,
//             success(res) {
//               console.log('notifyBLECharacteristicValueChange success', res.errMsg)
//             }
//           })
//         }
//       }
//     },
//     fail(res) {
//       console.error('getBLEDeviceCharacteristics', res)
//     }
//   })
//   // 操作之前先监听，保证第一时间获取数据
//   wx.onBLECharacteristicValueChange((characteristic) => {
//     const idx = inArray(this.data.chs, 'uuid', characteristic.characteristicId)
//     const data = {}

//     let valueStr = ab2hex(characteristic.value)
//     let len = valueStr.length / 2

//     console.log(`characteristic ${characteristic.characteristicId} has changed, value byte length is ${len}`)
//     console.log(valueStr)

//     let realCommandByteLenth = 0
//     let commandString

//     // console.log('onBLECharacteristicValueChange', characteristic.characteristicId + '--' + ab2hex(characteristic.value))
//     if (idx === -1) { //还没有 收到过这个特征值数据
//       data[`chs[${this.data.chs.length}]`] = {
//         uuid: characteristic.characteristicId,
//         value: valueStr
//       }
//     } else { //收到过了，需要进行判断 ，组合指令(如果不为空，就是已经分包了，就取第一条的数据，来判断长度)
//       // console.log(this.data.chs[idx].value.length + '--之前的数据：' + this.data.chs[idx].value)
//       if (valueStr.indexOf("fefe") != -1) {
//         //新的一条指令

//       } else { //同一条指令分包，直接合并
//         valueStr = this.data.chs[idx].value + valueStr
//       }

//       //有开头必定长度要大于40
//       if (valueStr.indexOf("fefe") != -1) {
//         if (valueStr.length < 26) {
//           console.log('怎么会小于13呢？错啦')
//           return
//         }
//         realCommandByteLenth = getCommandLength(valueStr)
//         commandString = getCommand(valueStr)

//       } else {
//         console.log('不包含fefe 肯定有问题！')
//         return
//       }

//       let endStr = valueStr.charAt(valueStr.length - 2) + valueStr.charAt(valueStr.length - 1)
//       if (endStr == '3b') {
//         console.log('--------------指令处理开始！！！-----------------')
//         console.log(`命令--${commandString}--应有的总长度  ${realCommandByteLenth}`)
//         //指令已经完整，处理指令
//         if (realCommandByteLenth == valueStr.length / 2) {
//           console.log(`命令${commandString}正确，开始处理指令`)

//           if (commandString == '6010') { //收到连接指令
//             this.handleConnectCommand(valueStr)
//           }

//         } else {
//           console.log(`命令${commandString}错误！`)
//         }
//       }

//       data[`chs[${idx}]`] = {
//         uuid: characteristic.characteristicId,
//         value: valueStr
//       }
//     }


//     // data[`chs[${this.data.chs.length}]`] = {
//     //   uuid: characteristic.characteristicId,
//     //   value: ab2hex(characteristic.value)
//     // }
//     this.setData(data)
//   })

// }

// //开关锁具
// operateLock(isOpen) {
//   commandNum++

//   let operateType
//   // 开锁类型	1字节	0x01：OPEN正常开锁 0x02：KEEP一直开锁 0x03：CLOSE关锁
//   if (isOpen) {
//     console.log(`isopen  `)
//     operateType = '02'
//   } else {
//     console.log(`isclose  `)
//     operateType = '03'
//   }
//   let commandBody = createBody('C160', operateType)
//   let commandStr = createCommand(commandBody)

//   console.log(`指令60C1 operateLock ${commandStr}`)

//   this.sendCommandData(hexStr2ab(commandStr))
//   // 开锁  FEFE37D500000600020003C160023B
//   // 开锁  fefe141500000600000003C160023b
// }

// //发送登录指令
// sendLogin() {
//   commandNum++

//   let ownerId = '00'
//   let ownerPwd = '000000000000'
//   let ownerPwdLengthStr = num2HexString(ownerPwd.length)

//   ownerPwd = `${ownerPwd}00000000000000` //必须是13位byte

//   let bodyContent = `${ownerId}${ownerPwdLengthStr}${ownerPwd}`
//   let commandBody = createBody('2160', bodyContent)
//   let commandStr = createCommand(commandBody)

//   // console.log(`sendLogin bodyContent ${bodyContent}`)
//   console.log(`指令6021 sendLogin ${commandStr}`)

//   this.sendCommandData(hexStr2ab(commandStr))
//   // FEFE4FDD000014000000112160000C000000000000000000000000003B
//   // fefe4FDD000014000000112160000c000000000000000000000000003b
//   // fefe5F0C000014000100112160000c000000000000000000000000003b
// }

// //处理连接指令，在此处 发一条 连接命令，和登录指令
// handleConnectCommand(valueStr) {
//   commandNum = 0 //连接包序号置为 0
//   // console.log(`commandNum ${commandNum}`)
//   let deviceName = valueStr.substring(valueStr.length - 32, valueStr.length - 2)
//   // console.log(`deviceName is ${deviceName} len ${deviceName.length}`)

//   let connectBody = createBody('1160', deviceName)
//   let connectCommandStr = createCommand(connectBody)
//   console.log(`指令6011 send connectCommandStr content :${connectCommandStr}`)
//   // FEFEB278000014000000111160694E41484F2D484A303031000000003B 目标crc B278
//   // fefe007D000014000000111160694e41484f2d484a303031000000003b

//   this.sendCommandData(hexStr2ab(connectCommandStr))

//   this.sendLogin()
// }

// //发送完整的指令，此处 会分包，每个包20个字节
// sendCommandData(buffer) {
//   let sendLength = 0 //已经发送的包长度 
//   if (buffer.byteLength > 20) {
//     while (sendLength < buffer.byteLength) {
//       let endLength = sendLength + 20

//       if (endLength > buffer.byteLength) {
//         endLength = buffer.byteLength
//       }
//       this.writeBLECharacteristicValue(buffer.slice(sendLength, endLength))

//       sendLength = endLength
//     }

//   } else {
//     this.writeBLECharacteristicValue(buffer)
//   }

// }

// //发送指令，此处已分包，buffer最多20个自己
// writeBLECharacteristicValue(buffer) {
//   // 向蓝牙设备发送一个0x00的16进制数据
//   // let buffer = new ArrayBuffer(1)
//   // let dataView = new DataView(buffer)
//   // dataView.setUint8(0, Math.random() * 255 | 0)

//   let dataView = new DataView(buffer)
//   // dataView.setUint8(1, 100)

//   console.log(`!!!!!!!!!!!!! 给设备发送了消息: ${this._characteristicId} content :${ab2hex(buffer)}`)
//   wx.writeBLECharacteristicValue({
//     deviceId: this._deviceId,
//     serviceId: this._serviceId,
//     characteristicId: this._characteristicId,
//     value: buffer,
//     success: function(res) {
//       console.log('writeBLECharacteristicValue success', res.errMsg)
//     },
//     fail: function(res) {
//       console.log('writeBLECharacteristicValue fail', res.errMsg)
//     }
//   })
// }

// //关闭蓝牙
// closeBluetoothAdapter() {
//   this.setData({
//     log: '结束流程'
//   })
//   wx.closeBluetoothAdapter()
//   this._discoveryStarted = false
// }

// module.exports = {
//   aa: aa
// }