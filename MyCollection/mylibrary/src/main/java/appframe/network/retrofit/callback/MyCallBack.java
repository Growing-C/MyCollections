package appframe.network.retrofit.callback;

import android.text.TextUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import appframe.network.response.MResponse;
import appframe.utils.LogUtils;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * <p>观察者类</p>
 * 使用MResponse的回调，由于即使从服务端返回了json数据，但是数据中可能没有所需信息，只有服务端返回的错误码，
 * 此时应该归类为failure，故定义该类来进行相应的处理
 * Created by RB-cgy on 2016/9/28.
 */
public abstract class MyCallBack<T> extends DisposableObserver<MResponse<T>> {
    public abstract void onSuccess(T model);

    public abstract void onFailure(int code, String msg);//有的地方不需要实现这个方法，所以此方法不使用abstract

    public abstract void onFinish();//有的地方不需要实现这个方法，所以此方法不使用abstract

    @Override
    public void onError(Throwable e) {
        System.out.println("MyCallBack onError");
        e.printStackTrace();
        String msg;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
//            msg = httpException.getMessage();
            LogUtils.d("code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            } else if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            } else {
                msg = "网络异常";
            }
            onFailure(code, msg);
        } else if (e instanceof SocketTimeoutException) {
            onFailure(0, "连接超时");
        } else if (e instanceof UnknownHostException) {
            onFailure(0, "无法连接到服务器");
        } else {
            msg = e.getMessage();
            if (TextUtils.isEmpty(msg)) {
                msg = "未知错误";
            }
            onFailure(0, msg);
        }
        onComplete();//服务器错误的时候不会主动调用onCompleted()
    }

    @Override
    public void onNext(MResponse<T> model) {
        System.out.println("MyCallBack onNext");
        if (model == null) {
            onFailure(0, "MResponse解析失败！");
            return;
        }

        switch (model.responseCode) {
            case "success":
//                if (!TextUtils.isEmpty(model.responseToken))
//                    MyApplication.getInstance().setToken(model.responseToken);  //token赋值

                if (model.responseData != null)
                    onSuccess(model.responseData);
                else
                    onFailure(999, "无数据错误！");
                break;
            case "common_error":
                if (TextUtils.isEmpty(model.responseMessage))
                    onFailure(0, "系统错误");
                else
                    onFailure(0, model.responseMessage);
                break;
            default:
                onFailure(0, model.responseMessage);
                break;
        }
    }

    public void onComplete() {
        onFinish();
    }

}
