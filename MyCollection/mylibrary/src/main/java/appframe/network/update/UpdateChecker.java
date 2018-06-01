package appframe.network.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static appframe.network.update.UpdateConfig.TYPE_NEED_UPDATE;
import static appframe.network.update.UpdateConfig.TYPE_NO_NETWORK;
import static appframe.network.update.UpdateConfig.TYPE_NO_SDCARD;
import static appframe.network.update.UpdateConfig.TYPE_NO_UPDATE;

/**
 * check update info
 * Created by RB-cgy on 2016/11/18.
 */

public class UpdateChecker {
    private String TAG = this.getClass().getSimpleName();

    private Context mContext;

    public UpdateChecker(Context context) {
        this.mContext = context;
    }

    /**
     * 检查更新
     *
     * @param handler
     */
    public void checkUpdate(final Handler handler) {
        if (TextUtils.isEmpty(UpdateConfig.sUpdateUrl)) {
            Log.i(TAG, "empty update url!");
            handler.sendEmptyMessage(TYPE_NO_UPDATE);//代表无需更新
            return;
        }

        if (!checkExternalStorage()) {
            handler.sendEmptyMessage(TYPE_NO_SDCARD);//没有存储卡
            return;
        }
        if (!checkNetworkState()) {
            handler.sendEmptyMessage(TYPE_NO_NETWORK);//无网络
            return;
        }


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)//连接超时时间(由于在logo页时间太长的话影响进去的时间，所以这里限制一下时间)
                .build();
        Request request = new Request.Builder()
                .url(UpdateConfig.sUpdateUrl)
                .build();

        Call call = okHttpClient.newCall(request);

        Log.i(TAG, "queryUpdate:" + UpdateConfig.sUpdateUrl);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "load updates fail~~\n" + e.toString());
                handler.sendEmptyMessage(TYPE_NO_UPDATE);//代表无需更新
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseS = response.body().string();
                Log.i(TAG, "onResponse:" + responseS);
                try {
                    JSONObject json = new JSONObject(responseS);
                    UpdateInfo updateInfo = new UpdateInfo(json);

                    UpdateConfig.sIsForce = updateInfo.force;

                    int webVersion = updateInfo.version2;//web端传来的版本号
                    String downUrl = updateInfo.file;//下载链接
                    int localVersion = getVersionCode();

                    if (!TextUtils.isEmpty(downUrl) && localVersion > 0 && localVersion < webVersion) {//安装的包版本低于服务器上的包，需要下载
                        handler.obtainMessage(TYPE_NEED_UPDATE, updateInfo).sendToTarget();//代表需要更新
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(TYPE_NO_UPDATE);//代表无需更新
            }
        });

    }

    /**
     * 仅供获取本地app版本号
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            Log.i("UpdateChecker", "versionName:" + packageInfo.versionName);//1.0
            Log.i("UpdateChecker", "versionCode:" + packageInfo.versionCode);//1
            Log.i("UpdateChecker", "packageName:" + packageInfo.packageName);//com.witon.minipay
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return versionName;
    }

//    -----------------------以下方法对外不可见--------------------------

    /**
     * 检查扩展存储是否存在
     *
     * @return
     */
    private boolean checkExternalStorage() {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /**
     * 获取本地app版本号
     */
    private int getVersionCode() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                mContext.getPackageName(), 0);
        Log.i(TAG, "versionName:" + packageInfo.versionName);//1.0
        Log.i(TAG, "versionCode:" + packageInfo.versionCode);//1
        Log.i(TAG, "packageName:" + packageInfo.packageName);//com.witon.minipay
        return packageInfo.versionCode;
    }

    /**
     * 检查网络连接状态
     *
     * @return false 没有网络 true 有网络
     */
    private boolean checkNetworkState() {
        ConnectivityManager connM = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connM.getActiveNetworkInfo();
        if ((netInfo == null) || (!netInfo.isConnected())) {
            return false;
        }
        return true;
    }


}
