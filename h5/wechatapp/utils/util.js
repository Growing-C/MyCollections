const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}


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

function mergeBytes(list = []) {
  let bytes = [];
  list.map((item) => {
    bytes.concat(item);
  })
  return bytes;
}

module.exports = {
  formatTime, //格式化时间
  shortNum2Hex, //short 型数据转换成16进制String
  num2HexString, //数字转为16进制的 字符串  比如 1--> 01  返回的必定是双数位的
  hexStr2ab, //将16进制转化为ArrayBuffer
  ab2hex, //ArrayBuffer转16进度字符串示例
  inArray, //获取val值在 arr中的脚标 -1代表没有
}