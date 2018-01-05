package com.example.test_webview_demo.commands;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.example.test_webview_demo.APPAplication;
import com.example.test_webview_demo.MainActivity;
import com.example.test_webview_demo.databeans.CommonDataBean;
import com.example.test_webview_demo.utils.X5WebView;
import com.google.gson.Gson;


/**
 * Created by dongliang on 2017/12/11.
 */

public class CommandClassForJS {
    private final String split = "\\^\\*\\^";
    private X5WebView webView;

    public CommandClassForJS(X5WebView webView) {
        this.webView = webView;
    }

    @JavascriptInterface
    public void RequestService(String commandMessage) {
        Log.i("test", "recvCommand:" + commandMessage);
        if (commandMessage != null && commandMessage.length() > 0) {
            if (commandMessage.substring(0,5).contains(CommandConstants.COMMAND_SEND_CONFIG)) {


                //TODO：参数暂时写死，后期可能保存本地
                CommonDataBean bean = new CommonDataBean();
                bean.hospitalID = "aqsdermyyadmin";
                bean.deviceID = "123";//TODO
                bean.macAddress = MobileManager.getAdresseMAC(APPAplication.getInstance());//TODO
                bean.server = "http://web.witontek.com/eHospital2/";
                bean.download = "http://d2.witon.us/minipay/dev/test/download/dev/aqsdermyyadmin/download.json";
                bean.webVersion = "0.1.8";
                bean.mode = "10";

                String jsonString = new Gson().toJson(bean);
                Log.i("test", "commandClassForJS:" + jsonString);


                invokeFuncInJs(CommandConstants.COMMAND_SEND_CONFIG, jsonString);

            }

        }
    }

    public void invokeFuncInJs(final String command, final String message) {
        Log.i("test", "invokeFuncInJs command:" + command + " message:" + message);
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:responseService('" + command + "','0000\\^\\*\\^" + message + "');");
            }
        });

        Log.i("test", "invokeFuncInJs end");
    }
}
