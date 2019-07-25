package com.cgy.mycollections.functions.file;

import android.text.TextUtils;

import com.cgy.mycollections.utils.PinYinUtils;

import java.io.File;

import appframe.utils.TimeUtils;

/**
 * Description :
 * Author :cgy
 * Date :2019/7/25
 */
public class FileInfo {
    public File file;
    public String fileName;

    public String fileFirstLetter;

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

    public String getLastModifyTime() {
        if (file != null) {
            return TimeUtils.getTime(file.lastModified());
        }
        return "";
    }

    public String getInfo() {
        String info = "";
        if (file.isDirectory()) {
            info += file.list().length + "项  ";
        } else {
            info += file.length() + "B  ";
        }
        info += getLastModifyTime();

        return info;
    }
}
