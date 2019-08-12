package com.cgy.mycollections.testsources.arknights;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/12
 */
public class OfficialHolder {

    public static final String FEMALE = "女";
    public static final String MALE = "男";

    //    狙击  术师  先锋  近卫  重装  医疗 辅助  特种
    public static final String PROFESSION_SNIPER = "狙击";
    public static final String PROFESSION_WARLOCK = "术士";
    public static final String PROFESSION_VANGUARD = "先锋";
    public static final String PROFESSION_GUARD = "近卫";
    public static final String PROFESSION_TANK = "重装";
    public static final String PROFESSION_DOCTOR = "医疗";
    public static final String PROFESSION_HELPER = "辅助";
    public static final String PROFESSION_SPECIAL = "特种";

    //位置  近战位 远程位
    public static final String POSITION_MELEE = "近战位";
    public static final String POSITION_RANGED = "远程位";

    //资历 新手  资深干员  高级资深干员
    public static final String SENIORITY_NEWER = "新手";
    public static final String SENIORITY_SENIOR = "资深干员";
    public static final String SENIORITY_HIGN = "高级资深干员";

    public static final List<Official> sAllOfficials;

    static {
        sAllOfficials = new ArrayList<>();
        sAllOfficials.add(new Official("伊芙利特", 6, FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGN));
        sAllOfficials.add(new Official("伊芙利特", 6, FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGN));
        sAllOfficials.add(new Official("伊芙利特", 6, FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGN));
        sAllOfficials.add(new Official("伊芙利特", 6, FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGN));
        sAllOfficials.add(new Official("伊芙利特", 6, FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGN));

    }


}
