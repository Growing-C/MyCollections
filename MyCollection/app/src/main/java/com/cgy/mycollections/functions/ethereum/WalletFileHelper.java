package com.cgy.mycollections.functions.ethereum;

import android.content.Context;

/**
 * Description :钱包文件助手，文件路径等内容
 * Author :cgy
 * Date :2018/12/12
 */
public class WalletFileHelper {
    private static final String FILE_SUFFIX = "/Wallet/wallet_file/";

    /**
     * 获取钱包的文件夹路径
     *
     * @param context
     * @return
     */
    public static String getExternalWalletFileDir(Context context) {
        return context.getExternalFilesDir(null) + FILE_SUFFIX;
    }

    /**
     * 获取文件路径
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getExternalWalletFile(Context context, String fileName) {
        return context.getExternalFilesDir(null) + FILE_SUFFIX + fileName;
    }


}
