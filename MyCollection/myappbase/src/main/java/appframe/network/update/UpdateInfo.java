package appframe.network.update;

import org.json.JSONObject;

/**
 * Created by RB-cgy on 2016/11/18.
 */

public class UpdateInfo {
    public String appName;//app名
    public String time;//时间
    public String memo;//描述
    public int version2;//versionCode
    public String file;//更新下载链接
    public String version;//versionName

    public UpdateInfo(JSONObject json) {
        if (json == null)
            return;
        this.appName = json.optString("appName");
        this.time = json.optString("time");
        this.memo = json.optString("memo");
        this.version2 = json.optInt("version2");
        this.file = json.optString("file");
        this.version = json.optString("version");
    }


}
