
1.屏幕适配时 values-xhdpi-1280x720应该是先匹配xhdpi再匹配后面的，是向下匹配的  比如1280x720的如果没有这个values则会匹配到下一级，
例如values-1080x600

string.xml中可以添加xliff标签来占位，可以在代码中添加字符或者数字
```
<string name="info">
your name is <xliff:g id="NAME">%1$s</xliff:g>, and your age is
<xliff:g id="AGE">%2$s</xliff:g>
   </string>
```
使用xliff时需要在resource标签里添加 `xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2" ` 
   
2.listview中的textview如果设置了ellipsize="marquee"即跑马灯，滚动的时候会卡顿，而且可能有的item文字显示不出来！

3.android studio查找所有中文  正则匹配表达式  `^((?!(\*|//)).)+[\u4e00-\u9fa5]`

4 Android屏幕密度density和分辨率的关系~  此处的density在Android中为densityDpi，即每英寸有多少个显示点
px=dip*(dpi/160)  density=dpi/160

densityDpi    density    对应包
  160            1        mdpi
  120          0.75       ldpi
                          hdpi
						  x-hdpi
						  xxhdpi
						  xxxhdpi
						  
5.遇到不同jar包里面的类重复的时候，可以将其中一个jar包改成zip解压缩，删除掉重复的.class文件然后压缩成zip格式，
再改成.jar导入即可，操作顺序必须按照上述顺序。			

6.dialog有个坑，当使用Theme.AppCompat.Light.Dialog 这个theme的时候（不一定是theme的问题） 如果dialog的xml中最外层是wrap_content,内部的layout设置的宽度
超过一定的值就会显示不全，最外层也要设置为希望的宽度才行，原理暂时不明	  


7.RecyclerView有个坑，其item的布局 根的高度不能用match_parent，不然会导致下面的数据都显示不出来，只显示一条

8.TabLayout有个坑，tabMode设置为fix的时候 即使设置了tabTextApearence也有可能字体会被压缩，因为一行放不下那么多，设置为scrollable或者 tabPadding修改一下可以解决