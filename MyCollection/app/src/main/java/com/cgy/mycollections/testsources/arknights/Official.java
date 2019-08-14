package com.cgy.mycollections.testsources.arknights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cgy.mycollections.testsources.arknights.OfficialHolder.SENIORITY_HIGH;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/12
 */
public class Official {

    public String officialName;
    public String star;//星级  1,2,3,4,5,6
    public String sex;//性别  男 女
    public String profession;//职业：  狙击  术师  先锋  近卫  重装  医疗 辅助  特种
    public String position;//  近战位 远程位
    public String seniority;//  新手  资深干员  高级资深干员

    public List<String> officialTags;//包含   性别，职业，位置，资历

    public Official(String name, String star, String sex, String profession, String position, String seniority, String... externalTags) {
        this.officialName = name;
        this.star = star;
        this.sex = sex;
        this.profession = profession;
        this.position = position;
        this.seniority = seniority;
        this.officialTags = new ArrayList<>();

        this.officialTags.add(star);
        this.officialTags.add(sex);
        this.officialTags.add(profession);
        this.officialTags.add(position);
        if (seniority != null && seniority.length() > 0)//3,4星是空的
            this.officialTags.add(seniority);
        if (externalTags != null && externalTags.length != 0) {
            Collections.addAll(this.officialTags, externalTags);//重复也没事
        }
    }

    /**
     * 满足目标tag组
     *
     * @param tags
     * @return
     */
    public boolean satisfy(List<String> tags) {
        if (tags == null || tags.size() == 0)
            throw new NullPointerException("tags 不能为空");

        //特殊逻辑 公招没有高资不出 6星
        if ("6".equals(star) && !tags.contains(SENIORITY_HIGH)) {
            return false;
        }

        for (String tag : tags) {
            if (!this.officialTags.contains(tag))
                return false;
        }
        return true;
    }
}
