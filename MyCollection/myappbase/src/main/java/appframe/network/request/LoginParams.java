package appframe.network.request;

import java.io.Serializable;

/**
 * Created by RB-cgy on 2016/9/28.
 */
public class LoginParams implements Serializable {

    public String user_name;
    public String user_password;
    public String hospital_id;
    public String d_version;
    public String client_version;//客户端(app)版本号
    public String client_osversion;//客户端操作系统版本号
    public String client_system;//客户端操作系统
    public String client_serianum;//登录设备号标示
}
