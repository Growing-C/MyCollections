当/system没有rw权限时，无法对system进行push等操作，一般情况下，都是通过mount获得system的rw权限，具体措施如下：

adb shell
mount -o rw,remount /system

mount 查看权限

其中adb shell 进入adb交互界面，mount -o rw,remount /system修改/system为读写权限(rw),mount的具体语法可用mount x查看，如下图所示


3.adb 打开app
adb shell am start -n com.android.chrome/com.google.android.apps.chrome.Main

## 权限相关
1.adb获取所有权限
```
adb shell pm list permissions
```

2.adb赋予app 某个权限
权限 xml路径 ： （详见/data/system/users/0/runtime-permissions.xml）


EDIT: It turns out that adb shell pm grant only works on emulators. Production devices do not allow shell to grant and revoke optional permissions.
```
adb shell pm grant com.cgy.mycollections android.permission.READ_LOGS
```
这个可能会报错
Operation not allowed: java.lang.SecurityException: grantRuntimePermission: Neither user 2000 nor current process has android.permission.GRANT_RUNTIME_PERMISSIONS.


## keytool 获取sha1
输入命令：keytool -list -v  -keystore keystore文件路径（如果当前在这个目录下 直接输入xxx.jks就行了，不需要加前面的路径）


## 获取dependencies 依赖树
./gradlew :app:dependencies >> ~/dependencieslog.txt
或
./gradlew app:dependencies

后面带有 “(*)” 的库就表示 这个库 有被覆盖过。

最后使用的是最高版本