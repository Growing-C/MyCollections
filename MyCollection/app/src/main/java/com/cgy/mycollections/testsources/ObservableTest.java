package com.cgy.mycollections.testsources;


import com.cgy.mycollections.utils.RxUtil;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
     * 7.errors.take(4) 代表了重试几次，4则只会重试4次
     * 8.多次emit 的时候如果flatmap里也是create 且里面有onerror，第一个里有多个onnext然后onerror，会导致崩溃
     * 9.只要每个 emitter发送的事件 只有一个就没有8 的问题，不管是onError还是onNext
     * 10.两个含有retry逻辑的observable通过flatmap拼接 retry执行没有问题，按照预期执行了各自的retry逻辑，任何时候dispose都会终止整个流程
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

        AtomicInteger counter1 = new AtomicInteger();
        Observable<Integer> ob1 =
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                        System.out.println("11111 testDelay ObservableOnSubscribe 111");
                        emitter.onNext(3);
                        System.out.println("11111 testDelay ObservableOnSubscribe 222");
//                        emitter.onNext(4);//flatMap中onerror之后会重试，此onnext会执行但是不会走到flatMap中也不会继续往下，等于无效
//                emitter.onError(new NullPointerException("wo cuo le !!"));//flatmap中调用了onError此处就不能再调用，不然会崩溃
                    }
                }).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Integer o) throws Exception {
                        System.out.println("11111 testDelay flatMap:" + o);
//                disposableObserver.dispose();
//                return Observable.just(o + 2);
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                                if (counter1.get() == 5) {
                                    System.out.println("11111 testDelay ObservableOnSubscribe 333");
                                    emitter.onNext(o + 10);
                                } else {
                                    System.out.println("11111 testDelay ObservableOnSubscribe 444");
                                    emitter.onError(new NullPointerException("111 wo cuo le !!"));
                                }
                            }
                        });
                    }
                }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> errors) throws Exception {
                        System.out.println("11111 testDelay retryWhen:");
                        return errors.take(Integer.MAX_VALUE).flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                counter1.incrementAndGet();
                                System.out.println("11111 testDelay delay retry by " + counter1.get() + " second(s)");
                                if (counter1.get() == 8) {
                                    disposableObserver.dispose();
                                }
                                return Observable.timer(counter1.get(), TimeUnit.SECONDS);
                            }
                        });
//                return errors.takeWhile(new Predicate<Throwable>() {
//                    @Override
//                    public boolean test(@NonNull Throwable throwable) throws Exception {
//                        System.out.println("testDelay takeWhile:" + counter.get());
//                        if (counter.get() == 2) {
//                            disposableObserver.dispose();
//                        }
//                        return counter.getAndIncrement() != 4;
//                    }
//                }).flatMap(new Function<Throwable, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
//                        System.out.println("testDelay delay retry by " + counter.get() + " second(s)");
//                        return Observable.timer(counter.get() + 5, TimeUnit.SECONDS);
//                    }
//                });
                    }
                });

        AtomicInteger counter2 = new AtomicInteger();
        Observable<Integer> ob2 =
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                        System.out.println("22222 testDelay ObservableOnSubscribe 111");
                        emitter.onNext(13);
                        System.out.println("22222 testDelay ObservableOnSubscribe 222");
//                        emitter.onNext(4);//flatMap中onerror之后会重试，此onnext会执行但是不会走到flatMap中也不会继续往下，等于无效
//                emitter.onError(new NullPointerException("wo cuo le !!"));//flatmap中调用了onError此处就不能再调用，不然会崩溃
                    }
                }).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Integer o) throws Exception {
                        System.out.println("22222 testDelay flatMap:" + o);
//                disposableObserver.dispose();
//                return Observable.just(o + 2);
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                                if (counter2.get() == 4) {
                                    System.out.println("22222 testDelay ObservableOnSubscribe 333");
                                    emitter.onNext(o + 10);
                                } else {
                                    System.out.println("22222 testDelay ObservableOnSubscribe 444");
                                    emitter.onError(new NullPointerException("2222  wo cuo le !!"));
                                }
                            }
                        });
                    }
                }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> errors) throws Exception {
                        System.out.println("testDelay retryWhen:");
                        return errors.take(Integer.MAX_VALUE).flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                counter2.incrementAndGet();
                                System.out.println("testDelay delay retry by " + counter2.get() + " second(s)");
                                if (counter2.get() == 8) {
                                    disposableObserver.dispose();
                                }
                                return Observable.timer(counter2.get(), TimeUnit.SECONDS);
                            }
                        });
                    }
                });

        ob1.flatMap(new Function<Integer, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(@NonNull Integer o) throws Exception {
                return ob2;
            }
        }).compose(RxUtil.applySchedulersJobUI()).subscribe(disposableObserver);
    }
}