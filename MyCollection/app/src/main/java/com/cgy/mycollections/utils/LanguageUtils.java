package com.cgy.mycollections.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Description :不完善，此处不应该有 zh  中文等特定的语言标识， 待改成全部用locale的方式
 * Author :cgy
 * Date :2018/11/9
 */
public class LanguageUtils {
    public static final String SHARED_PREF_LANGUAGE = "pref_language";
    public static final String KEY_LANGUAGE = "language";

    /**
     * 快速获取当前app的语言的context,最好在BaseActivity中加，因为现在语言每个界面都得设置
     * 见{@link android.app.Application#attachBaseContext(Context)}
     * 见{@link android.app.Activity#attachBaseContext(Context)}
     * 通过 super.attachBaseContext(LanguageUtils.getAppLanguageContext(context));来设置使用app内设置的语言
     *
     * @param context
     * @return
     */
    public static ContextWrapper getAppLanguageContext(Context context) {
        Locale appLanguage = getCurrentAppLanguage(context);
        if (appLanguage == null) {//默认语言的时候为空，需要使用系统默认
            appLanguage = getSystemLocale();
        }
        L.e("Language", "current language:" + appLanguage.getLanguage());
        return getLanguageContext(context, appLanguage);
    }

    /**
     * 根据 locale来获取 语言的context
     *
     * @param context
     * @param newLocale
     * @return
     */
    public static ContextWrapper getLanguageContext(@NonNull Context context, @NonNull Locale newLocale) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            L.e("Language", "Android NNNNNNN ");
//            configuration.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
//            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
//            L.e("Language", "KITKAT");
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);
        } else {
//            L.e("Language", "below KITKAT");
            configuration.setLocale(newLocale);
//            res.updateConfiguration(configuration, res.getDisplayMetrics());//不知道还需要不需要
            context = context.createConfigurationContext(configuration);
        }

        return new ContextWrapper(context);
    }

    /**
     * 设置app的语言
     *
     * @param context
     * @param locale
     */
    public static void setAppLanguage(Context context, Locale locale) {
        String language = null;
        if (locale == null) {
            //空的表示设置为跟随系统
        } else {
            language = locale.getLanguage();
        }
//            if (locale == Locale.ENGLISH) {
//            language = "en";//英语
//        } else if (locale == Locale.SIMPLIFIED_CHINESE) {
//            language = "zh";//简体中文
//        } else if (locale == Locale.JAPANESE) {
//            language = "jp";//日语
//        }
        storeLanguage(context, language);
    }

    /**
     * 获取当前app设置的语言,支持4种
     *
     * @param context
     * @return null-跟随系统   zh-简体中文  en-英语   jp-日语
     */
    public static Locale getCurrentAppLanguage(Context context) {
        String storedLanguage = getStoredLanguage(context);

        Locale locale = null;
        if (TextUtils.isEmpty(storedLanguage)) {
            //null表示是跟随系统语言,啥都不做
        } else {//不再使用 zh这种分辨模式，可扩展性更强
            locale = new Locale(storedLanguage);
        }
//        if (storedLanguage.equals("zh")) {//简体中文
//            locale = Locale.SIMPLIFIED_CHINESE;
//        } else if (storedLanguage.equals("en")) {//英语
//            locale = Locale.ENGLISH;
//        } else if (storedLanguage.equals("jp")) {//日语
//            locale = Locale.JAPANESE;
//        }

        return locale;
    }

    /**
     * 获取语言说明 如 中文
     *
     * @param context
     * @return
     */
    public static String getCurrentLanguageName(Context context) {
        Locale locale = getCurrentAppLanguage(context);
        if (locale == null) {//默认语言的时候为空，需要使用系统默认
            return null;
        }
        return locale.getDisplayLanguage(locale);

//        String storedLanguage = getStoredLanguage(context);
//
//        String languageName = null;
//        if (TextUtils.isEmpty(storedLanguage)) {
//            //null表示是跟随系统语言,啥都不做
//        } else if (storedLanguage.equals("zh")) {//简体中文
//            languageName = "中文";
//        } else if (storedLanguage.equals("en")) {//英语
//            languageName = "English";
//        } else if (storedLanguage.equals("jp")) {//日语
//            languageName = "日本語";
//        }
//        return languageName;
    }

    /**
     * 获取语言代号  如 zh
     *
     * @param context
     * @return
     */
    public static String getLanguageSymbol(Context context) {
        String lgType = Locale.getDefault().getLanguage();
        String result = getStoredLanguage(context);
        if (TextUtils.isEmpty(result))//默认语言
            result = lgType;
        return result;
    }

    /**
     * 获取系统的locale,注意{@link Locale#getDefault()}有坑，仅在application的attachBaseContext中正确
     * 如果在哪里设置过一次app内的语言后就不准确了，不能用于获取系统语言
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //此方法 getDefault不一定是系统的语言，在app进程开启 application的attachBaseContext中getDefault没问题，
            // 但是执行过一次getLanguageContext 之后getDefault就变了,变成 之前app内部设置的语言
            //所以不能用getDefault，需要使用Resources.getSystem().getConfiguration().getLocales()
//            locale = LocaleList.getDefault().get(0);
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
//            locale = Locale.getDefault();
            locale = Resources.getSystem().getConfiguration().locale;
        }
        return locale;
    }

    //<editor-fold desc="语言设置保存相关">

    /**
     * 保存设置的语言
     *
     * @param context
     * @param language
     * @description 获取应用语言（zh/en/ja)
     */
    public static void storeLanguage(Context context, String language) {
        SharePreUtil.putString(SHARED_PREF_LANGUAGE, context, KEY_LANGUAGE, language);
    }

    /**
     * 获取保存的语言
     *
     * @param context
     * @return
     */
    public static String getStoredLanguage(Context context) {
        return SharePreUtil.getString(SHARED_PREF_LANGUAGE, context, KEY_LANGUAGE);
    }

    /**
     * 清除设置的语言
     *
     * @return
     */
    public static void clearStoredLanguage(Context context) {
        SharePreUtil.clearPref(SHARED_PREF_LANGUAGE, context);
    }
    //</editor-fold>
}

