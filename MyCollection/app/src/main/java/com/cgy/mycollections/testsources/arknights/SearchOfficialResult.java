package com.cgy.mycollections.testsources.arknights;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/19
 */
public class SearchOfficialResult {
    public List<String> searchConditionList;

    public List<Official> officials;//满足条件的

    private int level3Count = 0;
    private int level4Count = 0;
    private int level5Count = 0;
    private int level6Count = 0;

    public void addOfficial(Official official) {
        if (officials == null)
            officials = new ArrayList<>();
        officials.add(official);
    }

    public boolean hasSatisfiedOfficials() {
        return officials != null && !officials.isEmpty();
    }


    @Override
    public String toString() {
        StringBuilder tags = new StringBuilder();
        for (int i = 0; i < searchConditionList.size(); i++) {
            tags.append(searchConditionList.get(i)).append(" ");
        }
        StringBuilder satisfied = new StringBuilder();
        if (hasSatisfiedOfficials()) {
            for (int i = 0, s = officials.size(); i < s; i++) {
                satisfied.append(officials.get(i).officialName).append(" ");
            }
        } else {
            satisfied.append("没有满足条件的干员");
        }
        return "条件：" + tags + "\n满足条件的干员：" + satisfied;
    }
}
