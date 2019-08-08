package com.witon.mylibrary.flux.dispatcher;


import com.witon.mylibrary.flux.actions.Action;
import com.witon.mylibrary.flux.stores.Store;

/**
 * 调度者
 * Created by RB-cgy on 2017/2/14.
 */
public class Dispatcher {

    private RxBus mEventBus;//一个dispatcher对应一个rxBus
//    private static Dispatcher sInstance;

    private Dispatcher() {
        this.mEventBus = RxBus.newInstance();
    }

    /**
     * 不再使用单例
     *
     * @return
     */
    public static Dispatcher get() {
        return new Dispatcher();
    }

    public void register(final Object cls) {
        mEventBus.register(cls);
    }

    public void unRegister(final Object cls) {
        mEventBus.unRegister(cls);
    }

    /**
     * 分发action事件
     *
     * @param eventType
     * @param data
     */
    public void dispatch(String eventType, Object... data) {
        if (eventType == null || eventType.length() == 0) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(eventType);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        post(actionBuilder.build());
    }

    /**
     * 发出改变事件
     *
     * @param o
     */
    public void emitChange(Store.StoreChangeEvent o) {
        post(o);
    }

    /**
     * 发送事件
     *
     * @param event
     */
    private void post(final Object event) {
        mEventBus.post(event);
    }

}
