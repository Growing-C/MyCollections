package com.cgy.mycollections.functions.net.wifip2p;

import java.io.File;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/15
 */
public class TransferData {
    public int dataType;//数据类型  1 -string  2-file
    public String transferContent;//string类型的传输输入内容
    public String transferFilePath;//传输文件路径
    public long dataLen;

    public TransferData(int dataType, String transferContent, String transferFilePath) {
        this.dataType = dataType;
        this.transferContent = transferContent;
        this.transferFilePath = transferFilePath;
        getSendDataLen();
    }

    /**
     * @return
     */
    public long getSendDataLen() {
        if (dataLen != 0)
            return dataLen;

        if (dataType == 1) {
            dataLen = transferContent == null ? 0 : transferContent.getBytes().length;
            return dataLen;
        } else {
            File file = new File(transferFilePath);
            if (file.exists()) {
                dataLen = file.length();
                return dataLen;
            }
            dataLen = 0;
            return dataLen;
        }
    }

    @Override
    public String toString() {
        return "dataType:" + dataType + "-transferContent:" + transferContent + "-transferFilePath:" + transferFilePath;
    }
}
