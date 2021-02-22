package com.cgy.mycollections.testsources;


import com.cgy.mycollections.functions.mediamanager.images.MediaInfo;
import com.cgy.mycollections.utils.RxUtil;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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


    /**
     * 1.retryWhen 的apply先于 ObservableOnSubscribe.subscribe执行，代表在发送事件之前就执行了，但是只会执行这一次
     * 2.当emitter.onError执行的时候会走errors.takeWhile.test方法，只要返回一直是false就会一直执行
     * 3.Observable.timer可以延迟下次事件发送时间
     * 4.在retry过程中 dispose的话不仅收不到回调，连下次emitter发送都不会再发。
     * 5.在flatMap过程中dispose无效，还是会收到当前的onNext,但是后续的onNext等操作不会再收到，跑还是会跑，即会将emitter 的事件发送完。
     * 6.使用了主线程子线程切换的话，除了Observer中的onNext onError,onComplete,其他 包括retryWhen初始化调用的那次全都是在子线程中执行
     */
    public static void testDelay() {
        System.out.println("testDelay  start");
        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer o) {
                System.out.println("testDelay onNext:" + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("testDelay onError:" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("testDelay onComplete:");
            }
        };
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("testDelay ObservableOnSubscribe 111");
                emitter.onNext(3);
                System.out.println("testDelay ObservableOnSubscribe 222");
                emitter.onNext(4);
                emitter.onError(new NullPointerException("wo cuo le !!"));
            }
        }).flatMap(new Function<Integer, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(@NonNull Integer o) throws Exception {
                System.out.println("testDelay flatMap:" + o);
//                disposableObserver.dispose();
                return Observable.just(o + 2);
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> errors) throws Exception {
                System.out.println("testDelay retryWhen:");
                AtomicInteger counter = new AtomicInteger();
                return errors.takeWhile(new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        System.out.println("testDelay takeWhile:" + counter.get());
                        if (counter.get() == 2) {
                            disposableObserver.dispose();
                        }
                        return counter.getAndIncrement() != 4;
                    }
                }).flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        System.out.println("testDelay delay retry by " + counter.get() + " second(s)");
                        return Observable.timer(counter.get() + 5, TimeUnit.SECONDS);
                    }
                });
            }
        }).compose(RxUtil.applySchedulersJobUI()).subscribe(disposableObserver);
    }
}