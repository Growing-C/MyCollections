package com.cgy.mycollections.functions.file;

import androidx.annotation.NonNull;

import android.text.TextUtils;

import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import com.cgy.mycollections.utils.FileUtil;
import com.cgy.mycollections.utils.PinYinUtils;
import com.cgy.mycollections.utils.image.ImageHelper;

import java.io.File;
import java.io.Serializable;

import appframe.utils.L;
import appframe.utils.TimeUtils;

/**
 * Description :
 * Author :cgy
 * Date :2019/7/25
 */
public class FileInfo implements Serializable {
    private File showFile;//可能是原始文件，也可能是隐藏后的文件但是还是使用未隐藏时的路径，所以可能不存在
    private File realFile;//真实存在的原始文件，当file不存在的时候（可能被隐藏）找到对应的原始文件
    private String fileName;
    private String filePath;
    //  getAbsolutePath:/storage/emulated/0/baidu
    //  getPath:/storage/emulated/0/baidu
    //  getName:baidu
    public String fileFirstLetter;
    public String fileType;//文件类型

    public int protectState = 0;//保护状态 0 未保护 1 已保护
    public long addProtectDate = 0;//添加保护的日期

    public FileInfo(@NonNull File file) {
        this.showFile = file;
        this.fileName = file.getName();
        this.filePath = file.getPath();
    }

    /**
     * 是否是文件夹
     *
     * @return
     */
    public boolean isDirectory() {
        return getRealFile().isDirectory();
    }

    /**
     * 获取文件，不保证一定存在
     *
     * @return
     */
    public File getShowFile() {
        return showFile;
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

    /**
     * 获取文件类型
     *
     * @return
     */
    public String getFileType() {
        if (!TextUtils.isEmpty(fileType)) {
            return fileType;
        }
        if (isDirectory())
            fileType = FileUtil.FILE_TYPE_DIR;
        else if (ImageHelper.isPicIgnoreDot(getRealFile().getPath())) {
            fileType = FileUtil.FILE_TYPE_IMAGE;
        } else {
            fileType = FileUtil.FILE_TYPE_FILE;
        }

        return fileType;
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
        if (getRealFile() != null) {
            return TimeUtils.getTime(getRealFile().lastModified());
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
        if (isDirectory()) {
            String[] children = getRealFile().list();

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
     * 获取真实文件,当file存在的时候和file一样
     * file不存在则根据隐藏规则找到隐藏后的文件（并不保证一定存在）
     *
     * @return
     */
    public File getRealFile() {
        if (realFile != null)
            return realFile;
        if (!showFile.exists()) {
            realFile = FileUtil.getOriginOrHiddenFileByFileState(showFile);
        } else {
            realFile = showFile;
        }
        return realFile;
    }

    /**
     * 获取文件大小
     *
     * @return
     */
    public String getFileLengthWithUnit() {
        long lengthInByte = getRealFile().length();
        return FileUtil.formatFileSize(lengthInByte);
    }
}
