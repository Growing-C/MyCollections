package appframe.network.retrofit;


import java.io.IOException;
import java.lang.reflect.Proxy;

import appframe.ProjectConfig;
import appframe.app.MyApplication;
import appframe.fortest.MockDataInterceptor;
import appframe.network.retrofit.converter.GsonConverterFactory;
import appframe.network.retrofit.network.NetworkStateInterceptor;
import appframe.network.retrofit.proxy.ProxyHandler;
import appframe.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by RB-cgy on 2016/9/28.
 * 此类中的方法除了ApiWrapper中其他地方都不应该使用
 *
 * @hide
 */
public class Api {
    private static ApiService mService;
    private static Retrofit mRetrofit;//全局使用一个retrofit对象

    private static ApiService mProxyService;//使用代理的apiService

    /**
     * 获得接口调用类，此处参数baseUrl可以指定使用的默认地址（方便测试）
     *
     * @param baseUrl
     * @return
     */
    public ApiService getService(String baseUrl) {
        if (mService == null) {
            mService = getRetrofit(baseUrl).create(ApiService.class);
        }
        return mService;
    }

    public ApiService getProxy(@android.support.annotation.NonNull ApiService apiService) {
        if (mProxyService == null) {
            mProxyService = (ApiService) Proxy.newProxyInstance(ApiService.class.getClassLoader(), new Class<?>[]{ApiService.class}, new ProxyHandler(apiService));
        }
        return mProxyService;
    }

    /**
     * 拦截器  给所有的请求添加header
     */
    private static Interceptor mHeaderInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(request);
        }
    };

    private static Retrofit getRetrofit(String baseUrl) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new NetworkStateInterceptor(MyApplication.getInstance()));//截获请求，增加是否有网络连接的判断
            builder.addInterceptor(mHeaderInterceptor);//添加请求头
            if (ProjectConfig.isDebugMode()) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//包含header，body数据
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
                builder.addInterceptor(new MockDataInterceptor());//TODO：伪造服务端数据的拦截器，开发完成后删除
            }
//            builder.connectTimeout(20, TimeUnit.SECONDS);//连接超时时间
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())//使用自己重写的GsonConverterFactory来处理token过期事件
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 对 Observable<T> 做统一的处理，处理了线程调度、分割返回结果等操作组合了起来
     *
     * @param responseObservable
     * @param <T>
     * @return
     */
    protected <T> Observable<T> applySchedulers(Observable<T> responseObservable) {
        return responseObservable.subscribeOn(Schedulers.io())//在线程池中执行
                .observeOn(AndroidSchedulers.mainThread())//在主线程中获得观察结果
                .flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(@NonNull T t) throws Exception {
                        return flatResponse(t);
                    }
                });

//        return null;
//        return responseObservable.subscribeOn(Schedulers.io())//在线程中执行
//                .observeOn(AndroidSchedulers.mainThread())//在主线程中获得观察结果
//                .flatMap(new Func1<T, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(T tResponse) {
//                        return flatResponse(tResponse);
//                    }
//                });
    }

    /**
     * 对网络接口返回的Response进行分割操作 对于json 解析错误以及返回的 响应实体为空的情况
     *
     * @param response
     * @return
     */
    public <T> Observable<T> flatResponse(final T response) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                //服务器发生错误貌似不会走这里，为啥哩？
                LogUtils.d("Api flatResponse:call");
                if (response != null) {
                    if (!e.isDisposed()) {//没有被处理
//                        System.out.println("flatResponse 0");
                        e.onNext(response);
                    }
                } else {//response为null即解析失败或者返回空的数据
                    if (!e.isDisposed()) {
                        e.onError(new APIException("自定义异常类型", "解析json错误或者服务器返回空的json"));
                    }
                }
//                System.out.println("flatResponse 1");
                if (!e.isDisposed()) {
//                    System.out.println("flatResponse 2");
                    e.onComplete();
                }
            }
        });
//        return Observable.create(new Observable.OnSubscribe<T>() {
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//                //服务器发生错误貌似不会走这里，为啥哩？
//                LogUtils.d("Api flatResponse:call");
//                if (response != null) {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onNext(response);
//                    }
//                } else {//response为null即解析失败或者返回空的数据
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onError(new APIException("自定义异常类型", "解析json错误或者服务器返回空的json"));
//                    }
//                }
//                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onCompleted();
//                }
//            }
//        });
    }

    /**
     *
     */
    public static class APIException extends RuntimeException {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

    }
}