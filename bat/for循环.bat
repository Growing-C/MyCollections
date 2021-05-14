@echo off
chcp 65001 
rem chcp 65001是为了解决中文乱码 
 

set /a START=0
set /a END=5
echo 准备开始循环


:for

set /a START+=1
set /a oddNumber=START %% 2

if /i '%oddNumber%'=='0' (
echo %START%是偶数
adb shell vdt rp HOST_VCU_DRIVE_MODE 3
) else (
echo %START%是奇数
adb shell vdt rp HOST_VCU_DRIVE_MODE 2
)

if %START% LSS %END% (
echo %START% 小于 %END% 
goto for
) else (
echo %START% 大于等于 %END% 
pause
)

:forEnd




@exit