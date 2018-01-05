package appframe.network.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


/**
 * update frame
 * Created by RB-cgy on 2016/11/17.
 */
public class UpdateAgent {
    private static Reference<Context> sContextRef;//使用弱引用，防止context无法回收

    static Handler sHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UpdateConfig.TYPE_NEED_UPDATE://需要更新
                    UpdateInfo updateInfo = (UpdateInfo) msg.obj;
                    UpdateDownload updateDownload = new UpdateDownload(updateInfo);
                    updateDownload.showUpdate(sContextRef.get(), sHandler);
                    break;
                case UpdateConfig.TYPE_NO_SDCARD:
                    //TODO:无sd卡
                    break;
                case UpdateConfig.TYPE_DOWNLOAD_OK://下载成功开启安装
                    installApk(sContextRef.get());
                    break;
                case UpdateConfig.TYPE_DOWNLOAD_ERROR:
                    Toast.makeText(sContextRef.get(), "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 查询更新
     *
     * @param context
     */
    public static void update(Context context) {
        sContextRef = new WeakReference<>(context);

        UpdateChecker checker = new UpdateChecker(context);
        checker.checkUpdate(sHandler);
    }


    /**
     * 安装apk
     */
    private static void installApk(Context context) {
        if (context == null) {
            Log.i("UpdateAgent", "context is null");
            return;
        }
        // 执行动作
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//新的task,没有这个无法安装成功
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(UpdateConfig.sUpdateFile),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
