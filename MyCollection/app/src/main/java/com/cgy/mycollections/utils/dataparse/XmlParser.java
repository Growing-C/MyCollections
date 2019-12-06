package com.cgy.mycollections.utils.dataparse;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/7/30
 */
public class XmlParser {
    /**
     * 读取asset中的xml文件内容
     * <p>
     * assetPath示例 "config/apps.xml"
     *
     * @param context
     * @return
     */
    public static List<String> getAllApps(Context context, String assetPath) {
        if (context == null)
            return null;
        List<String> allApps = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(assetPath);
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(is, "UTF-8");

            String content = null;
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        allApps = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("application")) {
                        } else if (xmlPullParser.getName().equalsIgnoreCase("name")) {
                            xmlPullParser.next();
                            content = xmlPullParser.getText();
                        } else if (xmlPullParser.getName().equalsIgnoreCase("icon")) {
                            xmlPullParser.next();
                            content = xmlPullParser.getText();
                        } else if (xmlPullParser.getName().equalsIgnoreCase("route")) {
                            xmlPullParser.next();
                            content = xmlPullParser.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("application")) {
                            allApps.add(content);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allApps;
    }
}
