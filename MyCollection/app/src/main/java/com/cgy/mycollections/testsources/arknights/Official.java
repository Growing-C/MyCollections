package com.cgy.mycollections.testsources.arknights;

import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/12
 */
public class Official {

    public String officialName;
    public int star;//星级  1,2,3,4,5,6
    public String sex;//性别  男 女
    public String profession;//职业：  狙击  术师  先锋  近卫  重装  医疗 辅助  特种
    public String position;//  近战位 远程位
    public String seniority;//  新手  资深干员  高级资深干员

    public List<String> tags;//包含   性别，职业，位置，资历

    public Official(String name, int star, String sex, String profession, String position, String seniority) {
        this.officialName = name;
        this.star = star;
        this.sex = sex;
        this.profession = profession;
        this.position = position;
        this.seniority = seniority;
    }
}
