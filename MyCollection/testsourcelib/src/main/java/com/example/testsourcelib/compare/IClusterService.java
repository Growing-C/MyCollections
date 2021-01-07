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

    void onOtaState(int otastate);

    /*------------------------------------------------------------------------OTA end--------------------------------------------------------*/



    /*-----------------------------------------------------------------------通用 start-------------------------------------------------------*/


    void onDayNight(int daynight);

    void onGear(int gear);

    void onElectricQuantity(int electricquantity);

    void onEnduranceMileage(int endurancemileage);

    void onEnduranceMileageNumVisible(boolean flag);

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

    /*-----------------------------------------------------------------------中型 osd(雷达报警/ACC跟车时距和速度调节/强制下电/来电显示) end -------------------------------------------------------------*/



    /*---------------------------------------------------------------------------common control start-----------------------------------------------------*/

    void onIsOffControProp(boolean isoffcontroprop);

    void onIsCharging(boolean ischarging);

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


    /*------------------------------------------------------------------warning start----------------------------------------------------------------------------*/

    void onTriggerAutoState(int value);

    void onTriggerState(int value);

    void onCommonTelltale(int id, boolean show);

    void onUnsetTelltale(int[] ids);

    /*------------------------------------------------------------------warning end----------------------------------------------------------------------------*/

    void onBehindDistValue(String behinddistvalue);


    void onBehindCLColor(int behindclcolor);


    void onBehindCLDist(int behindcldist);


    void onBehindCRColor(int behindcrcolor);


    void onBehindCRDist(int behindcrdist);


    void onBehindOLColor(int behindolcolor);


    void onBehindOLDist(int behindoldist);


    void onBehindORColor(int behindorcolor);


    void onBehindORDist(int behindordist);


    void onBehindSLColor(int behindslcolor);


    void onBehindSLDist(int behindsldist);


    void onBehindSRColor(int behindsrcolor);


    void onBehindSRDist(int behindsrdist);


    void onFrontDistValue(String frontdistvalue);


    void onFrontCLColor(int frontclcolor);


    void onFrontCLDist(int frontcldist);


    void onFrontCRColor(int frontcrcolor);


    void onFrontCRDist(int frontcrdist);


    void onFrontOLColor(int frontolcolor);


    void onFrontOLDist(int frontoldist);


    void onFrontORColor(int frontorcolor);


    void onFrontORDist(int frontordist);


    void onFrontSLColor(int frontslcolor);


    void onFrontSLDist(int frontsldist);


    void onFrontSRColor(int frontsrcolor);


    void onFrontSRDist(int frontsrdist);


    void onRCTALeftVisible(boolean rctaleftvisible);


    void onRCTARightVisible(boolean rctarightvisible);


    void onAdasRCWVisible(boolean rcwvisible);


    void onLeftC0(float leftc0);


    void onLeftC1(float leftc1);


    void onLeftC2(float leftc2);


    void onLeftC3(float leftc3);


    void onLeftType(int lefttype);


    void onLeftLeftC0(float leftleftc0);


    void onLeftLeftC1(float leftleftc1);


    void onLeftLeftC2(float leftleftc2);


    void onLeftLeftC3(float leftleftc3);


    void onLeftLeftType(int leftlefttype);


    void onRightC0(float rightc0);


    void onRightC1(float rightc1);


    void onRightC2(float rightc2);


    void onRightC3(float rightc3);


    void onRightType(int righttype);


    void onRightRightC0(float rightrightc0);


    void onRightRightC1(float rightrightc1);


    void onRightRightC2(float rightrightc2);


    void onRightRightC3(float rightrightc3);


    void onRightRightType(int rightrighttype);


    void onVehlnfo1Angle(int vehlnfo1angle);


    void onVehlnfo1X(int vehlnfo1x);


    void onVehlnfo1Y(float vehlnfo1y);


    void onVehlnfo1Color(int vehlnfo1color);


    void onVehlnfo1Type(int vehlnfo1type);


    void onVehlnfo2Angle(int vehlnfo2angle);


    void onVehlnfo2X(int vehlnfo2x);


    void onVehlnfo2Y(float vehlnfo2y);


    void onVehlnfo2Color(int vehlnfo2color);


    void onVehlnfo2Type(int vehlnfo2type);


    void onVehlnfo3Angle(int vehlnfo3angle);


    void onVehlnfo3X(int vehlnfo3x);


    void onVehlnfo3Y(float vehlnfo3y);


    void onVehlnfo3Color(int vehlnfo3color);


    void onVehlnfo3Type(int vehlnfo3type);


    void onVehlnfo4Angle(int vehlnfo4angle);


    void onVehlnfo4X(int vehlnfo4x);


    void onVehlnfo4Y(float vehlnfo4y);


    void onVehlnfo4Color(int vehlnfo4color);


    void onVehlnfo4Type(int vehlnfo4type);


    void onVehlnfo5Angle(int vehlnfo5angle);


    void onVehlnfo5X(int vehlnfo5x);


    void onVehlnfo5Y(float vehlnfo5y);


    void onVehlnfo5Color(int vehlnfo5color);


    void onVehlnfo5Type(int vehlnfo5type);


    void onVehlnfo6Angle(int vehlnfo6angle);


    void onVehlnfo6X(int vehlnfo6x);


    void onVehlnfo6Y(float vehlnfo6y);


    void onVehlnfo6Color(int vehlnfo6color);


    void onVehlnfo6Type(int vehlnfo6type);


    void onVehlnfo7Angle(int vehlnfo7angle);


    void onVehlnfo7X(int vehlnfo7x);


    void onVehlnfo7Y(float vehlnfo7y);


    void onVehlnfo7Color(int vehlnfo7color);


    void onVehlnfo7Type(int vehlnfo7type);


    void onVehlnfo8Angle(int vehlnfo8angle);


    void onVehlnfo8X(int vehlnfo8x);


    void onVehlnfo8Y(float vehlnfo8y);


    void onVehlnfo8Color(int vehlnfo8color);


    void onVehlnfo8Type(int vehlnfo8type);


    void onVehlnfo9Angle(int vehlnfo9angle);


    void onVehlnfo9X(int vehlnfo9x);


    void onVehlnfo9Y(float vehlnfo9y);


    void onVehlnfo9Color(int vehlnfo9color);


    void onVehlnfo9Type(int vehlnfo9type);


    void onVehlnfo10Angle(int vehlnfo10angle);


    void onVehlnfo10X(int vehlnfo10x);


    void onVehlnfo10Y(float vehlnfo10y);


    void onVehlnfo10Color(int vehlnfo10color);


    void onVehlnfo10Type(int vehlnfo10type);


    void onVehlnfo11Angle(int vehlnfo11angle);


    void onVehlnfo11X(int vehlnfo11x);


    void onVehlnfo11Y(float vehlnfo11y);


    void onVehlnfo11Color(int vehlnfo11color);


    void onVehlnfo11Type(int vehlnfo11type);


    void onParkSpaceALLState(int parkspaceallstate);


    void onParkSpaceL1State(int parkspacel1state);


    void onParkSpaceL2State(int parkspacel2state);


    void onParkSpaceL3State(int parkspacel3state);


    void onParkSpaceR1State(int parkspacer1state);


    void onParkSpaceR2State(int parkspacer2state);


    void onParkSpaceR3State(int parkspacer3state);


    void onLeftLeftStart(int leftleftstart);


    void onLeftLeftRange(int leftleftrange);


    void onLeftStart(int leftstart);


    void onLeftRange(int leftrange);


    void onRightRightStart(int rightrightstart);


    void onRightRightRange(int rightrightrange);


    void onRightStart(int rightstart);


    void onRightRange(int rightrange);


    void onALCState(int alcstate);


    void onALCX(int alcx);


    void onALCY(int alcy);


    void onAdasHold(boolean hold);


    void onTSRForbid(int value);


    void onTSRWarning(int value);


    void onTSRRateLimitingType(int value);


    void onTSRRateLimitingValue(int value);


    void onALCAngle(int value);


    void onACCDisableChangeCard(boolean show);


    void onAirVolumeState(int state);


    void onColorTest(int value);


    void onCommonTelltale(boolean show);


}
