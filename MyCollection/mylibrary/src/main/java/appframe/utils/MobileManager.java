package appframe.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 手机信息管理等
 * Created by RB-cgy on 2017/6/15.
 */

public class MobileManager {

    TelephonyManager mTelManager;

    public MobileManager(Context context) {
        mTelManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
    }

    /**
     * 获取唯一的设备id
     *
     * @return meid
     * android.permission.READ_PRIVILEGED_PHONE_STATE
     */
    public String getMEID() {
        return mTelManager.getDeviceId();
    }


}
