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
    public static final String SENIORITY_HIGH = "高级资深干员";

    //其他标签  输出 防护 生存 治疗 费用回复 群攻 减速 支援 快速复活 削弱 位移 召唤 控场 爆发
    public static final String EXTERNAL_ATTACK = "输出";
    public static final String EXTERNAL_DEFEND = "防护";
    public static final String EXTERNAL_LIVE = "生存";
    public static final String EXTERNAL_CURE = "治疗";
    public static final String EXTERNAL_COST_RECOVER = "费用回复";
    public static final String EXTERNAL_GROUP_ATTACK = "群攻";
    public static final String EXTERNAL_REDUCE = "减速";
    public static final String EXTERNAL_SUPPORT = "支援";
    public static final String EXTERNAL_FAST_RELIVE = "快速复活";
    public static final String EXTERNAL_WEAKEN = "削弱";
    public static final String EXTERNAL_MOVE = "位移";
    public static final String EXTERNAL_SUMMON = "召唤";
    public static final String EXTERNAL_CONTROL = "控场";
    public static final String EXTERNAL_ERUPT = "爆发";

    public static final List<Official> sAllOfficials;
    public static final List<String> sAllTags;

    static {
        sAllTags = new ArrayList<>();
        sAllTags.add(FEMALE);
        sAllTags.add(MALE);
        sAllTags.add(PROFESSION_SNIPER);
        sAllTags.add(PROFESSION_WARLOCK);
        sAllTags.add(PROFESSION_VANGUARD);
        sAllTags.add(PROFESSION_GUARD);
        sAllTags.add(PROFESSION_TANK);
        sAllTags.add(PROFESSION_DOCTOR);
        sAllTags.add(PROFESSION_HELPER);
        sAllTags.add(PROFESSION_SPECIAL);
        sAllTags.add(POSITION_MELEE);
        sAllTags.add(POSITION_RANGED);
        sAllTags.add(SENIORITY_NEWER);
        sAllTags.add(SENIORITY_SENIOR);
        sAllTags.add(SENIORITY_HIGH);
        sAllTags.add(EXTERNAL_ATTACK);
        sAllTags.add(EXTERNAL_DEFEND);
        sAllTags.add(EXTERNAL_LIVE);
        sAllTags.add(EXTERNAL_CURE);
        sAllTags.add(EXTERNAL_COST_RECOVER);
        sAllTags.add(EXTERNAL_GROUP_ATTACK);
        sAllTags.add(EXTERNAL_REDUCE);
        sAllTags.add(EXTERNAL_SUPPORT);
        sAllTags.add(EXTERNAL_FAST_RELIVE);
        sAllTags.add(EXTERNAL_WEAKEN);
        sAllTags.add(EXTERNAL_MOVE);
        sAllTags.add(EXTERNAL_SUMMON);
        sAllTags.add(EXTERNAL_CONTROL);
        sAllTags.add(EXTERNAL_ERUPT);

        sAllOfficials = new ArrayList<>();
        //术士组
        sAllOfficials.add(new Official("伊芙利特", "6", FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGH, EXTERNAL_GROUP_ATTACK, EXTERNAL_WEAKEN));
// 艾雅法拉 公招不出       sAllOfficials.add(new Official("艾雅法拉", "6", FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_HIGH));
        sAllOfficials.add(new Official("夜烟", "4", FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, null, EXTERNAL_ATTACK, EXTERNAL_WEAKEN));
        sAllOfficials.add(new Official("远山", "4", FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, null, EXTERNAL_GROUP_ATTACK));
        sAllOfficials.add(new Official("史都华德", "3", MALE, PROFESSION_WARLOCK, POSITION_RANGED, null, EXTERNAL_ATTACK));
        sAllOfficials.add(new Official("炎熔", "3", FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, null, EXTERNAL_GROUP_ATTACK));
        sAllOfficials.add(new Official("12F", "2", MALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_NEWER));
        sAllOfficials.add(new Official("杜林", "2", FEMALE, PROFESSION_WARLOCK, POSITION_RANGED, SENIORITY_NEWER));

        //狙击组
        sAllOfficials.add(new Official("能天使", "6", FEMALE, PROFESSION_SNIPER, POSITION_RANGED, SENIORITY_HIGH, EXTERNAL_ATTACK));
        sAllOfficials.add(new Official("蓝毒", "5", FEMALE, PROFESSION_SNIPER, POSITION_RANGED, SENIORITY_SENIOR, EXTERNAL_ATTACK));


        //重装组

        //先锋组

        //近卫组

        //特种组

        //医疗组

        //辅助组
    }

    public static List<SearchOfficialResult> findOfficialsByTags(String... tags) {
        List<SearchOfficialResult> results = new ArrayList<>();
        if (tags == null || tags.length == 0)
            return results;

        for (int i = 1; i < 6; i++) {
            List<List<String>> conditionList = getConditionListWithCount(i, tags);
            if (conditionList == null || conditionList.isEmpty()) {
                continue;
            }
            for (int j = 0, len = conditionList.size(); j < len; j++) {
                SearchOfficialResult result = new SearchOfficialResult();
                List<String> condition = conditionList.get(j);//其中的一种tag组合
                result.searchConditionList = condition;
                for (int k = 0, allOfficialCount = sAllOfficials.size(); k < allOfficialCount; k++) {
                    Official official = sAllOfficials.get(k);
                    if (official.satisfy(condition)) {
                        result.addOfficial(official);
                    }
                }
                if (result.hasSatisfiedOfficials()) {
                    results.add(result);//只加有满足条件的
                }
            }
        }

        return results;
    }

    /**
     * @param selectConditionCount
     * @param tags
     * @return
     */
    public static List<List<String>> getConditionListWithCount(int selectConditionCount, String... tags) {
        if (tags == null || tags.length < selectConditionCount || selectConditionCount > 5 || selectConditionCount <= 0)
            return null;

        List<List<String>> conditionsList = new ArrayList<>();

        int len = tags.length;
        if (selectConditionCount == 1) {
            for (int i = 0; i < len; i++) {
                List<String> oneConditionList = new ArrayList<>();
                oneConditionList.add(tags[i]);
                conditionsList.add(oneConditionList);
            }
        } else if (selectConditionCount == 2) {
            for (int i = 0; i < len - 1; i++) {
                for (int j = i + 1; j < len; j++) {
                    List<String> twoConditionList = new ArrayList<>();
                    twoConditionList.add(tags[i]);
                    twoConditionList.add(tags[j]);
                    conditionsList.add(twoConditionList);
                }
            }
        } else if (selectConditionCount == 3) {
            for (int i = 0; i < len - 2; i++) {
                for (int j = i + 1; j < len - 1; j++) {
                    for (int k = j + 1; k < len; k++) {
                        List<String> threeConditionList = new ArrayList<>();
                        threeConditionList.add(tags[i]);
                        threeConditionList.add(tags[j]);
                        threeConditionList.add(tags[k]);
                        conditionsList.add(threeConditionList);
                    }
                }
            }
        } else if (selectConditionCount == 4) {
            for (int i = 0; i < len - 3; i++) {
                for (int j = i + 1; j < len - 2; j++) {
                    for (int k = j + 1; k < len - 1; k++) {
                        for (int l = k + 1; l < len; l++) {
                            List<String> fourConditionList = new ArrayList<>();
                            fourConditionList.add(tags[i]);
                            fourConditionList.add(tags[j]);
                            fourConditionList.add(tags[k]);
                            fourConditionList.add(tags[l]);
                            conditionsList.add(fourConditionList);
                        }
                    }
                }
            }
        } else if (selectConditionCount == 5) {
            for (int i = 0; i < len - 4; i++) {
                for (int j = i + 1; j < len - 3; j++) {
                    for (int k = j + 1; k < len - 2; k++) {
                        for (int l = k + 1; l < len - 1; l++) {
                            for (int m = 0; m < len; m++) {
                                List<String> fiveConditionList = new ArrayList<>();
                                fiveConditionList.add(tags[i]);
                                fiveConditionList.add(tags[j]);
                                fiveConditionList.add(tags[k]);
                                fiveConditionList.add(tags[l]);
                                fiveConditionList.add(tags[m]);
                                conditionsList.add(fiveConditionList);
                            }
                        }
                    }
                }
            }

        }
        return conditionsList;
    }

}
