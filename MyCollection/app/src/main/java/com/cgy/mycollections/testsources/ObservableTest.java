package com.cgy.mycollections.testsources;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/5
 */
public class ObservableTest {
    public static void main(String[] args) {
//        testPublishSubject();
        testBackpressure();
    }

    public static void testZip() {


    }

    public static void testPublishSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        DisposableObserver<Integer> disposableObserver1 = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                System.out.println("disposableObserver1 onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("disposableObserver1 onError:" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("disposableObserver1 onComplete:");
            }
        };
        DisposableObserver<Integer> disposableObserver2 = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                System.out.println("disposableObserver2 onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("disposableObserver2 onError:" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("disposableObserver2 onComplete:");
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
    }

    private static void testBackpressure() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
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
                this.s=s;
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