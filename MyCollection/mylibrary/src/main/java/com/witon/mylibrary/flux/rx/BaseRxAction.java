package com.witon.mylibrary.flux.rx;


import com.witon.mylibrary.flux.dispatcher.Dispatcher;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * 主要用来网络通信时控制Observable生命周期
 * Created by RB-cgy on 2017/4/28.
 */
public class BaseRxAction {
    protected CompositeDisposable mComposite;//Observable容器，可以随时取消事件分发
    protected Dispatcher mDispatcher;//事件发送者


    public BaseRxAction(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    /**
     * 添加订阅的可观察对象
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mComposite == null) {
            mComposite = new CompositeDisposable();
        }
        mComposite.add(observable.subscribeWith(observer));
    }

    /**
     * 添加订阅链，可处理两个接口
     *
     * @param firstObservable
     * @param func1
     * @param observer
     * @param <T>
     * @param <R>
     */
    public <T, R> void addSubscriptionChain(Observable<T> firstObservable, Function<T, Observable<R>> func1, DisposableObserver<R> observer) {
        if (mComposite == null) {
            mComposite = new CompositeDisposable();
        }
        mComposite.add(firstObservable.flatMap(func1).subscribeWith(observer));
    }

    /**
     * RXjava取消注册，以避免内存泄露
     */
    public void unSubscribe() {
        if (mComposite != null && !mComposite.isDisposed()) {
            System.out.println("BaseRxAction unSubscribe");
            mComposite.clear();
        }
    }

    public void createAction(String eventType, Object... data) {
        mDispatcher.dispatch(eventType, data);
    }
}
