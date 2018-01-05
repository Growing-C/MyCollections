package appframe.network.retrofit.network;

import android.content.Context;

import java.io.IOException;

import appframe.network.retrofit.Api;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 每次调用请求前的网络状态监控
 * Created by RB-cgy on 2017/7/25.
 */
public class NetworkStateInterceptor implements Interceptor {
    NetworkMonitor mMonitor;

    public NetworkStateInterceptor(Context context) {
        mMonitor = new NetworkMonitor(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        boolean connected = mMonitor.isConnected();
        System.out.println("NetworkStateInterceptor connected:" + connected);
        if (connected) {
            return chain.proceed(chain.request());
        } else {
            throw new Api.APIException("400", "无网络连接，请检查");
        }
    }
}
