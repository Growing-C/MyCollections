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

## adb常用命令

// 列出进程列表 ，其中包含进程的 pid 等信息
```
adb shell ps
```

// 杀死指定pid的进程
```
adb shell kill pid
```

// 查看指定进程信息
```
adb shell ps -x pid 
```

// 把 pic目录下所有文件从电脑上拷贝到设备sd卡上
```
adb push ~/pic/ /mnt/sdcard/
```

// 把 a.png 从电脑上拷贝到设备sd卡上
```
adb push ~/a.png /mnt/sdcard/
```

// 把 a.png 从电脑上拷贝到设备sd卡上并重命名为 b.png
```
adb push ~/a.png /mnt/sdcard/b.png
```

// 把 a.png 从设备sd卡上拷贝到电脑上并命名为b.png
```
adb pull /mnt/sdcard/a.png ~/b.png
```

// 截屏放到指定目录下
```
adb shell screencap /sdcard/1.png
```

## adb 模拟按键
// 模拟输入，其中 %s 代表空格
adb shell input text "hello%sworld"

// 模拟按键，82 代表 KEYCODE_MENU 即菜单键
// 更多KEYCODE可以参考 http://developer.android.com/intl/zh-cn/reference/android/view/KeyEvent.html
adb shell input keyevent 82

// 模拟点击，屏幕上横坐标纵坐标分别为100 120的位置
// 要查看具体坐标值，可以打开开发者选项->指针位置
adb shell input tap 100 120

// 模拟滑动，从位置（0,1000）滑动到(800,600)
adb shell input swipe 0 1000 800 600

// 模拟长按，在位置（100,200）长按500毫秒
adb shell input swipe 100 200 100 200 500

## adb 重启app
adb shell am force-stop com.xiaopeng.montecarlo
adb shell pm clear com.xiaopeng.montecarlo 清空应用程序数据


adb shell am start -n com.xiaopeng.montecarlo/com.xiaopeng.montecarlo.MainActivity  启动