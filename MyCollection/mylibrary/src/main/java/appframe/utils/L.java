package appframe.utils;


import android.text.TextUtils;

import com.witon.mylibrary.BuildConfig;

import java.lang.reflect.Method;

/**
 * Description :Log util
 * Author :cgy
 * Date :2018/9/3
 */
public class L {
    private static final String METHOD_V = "v";
    private static final String METHOD_I = "i";
    private static final String METHOD_D = "d";
    private static final String METHOD_W = "w";
    private static final String METHOD_E = "e";
    private static final String METHOD_WTF = "wtf";

    /**
     * Library debug tag.
     */
    private static String STag = "linkstec";
    private static String SLogTagPrefix = "ML";//tag 前缀，所有的这个类打印的tag最前面都有这个
    /**
     * Library debug sign.
     */
    private static boolean SDebug = false;
    /**
     * Default length.
     * logcat一次输出最多这么多，再多就不见了
     */
    private static final int MAX_LENGTH = 4000;
    /**
     * Length of message.
     */
    private static int maxLength = MAX_LENGTH;

    /**
     * Set tag of log.
     *
     * @param tag tag.
     */
    public static void setTag(String tag) {
        STag = tag;
    }

    /**
     * Open debug mode of {@code NoHttp}.
     *
     * @param debug true open, false close.
     */
    public static void setDebug(boolean debug) {
        SDebug = debug;
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG || SDebug;
    }


    /**
     * Set the length of the line of the Log, value is {@value MAX_LENGTH}.
     *
     * @param length length.
     */
    public static void setMessageMaxLength(int length) {
        maxLength = length;
    }

    public static void i(String msg) {
        print(METHOD_I, msg);
    }

    public static void i(String tag, String msg) {
        print(METHOD_I, tag, msg);
    }

    public static void i(Throwable e) {
        i(e, "");
    }

    public static void i(Throwable e, String msg) {
        print(METHOD_I, msg, e);
    }

    public static void v(String msg) {
        print(METHOD_V, msg);
    }

    public static void v(String tag, String msg) {
        print(METHOD_V, tag, msg);
    }

    public static void v(Throwable e) {
        v(e, "");
    }

    public static void v(Throwable e, String msg) {
        print(METHOD_V, msg, e);
    }

    public static void d(String msg) {
        print(METHOD_D, msg);
    }

    public static void d(String tag, String msg) {
        print(METHOD_D, tag, msg);
    }

    public static void d(Throwable e) {
        d(e, "");
    }

    public static void d(Throwable e, String msg) {
        print(METHOD_D, msg, e);
    }

    public static void e(String msg) {
        print(METHOD_E, msg);
    }

    public static void e(String tag, String msg) {
        print(METHOD_E, tag, msg);
    }

    public static void e(Throwable e) {
        e(e, "");
    }

    public static void e(Throwable e, String msg) {
        print(METHOD_E, msg, e);
    }

    public static void w(String msg) {
        print(METHOD_W, msg);
    }

    public static void w(String tag, String msg) {
        print(METHOD_W, tag, msg);
    }

    public static void w(Throwable e) {
        w(e, "");
    }

    public static void w(Throwable e, String msg) {
        print(METHOD_W, msg, e);
    }

    public static void wtf(String msg) {
        print(METHOD_WTF, msg);
    }

    public static void wtf(Throwable e) {
        wtf(e, "");
    }

    public static void wtf(Throwable e, String msg) {
        print(METHOD_WTF, msg, e);
    }

    /**
     * Print log for define method. When information is too long, the Logger can also complete
     * printing. The equivalent of "{@code android.util.Log.i("Tag", "Message")}" "{@code
     * com.yolanda.nohttp.Logger.print("i", "Tag", "Message")}".
     *
     * @param method  such as "{@code v, i, d, w, e, wtf}".
     * @param message message.
     */
    private static void print(String method, String message) {
        print(method, STag, message);
    }

    /**
     * Print log for define method. When information is too long, the Logger can also complete
     * printing. The equivalent of "{@code android.util.Log.i("Tag", "Message")}" "{@code
     * com.yolanda.nohttp.Logger.print("i", "Tag", "Message")}".
     *
     * @param method  such as "{@code v, i, d, w, e, wtf}".
     * @param tag     tag.
     * @param message message.
     */
    public static void print(String method, String tag, String message) {
        if (isDebug()) {
            if (message == null)
                message = "null";
            int strLength = message.length();
            if (strLength == 0)
                invokePrint(method, tag, message);
            else {
                //大于最大长度的时候需要截取
                for (int i = 0; i < strLength / maxLength + (strLength % maxLength > 0 ? 1 : 0); i++) {
                    int end = (i + 1) * maxLength;
                    if (strLength >= end) {
                        printAutoNewLine(method, tag, message.substring(end - maxLength, end));
                    } else {
                        printAutoNewLine(method, tag, message.substring(end - maxLength));
                    }
                }
            }
        }
    }

    /**
     * 打印时自动换行
     *
     * @param method
     * @param tag
     * @param message
     */
    private static void printAutoNewLine(String method, String tag, String message) {
        if (!TextUtils.isEmpty(message)) {
            String[] singleLineMessage = message.split("\n");
            for (int i = 0, len = singleLineMessage.length; i < len; i++) {
                if (!TextUtils.isEmpty(singleLineMessage[i]))//过滤空行
                    invokePrint(method, i > 0 ? tag + randomKey() : tag, singleLineMessage[i]);
            }
        }
    }

    private static int last;

    /**
     * 由于studio 3.1之后会莫名其妙的换行以及前部时间等信息缺失，导致logger不对齐，此处logger使用这个strategy来防止这个情况
     *
     * @return
     */
    private static String randomKey() {
        int random = (int) (10 * Math.random());
        if (random == last) {
            random = (random + 1) % 10;
        }
        last = random;
        return String.valueOf(random);
    }

    /**
     * Through the reflection to call the print method.
     *
     * @param method  such as "{@code v, i, d, w, e, wtf}".
     * @param tag     tag.
     * @param message message.
     */
    private static void invokePrint(String method, String tag, String message) {
        try {
            Class<android.util.Log> logClass = android.util.Log.class;
            Method logMethod = logClass.getMethod(method, String.class, String.class);
            logMethod.setAccessible(true);
            logMethod.invoke(null, SLogTagPrefix + tag, message);
        } catch (Exception e) {
            System.out.println(tag + ": " + message);
        }
    }

    /**
     * Print log for define method. When information is too long, the Logger can also complete
     * printing. The equivalent of "{@code android.util.Log.i("Tag", "Message")}" "{@code
     * com.yolanda.nohttp.Logger.print("i", "Tag", "Message")}".
     *
     * @param method  such as "{@code v, i, d, w, e, wtf}".
     * @param message message.
     * @param e       error.
     */
    private static void print(String method, String message, Throwable e) {
        print(method, STag, message, e);
    }

    /**
     * Print log for define method. When information is too long, the Logger can also complete
     * printing. The equivalent of "{@code android.util.Log.i("Tag", "Message")}" "{@code
     * com.yolanda.nohttp.Logger.print("i", "Tag", "Message")}".
     *
     * @param method  such as "{@code v, i, d, w, e, wtf}".
     * @param tag     tag.
     * @param message message.
     * @param e       error.
     */
    private static void print(String method, String tag, String message, Throwable e) {
        if (isDebug()) {
            if (message == null)
                message = "null";
            invokePrint(method, tag, message, e);
        }
    }

    /**
     * Through the reflection to call the print method.
     *
     * @param method  such as "{@code v, i, d, w, e, wtf}".
     * @param tag     tag.
     * @param message message.
     * @param e       error.
     */
    private static void invokePrint(String method, String tag, String message, Throwable e) {
        try {
            Class<android.util.Log> logClass = android.util.Log.class;
            Method logMethod = logClass.getMethod(method, String.class, String.class, Throwable.class);
            logMethod.setAccessible(true);
            logMethod.invoke(null, SLogTagPrefix + tag, message, e);
        } catch (Exception e1) {
            System.out.println(tag + ": " + message);
        }
    }
}
