package appframe.network.update;

import java.io.File;

/**
 * update config eg. update site , etc.
 * Created by RB-cgy on 2016/11/18.
 */
public class UpdateConfig {
    /**
     * 需要更新
     **/
    public static final int TYPE_NEED_UPDATE = 0x01;
    /**
     * 没有网络
     **/
    public static final int TYPE_NO_NETWORK = 0x03;
    /**
     * Sd卡不存在
     **/
    public static final int TYPE_NO_SDCARD = 0x04;
    /**
     * 下载完成
     **/
    public static final int TYPE_DOWNLOAD_OK = 0x05;

    /**
     * 开始安装
     */
    public static final int TYPE_START_INSTALL = 0x09;
    /**
     * 下载失败
     **/
    public static final int TYPE_DOWNLOAD_ERROR = 0x07;
    /**
     * 无需更新
     **/
    public static final int TYPE_NO_UPDATE = 0x06;

    /**
     * 安装失败
     */
    public static final int TYPE_INSTALL_FAIL = 0x10;

    /**
     * 是否强制更新 false代表不强制
     */
    protected static boolean sIsForce = false;

    /**
     * 是否正在更新
     */
    public static boolean sIsUpdating = false;

    /**
     * 查询更新信息的地址
     **/
    protected static String sUpdateUrl;

    protected static File sUpdateFile;// 更新文件存放路径

    /**
     * 设置查询更新的地址
     *
     * @param url
     */
    public static void setUpdateUrl(String url) {
        sUpdateUrl = url;
    }

    public static boolean isForceUpdate() {
        return sIsForce;
    }
}
