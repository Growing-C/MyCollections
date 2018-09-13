package com.cgy.mycollections;

import android.util.Log;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        assertEquals(4, 2 + 2);
    }

//以下为Observable 相关测试
//    public void test() {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
//                Log.e("OBSER", "ObservableOnSubscribe");
//                observableEmitter.onNext("111111");
//                observableEmitter.onNext("22222");
//                observableEmitter.onNext("33333");
//                observableEmitter.onComplete();
//                Log.e("OBSER", "  subscribe isDisposed?" + (observableEmitter.isDisposed()));
//            }
//        }).subscribeOn(Schedulers.io())//在线程池中执
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Function<String, ObservableSource<Integer>>() {
//                    @Override
//                    public ObservableSource<Integer> apply(String s) throws Exception {
//                        Log.e("OBSER", "flatMap apply:" + s);
////                        if (s.equals("22222"))
////                            return flatResponse(null);
//                        return flatResponse(Integer.parseInt(s));
//                    }
//                })
//                .subscribe(new BaseApiCallback<Integer>() {
//                    @Override
//                    protected void safeOnNext(Integer model) {
//                        Log.e("OBSER", "DisposableObserver onNext:" + model);
//                        try {
//                            throw new NullPointerException("test exception");
//                        } catch (Exception e) {
//                            onError(e);
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        Log.e("OBSER", "DisposableObserver onFailure:" + msg);
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                        Log.e("OBSER", "DisposableObserver onComplete:");
//                    }
//                });
//    }
//
//    public <T> io.reactivex.Observable<T> flatResponse(final T response) {
//        return io.reactivex.Observable.create(new ObservableOnSubscribe<T>() {
//            @Override
//            public void subscribe(ObservableEmitter<T> e) throws Exception {
//                Log.e("OBSER", "1 flatResponse subscribe isDisposed?" + (e.isDisposed()));
////                throw new NullPointerException("stupid");
////                e.onNext("1  flatResponse:" + response);
////                e.onError(new Throwable("lalallallalalal"));
////                Log.e("OBSER", "2 flatResponse subscribe isDisposed?" + (e.isDisposed()));
////                e.onComplete();
//
////                try {
//                if (response != null) {
//                    if (!e.isDisposed()) {//没有被处
//                        e.onNext(response);
//                    }
//                } else {//response为null即解析失败或者返回空的数
//                    if (!e.isDisposed()) {
//                        e.onError(new ApiException(11, "解析json错误或者服务器返回空的json"));
//                    }
//                }
////                } catch (Exception ex) {
////                    throw ex;
////                }
//                Log.e("OBSER", "2 flatResponse subscribe isDisposed?" + (e.isDisposed()));
//                if (!e.isDisposed()) {
//                    e.onComplete();
//                }
//                Log.e("OBSER", "3 flatResponse subscribe isDisposed?" + (e.isDisposed()));
//            }
//        });

//                .onExceptionResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
//            @Override
//            public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
//                return null;
//            }
//        });

//                .onErrorReturn(new Function<Throwable, T>() {
//            @Override
//            public T apply(Throwable throwable) throws Exception {
//                Log.e("OBSER", "  flatResponse   onErrorReturn -->" + throwable.getMessage());
//                return response;
//            }
//        });
}