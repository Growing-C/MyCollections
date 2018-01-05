/**
 * 外部程序的转换器
 */

// 访问外壳
function requestServiceInJS(data, isDebugMode)
{
  // to shell: 3001^*^{"A":"a","B":"b"}
  // from shell: 3001, 0000^*^{"A":"a","B":"b"}

  // alert('send to shell: [' + data + ']');
  if (!isDebugMode) {
    // 正式环境，调用外壳
    // alert('send to shell: 【' + data + '】');
    window.external.RequestService(data);

  } else {
    // 内部测试环境，调用模拟程序
    console.log('---- call shell in debug mode -----');
    responseService('test', data);
  }

}

// 外壳的回调
function responseService(type, data)
{
  // alert('shell data: 【' + type + '】，【' + data + '】');
  window['angularComponentRef'].runThisFunctionFromOutside(type, data);
}
