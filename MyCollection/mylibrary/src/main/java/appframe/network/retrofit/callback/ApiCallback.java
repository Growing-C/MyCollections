package appframe.network.retrofit.callback;

import appframe.utils.LogUtils;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * github:https://github.com/WuXiaolong/
 */
public abstract class ApiCallback<T> extends DisposableObserver<T> {

    public abstract void onSuccess(T model);

    public abstract void onFailure(int code, String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            LogUtils.d("code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(code, msg);
        } else {
            onFailure(0, e.getMessage());
        }
        onComplete();
    }

    @Override
    public void onNext(T model) {

        onSuccess(model);
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
