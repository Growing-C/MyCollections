package appframe.network.retrofit;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import appframe.ProjectConfig;
import appframe.network.retrofit.converter.GsonConverterFactory;
import appframe.network.retrofit.network.NetworkStateInterceptor;
import appframe.network.retrofit.proxy.ProxyHandler;
import appframe.utils.JsonFormatter;
import appframe.utils.L;
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
    public ApiService getService(String baseUrl, Context application) {
        if (mService == null) {
            mService = getRetrofit(baseUrl, application).create(ApiService.class);
        }
        return mService;
    }

    public ApiService getProxy(@androidx.annotation.NonNull ApiService apiService) {
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

    private static Retrofit getRetrofit(String baseUrl, Context context) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new NetworkStateInterceptor(context));//截获请求，增加是否有网络连接的判断
            builder.addInterceptor(mHeaderInterceptor);//添加请求头
            if (ProjectConfig.isDebugMode()) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//包含header，body数据
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
//                builder.addInterceptor(new MockDataInterceptor());//TODO：伪造服务端数据的拦截器，开发完成后删除
            }

            Gson gson = new GsonBuilder()//会有以下的两个typeAdapter都是因为接口 sb一样 的把  list和boolean类型 为空的时候传的""! 不敢想象 菜鸟的不行
                    .registerTypeHierarchyAdapter(List.class, new JsonDeserializer<List<?>>() {
                        @Override
                        public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                            System.out.println(json.isJsonArray() + "deserialize----->" + typeOfT);
                            if (json.isJsonArray()) {
                                //这里要自己负责解析了
                                JsonArray array = json.getAsJsonArray();
                                Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                                List list = new ArrayList<>();
                                for (int i = 0; i < array.size(); i++) {
                                    JsonElement element = array.get(i);
                                    Object item = context.deserialize(element, itemType);
                                    list.add(item);
                                }
                                return list;
                            } else {
                                //和接口类型不符，返回空List
                                return Collections.EMPTY_LIST;
                            }
                        }
                    })
                    .registerTypeHierarchyAdapter(Boolean.class, new JsonDeserializer<Boolean>() {
                        @Override
                        public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            System.out.println("registerTypeHierarchyAdapter Boolean:-->toString:" + json.toString());

                            //由于json.toString 空字符串时会把两个引号带进来所以 只有这样了
                            if (TextUtils.isEmpty(json.toString()) || "\"\"".equals(json.toString())) {//后台在这个接口qryOutpatientPayRecords的has_detail这个boolean字段有可能传空字符串
                                return null;
                            } else
                                return json.getAsBoolean();
                        }
                    })
                    .create();

            builder.connectTimeout(20, TimeUnit.SECONDS);//连接超时时间
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))//使用自己重写的GsonConverterFactory来处理token过期事件
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

    public static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonFormatter.formatJson(JsonFormatter.decodeUnicode(message));
            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                L.d(mMessage.toString());
            }

        }
    }
}
