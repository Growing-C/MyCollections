package com.growingc.mediaoperator.utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.UUID;

import appframe.utils.L;
import appframe.utils.SharePreUtil;

/**
 * author: chengy
 * created on: 2021-6-4 16:49
 * description:
 */
public class AccountUtils {

    public static final String PREF_USER = "user";
    public static final String KEY_USER_ID = "user_id";

    public static String getUserId(@NonNull Context context) {
        String userId = SharePreUtil.getString(PREF_USER, context, KEY_USER_ID);
        if (TextUtils.isEmpty(userId)) {
            userId = UUID.randomUUID().toString();
            SharePreUtil.putString(PREF_USER, context, KEY_USER_ID, userId);
        }

        L.e("getUserId userId:" + userId);
        return userId;
    }

}
