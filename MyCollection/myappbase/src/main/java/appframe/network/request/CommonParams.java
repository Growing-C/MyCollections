package appframe.network.request;

/**
 * 通用的参数，不使用的字段设置为null即可避免生成json
 * 目前支持的接口如下：
 * 1.修改个人擅长接口
 * 2.获取医生详细信息接口
 * 3.修改密码接口
 * Created by RB-cgy on 2017/5/31.
 */
public class CommonParams {
    public String doctor_id;//多个公用参数（修改个人擅长，获取个人信息，修改密码）

    //修改个人擅长
    public String doctor_specialty;

    //修改密码
    public String pwd_old;//原始密码
    public String pwd_digest;//新密码
    public String user_type;//用户类型  2-医生

}
