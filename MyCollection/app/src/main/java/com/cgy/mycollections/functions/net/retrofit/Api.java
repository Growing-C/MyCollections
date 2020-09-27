package com.cgy.mycollections.functions.net.retrofit;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.net.retrofit.cache.CacheStrategy;
import com.cgy.mycollections.functions.net.retrofit.cache.NetworkHelper;
import com.cgy.mycollections.functions.net.retrofit.responsemonitor.IResponseReporter;
import com.cgy.mycollections.functions.net.retrofit.responsemonitor.ResponseMonitor;

import appframe.utils.L;

import com.cgy.mycollections.utils.SystemUtil;
import com.cgy.mycollections.utils.dataparse.JsonFormatter;

import org.jetbrains.annotations.NotNull;

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
import java.util.UUID;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.Cookie;
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

public final class Api {
    public Map<String, Object> apis = new HashMap<>();//api缓存
    private Map<String, Object> proxyApis = new HashMap<>();//proxy api缓存
    private static OkHttpClient okHttpClient;
    private Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    //    private Converter.Factory scalarsConverterFactory = ScalarsConverterFactory.create();
    private CallAdapter.Factory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static int TIME_OUT_SECONDS = 20;//默认超时时间 暂时设置为20，js那边有个接口超过10秒

    private static volatile Api mApi;
//    private X509TrustManager mX509TrustManager;

    //    private Certificate mCa;
    private String mBaseUrl;

    private Api() {
    }

    //最好外部使用到Api的时候不要继承了，直接用getInstance...这样可以有多个wrapper，同时只有一个Api实例
    public static Api getInstance() {
        if (mApi == null) {
            synchronized (Api.class) {
                if (mApi == null) {
                    mApi = new Api();
                }
            }
        }
        return mApi;
    }

    private Interceptor mMockDataInterceptor;//仅测试用
    private Interceptor mResponseMonitor;//额外的结果监控者
    private Interceptor mResponseReporter;//用于监控response错误 上报

    private List<Interceptor> mExtNetworkInterceptorList;// 额外的networkInterceptor
    private List<Interceptor> mExtInterceptorList;//额外的interceptor
    private Cache cache;

    /**
     * 外部设置的header
     */
    public Map<String, String> mExternalHeaders = new HashMap<String, String>();

    public void setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

//    /**
//     * 替换gsonConverterFactory,
//     *
//     * @param gsonConverterFactory
//     */
//    public void setGsonConverterFactory(Converter.Factory gsonConverterFactory) {
//        this.gsonConverterFactory = gsonConverterFactory;
//    }

    public void setNetCache(Cache cache) {
        this.cache = cache;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void enableCache(int cacheExpireTimeInSecond, CacheStrategy strategy) {
        NetworkHelper.getInstance().enableCache(cacheExpireTimeInSecond, strategy);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void disableCache() {
        NetworkHelper.getInstance().disableCache();
    }

    public void addNetworkInterceptor(Interceptor networkInterceptor) {
        if (mExtNetworkInterceptorList == null) {
            mExtNetworkInterceptorList = new ArrayList<>();
        }
        if (networkInterceptor != null)
            mExtNetworkInterceptorList.add(networkInterceptor);
    }

    public void addInterceptor(Interceptor interceptor) {
        if (mExtInterceptorList == null) {
            mExtInterceptorList = new ArrayList<>();
        }
        if (interceptor != null)
            mExtInterceptorList.add(interceptor);
    }

    /**
     * 删除所有的 interceptor 包括mExtInterceptorList和mExtNetworkInterceptorList
     *
     * @param interceptor
     */
    public void removeInterceptor(Interceptor interceptor) {
        if (mExtInterceptorList != null) {
            mExtInterceptorList.remove(interceptor);
        }
        if (mExtNetworkInterceptorList != null) {
            mExtNetworkInterceptorList.remove(interceptor);
        }
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
     * 获取某个请求头
     *
     * @param key
     * @return
     */
    public String getHeader(String key) {
        if (TextUtils.isEmpty(key))
            return "";//空的不操作

        return mExternalHeaders.get(key);
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
     * <p>
     * param apiNamePrefix          缓存用的api名称前缀（防止clazz一样的时候缓存出问题）
     *
     * @param clazz
     * @param factory                指定的convertFactory
     * @param containStringConverter 是否包含string converter
     * @param baseUrl                指定的url
     * @param useCache
     * @param <T>
     * @return
     */
    public <T> T createApi(final Class<T> clazz, Converter.Factory factory,
                           boolean containStringConverter,
                           @NonNull String baseUrl, boolean useCache) {
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

//        if (containStringConverter) {
//            builder.addConverterFactory(scalarsConverterFactory);
//        }

        builder.addConverterFactory(factory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory);
        builder.baseUrl(baseUrl);

        api = builder.build().create(clazz);
        if (useCache)//缓存
            apis.put(apiName, api);
        return (T) api;
    }

    /**
     * 由于代理在此处创建的话有点麻烦，改成外部创建保存到这里
     *
     * @param key
     * @param api
     */
    public void putProxyApi(String key, Object api) {
        if (TextUtils.isEmpty(key) || api == null)
            return;
        proxyApis.put(key, api);
    }

    public synchronized <T> T getProxyApi(String key, Class<T> clazz) {
        if (TextUtils.isEmpty(key))
            return null;
        Object api = proxyApis.get(key);

        if (clazz != null && clazz.isInstance(api)) {
            return (T) api;
        }

        return null;
    }

    public synchronized void clearAllProxyApi() {
        proxyApis.clear();
    }

    public synchronized void clearAllApi() {
        apis.clear();
        proxyApis.clear();
    }


    protected HostnameVerifier getHostnameVerifier(final String[] hostUrls) {
        return (hostname, session) -> {
            boolean ret = false;
            for (String host : hostUrls) {
                if (host.equalsIgnoreCase(hostname)) {
                    ret = true;
                }
            }
            return ret;
        };
    }


    /**
     * 生成OKHttpClient 对象
     *
     * @return
     */
    public OkHttpClient genericClient(String baseUrl) {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .sslSocketFactory(sslSocketFactory, mX509TrustManager) //新注释掉了，不需要设置这些东西，系统会自动校验证书
                    .hostnameVerifier(getHostnameVerifier(new String[]{baseUrl}))
//                    .certificatePinner(new CertificatePinner.Builder().add("www.guguaixia.com", "sha256/FsIBR1E5WVnukHQ6yOwNllrDlZt9MYjSxUuYiitx5G4=").build())
                    .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
//                    .addNetworkInterceptor(new TokenHeaderInterceptor())
//                    .addInterceptor(new NetworkStateInterceptor(BaseApplication.getDefault())) //截获请求，增加是否有网络连接的判断
//                    .addInterceptor(new HttpInterceptor())
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
                            CookieUtil.saveCookies(cookies, url.host());
//                            String c = getCookies(cookies);
//                            CookieUtil.setCookie(c);
                        }

                        @NotNull
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            return new ArrayList<Cookie>();
                        }
                    });
            if (ProjectConfig.isDebug()) {//debug 模式输出日志
                // Log信息拦截器

                //打印log 时使用的logger有个问题，是一整条打印的，可能会有很多行，有点难看
                // 可以用这个api 'com.orhanobut:logger:2.1.1' 来定制化 每行都可以设置一个tag
                MyLoggingInterceptor loggingInterceptor = new MyLoggingInterceptor(new HttpLogger());
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

            if (mExtNetworkInterceptorList != null) {
                for (Interceptor networkInterceptor :
                        mExtNetworkInterceptorList) {
                    builder.addNetworkInterceptor(networkInterceptor);
                }
            }
            if (mExtInterceptorList != null) {
                for (Interceptor interceptor :
                        mExtInterceptorList) {
                    builder.addInterceptor(interceptor);
                }
            }

            //设置缓存
            if (cache != null) {
                builder.cache(cache);
            } else {//没有启用缓存的时候还是使用 有无网络的判断逻辑
                builder.addInterceptor(new NetworkStateInterceptor(MyApplication.getInstance())); //截获请求，增加是否有网络连接的判断
            }

            okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 格式化log中的json   用Logger打印
     */
    protected static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        UUID uuid = UUID.randomUUID();

        /**
         * 手动设置start 防止出bug的时候有问题
         */
        public synchronized void setStart() {
            mMessage.setLength(0);
        }

        @Override
        public void log(String message) {
            // 请求或者响应开始
            //由于多条请求同时发生的时候，请求是异步的，就会发生这个请求的post和另一个请求的 end post组合了的情况
            //所以把请求和 结果分开
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            } else if (message.startsWith("<-- 200")) {
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
                L.e("EAGLE_BASE_HttpLogger", "HttpLogger id:" + uuid.toString() + "-->message" + message);
                realLogOutPut(mMessage.toString());
//                Logger.d(mMessage.toString());
            } else if (message.startsWith("--> END POST")) {
                L.e("EAGLE_BASE_HttpLogger", "HttpLogger id:" + uuid.toString() + "-->message" + message);
//                Logger.d(mMessage.toString());
                realLogOutPut(mMessage.toString());
            }

        }

        /**
         * 真实的log打印
         *
         * @param msg
         */
        private void realLogOutPut(String msg) {
//            if (formatLogJson) {
            try {
                msg = JsonFormatter.formatJson(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            }
            L.d("HttpLog", msg);
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
                L.d("ApiFactory", "Api flatResponse:call");
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
/*以下是新修改注释掉的。HTTPS不需要刻意去校验证书*/
//            X509TrustManager mX509TrustManager;
//            KeyStore keyStore;
//            TrustManagerFactory tmf;
//            SSLContext sslContext;
//            mX509TrustManager = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(
//                        X509Certificate[] chain,
//                        String authType) throws CertificateException {
//                    L.e("checkClientTrusted");
//                }
//
//                @Override
//                public void checkServerTrusted(
//                        X509Certificate[] chain,
//                        String authType) throws CertificateException {
//                    if (chain == null) {
//                        throw new IllegalArgumentException("Check Server X509Certificate is null");
//                    }
//                    if (chain.length < 0) {
//                        throw new IllegalArgumentException("Check Server X509Certificate is empty");
//                    }
//                    for (X509Certificate cert : chain) {
//                        cert.checkValidity();
////                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
//                        try {
//                            cert.verify(cert.getPublicKey());
//                        } catch (NoSuchAlgorithmException e) {
//                            e.printStackTrace();
//                        } catch (InvalidKeyException e) {
//                            e.printStackTrace();
//                        } catch (NoSuchProviderException e) {
//                            e.printStackTrace();
//                        } catch (SignatureException e) {
//                            e.printStackTrace();
//                        }
////                        }
//                    }
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[0];
//                }
//            };
//            final TrustManager[] trustAllCerts = new TrustManager[]{mX509TrustManager};
/*以上是新修改注释掉的。HTTPS不需要刻意去校验证书*/

/*
 * 以下是修改的，自签名证书可以这样写
 * */
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            InputStream caInput = BaseApplication.getDefault().getAssets().open("cert.cer");
//            try {
//                mCa = cf.generateCertificate(caInput);
//                System.out.println("ca=" + ((X509Certificate) mCa).getSubjectDN());
//            } finally {
//                caInput.close();
//            }
//            String keyStoreType = KeyStore.getDefaultType();
//            keyStore = KeyStore.getInstance(keyStoreType);
//            keyStore.load(null, null);
//            keyStore.setCertificateEntry("ca", mCa);
//
//            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//            tmf.init(keyStore);
//
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, tmf.getTrustManagers(), null);

//            sslContext.init(null, trustAllCerts, new SecureRandom());
/*
 * 以上是修改的，自签名证书可以这样写
 * */
//             sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts,
//                    new SecureRandom());
//            final SSLSocketFactory sslSocketFactory = sslContext
//                    .getSocketFactory();

// Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts,
//                    new SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext
//                    .getSocketFactory();


//    protected SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {
//        if (context == null) {
//            throw new NullPointerException("context == null");
//        }
//        CertificateFactory certificateFactory;
//        try {
//            certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null, null);
//            for (int i = 0; i < certificates.length; i++) {
//                InputStream certificate = context.getResources().openRawResource(certificates[i]);
//                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
//                if (certificate != null) {
//                    certificate.close();
//                }
//            }
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//            return sslContext.getSocketFactory();
//        } catch (Exception e) {
//
//        }
//        return null;
//    }


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