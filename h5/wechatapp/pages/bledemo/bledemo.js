// pages/bledemo/bledemo.js

const CRC = require('../../utils/crc16.js')

const app = getApp()
const targetDeviceId = 'FF:FC:9D:E9:B0:33'
// var mByteList=[]
const commandHead = 'fefe'
const commandVersion = '0000'
const commandEnd = '3b'
var commandNum = 0 //指令包序号

var writeCharacteristic

//获取val值在 arr中的脚标 -1代表没有
function inArray(arr, key, val) {
  for (let i = 0; i < arr.length; i++) {
    if (arr[i][key] === val) {
      return i;
    }
  }
  return -1;
}

// ArrayBuffer转16进度字符串示例
function ab2hex(buffer) {
  var hexArr = Array.prototype.map.call(
    new Uint8Array(buffer),
    function(bit) {
      return ('00' + bit.toString(16)).slice(-2)
    }
  )
  return hexArr.join('');
}

// 将16进制转化为ArrayBuffer
function hexStr2ab(hexStr) {
  return new Uint8Array(hexStr.match(/[\da-f]{2}/gi).map(function(h) {
    // console.log(`aaa :${h}`)
    return parseInt(h, 16)
  })).buffer
}


//数字转为16进制的 字符串  比如 1--> 01  返回的必定是双数位的
function num2HexString(num) {
  let numStr = num.toString(16)
  if (numStr.length % 2 == 1) {
    return `0${numStr}`
  }
  return numStr
}

//结果必为 2字节
function shortNum2Hex(num, bigEndian) {
  let hexNum = num.toString(16)
  if (hexNum.length % 2 == 1) { //确保是双数位
    hexNum = `0${hexNum}`
  }

  let numLen = hexNum.length
  if (numLen == 2) {
    hexNum = `00${hexNum}`
  } else if (numLen > 4) {
    return '0000'
  }

  if (bigEndian) { //大端的话 要调个个 比如  0056-->5600
    hexNum = `${hexNum.substring(2, 4)}${hexNum.substring(0, 2)}`
  }

  return hexNum
}


//是否是选中的设备
function isTargetDevice(device) {
  // console.log(device.deviceId.length + 'deviceID--->' + device.deviceId)
  // console.log('targetDeviceId--->' + targetDeviceId)
  // console.log(targetDeviceId == device.deviceId)
  return device.deviceId == targetDeviceId;

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

function mergeBytes(list = []) {
  let bytes = [];
  list.map((item) => {
    bytes.concat(item);
  })
  return bytes;
}


Page({
  data: {
    devices: [], //设备列表
    connected: false,
    chs: [], //设备特征值 数据列表
    log: 'log',
  },
  test() {
    let test = hexStr2ab('000014000000111160694e41484f2d484a303031000000003b')
    console.log('a?' + test.byteLength)
    console.log(ab2hex(test.slice(0, 4)))

    let dataView = new DataView(test)
    dataView.setUint8(1, 100)
    console.log(ab2hex(test.slice(0, 4)))
    // let a = 15001
    // console.log(`test ${num2HexString(a)}`)
    // console.log(CRC.crc16(CRC.stringToByte('12345678')));
    // crc目标 B278
    // this.operateLock(true)  
    // console.log(CRC.CRC.ToModbusCRC16('000014000000111160694e41484f2d484a303031000000003b'.toUpperCase()));
    // console.log(CRC.CRC.ToModbusCRC16('000014000000111160694E41484F2D484A303031000000003B'));
    // console.log(CRC.CRC.ToModbusCRC16('000014000000111160694e41484f2d484a303031000000003b'));
    // console.log(`shortNum2Hex: ${shortNum2Hex(33,true)}`)
    // console.log(CRC.CRC.ToCRC16('12345678', true));
    // console.log(CRC.CRC.ToCRC16('12345678', false));

    // console.log(CRC.CRC.ToModbusCRC16('12345678', true));


    // console.log(CRC.CRC.ToCRC16('你好，我们测试一下CRC16算法', true));
  },

  //打开蓝牙，开始扫描
  openBluetoothAdapter() {

    this.setData({
      log: '点击开始扫描'
    })
    let self = this
    wx.openBluetoothAdapter({

      success: (res) => {
        console.log('openBluetoothAdapter success', res)
        this.startBluetoothDevicesDiscovery()
      },
      fail: (res) => {
        this.setData({
          log: '请先打开蓝牙'
        })
        if (res.errCode === 10001) {
          wx.onBluetoothAdapterStateChange(function(res) {
            console.log('onBluetoothAdapterStateChange', res)
            console.log('onBluetoothAdapterStateChange this', this)
            if (res.available) {
              self.startBluetoothDevicesDiscovery()
            }
          })
        }
      }
    })
  },

  //获取蓝牙适配器是否打开
  getBluetoothAdapterState() {
    wx.getBluetoothAdapterState({
      success: (res) => {
        console.log('getBluetoothAdapterState', res)
        this.setData({
          log: '蓝牙打开着'
        });
        if (res.discovering) {
          this.onBluetoothDeviceFound()
        } else if (res.available) {
          this.startBluetoothDevicesDiscovery()
        }
      }
    })
  },

  //开始蓝牙扫描
  startBluetoothDevicesDiscovery() {
    if (this._discoveryStarted) {
      return
    }
    this.setData({
      log: '开始蓝牙扫描'
    })
    this._discoveryStarted = true
    wx.startBluetoothDevicesDiscovery({
      allowDuplicatesKey: true,
      success: (res) => {
        console.log('startBluetoothDevicesDiscovery success', res)
        this.onBluetoothDeviceFound()
      },
    })
  },

  //停止扫描
  stopBluetoothDevicesDiscovery() {
    this.setData({
      log: this.data.log + '--停止扫描'
    })
    wx.stopBluetoothDevicesDiscovery()
  },

  //发现蓝牙设备
  onBluetoothDeviceFound() {
    wx.onBluetoothDeviceFound((res) => {
      res.devices.forEach(device => {
        if (!device.name && !device.localName) {
          return
        }
        this.setData({
          log: '发现设备！'
        })
        const foundDevices = this.data.devices
        console.log('foundDevices', foundDevices)
        const idx = inArray(foundDevices, 'deviceId', device.deviceId)
        const data = {}
        if (idx === -1) {
          data[`devices[${foundDevices.length}]`] = device
        } else {
          data[`devices[${idx}]`] = device
        }
        this.setData(data)
        if (isTargetDevice(device)) {
          console.log('目标设备，准备连接')
          this.connectDevice(device)
        }
      })
    })
  },

  //开始连接蓝牙设备
  connectDevice(device) {
    const deviceId = device.deviceId
    const name = device.name

    this.setData({
      log: '开始连接设备：' + deviceId
    })
    wx.createBLEConnection({
      deviceId,
      success: (res) => {
        this.setData({
          connected: true,
          name,
          deviceId,
        })
        this.getBLEDeviceServices(deviceId)

        wx.onBLEConnectionStateChange(function(res) {
          // 该方法回调中可以用于处理连接意外断开等异常情况
          console.log(`device ${res.deviceId} state has changed, connected: ${res.connected}`)
        })
      }
    })
    this.stopBluetoothDevicesDiscovery()
  },

  //开始连接蓝牙设备
  createBLEConnection(e) {
    const ds = e.currentTarget.dataset
    const deviceId = ds.deviceId
    const name = ds.name

    this.setData({
      log: '开始连接设备：' + deviceId
    })
    wx.createBLEConnection({
      deviceId,
      success: (res) => {
        this.setData({
          connected: true,
          name,
          deviceId,
        })
        this.getBLEDeviceServices(deviceId)

        wx.onBLEConnectionStateChange(function(res) {
          // 该方法回调中可以用于处理连接意外断开等异常情况
          console.log(`device ${res.deviceId} state has changed, connected: ${res.connected}`)
        })
      }
    })
    this.stopBluetoothDevicesDiscovery()
  },

  //断开蓝牙连接
  closeBLEConnection() {

    this.setData({
      log: '断开连接'
    })
    wx.closeBLEConnection({
      deviceId: this.data.deviceId
    })
    this.setData({
      connected: false,
      chs: [],
      canWrite: false,
    })
  },

  //获取扫描到的蓝牙设备的 service uuid
  getBLEDeviceServices(deviceId) {
    wx.getBLEDeviceServices({
      deviceId,
      success: (res) => {
        for (let i = 0; i < res.services.length; i++) {
          if (res.services[i].isPrimary) {
            this.getBLEDeviceCharacteristics(deviceId, res.services[i].uuid)
            return
          }
        }
      }
    })
  },

  //获取蓝牙设备特征值
  getBLEDeviceCharacteristics(deviceId, serviceId) {
    console.log('+++++++++++++++++++++++++++开始获取设备特征值！！！+++++++++')
    wx.getBLEDeviceCharacteristics({
      deviceId,
      serviceId,
      success: (res) => {
        for (let i = 0; i < res.characteristics.length; i++) {
          let item = res.characteristics[i]
          console.log('000 getBLEDeviceCharacteristics success', item)
          if (item.properties.read) {
            console.log('start 111---------------readBLECharacteristicValue---')
            wx.readBLECharacteristicValue({
              deviceId,
              serviceId,
              characteristicId: item.uuid,
              success(res) {
                console.log('readBLECharacteristicValue:', res.errCode)
              }
            })
          }
          if (item.properties.write) {
            writeCharacteristic = item
            console.log('start 222---------------writeBLECharacteristicValue---')
            this.setData({
              canWrite: true
            })
            this._deviceId = deviceId
            this._serviceId = serviceId
            this._characteristicId = item.uuid
            // this.writeBLECharacteristicValue()
          }
          if (item.properties.notify || item.properties.indicate) {
            console.log('start 333--------------notifyBLECharacteristicValueChange---')
            wx.notifyBLECharacteristicValueChange({
              deviceId,
              serviceId,
              characteristicId: item.uuid,
              state: true,
              success(res) {
                console.log('notifyBLECharacteristicValueChange success', res.errMsg)
              }
            })
          }
        }
      },
      fail(res) {
        console.error('getBLEDeviceCharacteristics', res)
      }
    })
    // 操作之前先监听，保证第一时间获取数据
    wx.onBLECharacteristicValueChange((characteristic) => {
      const idx = inArray(this.data.chs, 'uuid', characteristic.characteristicId)
      const data = {}

      let valueStr = ab2hex(characteristic.value)
      let len = valueStr.length / 2

      console.log(`characteristic ${characteristic.characteristicId} has changed, value byte length is ${len}`)
      console.log(valueStr)

      let realCommandByteLenth = 0
      let commandString

      // console.log('onBLECharacteristicValueChange', characteristic.characteristicId + '--' + ab2hex(characteristic.value))
      if (idx === -1) { //还没有 收到过这个特征值数据
        data[`chs[${this.data.chs.length}]`] = {
          uuid: characteristic.characteristicId,
          value: valueStr
        }
      } else { //收到过了，需要进行判断 ，组合指令(如果不为空，就是已经分包了，就取第一条的数据，来判断长度)
        // console.log(this.data.chs[idx].value.length + '--之前的数据：' + this.data.chs[idx].value)
        if (valueStr.indexOf("fefe") != -1) {
          //新的一条指令

        } else { //同一条指令分包，直接合并
          valueStr = this.data.chs[idx].value + valueStr
        }

        //有开头必定长度要大于40
        if (valueStr.indexOf("fefe") != -1) {
          if (valueStr.length < 26) {
            console.log('怎么会小于13呢？错啦')
            return
          }
          realCommandByteLenth = getCommandLength(valueStr)
          commandString = getCommand(valueStr)

        } else {
          console.log('不包含fefe 肯定有问题！')
          return
        }

        let endStr = valueStr.charAt(valueStr.length - 2) + valueStr.charAt(valueStr.length - 1)
        if (endStr == '3b') {
          console.log('--------------指令处理开始！！！-----------------')
          console.log(`命令--${commandString}--应有的总长度  ${realCommandByteLenth}`)
          //指令已经完整，处理指令
          if (realCommandByteLenth == valueStr.length / 2) {
            console.log(`命令${commandString}正确，开始处理指令`)

            if (commandString == '6010') { //收到连接指令
              this.handleConnectCommand(valueStr)
            }

          } else {
            console.log(`命令${commandString}错误！`)
          }
        }

        data[`chs[${idx}]`] = {
          uuid: characteristic.characteristicId,
          value: valueStr
        }
      }


      // data[`chs[${this.data.chs.length}]`] = {
      //   uuid: characteristic.characteristicId,
      //   value: ab2hex(characteristic.value)
      // }
      this.setData(data)
    })

  },

  //开关锁具
  operateLock(isOpen) {
    commandNum++

    let operateType
    // 开锁类型	1字节	0x01：OPEN正常开锁 0x02：KEEP一直开锁 0x03：CLOSE关锁
    if (isOpen) {
      console.log(`isopen  `)
      operateType = '02'
    } else {
      console.log(`isclose  `)
      operateType = '03'
    }
    let commandBody = createBody('C160', operateType)
    let commandStr = createCommand(commandBody)

    console.log(`指令60C1 operateLock ${commandStr}`)

    this.sendCommandData(hexStr2ab(commandStr))
    // 开锁  FEFE37D500000600020003C160023B
    // 开锁  fefe141500000600000003C160023b
  },

  //发送登录指令
  sendLogin() {
    commandNum++

    let ownerId = '00'
    let ownerPwd = '000000000000'
    let ownerPwdLengthStr = num2HexString(ownerPwd.length)

    ownerPwd = `${ownerPwd}00000000000000` //必须是13位byte

    let bodyContent = `${ownerId}${ownerPwdLengthStr}${ownerPwd}`
    let commandBody = createBody('2160', bodyContent)
    let commandStr = createCommand(commandBody)

    // console.log(`sendLogin bodyContent ${bodyContent}`)
    console.log(`指令6021 sendLogin ${commandStr}`)

    this.sendCommandData(hexStr2ab(commandStr))
    // FEFE4FDD000014000000112160000C000000000000000000000000003B
    // fefe4FDD000014000000112160000c000000000000000000000000003b
    // fefe5F0C000014000100112160000c000000000000000000000000003b
  },

  //处理连接指令，在此处 发一条 连接命令，和登录指令
  handleConnectCommand(valueStr) {
    commandNum = 0 //连接包序号置为 0
    // console.log(`commandNum ${commandNum}`)
    let deviceName = valueStr.substring(valueStr.length - 32, valueStr.length - 2)
    // console.log(`deviceName is ${deviceName} len ${deviceName.length}`)

    let connectBody = createBody('1160', deviceName)
    let connectCommandStr = createCommand(connectBody)
    console.log(`指令6011 send connectCommandStr content :${connectCommandStr}`)
    // FEFEB278000014000000111160694E41484F2D484A303031000000003B 目标crc B278
    // fefe007D000014000000111160694e41484f2d484a303031000000003b

    this.sendCommandData(hexStr2ab(connectCommandStr))

    this.sendLogin()
  },

  //发送完整的指令，此处 会分包，每个包20个字节
  sendCommandData(buffer) {
    let sendLength = 0 //已经发送的包长度 
    if (buffer.byteLength > 20) {
      while (sendLength < buffer.byteLength) {
        let endLength = sendLength + 20

        if (endLength > buffer.byteLength) {
          endLength = buffer.byteLength
        }
        this.writeBLECharacteristicValue(buffer.slice(sendLength, endLength))

        sendLength = endLength
      }

    } else {
      this.writeBLECharacteristicValue(buffer)
    }

  },

  //发送指令，此处已分包，buffer最多20个自己
  writeBLECharacteristicValue(buffer) {
    // 向蓝牙设备发送一个0x00的16进制数据
    // let buffer = new ArrayBuffer(1)
    // let dataView = new DataView(buffer)
    // dataView.setUint8(0, Math.random() * 255 | 0)

    let dataView = new DataView(buffer)
    // dataView.setUint8(1, 100)

    console.log(`!!!!!!!!!!!!! 给设备发送了消息: ${this._characteristicId} content :${ab2hex(buffer)}`)
    wx.writeBLECharacteristicValue({
      deviceId: this._deviceId,
      serviceId: this._serviceId,
      characteristicId: this._characteristicId,
      value: buffer,
      success: function(res) {
        console.log('writeBLECharacteristicValue success', res.errMsg)
      },
      fail: function(res) {
        console.log('writeBLECharacteristicValue fail', res.errMsg)
      }
    })
  },

  //关闭蓝牙
  closeBluetoothAdapter() {
    this.setData({
      log: '结束流程'
    })
    wx.closeBluetoothAdapter()
    this._discoveryStarted = false
  },
})