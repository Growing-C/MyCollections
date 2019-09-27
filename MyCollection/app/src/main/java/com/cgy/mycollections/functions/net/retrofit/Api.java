package com.cgy.mycollections.functions.net.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.net.retrofit.responsemonitor.IResponseReporter;
import com.cgy.mycollections.functions.net.retrofit.responsemonitor.ResponseMonitor;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.SystemUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import appframe.ProjectConfig;
import appframe.network.retrofit.network.NetworkStateInterceptor;
import appframe.utils.Logger;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yanghailang on 16/7/19.
 */
public final class Api {
    public Map<String, Object> apis = new HashMap<String, Object>();
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static int TIME_OUT_SECONDS = 20;//默认超时时间 暂时设置为20，js那边有个接口超过10秒

    private static Api mApi;
    private X509TrustManager mX509TrustManager;

    private String mBaseUrl;

    private Api() {
    }

    //最好外部使用到Api的时候不要继承了，直接用getInstance...这样可以有多个wrapper，同时只有一个Api实例
    public static Api getInstance() {
        synchronized (Api.class) {
            if (mApi == null) {
                mApi = new Api();
            }
            return mApi;
        }
    }

    private Interceptor mMockDataInterceptor;//仅测试用
    private Interceptor mResponseMonitor;//额外的结果监控者
    private Interceptor mResponseReporter;//用于监控response错误 上报

    /**
     * 外部设置的header
     */
    public Map<String, String> mExternalHeaders = new HashMap<String, String>();


    public void setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value))
            return;//空的不操作

        mExternalHeaders.put(key, value);
    }

    /**
     * 移除请求头
     *
     * @param key
     */
    public void removeHeader(String key) {
        if (TextUtils.isEmpty(key))
            return;//空的不操作

        mExternalHeaders.remove(key);
    }

    /**
     * 清空外部请求头
     */
    public void clearExternalHeaders() {
        mExternalHeaders.clear();
    }

    /**
     * 接口数据模拟，仅开发的时候使用
     *
     * @param interceptor
     */
    public void setMockDataInterceptor(Interceptor interceptor) {
        this.mMockDataInterceptor = interceptor;
    }

    /**
     * 添加请求结果监控者，只监控不关心内容
     *
     * @param interceptor
     */
    public void addResponseMonitor(Interceptor interceptor) {
        this.mResponseMonitor = interceptor;
    }

    /**
     * 添加请求结果监控者，一旦有符合上报条件的就上报
     *
     * @param reporter
     */
    public void addResponseReporter(IResponseReporter reporter) {
        mResponseReporter = new ResponseMonitor(reporter);
    }

    public void addResponseReporter() {
        addResponseReporter(IResponseReporter.DEFAULT);
    }

    /**
     * 创建api实例,默认是使用缓存的
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createApi(final Class<T> clazz) {
        return createApi(clazz, null, false, mBaseUrl, true);
    }

    /**
     * 根据类型创建Api类型,包含返回值为string类型的converter
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createStringApi(final Class<T> clazz) {
        return createApi(clazz, null, true, mBaseUrl, true);
    }

    /**
     * 根据类型创建Api类型,不使用缓存，每次都创建最新的
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createNewApi(final Class<T> clazz) {
        return createApi(clazz, null, false, mBaseUrl, false);
    }

    /**
     * 所有的createApi最后都应该走这里
     *
     * @param clazz
     * @param factory                指定的convertFactory
     * @param containStringConverter 是否包含string converter
     * @param baseUrl                指定的url
     * @param useCache
     * @param <T>
     * @return
     */
    public <T> T createApi(final Class<T> clazz, Converter.Factory factory, boolean containStringConverter, String baseUrl, boolean useCache) {
        Object api;
        String apiName = clazz.getName();
        if (useCache) {//如果使用缓存，根据clazz名获取缓存中是否有

            api = apis.get(apiName);
            if (api != null)
                return (T) api;
        }

        if (factory == null) {
            factory = gsonConverterFactory;
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(genericClient(baseUrl));

        if (containStringConverter) {
//            builder.addConverterFactory(scalarsConverterFactory);
        }

        builder.addConverterFactory(factory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory);
        if (!TextUtils.isEmpty(baseUrl)) {
            builder.baseUrl(baseUrl);
        } else {
//            builder.baseUrl(BaseApplication.getDefault().getUrlConfig().getBASE_URL());
        }

        api = builder.build().create(clazz);
        if (useCache)//缓存
            apis.put(apiName, api);
        return (T) api;
    }


    protected SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            for (int i = 0; i < certificates.length; i++) {
                InputStream certificate = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
                if (certificate != null) {
                    certificate.close();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return null;
    }

    protected HostnameVerifier getHostnameVerifier(final String[] hostUrls) {
        HostnameVerifier TRUSTED_VERIFIER = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                boolean ret = false;
                L.e("getHostnameVerifier hostUrls len:" + hostUrls.length);
                for (String host : hostUrls) {
                    L.e("getHostnameVerifier host:" + host);
                    L.e("getHostnameVerifier hostname:" + hostname);
                    if (host.equalsIgnoreCase(hostname)) {
                        ret = true;
                    }
                }
                return ret;
            }
        };
        return TRUSTED_VERIFIER;
    }


    /**
     * 生成OKHttpClient 对象
     *
     * @return
     */
    public OkHttpClient genericClient(String baseUrl) {
        try {
            // Create a trust manager that does not validate certificate chains
            if (mX509TrustManager == null)
                mX509TrustManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] chain,
                            String authType) throws CertificateException {
                        L.e("checkClientTrusted");
                    }

                    @Override
                    public void checkServerTrusted(
                            X509Certificate[] chain,
                            String authType) throws CertificateException {
                        if (chain == null) {
                            throw new IllegalArgumentException("Check Server X509Certificate is null");
                        }
                        if (chain.length < 0) {
                            throw new IllegalArgumentException("Check Server X509Certificate is empty");
                        }
                        for (X509Certificate cert : chain) {
                            cert.checkValidity();
                     /*   if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
                            try {
                                cert.verify(cert.getPublicKey());
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (NoSuchProviderException e) {
                                e.printStackTrace();
                            } catch (SignatureException e) {
                                e.printStackTrace();
                            }
                        }*/
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };
            final TrustManager[] trustAllCerts = new TrustManager[]{mX509TrustManager};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();
//            String base_url = BaseApplication.getDefault().getUrlConfig().getBASE_URL();//https://eco.blockchainlock.io/walletapi/
//            base_url = base_url.substring(base_url.indexOf("://") + 3);//eco.blockchainlock.io/walletapi/
//            base_url = base_url.substring(0, base_url.indexOf("/"));//eco.blockchainlock.io
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, mX509TrustManager)
                    .hostnameVerifier(getHostnameVerifier(new String[]{baseUrl}))
//                    .certificatePinner(new CertificatePinner.Builder().add("www.guguaixia.com", "sha256/FsIBR1E5WVnukHQ6yOwNllrDlZt9MYjSxUuYiitx5G4=").build())
                    .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
//                    .addNetworkInterceptor(new TokenHeaderInterceptor())
                    .addInterceptor(new NetworkStateInterceptor(MyApplication.getInstance())) //截获请求，增加是否有网络连接的判断
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl url, List cookies) {
                            CookieUtil.saveCookies(cookies, url.host());
                        }

                        @Override
                        public List loadForRequest(HttpUrl url) {
                            return new ArrayList();
                        }
                    });
            if (ProjectConfig.isDebug()) {//debug 模式输出日志
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//包含header，body数据
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);

                if (mMockDataInterceptor != null) {
                    builder.addInterceptor(mMockDataInterceptor);
                }
            }

            //请求监视者
            if (mResponseMonitor != null)
                builder.addInterceptor(mResponseMonitor);
            if (mResponseReporter != null)
                builder.addInterceptor(mResponseReporter);

            okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public class TokenHeaderInterceptor implements Interceptor { //添加header的token拦截
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            // get token
//            String token = SharePreUtil.getString(Constants.TOKEN, BaseApplication.getDefault(), Constants.TOKEN);
//            Request originalRequest = chain.request();
//            // get new request, add request header
//            Request updateRequest = originalRequest.newBuilder()
//                    .header("token", token)
//                    .build();
//            return chain.proceed(updateRequest);
//        }
//
//    }

    //http返回code在BaseApiCallback中处理，交给实际的onFailure来执行操作
//    public class HttpInterceptor implements Interceptor { //添加异常处理的token拦截
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request originalRequest = chain.request();
//            Response response = chain.proceed(originalRequest);
//            int code = response.code();
//            Log.e("HttpInterceptor", "code:" + code);
//            if (code == 200) {
//
//            } else {
//                Message msg = Message.obtain();
//                if (code >= 400 && code < 500) {
//                    msg.what = 0;
//                } else if (code >= 500 && code < 600) {
//                    msg.what = 1;
//                }
//                msg.arg1 = code;
//                mHandler.sendMessage(msg);
//
//            }
//            return response;
//        }
//    }

//    /**
//     * 公用获取cookies 转成字符
//     *
//     * @param cookies
//     * @return String
//     */
//    public static String getCookies(List<Cookie> cookies) {
//        String str = "";
//        for (int i = 0; i < cookies.size(); i++) {
//            Cookie cookie = cookies.get(i);
//            str += cookie.name() + '=' + cookie.value();
//            if (i < cookies.size() - 1)
//                str += "; ";
//            L.e("ApiCookie", cookie.toString());
////           Date date=new Date( cookie.expiresAt());
////            Calendar c=Calendar.getInstance();
////            c.setTime(date);
//
//            L.e("ApiCookie", "expiresAt:" + System.currentTimeMillis());
//            L.e("ApiCookie", "expiresAt:" + cookie.expiresAt());
//            L.e("ApiCookie", "expiresAt:" + HttpDate.MAX_DATE);
//            L.e("ApiCookie", TimeUtils.getTime(cookie.expiresAt() / 1000));
//
//        }
//        return str;
//    }

    /**
     * 格式化log中的json   用Logger打印
     */
    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            //由于多条请求同时发生的时候，请求是异步的，就会发生这个请求的post和另一个请求的 end post组合了的情况
            //所以把请求和 结果分开
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            } else if (message.startsWith("<-- 200 OK")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化(似乎不需要格式化了)
//            if ((message.startsWith("{") && message.endsWith("}"))
//                    || (message.startsWith("[") && message.endsWith("]"))) {
//                message = JsonFormatter.formatJson(JsonFormatter.decodeUnicode(message));
//            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Logger.d(mMessage.toString());
            } else if (message.startsWith("--> END POST")) {
                Logger.d(mMessage.toString());
            }

        }
    }


    /**
     * Observable<T> 做统一的处理，处理了线程调度、分割返回结果等操作组合了起
     *
     * @param responseObservable
     * @param <T>
     * @return
     */
    public <T> io.reactivex.Observable<T> applySchedulers(io.reactivex.Observable<T> responseObservable) {
        return responseObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())//在线程池中执
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())//在主线程中获得观察结
                .flatMap(new Function<T, io.reactivex.Observable<T>>() {
                    @Override
                    public io.reactivex.Observable<T> apply(@NonNull T t) throws Exception {
                        return flatResponse(t);
                    }
                });

    }

    /**
     * 对网络接口返回的Response进行分割操作 对于json 解析错误以及返回 响应实体为空的情况
     * 主要用来防止 response为null的情况导致的crash
     *
     * @param response
     * @return
     */
    public <T> io.reactivex.Observable<T> flatResponse(final T response) {
        return io.reactivex.Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                Log.d("ApiFactory", "Api flatResponse:call");
                if (response != null) {
                    if (!e.isDisposed()) {
                        e.onNext(response);   //此处onNext 中抛出异常会导致 onError onComplete不会调用  加上tryCatch也没用！
                    }
                } else {//response为null即解析失败或者返回空的数
                    if (!e.isDisposed()) {
                        e.onError(new appframe.network.retrofit.Api.APIException("11", "parse_json_error"));
                    }
                }
                if (!e.isDisposed()) {
                    e.onComplete();//必须有 否则最终的subscribe中的Observer不会调用onComplete
                }
            }
        });
    }

}
//.addInterceptor(new Interceptor() {
//@Override
//public Response intercept(Chain chain) throws IOException {
//        Request.Builder builder = chain.request()
//        .newBuilder()
//        .addHeader("deviceID", RequestHeader.getInstance().getDeviceID())
//        .addHeader("phoneType", RequestHeader.getInstance().getPhoneType())
//        .addHeader("phoneSystem", RequestHeader.getInstance().getPhoneSystem())
//        .addHeader("appVersion", RequestHeader.getInstance().getAppVersion())
//        .addHeader("apiVersion", RequestHeader.getInstance().getApiVersion())
//        .addHeader("language", LanguageUtils.getLanguageSymbol(BaseApplication.getDefault()))
////                                    .addHeader("Cookie", CookieUtil.getCookie())
//        .addHeader("Content-Type", "application/json;charset=utf-8");
//        HttpUrl httpUrl = chain.request().url();
//        String cookie = CookieUtil.getUnExpiredCookies(httpUrl.host());
//        if (!TextUtils.isEmpty(cookie)) {
//        builder.addHeader("Cookie", cookie);
//        }
//
//        //添加额外的key
//        Iterator<String> externalHeaderIterator = mExternalHeaders.keySet().iterator();
//        while (externalHeaderIterator.hasNext()) {
//        String key = externalHeaderIterator.next();
//        builder.addHeader(key, mExternalHeaders.get(key));
//        }
//
////                            Request request = chain.request()
////                                    .newBuilder()
////                                    .addHeader("deviceID", RequestHeader.getInstance().getDeviceID())
////                                    .addHeader("phoneType", RequestHeader.getInstance().getPhoneType())
////                                    .addHeader("phoneSystem", RequestHeader.getInstance().getPhoneSystem())
////                                    .addHeader("appVersion", RequestHeader.getInstance().getAppVersion())
////                                    .addHeader("apiVersion", RequestHeader.getInstance().getApiVersion())
////                                    .addHeader("Cookie", CookieUtil.getCookie())
////                                    .addHeader("Content-Type", "application/json;charset=utf-8")
////                                    .build();
//        Response response = chain.proceed(builder.build());
////                            L.e("intercept", new String(response.body().bytes()));
//        return response;
//        }
//
//        })