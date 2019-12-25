package com.cgy.mycollections.functions.net.retrofit.responsemonitor;


import androidx.annotation.NonNull;

import appframe.utils.L;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.Interceptor;

/**
 * Description :接收到信息的错误上报者
 * Author :cgy
 * Date :2018/11/28
 */
public interface IResponseReporter {
    IResponseReporter DEFAULT = new IResponseReporter() {
        @Override
        public boolean needReport(String responseBody) {
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                String code = jsonObject.optString("code");
                if (!"0".equals(code)) {
                    return true;//不是0 表示请求结果错误，需要上报
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean report(@NonNull ReportModel report) {
            L.e("report-------------------------------\n" + new Gson().toJson(report));
            return false;
        }
    };

    /**
     * 根据返回内容判断是否需要上传
     *
     * @param responseBody
     * @return
     */
    boolean needReport(String responseBody);


    /**
     * 上传请求信息给后台
     *
     * @param report 要上传的内容，该参数不可能为空，不需要检验
     * @return
     * @see ResponseMonitor#intercept(Interceptor.Chain)
     */
    boolean report(ReportModel report);
}
