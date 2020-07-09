# flutter命令卡死
有时候 pub get 等命令执行的时候会报 Waiting for another flutter command to release the startup lock ，此时可能好久都无法进行下一步

windows可以通过kill dart.exe来解决
打开cmd，输入
```
taskkill /F /IM dart.exe
```
或者通过任务管理器关闭 dart.exe也行（可能需要重启编译器）


# flutter web 运行的时候可能会提示
Failed to establish connection with the application instance in Chrome
此时可以尝试运行 flutter clean 
或者使用release模式运行
```
flutter run -d chrome --release
```
 
# flutter LayoutBuilder
layoutBuilder可以根据parent的 constraints来决定 child的布局等内容，也可以通过这个来获取parent的最大最小宽高

# flutter List用于row

```
List<Widget> _buildWidgets()=>[]
```
如上通过方法build一个widgetlist 想用在Row 或者column中 
可以这样写(相当于级联)
```
Row(children:[
Text('xxx'),
..._buildWidgets()]); 
```