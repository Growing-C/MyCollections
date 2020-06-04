#flutter命令卡死
有时候 pub get 等命令执行的时候会报 Waiting for another flutter command to release the startup lock ，此时可能好久都无法进行下一步

windows可以通过kill dart.exe来解决
打开cmd，输入
```
taskkill /F /IM dart.exe
```
或者通过任务管理器关闭 dart.exe也行（可能需要重启编译器）
 