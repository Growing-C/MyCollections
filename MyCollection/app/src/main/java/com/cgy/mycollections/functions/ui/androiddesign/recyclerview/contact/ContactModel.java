package com.cgy.mycollections.functions.ui.androiddesign.recyclerview.contact;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cgy.mycollections.utils.PinYinUtils;

/**
 * Description :
 * Author :cgy
 * Date :2018/11/26
 */
public class ContactModel implements Comparable<ContactModel> {
    public String name;
    public String mobile;

    public String nameFirstLetter;


    /**
     * 姓名拼音首字母
     *
     * @return
     */
    public String getNameFirstLetter() {
        if (!TextUtils.isEmpty(name) && TextUtils.isEmpty(nameFirstLetter)) {
            nameFirstLetter = PinYinUtils.getFirstLetter(name, true);//汉字转换成拼音

            // 正则表达式，判断首字母是否是英文字母
            if (!nameFirstLetter.matches("[A-Z]")) {
                nameFirstLetter = "#";
            }
        }
        return nameFirstLetter;
    }

    @Override
    public int compareTo(@NonNull ContactModel o) {
        if (getNameFirstLetter().equals("@")
                || o.getNameFirstLetter().equals("#")) {
            return 1;
        } else if (getNameFirstLetter().equals("#")
                || o.getNameFirstLetter().equals("@")) {
            return -1;
        } else {
            return getNameFirstLetter().compareTo(o.getNameFirstLetter());
        }
    }
}
