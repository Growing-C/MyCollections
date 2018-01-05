package appframe.network.response;

import java.io.Serializable;

/**
 * 自定义回调数据,使用这个类必须确保接收到的json数据格式如下:
 * {
 * "responseCode":"success",
 * "responseData":{
 * "address":"",
 * },
 * "responseToken":"DB3BD47E865B30C62F9E6CA515B6F26C"
 * }
 * <p>
 * Created by RB-cgy on 2016/9/21.
 */
public class MResponse<T> implements Serializable {
    public String responseCode;//结果码

    public T responseData;

    public String responseToken;//登陆生成的token

    public String responseMessage;//响应信息
}
