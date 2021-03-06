package com.cgy.mycollections.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cgy.mycollections.functions.file.FileConstants;

import java.util.UUID;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/2
 */
public class CommonUtils {

    public static String getUserId(@NonNull Context context) {
        String userId = SharePreUtil.getString(FileConstants.PREF_USER, context, FileConstants.KEY_USER_ID);
        if (TextUtils.isEmpty(userId)) {
            userId = UUID.randomUUID().toString();
            L.e("userId:" + userId);
            SharePreUtil.putString(FileConstants.PREF_USER, context, FileConstants.KEY_USER_ID, userId);
        }

        return userId;
    }
}
