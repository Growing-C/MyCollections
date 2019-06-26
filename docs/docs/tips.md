 一目了然，for (；；)指令少，不占用寄存器，而且没有判断跳转，比while (1)好。 

?attr/selectableItemBackground设置view的波纹效果，如果其父控件有背景色，该view的基本颜色会和父控件一致，如果父控件没有，则和父控件的父控件一致，直到找到一个颜色
?attr/selectableItemBackground定义的文件可以在"sdk\platforms\android-xx\data\res\valuesdata\res\values\themes.xml"中找到 为
<item name="selectableItemBackground">@drawable/item_background</item>   item_background文件可以在 sdk\platforms\android-xx\data\res\drawable中找到


有的？attr/ 的内容可能不可以在app的theme 中定义 比如?android:attr/toastFrameBackground 这个Toast的背景，因为 toastFrameBackground没有设置为exported 没有写在public.xml中，意味着 非安卓平台自身的app无法自定义这个属性



## app内安装
如果app内安装老是报解析包错误，有多种可能原因
1.一个可能是android 7.0  8.0 安装需要用provider
2.6.0也出现的话  很可能是 跳转到安装页面的时候  那个安装界面不输出app  所以无法访问app的私有文件夹里的安装文件！
解决办法   
```
String[] command = {"chmod", "777", apkFile.getPath()};
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
```
用命令修改文件夹权限。
此时可能还是无法安装 ，可能是因为  设置- 安全中 允许未知来源的应用 权限 未打开！！  需要打开才能正常安装


## 打开谷歌play
1.通过 链接在浏览器中打开googleplay网址
http://play.google.com/store/apps/details?id=com.truecaller&hl=en
2.通过 链接 在浏览器中打开googlePlay app
https://play.app.goo.gl/?link=https://play.google.com/store/apps/details?id=io.blockchainlock.bclgo