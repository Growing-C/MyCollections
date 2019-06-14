package com.cgy.mycollections.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cgy.mycollections.MainActivity;
import com.cgy.mycollections.utils.L;

/**
 * Description :
 * Author :cgy
 * Date :2019/6/14
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        L.e("fffff", "BootReceiver onReceive");
        //if (intent.getAction().equals(action_boot) || intent.getAction().equals(action_packageadd)|| intent.getAction().equals(action_packageremove) || intent.getAction().equals(action_packagereplace))
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.e("ffffff", "BootReceiver android.intent.action.BOOT_COMPLETED");
            Intent bootStartIntent = new Intent(context, MainActivity.class);
            bootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(bootStartIntent);
        }
    }
}
