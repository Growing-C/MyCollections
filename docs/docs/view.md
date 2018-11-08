#Dialog
6.dialog有个坑，当使用Theme.AppCompat.Light.Dialog 这个theme的时候（不一定是theme的问题） 如果dialog的xml中最外层是wrap_content,内部的layout设置的宽度
超过一定的值就会显示不全，最外层也要设置为希望的宽度才行，原理暂时不明	  

#RecyclerView
7.RecyclerView有个坑，其item的布局 根的高度不能用match_parent，不然会导致下面的数据都显示不出来，只显示一条

#TabLayout
8.TabLayout有个坑，tabMode设置为fix的时候 即使设置了tabTextApearence也有可能字体会被压缩，因为一行放不下那么多，设置为scrollable或者 tabPadding修改一下可以解决

#fragment
9.fragment相关
fragment嵌套的时候 ，在一个fragment的布局xml中嵌套一个 <fragment> 要获取这个fragment对象的时候 在宿主fragment中必须使用 getChildFragmentManager.findFragmentById才能找到，
直接用 getFragmentManager是找不到的！   在childFragment中使用getParentFragment可以获取到parent fragment

#listview
2.listview中的textview如果设置了ellipsize="marquee"即跑马灯，滚动的时候会卡顿，而且可能有的item文字显示不出来！

#EditText
12.EditTExt限制输入字符方便的xml方式 
android:digits="1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"