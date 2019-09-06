package com.example.test_webview_demo;

import android.app.Activity;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.test_webview_demo.commands.CommandClassForJS;
import com.example.test_webview_demo.utils.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

public class TestActivity extends Activity {

    private X5WebView webView;

    private CommandClassForJS commandClassForJS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        webView = (X5WebView) findViewById(R.id.web_filechooser);

        commandClassForJS = new CommandClassForJS(webView);

        webView.addJavascriptInterface(commandClassForJS, "external");

        webView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.i("test", "openFileChooser 3");
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
                return true;
            }

        });

//        webView.loadUrl("file:///android_asset/webpage/fileChooser.html");
        webView.loadUrl("file:///android_asset/web-20171115-161418/index.html");
    }

    @Override
    public void onBackPressed() {

        if (findViewById(R.id.test).getVisibility() == View.VISIBLE)
            findViewById(R.id.test).setVisibility(View.GONE);
        else
            findViewById(R.id.test).setVisibility(View.VISIBLE);
    }

    /**
     * 确保注销配置能够被释放
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (this.webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
