package com.witon.mylibrary.flux.actions.creator;


import com.witon.mylibrary.flux.dispatcher.Dispatcher;

/**
 * 全局的事件创造者
 * Created by RB-cgy on 2017/2/17.
 */

public class ActionsCreator {
    private static ActionsCreator instance;
    final Dispatcher dispatcher;

    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    /**
     * 生成事件并发送
     *
     * @param eventType
     * @param data
     */
    public void create(String eventType, Object... data) {
        dispatcher.dispatch(eventType, data);
    }

}
