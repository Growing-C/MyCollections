package com.cgy.mycollections.functions.mediamanager.images;

import androidx.annotation.NonNull;

import com.cgy.mycollections.functions.file.FileInfo;

import java.io.File;
import java.io.Serializable;

/**
 * Description :
 * Author :cgy
 * Date :2019/12/10
 */
public class ImageInfo implements Serializable {
    public String imageFilePath;

    private ImageInfo() {
    }

    public static ImageInfo importFromThumbnail(@NonNull ThumbnailInfo thumbnailInfo) {
        ImageInfo info = new ImageInfo();
        info.imageFilePath = thumbnailInfo.data;


        return info;
    }

    public static ImageInfo importFromFileInfo(@NonNull FileInfo fileInfo) {
        ImageInfo info = new ImageInfo();
        info.imageFilePath = fileInfo.filePath;

        return info;
    }
}
