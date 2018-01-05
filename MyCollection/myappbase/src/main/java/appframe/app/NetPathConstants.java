package appframe.app;


import appframe.ProjectConfig;

/**
 * 网络连接路径
 * Created by RB-cgy on 2016/9/14.
 */
public class NetPathConstants {

    /**
     * 服务器地址
     */
    public static final String SERVER_URL;

    /**
     * 正式时使用正式地址; 测试使用测试服务器地址.
     */
    static {
        if (ProjectConfig.isDebug()) {//此处不使用ProjectConfig.isDebugMode()是因为可能debug包和release包请求地址不同，release情况下开启debug在使用ProjectConfig.isDebugMode()的情况下会替换地址
//            SERVER_URL = "http://server:port/eHospital2/ed/";//测试库
            SERVER_URL = "http://d3.witon.us/eHospital2/";//测试库
            // SERVER_URL = "http://d2.witon.us/eHospital2/";//测试库
//            SERVER_URL = "http://xxxx/";
        } else {
            SERVER_URL = "http://d3.witon.us/eHospital2/";//测试库
            //SERVER_URL = "http://d2.witon.us/eHospital2/";//测试库
//            SERVER_URL = "http://xxxxxxxx/";//正式库
        }
        System.out.println("SERVER_URL:" + SERVER_URL);
    }


    public static final String URL_LOGIN = "ed/edLogin";//登陆

}
