1.
android studio显示乱码的时候去settings file encoding 改成gbk
右下角有个编码的地方也可以改
若安装app显示乱码 在gradle中添加
android {
    compileOptions.encoding = "GBK" 即可


2.当as app编译后 app上有个x 点运行提示Please select Android SDK的时候，
选择file-settings-android SDK 点击edit在出现的页面里一直next即可（原理是重新安装一下sdk，一般来说会提示nothing to do 但是问题能解决。）
猜测可能原因是 项目编译的时候sdk配置错误，上面的步骤大概是刷新了一下sdk的配置，所以问题解决了


3.有时候修改了引用的 aar包内容 引用maven上的aar包 还是会显示老的内容，因为gradle有缓存，可以通过
gradlew build --refresh-dependencies    来强制清除依赖刷新引用库

 是在 .idea/libraries 里会记录每一个第三方库的 classes, javadoc 以及 sources 所对应的路径。其中 classes 对应的正是前面所提到的 transforms-1 里的目录，同样也正如前面所说，其中包含的路径是有 hash 值的，更新了依赖之后，hash 值不同，新的缓存路径也就不同了，而这里还是用的原来被删的那个路径，找不到对应的文件当然编辑器里就提示 cannot resolve symbol 了。所以正确而直接的解决方案应该是删除 .idea/libraries/ 里对应该第三方库的 xml 文件让它重新生成，或者是直接修改该 xml 文件的内容，改为更新依赖之后的路径。



4. 一个gradle项目 引用的包 如：eagleBase:1.0.0 在git上面修改了内容 但是版本还是1.0.0  要更新项目中的引用 
使用gradlew --refresh-dependencies 命令刷新依赖库   
但是此时android studio上面还是无法识别的
需要删除 .idea/library/Gradl__eagleBase_1.0.0.xml  这个文件(此文件作用是 在External Libraries下面显示那个引用内容)
此文件目录引用的jar 指向
jar://$USER_HOME$/.android/build-cache/9b5d2cfede5006242603af689c6c3dea44e4e644/output/jars/classes.jar
删除后9b5d2cfede5006242603af689c6c3dea44e4e644 这个目录下文件还在！ 
然后 clean  build  sync来几下实在不行关了重开基本就能找到新的classes.jar了 工程中类也不会是红的了。
新的 dependency 应该是缓存在  \.gradle\caches\modules-2\files-2.1这下面
待研究 studio 如何把这个dependency和  .android下面的build-cache对应起来的


5.多个support v7冲突的话 可以用exclude 也可以
configurations.all {
    resolutionStrategy.eachDependency { details ->
        def requested = details.requested
       if (requested.group == 'com.android.support') {
            if (!requested.name.startsWith("multidex")) {
                details.useVersion '27.1.1'
            }
        }
    }
}

或者

configurations.all {
    resolutionStrategy {
        force 'com.android.support:support-v4:27.1.0'
    }
}


6.引入aar
在build.gradle的android 标签下增加
 repositories {
        flatDir {
            dirs 'libs'
        }
    }
把.aar文件放到lib中  dependency中添加   implementation(name: 'EagleBase-1.0.2', ext: 'aar') 即可