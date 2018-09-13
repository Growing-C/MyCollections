## 设置entryActivity
 
引导页展示1.5s之后会跳转到业务界面，这个业务界面设置在`EagleBase`模块的`BaseApplication`中设置，若未设置则不会跳转

具体内容通过json来配置，配置json路径为`main/assets/config/app.json`

json结构大致如下

```json
{
  "customerId":0,//客户类型，0慧晶，1日本
  "package":"com.linkstec.blockchains.bcllock", //包名
  "appName":"慧晶智能",
  "baseUrl":"",
  "isShowBCL":false,
  "htmlUrl":"http://192.168.2.94:10080/project/iot/ilock/ilock-user",

  "configs":{

  },
  "nativeapp": {
    "urls": [
      "/ibmessage/ibmessagenativemodule?icon=\ue699&title=ibmessage&color=#222222&selectColor=#2097f4",
      "/ibmessage/ibmessagenativemodule?icon=\ue699&title=ibmessage&color=#222222&selectColor=#2097f4"
    ],
    "type": 0,
    "extra": {
      "type": "add",
      "style": {
        "subType": "round",
        "title": "扫码•支付",
        "backgroundColor": "#FED037",
        "foregroundColor": "#ffffff",
        "image": "return_product_icon",
        "column": 4
      },
      "extraParams": [
        {
          "name": "测试1",
          "icon": "gaealogor.png",
          "url": "https://www.baidu.com"
        },
        {
          "name": "测试2",
          "icon": "gaealogor.png",
          "url": "eagle://lmsplogin/lmsploginnativemodule"
        },
        {
          "name": "测试3",
          "icon": "gaealogor.png",
          "url": "eagle://lmsplogin/lmsploginnativemodule"
        },
        {
          "name": "测试4",
          "icon": "gaealogor.png",
          "url": "eagle://lmsplogin/lmsploginnativemodule"
        },
        {
          "name": "测试5",
          "icon": "gaealogor.png",
          "url": "eagle://lmsplogin/lmsploginnativemodule"
        },
        {
          "name": "测试6",
          "icon": "gaealogor.png",
          "url": "eagle://lmsplogin/egloginnativemodule"
        },
        {
          "name": "测试7",
          "icon": "gaealogor.png",
          "url": "eagle://lmsplogin/egloginnativemodule"
        },
        {
          "name": "测试8",
          "icon": "gaealogor.png",
          "url": "http://www.hao123.com"
        }
      ]
    }
  },
  "webapp": null,
  "configurations": {
    "bugly": {
      "appId": "544b983bbc"
    },
    "shareSDK": {
      "umeng": "5ad04358f29d984ab7000078",
      "platforms": {
        "weibo": {
          "appKey": "3921700954",
          "appSecret": "04b48b094faeb16683c32669824ebdad",
          "redirectURL": "https://sns.whalecloud.com/sina2/callback"
        },
        "wechat": {
          "appKey": "wxdc1e388c3822c80b",
          "appSecret": "3baf1193c85774b3fd9d18447d76cab0",
          "redirectURL": "http://mobile.umeng.com/social"
        },
        "tencent": {
          "appKey": "1105821097",
          "appSecret": "",
          "redirectURL": "http://mobile.umeng.com/social"
        }
      }
    },
    "guide": [
      "闪屏1.png",
      "闪屏2.png",
      "闪屏3.png",
      "闪屏4.png"
    ],
    "login": {
      "loginUrl": "/lmsplogin/lmsploginnativemodule",
      "isLoginAlways": true,
      "loginIcon": "loginIcon.png",
      "loginCopyRight": "string",
      "loginTitle": "loginTitle",
      "api": {
        "env": "dev",
        "dev": {
          "login": "http://192.168.2.92:8091/m/MLogin",
          "verify": "http://192.168.10.165:8091/ibf/rand?rc="
        },
        "product": {
          "login": "https://ibank.mszq.com:443/ibf/MLoginServlet",
          "verify": "https://ibank.mszq.com:443/ibf/rand?rc="
        }
      }
    }
  }
}
```

如果`configurations`下面的`login`不为空则会跳转到`login`下面的`loginUrl`对应的界面

如果为空， app是webapp的话，则会跳转到 `/eaglebase/web/eaglewebview`

app如果是nativeapp则逻辑如下

```java
          	if (this.mNativeapp.getType() == 0) {
                    this.entryActivity = "/tab/maintabactivity";
                } else if (this.mNativeapp.getType() == 1) {
                    this.entryActivity = "/menu/mainmenuactivity";
                } else if (this.mNativeapp.getType() == 2) {
                    this.entryActivity = "/tab/maintabactivity";
                    ExtraBean tabbarExtra = this.mNativeapp.getExtra();
                    if (tabbarExtra != null && "add".equals(tabbarExtra.getType())) {
                        this.mTabbarExtraStyle = tabbarExtra.getStyle();
                        this.mTabbarExtraParams = tabbarExtra.getExtraParams();
                    }
                } else {
                    if (this.mNativeapp.getType() != -1) {
                        throw new RuntimeException("no entry activity found, pls check the config of app.json.");
                    }

                    this.entryActivity = (String)this.mNativeapp.getUrls().get(0);
                }
```
