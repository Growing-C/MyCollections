package com.cgy.mycollections.testsources;


import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/5
 */
public class ObservableTest {
    public static void main(String[] args) {
        testPublishSubject();
    }

    public static void testZip(){


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
}
