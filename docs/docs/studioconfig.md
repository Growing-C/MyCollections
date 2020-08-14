#1.android studio 查看java源码
有时候点进java源码 会显示decompiled class,此时是因为studio默认的jre目录下没有java源码导致的。
jdk路径在 file->othersettings->defalut project structure中可以查看。
解决方法为，从jdk安装路径比如 
```
C:\Program Files\Java\jdk1.8.0_45
```
中复制一份src.zip
放到android studio默认的jdk路径下，如
```
C:\android\androidstudio4.0\jre
或者
C:\android\androidstudio4.0\jre\jre
```
(都放肯定可以解决)

#2.修改 默认的安卓配置 .AndroidStudio路径
在C:\android\androidstudio4.0\bin\idea.properties中加如下两行即可
```
idea.config.path=C:/android/androidhome/.AndroidStudio/config

idea.system.path=C:/android/androidhome/.AndroidStudio/system
```