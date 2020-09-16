package com.cgy.mycollections.functions.net.retrofit.mock;

import android.text.TextUtils;

import java.io.IOException;

import appframe.ProjectConfig;
import appframe.utils.LogUtils;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 自定义okHttp拦截器，可定制接口伪造http响应数据,用于在服务端还未开发完成时进行测试
 * Created by RB-cgy on 2016/9/29.
 */
public class MockDataInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        String path = chain.request().url().uri().getPath();
        LogUtils.d("intercept: path=" + path);
        response = interceptRequestWhenDebug(chain, path);
        if (null == response) {
            LogUtils.d("intercept: null == response");
            response = chain.proceed(chain.request());
        }
        return response;
    }

    /**
     * 测试环境下拦截需要的接口请求，伪造数据返回
     *
     * @param chain 拦截器链
     * @param path  请求的路径path
     * @return 伪造的请求Response，有可能为null
     */
    private Response interceptRequestWhenDebug(Chain chain, String path) {
        Response response = null;
        if (ProjectConfig.isDebugMode()) {
            LogUtils.d("interceptRequestWhenDebug------");
            Request request = chain.request();

            if (path.contains("/v1/shortLinks")) {//生成短链接
                response = getMockResponse(request, "mock/DynamicLinke.json");
            }
        }
        return response;
    }

    /**
     * 伪造活动列表接口响应
     *
     * @param request 用户的请求
     * @return 伪造的活动列表HTTP响应
     */
    private Response getMockResponse(Request request, String path) {
        LogUtils.d("getMockResponse");
        Response response;
        String data = MockDataGenerator.getMockDataFromJsonFile(path);
        response = getHttpSuccessResponse(request, data);
        return response;
    }

    /**
     * 根据数据JSON字符串构造HTTP响应，在JSON数据不为空的情况下返回200响应，否则返回500响应
     *
     * @param request  用户的请求
     * @param dataJson 响应数据，JSON格式
     * @return 构造的HTTP响应
     */
    private Response getHttpSuccessResponse(Request request, String dataJson) {
        Response response;
        if (TextUtils.isEmpty(dataJson)) {
            LogUtils.d("getHttpSuccessResponse: dataJson is empty!");
            response = new Response.Builder()
                    .code(500)
                    .protocol(Protocol.HTTP_1_0)
                    .request(request)
                    //必须设置protocol&request，否则会抛出异常
                    .build();
        } else {
            response = new Response.Builder()
                    .code(200)
                    .message("OK")//必须有message，不然会报错
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .addHeader("Content-Type", "application/json")
                    .body(ResponseBody.create(MediaType.parse("application/json"), dataJson)).build();
        }
        return response;
    }

    private Response getHttpFailedResponse(Chain chain, int errorCode, String errorMsg) {
        if (errorCode < 0) {
            throw new IllegalArgumentException("httpCode must not be negative");
        }
        Response response;
        response = new Response.Builder()
                .code(errorCode)
                .message(errorMsg)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .build();
        return response;
    }
}
