package appframe.network.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;


import com.witon.mylibrary.widget.CommonDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static appframe.network.update.UpdateConfig.TYPE_DOWNLOAD_ERROR;
import static appframe.network.update.UpdateConfig.TYPE_DOWNLOAD_OK;

/**
 * 下载更新
 * Created by RB-cgy on 2016/11/17.
 */
class UpdateDownload {
    private UpdateInfo mUpdateInfo;
    private Context mContext;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;//下载dialog

    public UpdateDownload(UpdateInfo updateInfo) {
        this.mUpdateInfo = updateInfo;
    }

    /**
     * 查询是否有更新信息
     */
    protected void showUpdate(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        if (context == null) {
            System.out.println("UpdateAgent context null ");
            return;
        }
        if (UpdateConfig.sIsUpdating)//已经在更新就不进行后续工作了
            return;

        CommonDialog a = new CommonDialog.Builder(context)
                .setTitle("发现新版本")//设置标题
                .setMessage(mUpdateInfo.memo)//设置消息
//                .setSubMessage("测试用副消息")//副消息，可选
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Positive button onClick");
                        startDownload();
                    }
                })
//                .setNegativeButton("取消", null)//可选
                .create();

        a.setCancelable(false);//不可取消
        a.show();

        UpdateConfig.sIsUpdating = true;//由于更新不可取消 到这一步一定是在更新了  此后该状态不需要改变 也仅在这里会改变状态

//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
//                .setTitle("发现新版本")
//                .setMessage(mUpdateInfo.memo)
//                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startDownload();
//                    }
//                });
//        if (UpdateConfig.sIsForce) {//强制更新没有取消
//        } else {//非强制更新
//            dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    UpdateConfig.sIsUpdating = false;
//                }
//            });
//        }
//
//        AlertDialog dialog = dialogBuilder.create();
//        dialog.setCancelable(false);//不可取消
//
//        dialog.show();
    }

    /**
     * 静默下载更新,由于android安全问题，静默安装需要root权限，
     * 还有一种方法是使用无障碍服务来智能安装，不过似乎还不如直接弹框，所以这个功能暂时不实现
     */
    protected void silentDownload() {
        createFile(mUpdateInfo.appName);

        new DownloadThread().start();//下载线程
    }

    /**
     * 可视的下载对话框
     */
    private void startDownload() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("软件升级");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        if (!UpdateConfig.sIsForce) {//非强制更新才有取消和后台更新取消
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            mProgressDialog.dismiss();
                            mProgressDialog = null;
                        }
                    });
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "后台更新",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            mProgressDialog.dismiss();
                            mProgressDialog = null;
                        }
                    });
        }
        mProgressDialog.setCancelable(false);

        createFile("update");//FIXME:名称暂时 自己定义,防止 包名为中文时可能有问题
//        createFile(mUpdateInfo.appName);

        new DownloadThread().start();//下载线程
    }

    /**
     * 本地创建下载文件
     *
     * @param appName
     */
    private void createFile(String appName) {
        UpdateConfig.sUpdateFile = new File(Environment.getExternalStorageDirectory()
                + "/witon/", appName + ".apk");

        if (UpdateConfig.sUpdateFile.exists()) {
            UpdateConfig.sUpdateFile.delete();
        }
        if (!UpdateConfig.sUpdateFile.getParentFile().exists()) {
            UpdateConfig.sUpdateFile.getParentFile().mkdirs();
        }
    }

    /**
     * 设置进度条最大值
     */
    private void setMax(final int total) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mProgressDialog != null)
                    mProgressDialog.setMax(total);
            }
        });
    }

    /**
     * 显示下载对话框
     */
    private void showProgress() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mProgressDialog != null)
                    mProgressDialog.show();
            }
        });
    }

    /**
     * 设置下载进度
     */
    private void postProgress(final int len) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mProgressDialog != null)
                    mProgressDialog.setProgress(len);
            }
        });
    }

    /**
     * 设置下载进度
     */
    private void postClose() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        });
    }

    /**
     * 下载线程
     */
    private class DownloadThread extends Thread {
        @Override
        public void run() {
            int len;
            long downSize = 0;// 已下载文件大小
            long totalSize;// 文件总 长度
            URL url;
            try {
                url = new URL(mUpdateInfo.file);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(5000);
                conn.connect();
                // 获取到文件的大小
                totalSize = conn.getContentLength();
                setMax(100);
                System.out.println("totalSize:" + totalSize);
                showProgress();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("start down load~~~~~~~~200");
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(
                            UpdateConfig.sUpdateFile);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];

                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        downSize += len;
                        // 获取当前下载量
                        postProgress((int) (downSize * 100 / totalSize));
                    }
                    fos.close();
                    bis.close();
                    is.close();

                    if (UpdateConfig.sUpdateFile.exists() && UpdateConfig.sUpdateFile.length() > 0) {
                        postClose();
                        mHandler.sendEmptyMessage(TYPE_DOWNLOAD_OK);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            postClose();
            mHandler.sendEmptyMessage(TYPE_DOWNLOAD_ERROR);
        }
    }

}
