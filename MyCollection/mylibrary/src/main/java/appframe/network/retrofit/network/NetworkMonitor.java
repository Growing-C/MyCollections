package appframe.network.retrofit.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态监听
 * Created by RB-cgy on 2017/7/25.
 */
public class NetworkMonitor {

    private final Context applicationContext;

    public NetworkMonitor(Context context) {
        applicationContext = context.getApplicationContext();
    }

    /**
     * 是否网络已连接
     *
     * @return
     */
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
