package com.cgy.mycollections.functions.net.wifip2p;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/15
 */
public interface OnProgressChangListener {

    //当传输进度发生变化时
    void onProgressChanged(TransferData transferData, int progress);

    //当传输结束时
    void onTransferFinished(TransferData transferData);

}