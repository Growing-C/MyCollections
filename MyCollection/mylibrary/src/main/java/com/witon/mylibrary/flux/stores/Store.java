package com.witon.mylibrary.flux.stores;


import com.witon.mylibrary.flux.actions.Action;
import com.witon.mylibrary.flux.dispatcher.Dispatcher;

/**
 * 事件处理
 * Created by RB-cgy on 2017/2/21.
 */
public abstract class Store {
    final Dispatcher dispatcher;

    public Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    void emitStoreChange(String eventType, Object eventData) {
        dispatcher.emitChange(new StoreChangeEvent(eventType, eventData));
    }

    void emitStoreChange(String eventType) {
        dispatcher.emitChange(new StoreChangeEvent(eventType));
    }

    public abstract void onAction(Action action);

    public class StoreChangeEvent {
        String eventType = null;
        Object data;

        public StoreChangeEvent(String eventType) {
            this.eventType = eventType;
        }

        public StoreChangeEvent(String eventType, Object eventData) {
            this.eventType = eventType;
            this.data = eventData;
        }

        public String getEventType() {
            return eventType;
        }

        public Object getEventData() {
            return data;
        }

    }
}
