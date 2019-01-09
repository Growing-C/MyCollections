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
  
        double pi = 3.1415927;//圆周率
//取一位整数
        System.out.println(new DecimalFormat("0").format(pi));//3
//取一位整数和两位小数
        System.out.println(new DecimalFormat("0.00").format(pi));//3.14
//取两位整数和三位小数，整数不足部分以0填补。
        System.out.println(new DecimalFormat("00.000").format(pi));// 03.142
//取所有整数部分
        System.out.println(new DecimalFormat("#").format(pi));//3
//以百分比方式计数，并取两位小数
        System.out.println(new DecimalFormat("#.##%").format(pi));//314.16%

        long c = 299792458;//光速
//显示为科学计数法，并取五位小数
        System.out.println(new DecimalFormat("#.#####E0").format(c)); //2.99792E8
//显示为两位整数的科学计数法，并取四位小数
        System.out.println(new DecimalFormat("00.####E0").format(c)); //29.9792E7
//每三位以逗号进行分隔。
        System.out.println(new DecimalFormat(",###").format(c)); //299,792,458
        // 将格式嵌入文本
        System.out.println(new DecimalFormat("光速大小为每秒,###米。").format(c));
   
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