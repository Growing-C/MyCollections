package com.growingc.mediaoperator.beans;

/**
 * Description :媒体信息
 * Author :cgy
 * Date :2019/7/23
 * <p>
 * mediaStore的数据库中存储图片的columns
 * total:20--column name:->_id
 * total:20--column name:->_data   内容是文件路径 如：/storage/emulated/0/xxx/.20190619_070221.gif
 * total:20--column name:->_size
 * total:20--column name:->_display_name
 * total:20--column name:->mime_type
 * total:20--column name:->title
 * total:20--column name:->date_added
 * total:20--column name:->date_modified
 * total:20--column name:->description
 * total:20--column name:->picasa_id
 * total:20--column name:->isprivate
 * total:20--column name:->latitude
 * total:20--column name:->longitude
 * total:20--column name:->datetaken
 * total:20--column name:->orientation
 * total:20--column name:->mini_thumb_magic
 * total:20--column name:->bucket_id
 * total:20--column name:->bucket_display_name
 * total:20--column name:->width
 * total:20--column name:->height
 */
public class MediaInfo {
    public String id;
    public String data;//内容是文件路径 如：/storage/emulated/0/xxx/.20190619_070221.gif
    public int size;
    public String display_name;
    public String mime_type;
    public String title;
    public long date_added;//添加日期  单位s
    public long date_modified;//修改日期 单位s
    public String description;
    public String picasa_id;
    public String isprivate;
    public String latitude;
    public String longitude;
    public String datetaken;
    public String orientation;
    public String mini_thumb_magic;
    public String bucket_id;
    public String bucket_display_name;
    public String width;
    public String height;

    @Override
    public String toString() {
        return "id:" + id + "-data:" + data
                + "-size:" + size
                + "-display_name:" + display_name
                + "-mime_type:" + mime_type
                + "-title:" + title
                + "-date_added:" + date_added
                + "-date_modified:" + date_modified
                + "-picasa_id:" + picasa_id
                + "-isprivate:" + isprivate
                + "-latitude:" + latitude
                + "-longitude:" + longitude
                + "-datetaken:" + datetaken
                + "-orientation:" + orientation
                + "-mini_thumb_magic:" + mini_thumb_magic
                + "-bucket_id:" + bucket_id
                + "-bucket_display_name:" + bucket_display_name
                + "-width:" + width
                + "-height:" + height;
    }
}
