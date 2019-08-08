package com.witon.mylibrary.flux.actions.creator;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;


import com.witon.mylibrary.flux.dispatcher.Dispatcher;
import com.witon.mylibrary.flux.rx.BaseRxAction;

import java.io.File;
import java.io.FileOutputStream;

import appframe.network.retrofit.ApiWrapper;
import appframe.utils.LogUtils;

import static com.witon.mylibrary.flux.actions.BaseActions.ACTION_REQUEST_END;
import static com.witon.mylibrary.flux.actions.BaseActions.ACTION_REQUEST_START;


/**
 * Created by RB-cgy on 2017/4/28.
 */
public class MainActionsCreator extends BaseRxAction {
    public MainActionsCreator(Dispatcher dispatcher) {
        super(dispatcher);
    }


//    public void getUserHeader() {
//        //已实名认证，已绑卡，下载用户头像：
//        mDispatcher.dispatch(ACTION_REQUEST_START);//开始发送
//        addSubscription(ApiWrapper.getInstance().getUserPic(), new MyCallBack<UserPic>() {
//            @Override
//            public void onSuccess(String code, String msg, UserPic model) {
//                if (model != null && "1".equals(model.code)) {
//                    if (!TextUtils.isEmpty(model.pic)) {
//                        String path = MyApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/" + Constants.PICNAME;
//
//                        MyApplication.getInstance().getLoginInfo().picUrl = path;
//
//                        File pic = new File(path);
//                        if (pic.exists()) pic.delete();
//                        try {
//                            pic.createNewFile();
//                            FileOutputStream picS = new FileOutputStream(pic);
//                            picS.write(Base64.decode(model.pic, 0));
//                            picS.flush();
//                            picS.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                LogUtils.d("onFailure:" + msg);
////                mDispatcher.dispatch(COMMON_ACTION_FAIL, Constants.KEY_ERROR_MSG, msg);
//            }
//
//            @Override
//            public void onFinish() {
//                mDispatcher.dispatch(ACTION_REQUEST_END);
//            }
//        });
//    }

}
