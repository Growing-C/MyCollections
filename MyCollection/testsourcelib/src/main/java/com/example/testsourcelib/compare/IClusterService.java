package com.example.testsourcelib.compare;

/**
 * @author yangwq
 * @date 2020-12-17 12:50
 */
interface IClusterService {


    /*------------------------------------------------------------------------card control start --------------------------------------------------------*/

    void onLeftListVisible(boolean leftlistvisible);

    void onLeftListIndex(int leftlistindex);

    void onLeftListSensorFault(boolean leftlistsensorfault);

    void onLeftCardIndex(int leftcardindex);

    void onLeftCardVisible(boolean leftcardvisible);

    void onRightListVisible(boolean rightlistvisible);

    void onRightListIndex(int rightlistindex);

    void onRightCardIndex(int rightcardindex);

    void onRightCardVisible(boolean rightcardvisible);

    /*------------------------------------------------------------------------card control end --------------------------------------------------------*/




    /*------------------------------------------------------------------------map card start --------------------------------------------------------*/

    void onNavigationStyle(int navigationstyle);

    void onNavigationArrowID(int navigationarrowid);

    void onNavigationArrowState(boolean navigationarrowstate);

    void onNavigationDistance(String navigationdistance);

    void onNavigationDistanceUnits(String navigationdistanceunits);

    void onNavigationMoves(String navigationmoves);

    void onNavigationRoadName(String navigationroadname);

    void onNavigationGuidanceVisible(boolean navigationguidancevisible);

    void onNavigationEnabled(boolean navigationenabled);

    /*------------------------------------------------------------------------map card end --------------------------------------------------------*/



    /*------------------------------------------------------------------------车况 tire start --------------------------------------------------------*/

    void onTireFLTireState(int tirefltirestate);

    void onTireFLPressureState(int tireflpressurestate);

    void onTireFLPressure(String tireflpressure);

    void onTireFLPressureUnits(String tireflpressureunits);

    void onTireFLTemperaturesState(int tirefltemperaturesstate);

    void onTireFLTemperatures(String tirefltemperatures);

    void onTireFLTemperaturesUnits(String tirefltemperaturesunits);

    void onTireFRTireState(int tirefrtirestate);

    void onTireFRPressureState(int tirefrpressurestate);

    void onTireFRPressure(String tirefrpressure);

    void onTireFRPressureUnits(String tirefrpressureunits);

    void onTireFRTemperaturesState(int tirefrtemperaturesstate);

    void onTireFRTemperatures(String tirefrtemperatures);

    void onTireFRTemperaturesUnits(String tirefrtemperaturesunits);

    void onTireBLTireState(int tirebltirestate);

    void onTireBLPressureState(int tireblpressurestate);

    void onTireBLPressure(String tireblpressure);

    void onTireBLPressureUnits(String tireblpressureunits);

    void onTireBLTemperaturesState(int tirebltemperaturesstate);

    void onTireBLTemperatures(String tirebltemperatures);

    void onTireBLTemperaturesUnits(String tirebltemperaturesunits);

    void onTireBRTireState(int tirebrtirestate);

    void onTireBRPressureState(int tirebrpressurestate);

    void onTireBRPressure(String tirebrpressure);

    void onTireBRPressureUnits(String tirebrpressureunits);

    void onTireBRTemperaturesState(int tirebrtemperaturesstate);

    void onTireBRTemperatures(String tirebrtemperatures);

    void onTireBRTemperaturesUnits(String tirebrtemperaturesunits);

    /*------------------------------------------------------------------------tire end --------------------------------------------------------*/



    /*------------------------------------------------------------------------里程 start --------------------------------------------------------*/


    void onHoodEngine(boolean hoodengine);

    void onHoodFastCharge(int hoodfastcharge);

    void onHoodNormalCharge(int hoodnormalcharge);

    void onHoodTrunk(boolean hoodtrunk);

    void onThisTimeValue(String thistimevalue);

    void onThisTimeUnits(String thistimeunits);

    void onAfterChargingValue(String afterchargingvalue);

    void onAfterChargingUnits(String afterchargingunits);

    void onSubtotalAValue(String subtotalavalue);

    void onSubtotalAUnits(String subtotalaunits);

    void onSubtotalBValue(String subtotalbvalue);

    void onSubtotalBUnits(String subtotalbunits);

    void onTotalValue(String totalvalue);

    void onTotalUnits(String totalunits);

    void onAverageValue(String averagevalue);

    void onAverageUnits(String averageunits);

    void onElapsedTimeValue(String elapsedtimevalue);

    void onElapsedTimeUnits(String elapsedtimeunits);

    /*------------------------------------------------------------------------里程 end --------------------------------------------------------*/



    /*------------------------------------------------------------------------music card start --------------------------------------------------------*/

    void onMusicImageState(boolean musicimagestate);

    void onMusicPlayState(boolean musicplaystate);

    void onMusicSoundState(int musicsoundstate);

    void onMusicString1(String musicstring1);

    void onMusicString2(String musicstring2);

    void onMusicBarValue(int musicbarvalue);

    /*------------------------------------------------------------------------music card end --------------------------------------------------------*/




    /*------------------------------------------------------------------------sensor start --------------------------------------------------------*/


    void onMillimeterWaveRadarState(int millimeterwaveradarstate);

    void onMillimeterWaveRadarFC(int millimeterwaveradarfc);

    void onMillimeterWaveRadarFL(int millimeterwaveradarfl);

    void onMillimeterWaveRadarFR(int millimeterwaveradarfr);

    void onMillimeterWaveRadarRCL(int millimeterwaveradarrcl);

    void onMillimeterWaveRadarRCR(int millimeterwaveradarrcr);


    void onUltrasonicRadarState(int ultrasonicradarstate);

    void onUltrasonicRadarFCL(int ultrasonicradarfcl);

    void onUltrasonicRadarFCR(int ultrasonicradarfcr);

    void onUltrasonicRadarFOL(int ultrasonicradarfol);

    void onUltrasonicRadarFOR(int ultrasonicradarfor);

    void onUltrasonicRadarFSL(int ultrasonicradarfsl);

    void onUltrasonicRadarFSR(int ultrasonicradarfsr);

    void onUltrasonicRadarRCL(int ultrasonicradarrcl);

    void onUltrasonicRadarRCR(int ultrasonicradarrcr);

    void onUltrasonicRadarROL(int ultrasonicradarrol);

    void onUltrasonicRadarROR(int ultrasonicradarror);

    void onUltrasonicRadarRSL(int ultrasonicradarrsl);

    void onUltrasonicRadarRSR(int ultrasonicradarrsr);


    void onSensorFaultCarState(int sensorfaultcarstate);

    void onSmartCameraState(int smartcamerastate);

    void onSmartCameraSite1(int smartcamerasite1);

    void onSmartCameraSite2(int smartcamerasite2);

    void onSmartCameraSite3(int smartcamerasite3);

    void onSmartCameraSite4(int smartcamerasite4);

    void onSmartCameraSite5(int smartcamerasite5);

    void onSmartCameraSite6(int smartcamerasite6);

    void onSmartCameraSite7(int smartcamerasite7);

    void onSmartCameraSite8(int smartcamerasite8);


    void onLookAroundCameraState(int lookaroundcamerastate);

    void onLookAroundCameraF(int lookaroundcameraf);

    void onLookAroundCameraL(int lookaroundcameral);

    void onLookAroundCameraB(int lookaroundcamerab);

    void onLookAroundCameraR(int lookaroundcamerar);

    void onTextVisible(boolean textvisible);

    void onTextValue(String textvalue);


    /*------------------------------------------------------------------------sensor end --------------------------------------------------------*/



    /*------------------------------------------------------------------------waring start --------------------------------------------------------*/


    /*------------------------------------------------------------------------waring end --------------------------------------------------------*/


    //    void onImageGuidanceTexture(imageguidancetexture){
//
//    }
//    void onImageMusicTexture(imagemusictexture){
//
//    }
//    void onImageNaviTexture(imagenavitexture){
//
//    }


    /*------------------------------------------------------------------------能耗卡片 start --------------------------------------------------------*/

    void onPowerAverageEnergyConsumption(int poweraverageenergyconsumption);

    void onPowerCurveValue(float powercurvevalue);

    /*------------------------------------------------------------------------能耗卡片 end--------------------------------------------------------*/



    /*------------------------------------------------------------------------充放电 start--------------------------------------------------------*/

    void onAppointmentHour(String appointmenthour);

    void onAppointmentMinute(String appointmentminute);

    void onCompleteHour(String completehour);

    void onCompleteMinute(String completeminute);

    void onHeatBatteryHour(String heatbatteryhour);

    void onHeatBatteryMinute(String heatbatteryminute);

    void onPowerInformationCurrent(String powerinformationcurrent);

    void onPowerInformationVoltage(String powerinformationvoltage);

    void onPowerInformationVisible(boolean powerinformationvisible);

    void onChargingState(int chargingstate);

    void onTimeRemainingHour(String timeremaininghour);

    void onTimeRemainingMinute(String timeremainingminute);

    void onEsocmaxchg(int esocmaxchg);

    /*------------------------------------------------------------------------充放电 end--------------------------------------------------------*/



    /*------------------------------------------------------------------------OTA start--------------------------------------------------------*/


    void onProgressBar(int progressbar);

    /*------------------------------------------------------------------------OTA end--------------------------------------------------------*/



    /*-----------------------------------------------------------------------通用 start-------------------------------------------------------*/


    void onDayNight(int daynight);

    void onGear(int gear);

    void onElectricQuantity(int electricquantity);

    void onEnduranceMileage(int endurancemileage);

    void onElectricityColorControlProp(int electricitycolorcontrolprop);

    void onTurnLeftLightControProp(boolean turnleftlightcontroprop);

    void onTurnRightLightControProp(boolean turnrightlightcontroprop);

    void onCarColorControProp(int carcolorcontroprop);

    void onDoorBL(boolean doorbl);

    void onDoorBR(boolean doorbr);

    void onDoorFL(boolean doorfl);

    void onDoorFR(boolean doorfr);

    void onSpeed(int speed);

    void onTime(String time);

    void onTemperature(int temperature);

    void onDrivingMode(int drivingmode);

    void onReadyIndicatorLight(boolean readyindicatorlight);

    void onBatteryLifeStandard(int batterylifestandard);

    void onTimePattern(int timepattern);

    void onMorningOrAfternoon(int morningorafternoon);

    /*-----------------------------------------------------------------------通用 end-------------------------------------------------------*/






    /*-----------------------------------------------------------------------左右 小型osd(音量和温度 osd) start-------------------------------------------------------*/

    void onTemperature(float temperature);

    void onAirVolume(int airvolume);

    void onTemperatureVisible(boolean temperaturevisible);

    void onAirVolumeVisible(boolean airvolumevisible);

    void onMidVolume(float midvolume);

    void onMidVolumeSilence(boolean midvolumesilence);

    void onMidVoiceVisible(boolean midvoicevisible);

    /*-----------------------------------------------------------------------左右 小型osd(音量和温度 osd) end-------------------------------------------------------*/



    /*-----------------------------------------------------------------------中型 osd(雷达报警/ACC跟车时距和速度调节/强制下电/来电显示) start -------------------------------------------------------------*/

    void onLowSpeedAlarm(boolean lowspeedalarm);

    void onPreventNGearByMistakeTime(int preventngearbymistaketime);

    void onPreventNGearByMistakeVisible(boolean preventngearbymistakevisible);

    void onAbnormalVehicleCondition(boolean abnormalvehiclecondition);

    void onACCOperationGuide(boolean accoperationguide);

    void onACCDistanceAdjust(int accdistanceadjust);

    void onACCDistanceAdjustVisible(boolean accdistanceadjustvisible);

    void onACCSpeedAdjust(int accspeedadjust);

    void onACCSpeedAdjustVisible(boolean accspeedadjustvisible);

    void onForcedPowerDownVisible(boolean forcedpowerdownvisible);

    void onForcedPowerDownState(int forcedpowerdownstate);

    void onCallState(int callstate);

    void onCallVisible(boolean callvisible);

    void onCallerID(String callerid);

    void onCallTime(String calltime);

    void onSensorFailureVisible(boolean sensorfailurevisible);

    /*-----------------------------------------------------------------------中型 osd(雷达报警/ACC跟车时距和速度调节/强制下电/来电显示) end -------------------------------------------------------------*/



    /*---------------------------------------------------------------------------common control start-----------------------------------------------------*/

    void onIsOffControProp(boolean isoffcontroprop);

    void onIsCharging(boolean ischarging);

    void onOtaState(int otastate);

    /*---------------------------------------------------------------------------common control end-----------------------------------------------------*/


    /*-----------------------------------------------------------------adas start--------------------------------------------------------------------------*/

    void onACCIsCC(boolean acciscc);

    void onACCSpeed(int accspeed);

    void onACCState(int accstate);

    void onAdasAEBVisible(boolean aebvisible);

    void onBSDLeft(int bsdleft);

    void onBSDRight(int bsdright);

    void onAdasFCWVisible(boolean fcwvisible);

    void onAdasLCCState(int lccstate);

    void onLightClearanceLamp(boolean lightclearancelamp);

    void onLightDippedHeadlight(boolean lightdippedheadlight);

    void onLightDRL(boolean lightdrl);

    void onLightFullBeamHeadlight(boolean lightfullbeamheadlight);

    void onLightRearFogLamp(boolean lightrearfoglamp);

    void onLightSopLamp(boolean lightsoplamp);

    /*------------------------------------------------------------------adas end----------------------------------------------------------------------------*/
}
