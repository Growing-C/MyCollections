package com.cgy.mycollections.functions.file;

import androidx.annotation.NonNull;

import android.text.TextUtils;

import com.cgy.mycollections.utils.FileUtil;
import com.cgy.mycollections.utils.PinYinUtils;

import java.io.File;
import java.io.Serializable;

import appframe.utils.TimeUtils;

/**
 * Description :
 * Author :cgy
 * Date :2019/7/25
 */
public class FileInfo implements Serializable {
    private File file;
    private String fileName;
    private String filePath;
    //  getAbsolutePath:/storage/emulated/0/baidu
    //  getPath:/storage/emulated/0/baidu
    //  getName:baidu
    public String fileFirstLetter;

    public int protectState = 0;//保护状态 0 未保护 1 已保护
    public long addProtectDate = 0;//添加保护的日期

    public FileInfo(@NonNull File file) {
        this.file = file;
        this.fileName = file.getName();
        this.filePath = file.getPath();
    }

    public File getFile() {
        return file;
    }

    /**
     * 获取完整的文件名
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 获取完整的文件名，然后移除隐藏标志，供外部显示
     *
     * @return
     */
    public String getShowFileNameWithoutHideFilter() {
        return FileUtil.getFileOriginName(fileName);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        if (file.isDirectory())
            return FileConstants.FILE_TYPE_DIR;

        return FileConstants.FILE_TYPE_FILE;
    }

    /**
     * 文件名拼音首字母
     *
     * @return
     */
    public String getNameFirstLetter() {
        if (!TextUtils.isEmpty(fileName) && TextUtils.isEmpty(fileFirstLetter)) {
            fileFirstLetter = PinYinUtils.getFirstLetter(fileName, true);//汉字转换成拼音

            // 正则表达式，判断首字母是否是英文字母
            if (!fileFirstLetter.matches("[A-Z]")) {
                fileFirstLetter = "#";
            }
        }
        return fileFirstLetter;
    }

    /**
     * 获取上次修改文件的时间
     *
     * @return
     */
    public String getLastModifyTime() {
        if (file != null) {
            return TimeUtils.getTime(file.lastModified());
        }
        return "";
    }

    /**
     * 获取添加保护的时间
     *
     * @return
     */
    public String getAddProtectDate() {
        if (addProtectDate > 0)
            return TimeUtils.getTime(addProtectDate);
        return "";
    }

    public int getDirChildCount(boolean containHideFiles) {
        int childCount = 0;
        if (file.isDirectory()) {
            String[] children = file.list();

            if (children != null) {
                childCount = children.length;
                if (!containHideFiles) {
                    for (String childName : children) {
                        if (childName.startsWith(".")) {//文件名会为空吗？
                            childCount--;
                        }
                    }
                }
            }
        }
        return childCount;
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    public String getFileLengthWithUnit() {
        long lengthInByte = file.length();
        return FileUtil.formatFileSize(lengthInByte);
    }
}
