package com.cgy.mycollections.testsources.arknights;

import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/8/12
 */
public class ArknightsTest {
    public static void main(String[] args) {
        List<SearchOfficialResult> results = OfficialHolder.findOfficialsByTags(OfficialHolder.SENIORITY_HIGH );

        if (results != null) {
            for (int i = 0, len = results.size(); i < len; i++) {
                System.out.println(results.get(i).toString());
            }
        }
    }
}
