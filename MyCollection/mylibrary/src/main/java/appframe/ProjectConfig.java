package appframe;


import com.witon.mylibrary.BuildConfig;

/**
 * 项目配置
 * Created by RB-cgy on 2017/2/14.
 */

public class ProjectConfig {
    private static boolean sIsDebug = false;//默认处于非debug状态，debug包不需要操作，release包设置这个属性可以设置为debug状态

    public static boolean isDebug() {
        return BuildConfig.DEBUG;//默认使用BuildConfig.DEBUG 防止其他地方调用的时候还要引入包名（主要为了appframe拷入其他项目的时候减少出错）
    }

    /**
     * 项目是否处于debug状态，用来全局控制debug，其他地方用到debug应该都用这个方法（清除其他地方对BuildConfig.Debug的依赖）
     *
     * @return true-代表是debug状态
     * false - 代表不是debug状态
     */
    public static boolean isDebugMode() {
        return BuildConfig.DEBUG || sIsDebug;//debug包默认开启debug，release包需要手动调用setDebug(true)来开启debug
    }

    /**
     * 设置是否在debug状态，当且仅当包是release包的时候有用
     *
     * @param isDebug
     */
    public static void setDebugMode(boolean isDebug) {
        sIsDebug = isDebug;
    }

}
