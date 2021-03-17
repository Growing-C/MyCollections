@echo off
chcp 65001 
rem chcp 65001是为了解决中文乱码 

set LEFT_TAB=0
set RIGHT_TAB=0
set USE_BROAD=1
rem 1代表使用广播，2代表使用vdt

:selectWay
set /p w=请选择操作方式 （1广播，2vdt命令，3退出）
if /i '%w%'=='1' ( 
set USE_BROAD=1
goto endSelectWay
)
if /i '%w%'=='2' (
set USE_BROAD=2
goto endSelectWay
)
if /i '%w%'=='3' goto end
echo 输入有误，请重新输入：&&goto selectWay 
:endSelectWay
 

if /i '%USE_BROAD%'=='1' echo 选择了使用广播


if /i '%USE_BROAD%'=='2' (
echo 选择了使用vdt命令
)



:start
echo;
set /p a=请选择要操作的内容 （1左卡片列表，2右卡片列表，3底部状态栏，4充放电界面，5主界面，6指示灯，7卡片内容，8退出）
if /i '%a%'=='1' goto leftCard
if /i '%a%'=='2' goto rightCard
if /i '%a%'=='3' goto bottomBar
if /i '%a%'=='4' goto chargePage
if /i '%a%'=='5' goto mainPage
if /i '%a%'=='6' goto indicatorLight
if /i '%a%'=='7' goto showCard
if /i '%a%'=='8' goto end
echo 输入有误，请重新输入：&&goto start 


:mainPage
if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_CHARGE --ei charge_state -1
) else (
adb shell vdt rp HOST_MCU_CHARGING_STATE 0
)
echo 显示了主界面
goto start


::左边卡片逻辑
:leftCard

if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_SHOW_LIST --ei list_pos 0 --ez list_visible true --ei list_index %LEFT_TAB%
) else (
adb shell vdt rp HOST_ICM_SYNC_SIGNAL '"{\"SyncMode\":\"LeftSwitchMode\",\"msgId\":\"\",\"SyncProgress\":1}"'
)

echo 显示了左卡片列表 
call :selectTab %LEFT_TAB% 0
set LEFT_TAB=%tab% 
goto start


%右边卡片逻辑%
:rightCard
if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_SHOW_LIST --ei list_pos 1 --ez list_visible true --ei list_index %RIGHT_TAB%
) else (
adb shell vdt rp HOST_ICM_SYNC_SIGNAL '"{\"SyncMode\":\"RightSwitchMode\",\"msgId\":\"\",\"SyncProgress\":1}"'
)

echo 显示了右卡片列表
call :selectTab %RIGHT_TAB% 1
set RIGHT_TAB=%tab% 
goto start


%指示灯%
:indicatorLight
echo;
set /p a=请选择要操作的内容 （1指示灯全开，2指示灯全关，3返回）
if /i '%a%'=='1' (
call :operateIndicator 1
)
if /i '%a%'=='2' (
call :operateIndicator 2
)
if /i '%a%'=='3' goto start

goto indicatorLight




%前雷达预警%
:frontRadar
echo 显示前雷达预警
if /i '%USE_BROAD%'=='1' (

echo 广播暂不支持

) else (
adb shell vdt rp HOST_ERADAR_LEVEL_FCL 3
adb shell vdt rp HOST_ERADAR_SPACING_FCL 140
adb shell vdt rp HOST_ERADAR_LEVEL_FSR 1
adb shell vdt rp HOST_ERADAR_FSR 60
adb shell vdt rp HOST_ERADAR_LEVEL_FSL 1
adb shell vdt rp HOST_ERADAR_FSL 60
adb shell vdt rp HOST_ERADAR_LEVEL_FR 2
adb shell vdt rp HOST_ERADAR_SPACING_FOR 100
adb shell vdt rp HOST_ERADAR_LEVEL_FL 2
adb shell vdt rp HOST_ERADAR_SPACING_FOL 100
adb shell vdt rp HOST_ERADAR_LEVEL_FCR 3
adb shell vdt rp HOST_ERADAR_SPACING_FCR 140
)

goto start



%开关全部指示灯%
:operateIndicator
set indicatorOperate=%1

if /i '%USE_BROAD%'=='1' (
echo 暂不支持广播

 
rem adb shell vdt rp HOST_VCU_CURRENT_GEARLEV 1 会导致其他命令无响应
rem adb shell vdt rp HOST_BCM_AIRBAG_FAULT_ST 1
rem adb shell vdt rp HOST_VCU_IND_12V_BAT 1
goto :eof
)  
if /i '%indicatorOperate%'=='1' (

echo 打开全部指示灯

adb shell vdt rp HOST_VCU_RAW_CAR_SPEED 120
adb shell vdt rp HOST_VCU_CURRENT_GEARLEV 1
adb shell vdt rp HOST_ESP_ABS_FAULT 1
adb shell vdt rp HOST_LTURNLAMP_RTURNLAMP_ST 0 0
adb shell vdt rp HOST_BCM_2NDLEFTSEAT_BELTSBR_WARNING 2
adb shell vdt rp HOST_BCM_2NDMIDSEAT_BELTSBR_WARNING 2
adb shell vdt rp HOST_BCM_2NDRIGHTSEAT_BELTSBR_WARNING 2
adb shell vdt rp HOST_BCM_DOOR 1 1 1 1
adb shell vdt rp HOST_BCM_EDRIVER_SEAT 1
adb shell vdt rp HOST_ESP_IND_LIGHT_AVH 2
adb shell vdt rp HOST_BCM_PARKING_LAMP 1
adb shell vdt rp HOST_BCM_ELOW_BEAM 1
adb shell vdt rp HOST_HIGH_BEAM_LIGHTS_STATE 1
adb shell vdt rp HOST_ESP_APB_FUNC_LAMP 1
adb shell vdt rp HOST_BCM_EPSNGR_SEAT 1
adb shell vdt rp HOST_ESP_SYS_WARNIND_REQ 1
adb shell vdt rp HOST_ESP_BOOST_FAULT_ST 1
adb shell vdt rp HOST_VCU_CHARGE_GUN_STATUS 1
adb shell vdt rp HOST_TPMS_SYSFAULTWARN 1
adb shell vdt rp HOST_SCU_MRR_FCW_WARNING_ST 1
adb shell vdt rp HOST_FOG_LIGHTS_SWITCH_XP 1
adb shell vdt rp HOST_BCM_SMART_HIGHBEAM_ST 1
adb shell vdt rp HOST_ESP_WARN_LAMP 1
adb shell vdt rp HOST_ESP_ESP_FAULT 1
adb shell vdt rp HOST_ESC_ESP 1
adb shell vdt rp HOST_XPU_AUTO_PILOT_ST 1
adb shell vdt rp HOST_VCU_EVERRLAMP_DSP 1
adb shell vdt rp HOST_VCU_EMOTOR_SYS_HOT_DISP 1
adb shell vdt rp HOST_VCU_BMS_SOCLOW_STATUS 1
adb shell vdt rp HOST_VCU_BATCOLD_DISP 1
adb shell vdt rp HOST_VCU_BATT_FAULT_DISP 1
adb shell vdt rp HOST_VCU_HV_CUTOFF_DISP 1
adb shell vdt rp HOST_VCU_POWERLIMITATION_DSP 1
adb shell vdt rp HOST_VCU_COOLANT_OVERHEAT_ST 1
adb shell vdt rp HOST_MCU_EACC_CC_LIGHT 2
adb shell vdt rp HOST_VCU_EVSYS_READYST 1

) else (
echo 关闭全部指示灯
adb shell vdt rp HOST_ESP_ABS_FAULT 0
adb shell vdt rp HOST_LTURNLAMP_RTURNLAMP_ST 0 0
adb shell vdt rp HOST_BCM_2NDLEFTSEAT_BELTSBR_WARNING 0
adb shell vdt rp HOST_BCM_2NDMIDSEAT_BELTSBR_WARNING 0
adb shell vdt rp HOST_BCM_2NDRIGHTSEAT_BELTSBR_WARNING 0
adb shell vdt rp HOST_BCM_DOOR 0 0 0 0
adb shell vdt rp HOST_BCM_EDRIVER_SEAT 0
adb shell vdt rp HOST_ESP_IND_LIGHT_AVH 0
adb shell vdt rp HOST_BCM_PARKING_LAMP 0
adb shell vdt rp HOST_BCM_ELOW_BEAM 0
adb shell vdt rp HOST_HIGH_BEAM_LIGHTS_STATE 0
adb shell vdt rp HOST_ESP_APB_FUNC_LAMP 0
adb shell vdt rp HOST_BCM_EPSNGR_SEAT 0
adb shell vdt rp HOST_ESP_SYS_WARNIND_REQ 0
adb shell vdt rp HOST_ESP_BOOST_FAULT_ST 0
adb shell vdt rp HOST_VCU_CHARGE_GUN_STATUS 0
adb shell vdt rp HOST_TPMS_SYSFAULTWARN 0
adb shell vdt rp HOST_BCM_AIRBAG_FAULT_ST 0
adb shell vdt rp HOST_VCU_IND_12V_BAT 0
adb shell vdt rp HOST_SCU_MRR_FCW_WARNING_ST 0
adb shell vdt rp HOST_FOG_LIGHTS_SWITCH_XP 0
adb shell vdt rp HOST_BCM_SMART_HIGHBEAM_ST 0
adb shell vdt rp HOST_ESP_WARN_LAMP 0
adb shell vdt rp HOST_ESP_ESP_FAULT 0
adb shell vdt rp HOST_ESC_ESP 0
adb shell vdt rp HOST_XPU_AUTO_PILOT_ST 0
adb shell vdt rp HOST_VCU_EVERRLAMP_DSP 0
adb shell vdt rp HOST_VCU_EMOTOR_SYS_HOT_DISP 0
adb shell vdt rp HOST_VCU_BMS_SOCLOW_STATUS 0
adb shell vdt rp HOST_VCU_BATCOLD_DISP 0
adb shell vdt rp HOST_VCU_BATT_FAULT_DISP 0
adb shell vdt rp HOST_VCU_HV_CUTOFF_DISP 0
adb shell vdt rp HOST_VCU_POWERLIMITATION_DSP 0
adb shell vdt rp HOST_VCU_COOLANT_OVERHEAT_ST 0
)

goto :eof




::底部状态栏逻辑
:bottomBar
if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei battery 77
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei battery_state 2
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei out_temp 5
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei drive_mode 3
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei bl_standard 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei endurance_mileage 555
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei time_mode 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei time_mn 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --es time "3:14"
) else (
adb shell vdt rp HOST_VCU_RANDIS_MODE 0
adb shell vdt rp HOST_VCU_DSTBAT_DISP_NEDC 999
adb shell vdt rp HOST_VCU_DSTBAT_DISP_WLTP 999
adb shell vdt rp HOST_VCU_BMS_SOCDISP 40
adb shell vdt rp HOST_ENV_OUTSIDE_TEMPERATURE 20
adb shell vdt rp HOST_VCU_DRIVE_MODE 1
)
echo 显示了底部状态栏
goto start



%充放电界面%
:chargePage

set /p cp=请选择充放电状态(1-12状态，0退出)

if /i '%cp%'=='0' goto start

if /i '%USE_BROAD%'=='1' (
echo 广播暂不支持
goto chargePage
)

if /i '%cp%'=='1' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 1

adb shell vdt rp HOST_VCU_RANDIS_MODE 0
adb shell vdt rp HOST_VCU_DSTBAT_DISP_NEDC 605
adb shell vdt rp HOST_VCU_BMS_SOCDISP 40
adb shell vdt rp HOST_ECHARGE_STATUS 1
)

if /i '%cp%'=='2' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 2
adb shell vdt rp HOST_ECHARGE_LIMIT_TIME 200
adb shell vdt rp HOST_ECHARGE_CUR 2 2 50 0 0
adb shell vdt rp HOST_ECHARGE_VOLTAGE 2 2 200
)

if /i '%cp%'=='3' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 3
)

if /i '%cp%'=='4' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 4
)

if /i '%cp%'=='5' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 5
)

if /i '%cp%'=='6' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 6
adb shell vdt rp HOST_TBOX_APPOINT_CHG_SET_HOUR 10
adb shell vdt rp HOST_TBOX_APPOINT_CHG_SET_MIN 55
)

if /i '%cp%'=='7' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 7
adb shell vdt rp HOST_EBAT_PREPARE_TIME 555
)

if /i '%cp%'=='8' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 8

)

if /i '%cp%'=='9' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 9

)

if /i '%cp%'=='10' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 10

)

if /i '%cp%'=='11' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 11

)

if /i '%cp%'=='12' (
adb shell vdt rp HOST_MCU_CHARGING_STATE 12

)
goto chargePage


%选择左边或者右边卡片%
:selectTab 
echo;
set tab=%1
set tabPos=%2
echo 调用了选择tab,currentTab:%tab%
echo 0-地图
echo 1-音乐
echo 2-车况
echo 3-能耗

echo 4-里程

echo 5-传感器

:selectLoop
echo 当前tab是%tab%
set /p select=请选择要操作的内容 (1下一项，2上一项，3确定选择，4退出)

if /i '%USE_BROAD%'=='1' if /i '%tab%'=='0' if /i '%select%'=='2' echo 输入有误，请重新输入：&&goto selectLoop

if /i '%USE_BROAD%'=='1' if /i '%tab%'=='5' if /i '%select%'=='1' echo 输入有误，请重新输入：&&goto selectLoop


if /i '%select%'=='1' (
 set /a tab=%tab%+1
 call :listDown %tabPos%
 goto selectLoop
) else if /i '%select%'=='2' (
 set /a tab=%tab%-1
 call :listUp %tabPos%
 goto selectLoop
) else if /i '%select%'=='3' (
 echo 列表确定了当前选择 :%tab%
 call :selectCard %tabPos%
rem call :showCard %tab%
 goto selectLoopEnd
) else if /i '%select%'=='4' ( 
 goto selectLoopEnd
) else (
 echo 输入有误，请重新输入：&&goto selectLoop
)
:selectLoopEnd
echo;
goto :eof


:listDown
set selctListPos=%1

if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_LIST_DOWN --ei list_pos %selctListPos%
goto :eof
)

rem 选中左边
if /i '%selctListPos%'=='0' (
adb shell vdt rp HOST_ICM_WHEEL_KEY '"{\"wheelKey\":1082}"'
) else (
adb shell vdt rp HOST_ICM_WHEEL_KEY '"{\"wheelKey\":1084}"'
)
goto :eof


:listUp
set selctListPos=%1

if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_LIST_UP --ei list_pos %selctListPos%
goto :eof
)

rem 选中左边
if /i '%selctListPos%'=='0' (
adb shell vdt rp HOST_ICM_WHEEL_KEY '"{\"wheelKey\":1081}"'
) else (
adb shell vdt rp HOST_ICM_WHEEL_KEY '"{\"wheelKey\":1083}"'
)
goto :eof


rem 选中list中的某个card
:selectCard
set selctListPos=%1

if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_LIST_SELECT --ei list_pos %selctListPos%
goto :eof
)

rem 选中左边
if /i '%selctListPos%'=='0' (
adb shell vdt rp HOST_ICM_SYNC_SIGNAL '"{\"SyncMode\":\"LeftSwitchMode\",\"msgId\":\"\",\"SyncProgress\":0}"'
) else (
adb shell vdt rp HOST_ICM_SYNC_SIGNAL '"{\"SyncMode\":\"RightSwitchMode\",\"msgId\":\"\",\"SyncProgress\":0}"'
)
goto :eof






goto cardLog
* 0-地图
* 1-音乐
* 2-车况
* 3-能耗
* 4-里程
* 5-传感器
根据index选择展示的card是哪一个
:cardLog
:showCard
echo.
set /p index=请选择要显示的内容 （0导航卡片，1音乐卡片，2能耗卡片，3车况卡片，4里程卡片，5传感器卡片，6雷达卡片，7退出）

echo showCard index:%index%

if /i '%index%'=='0' (
 call :showNavi
) else if /i '%index%'=='1' (
 call :showMusic
) else if /i '%index%'=='2' (
 call :showEnergy
) else if /i '%index%'=='3' (
 call :showCarCondition
) else if /i '%index%'=='4' (
 call :showOdometer
) else if /i '%index%'=='5' (
 call :showSensor
) else if /i '%index%'=='6' (
simulation_radar_data.sh
)  else if /i '%index%'=='7' (
 call :start
) else (
echo 输入有误，请重新输入：&&goto showCard 
)

goto showCard



@rem 显示导航卡片
:showNavi
echo call showNavi
goto :eof

rem 显示音乐卡片
:showMusic
echo call showMusic
goto :eof

%显示能耗卡片%
:showEnergy
if /i '%USE_BROAD%'=='1' (
echo 广播暂不支持
) else (
adb shell vdt rp HOST_EAVG_VEHELCCONSP 60
simulation_power_consumption_data.sh
)
echo call showEnergy
goto :eof

::显示车况卡片
:showCarCondition
if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei endurance_mileage 444
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei lf_door_state 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei rf_door_state 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei lf_tire_state 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei lb_tire_state 1
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --es lf_tire_pressure "3.5"
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --es lb_tire_pressure "3.2"
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei tc_state 1
) else (
adb shell vdt rp HOST_BCM_DOOR 1 1 1 1
adb shell vdt rp HOST_BCM_BONNET 0
adb shell vdt rp HOST_BCM_R_CHARGER_PORT 0
adb shell vdt rp HOST_BCM_L_CHARGER_PORT 0
adb shell vdt rp HOST_BCM_TRUNKAJAR 0
adb shell vdt rp HOST_ETPMS_TEMPERATURE_LF 0 70
adb shell vdt rp HOST_ETPMS_TEMPERATURE_RF 0 50
adb shell vdt rp HOST_ETPMS_TEMPERATURE_LB 0 60
adb shell vdt rp HOST_ETPMS_TEMPERATURE_RB 0 40
adb shell vdt rp HOST_ETPMS_STATUS_LF 1
adb shell vdt rp HOST_ETPMS_STATUS_LB 1
adb shell vdt rp HOST_ETPMS_STATUS_RF 1
adb shell vdt rp HOST_ETPMS_STATUS_RB 1
adb shell vdt rp HOST_ETPMS_PRESSURE_LF 3.1
rem adb shell vdt rp HOST_ETPMS_PRESSURE_LB 3.2
adb shell vdt rp HOST_ETPMS_PRESSURE_RF 3.3
rem adb shell vdt rp HOST_ETPMS_PRESSURE_RB 3.4
)
echo call showCarCondition
goto :eof

rem 显示里程卡片
:showOdometer
if /i '%USE_BROAD%'=='1' (
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --es dis_after_start "200"
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --es total_dis "123456"
adb shell am broadcast -a com.xiaopeng.instrument.ACTION_DATA --ei a_energy 15
) else (
adb shell vdt rp HOST_EAVG_VEHELCCONSP 60
adb shell vdt rp HOST_ICM_TRIP_SINC_IGON_TIME 222 777
adb shell vdt rp HOST_ICM_TRIP_SINC_CHRG_TIME 123 1
adb shell vdt rp HOST_ICM_TOTAL_ODOMETER 9993
)
echo showOdometer
goto :eof


%显示传感器卡片%
:showSensor
echo showSensor
goto :eof


pause

:end
@exit