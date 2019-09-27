package com.cgy.mycollections.functions.net.retrofit.responsemonitor;


import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Description :请求返回数据监测者
 * Author :cgy
 * Date :2018/11/28
 */
public class ResponseMonitor implements Interceptor {
    public final String TAG = ResponseMonitor.class.getSimpleName();

    private static final Charset UTF8 = Charset.forName("UTF-8");

    IResponseReporter mErrorReporter;

    public ResponseMonitor(IResponseReporter reporter) {
        this.mErrorReporter = reporter;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (mErrorReporter == null)//为空就没必要继续了
            return chain.proceed(chain.request());

        ReportModel reportModel = new ReportModel();

        //----------request---------------
        ReportModel.RequestModel requestModel = reportModel.request;
        Request request = chain.request();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        requestModel.url = "--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : "");

        requestModel.headers = "";
        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                requestModel.headers += "Content-Type: " + requestBody.contentType() + "\n";
            }
            if (requestBody.contentLength() != -1) {
                requestModel.headers += "Content-Length: " + requestBody.contentLength() + "\n";
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                requestModel.headers += name + ": " + headers.value(i) + "\n";
            }
        }

        if (hasRequestBody && !bodyEncoded(request.headers())) {//有请求内容，且  content-encoding符合要求
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {
                requestModel.requestBody = "";
                requestModel.requestBody += buffer.readString(charset);
            }
        }

        //----------request---------------


        //----------response---------------
        ReportModel.ResponseModel responseModel = reportModel.response;
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        responseModel.url = "<-- "
                + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url();
        responseModel.responseTime = tookMs + "ms";
        Headers responseHeaders = response.headers();
        responseModel.headers = "";
        for (int i = 0, count = responseHeaders.size(); i < count; i++) {
            responseModel.headers += responseHeaders.name(i) + ": " + responseHeaders.value(i) + "\n";
        }

        //有返回内容，且返回内容符合要求
        if (HttpHeaders.hasBody(response) && !bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {//可解析
                if (contentLength != 0) {
                    responseModel.responseBody = "";
                    responseModel.responseBody += buffer.clone().readString(charset);
                }
            }

        }
        //符合要求的时候就上报给后台
        if (mErrorReporter.needReport(responseModel.responseBody)) {
            mErrorReporter.report(reportModel);
        }
        //----------response---------------
        return response;
    }


    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
