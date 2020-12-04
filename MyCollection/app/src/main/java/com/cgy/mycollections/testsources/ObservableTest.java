package com.cgy.mycollections.testsources;


import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/5
 */
public class ObservableTest {
    public static void main(String[] args) {
        testPublishSubject();
//        testBackpressure();
    }

    public static void testZip() {


    }

    /**
     * publishSubject 特性
     * 1.observer只能接收 subscribe之后的onNext
     * 2.onError之后的所有onNext,onComplete都无效
     * 3.onError之后的subscribe的observer能收到onError事件
     * 4.onComplete之后的所有onNext,onError都无效
     * 5.onComplete之后的subscribe的observer能收到onComplete事件
     * 6.publishSubject.observeOn切换观察线程只管后面subscribe的那个observable，
     * 其他没有observeOn的subscribe依然在事件发送线程
     */
    public static void testPublishSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        DisposableObserver<Integer> disposableObserver1 = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NotNull Integer integer) {
                System.out.println("disposableObserver1 onNext:" + integer + Thread.currentThread().toString());
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("disposableObserver1 onError:" + e + Thread.currentThread().toString());
            }

            @Override
            public void onComplete() {
                System.out.println("disposableObserver1 onComplete:");
            }
        };
        DisposableObserver<Integer> disposableObserver2 = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NotNull Integer integer) {
                System.out.println("disposableObserver2 onNext:" + integer + Thread.currentThread().toString());
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("disposableObserver2 onError:" + e + Thread.currentThread().toString());
            }

            @Override
            public void onComplete() {
                System.out.println("disposableObserver2 onComplete:");
            }
        };
        DisposableObserver<Integer> disposableObserver3 = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NotNull Integer integer) {
                System.out.println("disposableObserver3 onNext:" + integer + Thread.currentThread().toString());
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("disposableObserver3 onError:" + e + Thread.currentThread().toString());
            }

            @Override
            public void onComplete() {
                System.out.println("disposableObserver3 onComplete:");
            }
        };
        publishSubject.subscribe(disposableObserver1);
        publishSubject.onNext(11111);
        publishSubject.onNext(22222);
        publishSubject.onError(new NullPointerException("aaa"));
        publishSubject.subscribe(disposableObserver2);
        disposableObserver1.dispose();
        publishSubject.onNext(333333);

        publishSubject.onComplete();
        publishSubject.subscribe(disposableObserver3);
        publishSubject.onNext(4444);
        //以下是log
//        disposableObserver1 onNext:11111
//        disposableObserver1 onNext:22222
//        disposableObserver1 onError:java.lang.NullPointerException: aaa
//        disposableObserver2 onError:java.lang.NullPointerException: aaa
//        disposableObserver3 onError:java.lang.NullPointerException: aaa
    }

    private static void testBackpressure() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NotNull FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        flowable.subscribe(new Subscriber<Integer>() {
            Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                this.s = s;
                s.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("onNext:" + integer);
                s.request(1);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}