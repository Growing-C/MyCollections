package appframe.network.retrofit.proxy;

import android.text.TextUtils;


import com.witon.mylibrary.model.LoginInfoBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import appframe.network.request.RequestParams;
import appframe.network.retrofit.Api;
import appframe.network.retrofit.ApiService;
import appframe.network.retrofit.ApiWrapper;
import appframe.network.retrofit.callback.MyCallBack;
import appframe.utils.LogUtils;
import appframe.utils.SharedPreferencesUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Proxy和converter包下的类都是为token刷新服务的，如果改变token刷新机制可以删除这两个包
 * 该类用来处理token过期事件，{@link Api#getProxy(ApiService)} ()}使用这个方法调用的接口会自动处理token过期事件
 */
public class ProxyHandler implements InvocationHandler {

    private static long tokenChangedTime = 0;

    private Object mProxyObject;//ApiService

    public ProxyHandler(Object proxyObject) {
        mProxyObject = proxyObject;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final InvokeArguments invokeArguments = new InvokeArguments();
        return Observable.just(true).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Object o) throws Exception {
                try {
                    try {
                        if (invokeArguments.isTokenNeedRefresh) {
                            updateMethodToken(invokeArguments, args);
                        }
                        LogUtils.d("ProxyHandler invoke ----------------------------------------flatMap");
                        return (Observable<?>) method.invoke(mProxyObject, args);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof IllegalArgumentException && throwable.getMessage().equals("token_timeout")) {
                            //token_过期处理
                            LogUtils.d("ProxyHandler retryWhen --->flatMap ----->token_timeout");
                            return refreshTokenWhenTokenInvalid(invokeArguments);
                        }
                        return Observable.error(throwable);//使用error避免进入死循环
                    }
                });
            }
        });
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
    private Observable<?> refreshTokenWhenTokenInvalid(final InvokeArguments invokeArguments) {
        synchronized (ProxyHandler.class) {
            invokeArguments.retryCount++;
            if (invokeArguments.retryCount > 3) {//一般来说不可能刷新了还会返回token_timeout,这个限制主要是为测试时一直返回token_timeout所做
                return Observable.error(new Api.APIException("refresh_token_error",
                        "token refresh for " + invokeArguments.retryCount + " times, but still time out,anything wrong?"));
            }

            invokeArguments.refreshTokenError = null;
            System.out.println("refreshTokenWhenTokenInvalid thread:" + Thread.currentThread().getName() + "--id:" + Thread.currentThread().getId());
            // call the refresh token api.
//            String loginName = SharedPreferencesUtils.getInstance(MyApplication.getInstance()).getString(Constants.USER_PHONE, "");
//            String loginPwd = SharedPreferencesUtils.getInstance(MyApplication.getInstance()).getString(Constants.USER_PASSWORD, "");

//            ApiWrapper.getInstance().syncLogin(loginName, loginPwd, "")
//                    .subscribe(new MyCallBack<LoginInfoBean>() {
//                        @Override
//                        public void onSuccess(LoginInfoBean model) {
//                            System.out.println("refresh token ->login thread:" + Thread.currentThread().getName() + "--id:" + Thread.currentThread().getId());
//                            if (model != null) {//token刷新操作在MyCallBack中
//                                invokeArguments.isTokenNeedRefresh = true;
//                                tokenChangedTime = new Date().getTime();
//                                System.out.println("Refresh token success, time = " + tokenChangedTime);
//                            }
//                        }
//                        @Override
//                        public void onError(Throwable e) {
//                            System.out.println("refresh token ->login onError");
//                            invokeArguments.refreshTokenError = e;
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg) {
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//
//                        }
//                    });
            System.out.println("token refresh logic ok-------------------------------------");
            if (invokeArguments.refreshTokenError != null) {
                return Observable.error(invokeArguments.refreshTokenError);
            } else {
                return Observable.just(true);
            }
        }
    }

    /**
     * Update the token of the args in the method.
     * <p>
     * PS： 因为这里使用的是 GET 请求，所以这里就需要对 Query 的参数名称为 token 的方法。
     * 若是 POST 请求，或者使用 Body ，自行替换。因为 参数数组已经知道，进行遍历找到相应的值，进行替换即可（更新为新的 token 值）。
     */
    private void updateMethodToken(InvokeArguments invokeArguments, Object[] args) {
//        if (invokeArguments.isTokenNeedRefresh && !TextUtils.isEmpty(MyApplication.getInstance().getToken())) {
//            for (int i = 0, len = args.length; i < len; i++) {
//                if (args[i] instanceof RequestParams) {
//                    System.out.println("updateMethodToken changed token:" + MyApplication.getInstance().getToken());
//                    ((RequestParams) args[i]).requestToken = MyApplication.getInstance().getToken();
//                }
//            }
//            invokeArguments.isTokenNeedRefresh = false;
//        }
    }

    /**
     * ProxyHandler中 最好不要用成员变量，会有多个接口使用这个handler的invoke方法，为了防止不同接口
     * 调用过程中修改成员变量的情况发生，提取出invoke方法中需要用的变量为一个类，每次使用的时候创建
     */
    class InvokeArguments {
        Throwable refreshTokenError = null;//token刷新错误
        boolean isTokenNeedRefresh = false;//token是否需要刷新,默认是false
        int retryCount = 0;//重试次数
    }
}