package com.cgy.mycollections.functions.net.retrofit.params;

import android.text.TextUtils;

/**
 * Description :
 * Author :cgy
 * Date :2019/9/27
 */
public class DynamicLinkParam {

//                {
//                    "dynamicLinkInfo": {
//                    "domainUriPrefix": "https://blockchainlock.page.link",
//                            "link": "https://www.example.com/",
//                            "androidInfo": {
//                        "androidPackageName": "com.example.android"
//                    },
//                    "iosInfo": {
//                        "iosBundleId": "com.example.ios"
//                    },
//                "suffix": {
//                    "option": "SHORT" or "UNGUESSABLE"
//                }
//                }
//                }

    public DynamicLinkParam() {
        dynamicLinkInfo = new DynamicLinkInfo();
        suffix = new Suffix();
    }

    public DynamicLinkInfo dynamicLinkInfo;

    public Suffix suffix;

    public void setDomainUriPrefix(String domainUriPrefix) {
        dynamicLinkInfo.domainUriPrefix = domainUriPrefix;
    }

    public void setLink(String link) {
        dynamicLinkInfo.link = link;
    }

    public void setAndroidPackageName(String name) {
        dynamicLinkInfo.androidInfo = new AndroidInfo();
        dynamicLinkInfo.androidInfo.androidPackageName = name;
    }

    public void setIosBundleId(String id) {
        if (!TextUtils.isEmpty(id)) {
            dynamicLinkInfo.iosInfo = new IosInfo();
            dynamicLinkInfo.iosInfo.iosBundleId = id;
        }
    }

    public void setSuffix(String suffix) {
        this.suffix.option = suffix;
    }

    class DynamicLinkInfo {
        public String domainUriPrefix;
        public String link;
        public AndroidInfo androidInfo;
        public IosInfo iosInfo;
    }

    class AndroidInfo {
        public String androidPackageName;
    }

    class IosInfo {
        public String iosBundleId;
    }

    class Suffix {
        public String option;
    }
}
