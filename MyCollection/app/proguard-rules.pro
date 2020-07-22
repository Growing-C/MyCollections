# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\cgy\software\studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#---------------------------------默认保留区---------------------------------
# 保持Parcelable不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保持哪些类不被混淆
#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity{*;}
-keep public class * extends android.app.Application{
     public <methods>;
}

-keep public class * extends androidx.fragment.app.Fragment{*;}
-keep public class * extends android.support.v4.app.Fragment{*;}
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}## 保留support下的所有类及其内部类
-keep class androidx.appcompat.** {*;}## 保留support下的所有类及其内部类

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
#表示不混淆上面声明的类，最后这两个类我们基本也用不上，是接入Google原生的一些服务时使用的。

#-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class android.webkit.WebView {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
-keep class com.linkstec.eagle.base.component.WebViewComponent {*;}
-keep class com.linkstec.eagle.base.component.WebViewComponent$* {*;}
#----------------------------------------------------------------------------
#----------------------


-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

#-------------------------------------------定制化区域----------------------------------------------


-keep class com.linkstec.eagle.base.callbacks.** {*;}
-keep class com.linkstec.eagle.base.permission.** {*;}
-keep class com.linkstec.eagle.base.utils.** {*;}
-keep class com.linkstec.eagle.base.model.** {*;}
-keep class com.linkstec.eagle.base.module.** {*;}
-keep class com.linkstec.eagle.base.adapter.** {*;}
-keep class com.linkstec.eagle.base.widget.** {*;}
-keep public class com.linkstec.eagle.base.http.callbacks.** {*;}
#---------------------------------1.实体类---------------------------------

-keep public class * extends com.linkstec.eagle.base.model.BaseModel{*;}
-keep public class com.linkstec.eagle.base.update.DownloadApkTask{*;}
-keep public class com.linkstec.eagle.base.update.UpdateChecker{
   <methods>;
}
-keep public class com.linkstec.eagle.base.update.UpdateDialog{
   *;
}
-keep public class com.linkstec.eagle.base.update.UpdateInterface{*;}
-keep public class com.linkstec.eagle.base.http.api.BaseRxAction{
   *;
}
-keep public class com.linkstec.eagle.base.pop.**{
   *;
}
-keep public class com.linkstec.eagle.base.http.converters.**{
   *;
}
-keep public class com.linkstec.eagle.base.http.**{
   *;
}

-keepclasseswithmembers class com.linkstec.eagle.base.http.api.Api{
   public *;
}
-keep class com.linkstec.eagle.base.core.Constants{ *;}
-keep class com.linkstec.eagle.base.core.Constants$*{ *;}
-keep class com.linkstec.eagle.base.core.ActivityStack{ *;}
-keep class com.linkstec.eagle.base.core.BaseTabActivity{ *;}
#-------------------------------------------------------------------------


#---------------------------------2.与js互相调用的类------------------------

-keep class com.linkstec.eagle.base.web.** {*;}


#-------------------------------------------------------------------------

#---------------------------------3.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------
-dontshrink     #不要删除未使用的类
-ignorewarnings                # 抑制警告
#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-verbose #打印混淆详细信息
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------4.第三方包-------------------------------

-dontnote org.apache.http.**
-dontnote android.net.http.**

# -------fixme:org.greenrobot:eventbus:3.0.0---------
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep enum com.linkstec.eagle.base.enums.FloatColor { *; }
#-keep public interface com.linkstec.eagle.base.core.BaseActivity$OnFloatBtnCLickListener{ *; }
#-keep public interface com.linkstec.eagle.base.core.BaseFragment$OnFloatBtnCLickListener{ *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# ------TODO： com.orhanobut:logger:2.1.1 -----------------

# ------fixme:com.alibaba:arouter-api -----------------
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# If you use the byType method to obtain Service, add the following rules to protect the interface:
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# If @Autowired is used for injection in non-Activity classes, add the following rules to prevent injection failures
#-keepnames class * {
#    @com.alibaba.android.arouter.facade.annotation.Autowired <fields>;
#}

# ------fixme:com.squareup.okhttp3:okhttp -----------------
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# ------fixme:com.squareup.retrofit2-----------------
# Retrofit does reflection on generic parameters and InnerClass is required to use Signature.
-keepattributes Signature, InnerClasses

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

# ------fixme: rxjava-----------------
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# ------fixme:bugly -----------------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#-------------------------------------------------------------------------

