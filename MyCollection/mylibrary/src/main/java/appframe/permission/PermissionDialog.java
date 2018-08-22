package appframe.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;


/**
 * to avoid building a dialog everywhere your permission request is denied,we use this class to conveniently construct an AlertDialog
 * Created by RB-cgy on 2016/8/12.
 */
public class PermissionDialog {
    AlertDialog mDialog;
    Context mContext;

    public PermissionDialog(Context context, String msg) {
        this.mContext = context;

        mDialog = new AlertDialog.Builder(context)
                .setTitle("注意")//设置标题
                .setMessage(msg)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //open a system interface which contains app info,available since android 2.3
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                        mContext.startActivity(localIntent);
                    }
                })
                .setNegativeButton("取消", null)//可选
                .create();

    }

    public void show() {
        mDialog.show();
    }

}
