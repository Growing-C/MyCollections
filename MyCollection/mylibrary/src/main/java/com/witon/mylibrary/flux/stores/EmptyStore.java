package com.witon.mylibrary.flux.stores;


import com.witon.mylibrary.flux.actions.Action;
import com.witon.mylibrary.flux.annotation.Subscribe;
import com.witon.mylibrary.flux.dispatcher.Dispatcher;

import static com.witon.mylibrary.flux.actions.BaseActions.ACTION_REQUEST_END;
import static com.witon.mylibrary.flux.actions.BaseActions.ACTION_REQUEST_START;
import static com.witon.mylibrary.flux.actions.BaseActions.COMMON_ACTION_FAIL;

/**
 * 占位用，有的view不需要store，避免创建多个空store
 * Created by RB-cgy on 2017/4/28.
 */
public class EmptyStore extends Store {
    public EmptyStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case ACTION_REQUEST_START://请求开始
                emitStoreChange(ACTION_REQUEST_START);
                break;
            case ACTION_REQUEST_END://请求结束
                emitStoreChange(ACTION_REQUEST_END);
                break;
            case COMMON_ACTION_FAIL://请求失败
                emitStoreChange(COMMON_ACTION_FAIL, action.getData().get("error_key"));
                break;
            default:
                break;
        }
    }
}
