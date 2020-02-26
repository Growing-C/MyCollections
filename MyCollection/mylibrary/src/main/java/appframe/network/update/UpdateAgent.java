package appframe.network.update;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.widget.Toast;


import com.witon.mylibrary.R;
import com.witon.mylibrary.widget.CommonDialog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import appframe.permission.PermissionManager;


/**
 * update frame
 * Created by RB-cgy on 2016/11/17.
 */
public class UpdateAgent {
    private static Reference<Context> sContextRef;//使用弱引用，防止context无法回收
    private static Reference<UpdateCallBack> sCallBackRef;//使用弱引用，防止callBack无法回收

    public static final int GET_UNKNOWN_APP_SOURCES = 0x99;

    static Handler sHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UpdateConfig.TYPE_NO_UPDATE://无需更新
                    System.out.println("TYPE_NO_UPDATE==============");
                    if (sCallBackRef != null && sCallBackRef.get() != null) {//回调
                        sCallBackRef.get().noUpdate();
                        sCallBackRef.clear();//只使用一次
                        sCallBackRef = null;
                    }
                    break;
                case UpdateConfig.TYPE_NEED_UPDATE://需要更新
                    UpdateInfo updateInfo = (UpdateInfo) msg.obj;
                    UpdateDownload updateDownload = new UpdateDownload(updateInfo);
                    updateDownload.showUpdate(sContextRef.get(), sHandler);
                    break;
                case UpdateConfig.TYPE_NO_SDCARD:
                    //TODO:无sd卡
                    break;
                case UpdateConfig.TYPE_DOWNLOAD_OK://下载成功开启安装
                    //现在应该不需要延迟了，暂时放1秒
                    sHandler.sendEmptyMessageDelayed(UpdateConfig.TYPE_START_INSTALL, 500);
                    break;
                case UpdateConfig.TYPE_START_INSTALL://开始安装
                    installApk(sContextRef.get());
                    break;
                case UpdateConfig.TYPE_DOWNLOAD_ERROR:
                    if (sCallBackRef != null && sCallBackRef.get() != null) {//回调
                        sCallBackRef.get().noUpdate();
                        sCallBackRef.clear();
                        sCallBackRef = null;
                    }
                    Toast.makeText(sContextRef.get(), "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                case UpdateConfig.TYPE_INSTALL_FAIL://安装失败
                    Toast.makeText(sContextRef.get(), "安装失败", Toast.LENGTH_SHORT).show();
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
    public static void update(Context context, UpdateCallBack updateCallBack) {
        if (UpdateConfig.sIsUpdating)//已经在更新就不进行后续工作了
            return;
        if (updateCallBack != null)
            sCallBackRef = new WeakReference<>(updateCallBack);

        sContextRef = new WeakReference<>(context);

        UpdateChecker checker = new UpdateChecker(context);
        checker.checkUpdate(sHandler);
    }

    /**
     * 查询更新
     *
     * @param context
     */
    public static void update(Context context) {
        update(context, null);
    }

    /**
     * 请求权限结果，主要为安卓8.0以上请求安装apk权限
     *
     * @param activity
     * @param requestCode
     * @param grantResults
     */
    public static void onRequestPermissionsResult(final Activity activity, int requestCode, int[] grantResults) {
        if (requestCode == PermissionManager.REQUEST_INSTALL_PACKAGES) {//处理安装权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                由于这个权限似乎不是运行时权限，所以无法再代码中请求权限，还是需要用户跳转到设置界面中自己去打开权限。
//                这里似乎永远进不来，暂时保留
                Uri updateFileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", UpdateConfig.sUpdateFile);
                activity.grantUriPermission(activity.getPackageName(), updateFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                executeInstall(activity, updateFileUri);
            } else {//似乎肯定只走这里
                CommonDialog a = new CommonDialog.Builder(activity)
                        .setTitle("提示")//设置标题
                        .setMessage("安装应用需要打开安装未知来源应用权限，\n请您点击 设置\n选中列表中的\"" + activity.getString(R.string.app_name) + "\"\n选中允许安装未知应用，即可安装")//设置消息
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("Positive button onClick");

                                Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                activity.startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
                            }
                        })
                        .create();

                a.setCancelable(false);//不可取消
                a.show();
//                Toast.makeText(activity, "请选中列表中的\"" + activity.getString(R.string.app_name) + "\"-选中允许安装未知应用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 安装apk 权限被拒绝后手动请求系统权限窗口回调
     *
     * @param requestCode
     */
    public static void onActivityResult(Activity activity, int requestCode) {
        if (requestCode == GET_UNKNOWN_APP_SOURCES) {
            installApk(activity);
        }
    }

    /**
     * 安装apk
     */
    private static void installApk(Context context) {
        if (context == null) {
            Log.i("UpdateAgent", "context is null");
            return;
        }

        Uri updateFileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//安卓8.0以上需要处理未知应用来源权限问题
            if (context.getPackageManager().canRequestPackageInstalls()) {//是否已经允许安装未知来源的app，从给了权限的第二次开始应该就会走这里

                updateFileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", UpdateConfig.sUpdateFile);

                context.grantUriPermission(context.getPackageName(), updateFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {//没有权限，此时要请求权限
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, PermissionManager.REQUEST_INSTALL_PACKAGES);//requestCode使用PermissionManager中的，防止出现不同权限requestCode相同导致的问题
                return;
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //由于Android 7.0以上 会有uri暴露的问题，所以根据版本处理下兼容性
            updateFileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", UpdateConfig.sUpdateFile);

            context.grantUriPermission(context.getPackageName(), updateFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            updateFileUri = Uri.fromFile(UpdateConfig.sUpdateFile);
        }

        executeInstall(context, updateFileUri);
    }

    /**
     * 最终执行安装，不关心版本
     *
     * @param context
     * @param updateFileUri
     */
    private static void executeInstall(Context context, Uri updateFileUri) {
        if (updateFileUri == null || context == null)
            return;

        Log.i("UpdateAgent", "updateFileUri：" + updateFileUri.toString());
        // 执行动作
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//Granting Temporary Permissions to a URI
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//新的task,没有这个无法安装成功
        // 执行的数据类型
        intent.setDataAndType(updateFileUri,
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
