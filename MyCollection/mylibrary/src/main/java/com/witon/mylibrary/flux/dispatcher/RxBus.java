package com.witon.mylibrary.flux.dispatcher;



import com.witon.mylibrary.flux.annotation.Subscribe;

import org.reactivestreams.Publisher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 事件总线,解耦不同的类之间的调用关系
 * Created by RB-cgy on 2017/2/17.
 */
public class RxBus {
    private Subject mSubject;

    protected Map<Object, Disposable> mSubscriptions = new HashMap<>();//订阅的类和Disposable的map

    private RxBus() {
        mSubject = PublishSubject.create().toSerialized();//创建线程安全的subject对象
    }

    /**
     * 不再使用单例
     *
     * @return
     */
    public static RxBus newInstance() {
        return new RxBus();
    }

    /**
     * 注册所有接受事件的类
     *
     * @param obj
     */
    public void register(@NonNull final Object obj) {
        Flowable.just(obj).filter(new Predicate() {
            @Override
            public boolean test(@NonNull Object o) throws Exception {
                return !mSubscriptions.containsKey(o);//判断订阅者是否已经订阅过，如果为false则不往下
            }
        }).flatMap(new Function<Object, Publisher<Method>>() {
            @Override
            public Publisher<Method> apply(@NonNull Object o) throws Exception {
//                System.out.println(obj.getClass().getSimpleName() + "--->1:register");
                return Flowable.fromArray(o.getClass().getDeclaredMethods());//获取类中的所有方法
            }
        }).map(new Function<Method, Method>() {
            @Override
            public Method apply(@NonNull Method method) throws Exception {
                method.setAccessible(true);
                return method;
            }
        }).filter(new Predicate<Method>() {
            @Override
            public boolean test(@NonNull Method m) throws Exception {
                return m.isAnnotationPresent(Subscribe.class);//筛选出@Subscribe方法
            }
        }).subscribe(new Consumer<Method>() {
            @Override
            public void accept(@NonNull Method m) throws Exception {
                addSubscription(m, obj);//添加订阅
            }
        });
    }

    /**
     * 添加订阅
     *
     * @param m
     * @param target
     */
    private void addSubscription(final Method m, final Object target) {
        //获取方法内参数
        Class[] parameterType = m.getParameterTypes();
        //只获取第一个方法参数，否则默认为Object
        Class clazz = Object.class;
        if (parameterType.length > 0) {
            clazz = parameterType[0];
        }
        mSubject.ofType(clazz).subscribe(new Observer() {//只订阅对应方法参数类型的事件，过滤掉其他事件
            @Override
            public void onSubscribe(Disposable d) {
                mSubscriptions.put(target, d);//添加到map中方便解除注册
//                System.out.println(target.getClass().getSimpleName() + "--->2 :addSubscription success!!   method:" + m.getName());
            }

            @Override
            public void onNext(Object o) {
                try {
//                    System.out.println(m.getName() + "--->onNext!!");
                    m.invoke(target, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

    }

    /**
     * 移除订阅
     *
     * @param obj
     */
    public void unRegister(@NonNull Object obj) {
        Disposable disposable = mSubscriptions.get(obj);

        if (disposable != null && !disposable.isDisposed()) {//如果不为空，且没有被处理掉则进行处理
            disposable.dispose();//调用了这个方法就不会再触发onNext
        }

        mSubscriptions.remove(obj);
    }

    /**
     * 发起一个事件,该方法仅供Dispatcher调用
     *
     * @param o
     */
    protected void post(Object o) {
        mSubject.onNext(o);//onNext事件会被当前已经订阅的所有Observer接收到
    }

}
