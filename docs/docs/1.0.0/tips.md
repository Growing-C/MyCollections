 一目了然，for (；；)指令少，不占用寄存器，而且没有判断跳转，比while (1)好。 

?attr/selectableItemBackground设置view的波纹效果，如果其父控件有背景色，该view的基本颜色会和父控件一致，如果父控件没有，则和父控件的父控件一致，直到找到一个颜色
?attr/selectableItemBackground定义的文件可以在"sdk\platforms\android-xx\data\res\valuesdata\res\values\themes.xml"中找到 为
<item name="selectableItemBackground">@drawable/item_background</item>   item_background文件可以在 sdk\platforms\android-xx\data\res\drawable中找到


有的？attr/ 的内容可能不可以在app的theme 中定义 比如?android:attr/toastFrameBackground 这个Toast的背景，因为 toastFrameBackground没有设置为exported 没有写在public.xml中，意味着 非安卓平台自身的app无法自定义这个属性


