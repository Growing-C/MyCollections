# 语法

可以查看 [Showcase](https://www.cnblogs.com/bugly/p/7085469.html).


-keep [,modifier，...] class_specification
指定需要保留的类和类成员（作为公共类库，应该保留所有可公开访问的public方法）

-keepclassmembers [,modifier，...] class_specification
指定需要保留的类成员:变量或者方法

-keepclasseswithmembers [,modifier，...] class_specification
指定保留的类和类成员，条件是所指定的类成员都存在（既在压缩阶段没有被删除的成员，效果和keep差不多）

-keepnames class_specification
[-keep allowshrinking class_specification 的简写]
指定要保留名称的类和类成员，前提是在压缩阶段未被删除。仅用于模糊处理

-keepclassmembernames class_specification
[-keepclassmembers allowshrinking class_specification 的简写]
指定要保留名称的类成员，前提是在压缩阶段未被删除。仅用于模糊处理

-keepclasseswithmembernames class_specification
[-keepclasseswithmembers allowshrinking class_specification 的简写]
指定要保留名称的类成员，前提是在压缩阶段后所指定的类成员都存在。仅用于模糊处理
 



# 混淆注意事项
 
1，jni方法不可混淆，因为这个方法需要和native方法保持一致；

```
-keepclasseswithmembernames class * { # 保持native方法不被混淆    
    native <methods>;
}
```
2，反射用到的类不混淆(否则反射可能出现问题)；

3，AndroidMainfest中的类不混淆，所以四大组件和Application的子类和Framework层下所有的类默认不会进行混淆。自定义的View默认也不会被混淆；所以像网上贴的很多排除自定义View，或四大组件被混淆的规则在Android Studio中是无需加入的；

!>实际操作中发现Application子类 Activity子类之类的全都混淆了！所以可能还是需要加上的

```

#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}## 保留support下的所有类及其内部类

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
#表示不混淆上面声明的类，最后这两个类我们基本也用不上，是接入Google原生的一些服务时使用的。
```

4，与服务端交互时，使用GSON、fastjson等框架解析服务端数据时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象；

5，使用第三方开源库或者引用其他第三方的SDK包时，如果有特别要求，也需要在混淆文件中加入对应的混淆规则；

6，有用到WebView的JS调用也需要保证写的接口方法不混淆，原因和第一条一样；

7，Parcelable的子类和Creator静态成员变量不混淆，否则会产生Android.os.BadParcelableException异常；

```
# 保持Parcelable不被混淆    
-keep class * implements Android.os.Parcelable {       
    public static final Android.os.Parcelable$Creator *;
}
```
8，使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，见第二条规则。
```
-keepclassmembers enum * {  
    public static **[] values();  
    public static ** valueOf(java.lang.String);  
}
```
 

9.如果因为混淆编译 提示先解决warnning，可以在proguard中增加
```
-ignorewarnings                # 抑制警告
```

10.如果出现`Required: PROJECT. Found:xxx `可以删除build文件夹再重试


11.最简单的不要删除无用类的方法
```
-dontshrink     #不要删除未使用的类
```

# 一些配置

```
-keepnames class com.dev.demo.one.ClassOneThree*{*;}```

- ***allowoptimization*** 指定对象可能会被优化，即使他们被keep选项保留。所指定对象可能会被改变（优化步骤），但可能不会被混淆或者删除。该修饰符只对实现异常要求有用。
- ***allowobfuscation*** 指定对象可能会被混淆，即使他们被keep保留。所指定对象可能会被重命名，但可能不会被删除或者优化。该修饰符只对实现异常要求有用。
***
###Shrinking Options *压缩选项*
压缩是默认开启的。压缩会删除没有使用的类以及类成员，除了由各种“-keep”选项列出的类和它们直接或间接依赖的类。 在每个优化步骤之后都会进行压缩步骤，因为一些优化后可能会出现更多可以删除的类及类成员
**-dontshrink**
关闭压缩
**-printusage** [*filename*]
列出被那些未使用的代码，并可输出到指定文件。仅用于收缩阶段
**-whyareyoukeeping** *class_specification*
打印指定的类在压缩阶段为什么会保留其类、类成员的详细信息
***
###Optimization Options *优化选项*
优化是默认情况下启用的;。所有方法都在字节码级进行优化。
**-dontoptimize**
关闭优化
**-optimizations** *optimization_filter*
指定更精细级别的优化，仅用于优化阶段
**-optimizationpasses** *n*
指定优化次数，默认情况下1次。多次可以更进一步优化代码，如果在一次优化后没有改进则优化结束。仅用于优化阶段
**-assumenosideeffects** *class_specification*
指示没有任何副作用的类方法。优化过程中如果确定这些方法没有被调用或者返回值没有被使用则删除它们。ProGuard会分析出库代码以外的程序代码。如指定System.currentTimeMillis（）方法，任何对他的空闲调用将被删除。仅实用与优化。慎用！
**-allowaccessmodification**
指示容许修改类和类成员的访问修饰符，这可以改进优化结果。
**-mergeinterfacesaggressively**
指示容许合并接口，及时他们的实现类没有实现所有接口方法。这个可以减少类的总数来减小输出的大小。仅实用于优化
***
###Obfuscation Options  *混淆*
混淆是默认开启的。混淆使类和类成员名称变成短的随机名，被各种“-keep”选项保护的类、类成员除外。对调试有用的内部属性（源文件名称，变量名称，行号）将被删除。
**-dontobfuscate**
关闭混淆
**-printmapping** [*filename*]
打印旧名称到重命名的类、类成员的新名称的映射关系，可输出到指定文件。
仅实用于混淆处理
**-applymapping** [*filename*]
指定文件为映射文件，混淆时映射文件中列出的类和类成员接收指定的名称，文件未提及的类和类成员接收新名称。
**-obfuscationdictionary** [*filename*]
指定一个文本文件，其中所有有效字词都用作混淆字段和方法名称。 默认情况下，诸如“a”，“b”等短名称用作混淆名称。 使用模糊字典，您可以指定保留关键字的列表，或具有外来字符的标识符，例如： 忽略空格，标点符号，重复字和＃符号后的注释。 注意，模糊字典几乎不改善混淆。 有些编译器可以自动替换它们，并且通过使用更简单的名称再次混淆，可以很简单地撤消该效果。 最有用的是指定类文件中通常已经存在的字符串（例如'Code'），从而减少类文件的大小。 仅适用于混淆处理。
**-classobfuscationdictionary** [*filename*]
指定一个文本文件，其中所有有效词都用作混淆类名。 与-obfuscationdictionary类似。 仅适用于混淆处理。
**-packageobfuscationdictionary** [*filename*]
指定一个文本文件，其中所有有效词都用作混淆包名称。与-obfuscationdictionary类似。 仅适用于混淆处理。
 **-overloadaggressively**
深度重载混淆。这个选项可能会使参数和返回类型不同的方法和属性混淆后获得同样的名字，这个选项会使代码更小（且不易理解），仅实用于混淆处理 

**-useuniqueclassmembernames**
指定为具有相同名称的类成员分配相同的混淆名称，并为具有不同名称（对于每个给定类成员签名）的类成员分配不同的混淆名称。 没有该选项，更多的类成员可以映射到相同的短名称，如'a'，'b'等。因此选项稍微增加了生成的代码的大小，但它确保保存的混淆名称映射可以始终 在随后的增量混淆步骤中受到尊重。

**-dontusemixedcaseclassnames**
混淆时不生成大小写混合的类名。 默认情况下，混淆的类名称可以包含大写字符和小写字符的混合。仅适用于混淆处理。

**-keeppackagenames** [*package_filter*]
指定不混淆给定的包名称。*package_filter*过滤器是以逗号分隔的包名称列表。包名称可以包含 **?**、** * **、** ** ** 通配符。仅适用于混淆处理。

**-flattenpackagehierarchy** [*package_name*]
将所有重命名后的包移动到给定的包中重新打包，如果没有参数或者空字符串，包将被移动到根包中，此选项进一步混淆包名称，可以使代码跟小更不易理解。仅适用于混淆处理。
**-repackageclasses** [*package_name*]
重新打包所有重命名的类文件，将它们移动到给定包中。 如果包中没有参数或一个空字符串，包被完全删除。 此选项将覆盖-flattenpackagehierarchy选项。  它可以使处理后的代码更小，更不容易理解。 它的已弃用名称是-defaultpackage。 仅适用于混淆处理。
**-keepattributes** [*attribute_filter*]
指定要保留的任何可选属性(注释...)。 可以使用一个或多个-keepattributes指令指定属性。 *attribute_filter*过滤器是以逗号分隔的属性名称列表。如果代码依赖于注释，则可能需要保留注释。 仅适用于混淆处理。
**-keepparameternames**
指定保留参数名称和保留的方法类型。 此选项实际上保留调试属性LocalVariableTable和LocalVariableTypeTable的修剪版本。 它在处理库时很有用。 一些IDE可以使用该信息来帮助使用库的开发人员，例如使用工具提示或自动完成。仅适用于混淆处理。
**-renamesourcefileattribute** [string]
指定要放在类文件的SourceFile属性（和SourceDir属性）中的常量字符串。 请注意，该属性必须以开头存在，因此也必须使用-keepattributes指令明确保留。 例如，您可能希望使已处理的库和应用程序生成有用的混淆堆栈跟踪。仅适用于混淆处理。
**-adaptclassstrings** [*class_filter*]
混淆和类名称对应的字符串常量，如果没有filter则匹配与类名称相对应的所有字符串常量，使用filter则仅匹配与filter匹配的类中的字符串常量。 仅适用于混淆处理。
**-adaptresourcefilenames** [*file_filter*]
根据相应类文件（如果有）的混淆名称指定要重命名的资源文件。 如果没有过滤器，则重命名与类文件相对应的所有资源文件。 使用过滤器，仅重命名匹配的文件。仅适用于混淆处理。
**-adaptresourcefilecontents** [*file_filter*]
指定要更新其内容的资源文件。 根据相应类的混淆名称（如果有）重命名资源文件中提到的任何类名。 如果没有过滤器，所有资源文件的内容都会更新。 使用过滤器，仅更新匹配的文件。 资源文件使用平台的默认字符集进行解析和编写。 您可以通过设置环境变量LANG或Java系统属性file.encoding来更改此默认字符集。 仅适用于混淆处理。
***
###Preverification Options *预验证*
预验证默认情况下启动的。能提高虚拟机的加载效率。
**-dontpreverify**
关闭预验证
**-microedition**
指定已处理的类文件针对Java Micro Edition。 然后，preverifier将添加适当的StackMap属性，这些属性与Java Standard Edition的默认StackMapTable属性不同。 例如，如果您正在处理midlet，则需要此选项。
***
###General Options *一般选项*
**-verbose**
混淆过程中打印详细信息，如果异常终止则打印整个堆栈信息
**-dontnote** [*class_filter*]
不打印配置类中可能的错误或遗漏的注释，如类名称中的拼写错误，或者可能有用的缺失选项。 可选*class_filter*是正则表达式; ProGuard不打印关于具有匹配名称的类的注释。
**-dontwarn** [*class_filter*]
不对指定的类、包中的不完整的引用发出警告
**-ignorewarnings**
忽略警告继续处理
**-printconfiguration** [*filename*]
打印配置信息，到指定文件。包括文件和替换的变量
**-dump** [*filename*]
打印类文件内部结构到指定文件
***
###<p id='classpath'>Class Paths</p>
*class_paths* 主要用以指示选项（***-injars、-outjars、-libraryjars***）的输入输出文件路径。ProGuard接受类路径的泛化。由一系列文件和分隔符组成(在Unix上为***':'***，windows上为***';'***)；文件的顺序决定着优先级。
***输入项可包括：***
  - class 文件或者 resource文件；
  - jar 文件， 可包含上述任意文件；
  - war文件，可包含上述任意文件；
  - ear文件， 可包含上述任意文件；
  - zip文件， 可包含上述任意文件；
  - 包含上面任意文件的目录结构。

直接指定类文件或者资源文件的路径会被忽略，类文件可作为jar文件、war、ear、zip或者目录结构的一个部分。此外，类文件的路径在归档或目录中不应有任何其他目录前缀。

***输出项可包括：***
  - jar 文件， 包含了所处理的所有class文件或者资源文件；
  - war文件，包含上述任意文件；
  - ear文件， 包含上述任意文件；
  - zip文件， 包含上述任意文件；
  - 包含上面任意文件的目录结构。

ProGuard 在输出时，会以合理的形式打包输出结果，根据需要重新构建输入文件。将所有类容写入输出目录是ProGuard最便捷的方式，输出目录包含了输入类容完整的重构。包可以是任意复杂的，可以是处理整个应用程序，zip文件中的包及其文档，将再次作为zip文件被处理。


ProGuard容许在文件名中称使用过滤器，它们能相对于完整的文件名过滤条一些文件或者其内容，过滤器位于括号()之间，他能支持5个类型以“***;***隔开”：
  - 对所有的zip文件名称的过滤；
  - 对所有的ear文件名称的过滤；
  - 对所有的war文件名称的过滤；
  - 对所有的jar文件名称的过滤；
  - 对于所有的class文件名，资源文件名。

括号中的过滤器如果少于5个，则它们将被假设为最后一个过滤器，空的过滤器将被忽视， 格式如下：
***classpathentry([[[[zipfilter;]earfilter;]warfilter;]jarfilter;]filefilter)***
***“[]”*** 表示其内容是可选的。

```

# 混淆模板

>简化版

```proguard

#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-verbose  #打印混淆日志
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
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
-keepclassmembers enum * {  
    public static **[] values();  
    public static ** valueOf(java.lang.String);  
}
# 保持Parcelable不被混淆   
-keep class * implements Android.os.Parcelable {         
    public static final Android.os.Parcelable$Creator *;
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#------------------------------------------------------------------------------------



#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------



#-------------------------------------------------------------------------

#---------------------------------2.与js互相调用的类-------------------------------



#-------------------------------------------------------------------------

#---------------------------------3.反射相关的类和方法------------------------



#-------------------------------------------------------------------------

#---------------------------------4.第三方包-----------------------




```

>详细说明版

```
#

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------

-optimizationpasses 5                   # 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
-dontusemixedcaseclassnames             # 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
-dontskipnonpubliclibraryclasses        # 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclassmembers   # 指定不去忽略包可见的库类的成员
-dontoptimize                           #不进行优化，建议使用此选项，
-dontpreverify                          # 不进行预校验,Android不需要,可加快混淆速度。
-ignorewarnings                         # 屏蔽警告 
-keepattributes *Annotation*            # 保护代码中的Annotation不被混淆
-keepattributes Signature               # 避免混淆泛型, 这在JSON实体映射时非常重要
-keepattributes SourceFile,LineNumberTable  # 抛出异常时保留代码行号

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

 #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
# 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
# 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
#指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
-allowaccessmodification

#当有优化和使用-repackageclasses时才适用。
-repackageclasses ''

 # 混淆时记录日志(打印混淆的详细信息)
 # 这句话能够使我们的项目混淆后产生映射文件
 # 包含有类名->混淆后类名的映射关系
-verbose

#
# ----------------------------- 默认保留 -----------------------------
#
#----------------------------------------------------

# 保持Parcelable不被混淆   
-keep class * implements Android.os.Parcelable {         
    public static final Android.os.Parcelable$Creator *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保持哪些类不被混淆
#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}## 保留support下的所有类及其内部类

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
#表示不混淆上面声明的类，最后这两个类我们基本也用不上，是接入Google原生的一些服务时使用的。
#----------------------------------------------------

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**


#表示不混淆任何包含native方法的类的类名以及native方法名，这个和我们刚才验证的结果是一致
-keepclasseswithmembernames class * {
    native <methods>;
}


#这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
#表示不混淆Activity中参数是View的方法，因为有这样一种用法，在XML中配置android:onClick=”buttonClick”属性，
#当用户点击该按钮时就会调用Activity中的buttonClick(View view)方法，如果这个方法被混淆的话就找不到了
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

#表示不混淆枚举中的values()和valueOf()方法，枚举我用的非常少，这个就不评论了
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#表示不混淆任何一个View中的setXxx()和getXxx()方法，
#因为属性动画需要有相应的setter和getter的方法实现，混淆了就无法工作了。
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

#表示不混淆Parcelable实现类中的CREATOR字段，
#毫无疑问，CREATOR字段是绝对不能改变的，包括大小写都不能变，不然整个Parcelable工作机制都会失败。
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
# 这指定了继承Serizalizable的类的如下成员不被移除混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留R下面的资源
#-keep class **.R$* {
# *;
#}
#不混淆资源类下static的
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#
#----------------------------- WebView(项目中没有可以忽略) -----------------------------
#
#webView需要进行特殊处理
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#在app中与HTML5的JavaScript的交互进行特殊处理
#我们需要确保这些js要调用的原生方法不能够被混淆，于是我们需要做如下处理：
-keepclassmembers class com.ljd.example.JSInterface {
    <methods>;
}

#
#---------------------------------实体类---------------------------------
#--------(实体Model不能混淆，否则找不到对应的属性获取不到值)-----
#
-dontwarn com.suchengkeji.android.confusiondemo.md.**
#对含有反射类的处理
-keep class com.suchengkeji.android.confusiondemo.md.** { *; }
#
# ----------------------------- 其他的 -----------------------------
#
# 删除代码中Log相关的代码
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**


#
# ----------------------------- 第三方 -----------------------------
#
-dontwarn com.orhanobut.logger.**
-keep class com.orhanobut.logger.**{*;}
-keep interface com.orhanobut.logger.**{*;}

-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}
#        。。。。。。 
```


# 常用三方包混淆

```

# ------- :org.greenrobot:eventbus:3.0.0---------
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# ------TODO： com.orhanobut:logger:2.1.1 -----------------

# ------ :com.alibaba:arouter-api -----------------
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

# ------ :com.squareup.okhttp3:okhttp -----------------
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# ------ :com.squareup.retrofit2-----------------
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

# ------ : rxjava-----------------
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

# ------ :bugly -----------------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
```