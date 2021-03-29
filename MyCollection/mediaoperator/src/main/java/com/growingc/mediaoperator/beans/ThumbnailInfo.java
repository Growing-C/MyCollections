package com.growingc.mediaoperator.beans;

/**
 * Description : 缩略图信息
 * Author :cgy
 * Date :2019/8/5
 *
 * <p>
 * mediaStore的数据库中存储图片缩略图的columns
 * <p>
 * total:6--column name:->_id
 * total:6--column name:->_data  缩略图路径，Path to the thumbnail file on disk.
 * total:6--column name:->image_id
 * total:6--column name:->kind  类型   小图 MINI_KIND = 1;更小的图 MICRO_KIND = 3;
 * total:6--column name:->width
 * total:6--column name:->height
 * <p>
 * 示例：
 * id:184-data:/storage/emulated/0/DCIM/.thumbnails/1564986249818.jpg-imageId:150227-kind:1-width:75-height:75
 */
public class ThumbnailInfo {
    public String id;
    public String data;
    public String imageId;
    public int kind;
    public int width;
    public int height;


    @Override
    public String toString() {
        return "id:" + id
                + "-data:" + data
                + "-imageId:" + imageId
                + "-kind:" + kind
                + "-width:" + width
                + "-height:" + height;
    }
}
