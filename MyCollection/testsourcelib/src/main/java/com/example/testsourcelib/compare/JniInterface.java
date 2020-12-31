package com.example.testsourcelib.compare;


public class JniInterface {

    private static final String TAG = JniInterface.class.getSimpleName();
    private static IClusterService sClusterService = null;

//    static {
//        System.loadLibrary("JniCluster");
//        init();
//    }
//
//    public native static void init();

//    public static void setClusterService(IClusterService iClusterService) {
//        Log.d(TAG, "setClusterService");
//        sClusterService = iClusterService;
//    }


    /*----------------------------------------------------------------------card control start -----------------------------------------------------------*/

    /**
     * 左侧卡片选择列表是否显示
     *
     * @param leftlistvisible false不显示 true显示 默认值 false
     */
    public static void onLeftListVisible(boolean leftlistvisible) {
        Log.d(TAG, "onLeftListVisible  " + leftlistvisible);
        sClusterService.onLeftListVisible(leftlistvisible);
    }

    /**
     * 左侧卡片选择列表当前高亮项
     *
     * @param leftlistindex 0-5 默认值 0
     */
    public static void onLeftListIndex(int leftlistindex) {
        Log.d(TAG, "onLeftListIndex  " + leftlistindex);
        sClusterService.onLeftListIndex(leftlistindex);

    }

    /**
     * 报警卡片是否显示
     *
     * @param leftlistsensorfault false不显示 true显示
     */
    public static void onLeftListSensorFault(boolean leftlistsensorfault) {
        Log.d(TAG, "onLeftListSensorFault  " + leftlistsensorfault);
        sClusterService.onLeftListSensorFault(leftlistsensorfault);

    }

    /**
     * 左侧卡片当前显示项
     * 0-地图
     * 1-音乐
     * 2-车况
     * 3-能耗
     * 4-里程
     * 5-传感器
     *
     * @param leftcardindex 0-5  默认值 0
     */
    public static void onLeftCardIndex(int leftcardindex) {
        Log.d(TAG, "onLeftCardIndex  " + leftcardindex);
        sClusterService.onLeftCardIndex(leftcardindex);
    }

    /**
     * 左侧卡片是否显示
     *
     * @param leftcardvisible false不显示 true显示 默认值 false
     */
    public static void onLeftCardVisible(boolean leftcardvisible) {
        Log.d(TAG, "onLeftCardVisible  " + leftcardvisible);
        sClusterService.onLeftCardVisible(leftcardvisible);

    }

    /**
     * 右侧卡片选择列表是否显示
     *
     * @param rightlistvisible false不显示 true显示
     */
    public static void onRightListVisible(boolean rightlistvisible) {
        Log.d(TAG, "onRightListVisible  " + rightlistvisible);
        sClusterService.onRightListVisible(rightlistvisible);
    }

    /**
     * 右侧卡片选择列表当前高亮项 0-4
     *
     * @param rightlistindex
     */
    public static void onRightListIndex(int rightlistindex) {
        Log.d(TAG, "onRightListIndex  " + rightlistindex);
        sClusterService.onRightListIndex(rightlistindex);
    }

    /**
     * 右侧卡片当前显示项
     *
     * @param rightcardindex 0-4
     */
    public static void onRightCardIndex(int rightcardindex) {
        Log.d(TAG, "onRightCardIndex  " + rightcardindex);
        sClusterService.onRightCardIndex(rightcardindex);
    }

    /**
     * 右侧卡片是否显示
     *
     * @param rightcardvisible false不显示 true显示
     */
    public static void onRightCardVisible(boolean rightcardvisible) {
        Log.d(TAG, "onRightCardVisible  " + rightcardvisible);
        sClusterService.onRightCardVisible(rightcardvisible);
    }

    /*------------------------------------------------------------------------card control end --------------------------------------------------------*/





    /*------------------------------------------------------------------------map card start --------------------------------------------------------*/


    public static void onNavigationStyle(int navigationstyle) {
        Log.d(TAG, "onNavigationStyle  " + navigationstyle);
        sClusterService.onNavigationStyle(navigationstyle);
    }

    public static void onNavigationArrowID(int navigationarrowid) {
        Log.d(TAG, "onNavigationArrowID  " + navigationarrowid);
        sClusterService.onNavigationArrowID(navigationarrowid);
    }

    public static void onNavigationArrowState(boolean navigationarrowstate) {
        Log.d(TAG, "onNavigationArrowState  " + navigationarrowstate);
        sClusterService.onNavigationArrowState(navigationarrowstate);
    }

    public static void onNavigationDistance(String navigationdistance) {
        Log.d(TAG, "onNavigationDistance  " + navigationdistance);
        //todo
        //sClusterService.onNavigationDistance(navigationdistance);
    }

    public static void onNavigationDistanceUnits(String navigationdistanceunits) {
        Log.d(TAG, "onNavigationDistanceUnits  " + navigationdistanceunits);
        sClusterService.onNavigationDistanceUnits(navigationdistanceunits);
    }

    public static void onNavigationMoves(String navigationmoves) {
        Log.d(TAG, "onNavigationMoves  " + navigationmoves);
        sClusterService.onNavigationMoves(navigationmoves);
    }

    public static void onNavigationRoadName(String navigationroadname) {
        Log.d(TAG, "onNavigationRoadName  " + navigationroadname);
        sClusterService.onNavigationRoadName(navigationroadname);
    }

    public static void onNavigationGuidanceVisible(boolean navigationguidancevisible) {
        Log.d(TAG, "onNavigationGuidanceVisible  " + navigationguidancevisible);
        sClusterService.onNavigationGuidanceVisible(navigationguidancevisible);
    }

    public static void onNavigationEnabled(boolean navigationenabled) {
        Log.d(TAG, "onNavigationEnabled  " + navigationenabled);
        sClusterService.onNavigationEnabled(navigationenabled);
    }

    /*------------------------------------------------------------------------map card end --------------------------------------------------------*/


    /*------------------------------------------------------------------------tire start --------------------------------------------------------*/
    //<editor-fold desc="轮胎相关 ">

    /**
     * 轮胎状态（左前）
     *
     * @param tirefltirestate 轮胎状态 0 normal 1 red
     */
    public static void onTireFLTireState(int tirefltirestate) {
        Log.d(TAG, "onTireFLTireState  " + tirefltirestate);
        sClusterService.onTireFLTireState(tirefltirestate);
    }

    /**
     * 轮胎压力状态（左前）
     *
     * @param tireflpressurestate 胎压状态 0 normal 1 red
     */
    public static void onTireFLPressureState(int tireflpressurestate) {
        Log.d(TAG, "onTireFLPressureState  " + tireflpressurestate);
        sClusterService.onTireFLPressureState(tireflpressurestate);
    }

    /**
     * 轮胎压力（左前）
     *
     * @param tireflpressure 胎压
     */
    public static void onTireFLPressure(String tireflpressure) {
        Log.d(TAG, "onTireFLPressure  " + tireflpressure);
        sClusterService.onTireFLPressure(tireflpressure);
    }

    /**
     * 轮胎压力单位（左前）
     *
     * @param tireflpressureunits 胎压单位
     */
    public static void onTireFLPressureUnits(String tireflpressureunits) {
        Log.d(TAG, "onTireFLPressureUnits  " + tireflpressureunits);
        sClusterService.onTireFLPressureUnits(tireflpressureunits);
    }

    /**
     * 轮胎温度状态（左前）
     *
     * @param tirefltemperaturesstate 温度状态 0 normal 1 red
     */
    public static void onTireFLTemperaturesState(int tirefltemperaturesstate) {
        Log.d(TAG, "onTireFLTemperaturesState  " + tirefltemperaturesstate);
        sClusterService.onTireFLTemperaturesState(tirefltemperaturesstate);
    }

    /**
     * 轮胎温度（左前）
     *
     * @param tirefltemperatures 轮胎温度
     */
    public static void onTireFLTemperatures(String tirefltemperatures) {
        Log.d(TAG, "onTireFLTemperatures  " + tirefltemperatures);
        sClusterService.onTireFLTemperatures(tirefltemperatures);
    }

    /**
     * 轮胎温度单位（左前）
     *
     * @param tirefltemperaturesunits 轮胎温度单位
     */
    public static void onTireFLTemperaturesUnits(String tirefltemperaturesunits) {
        Log.d(TAG, "onTireFLTemperaturesUnits  " + tirefltemperaturesunits);
        sClusterService.onTireFLTemperaturesUnits(tirefltemperaturesunits);
    }

    /**
     * 轮胎状态（右前）
     *
     * @param tirefrtirestate 轮胎状态 0 normal 1 red
     */
    public static void onTireFRTireState(int tirefrtirestate) {
        Log.d(TAG, "onTireFRTireState  " + tirefrtirestate);
        sClusterService.onTireFRTireState(tirefrtirestate);
    }

    /**
     * 轮胎压力状态（右前）
     *
     * @param tirefrpressurestate 胎压状态 0 normal 1 red
     */
    public static void onTireFRPressureState(int tirefrpressurestate) {
        Log.d(TAG, "onTireFRPressureState  " + tirefrpressurestate);
        sClusterService.onTireFRPressureState(tirefrpressurestate);
    }

    /**
     * 轮胎压力（右前）
     *
     * @param tirefrpressure 胎压
     */
    public static void onTireFRPressure(String tirefrpressure) {
        Log.d(TAG, "onTireFRPressure  " + tirefrpressure);
        sClusterService.onTireFRPressure(tirefrpressure);
    }

    /**
     * 轮胎压力单位（右前）
     *
     * @param tirefrpressureunits 胎压单位
     */
    public static void onTireFRPressureUnits(String tirefrpressureunits) {
        Log.d(TAG, "onTireFRPressureUnits  " + tirefrpressureunits);
        sClusterService.onTireFRPressureUnits(tirefrpressureunits);
    }

    /**
     * 轮胎温度状态（右前）
     *
     * @param tirefrtemperaturesstate 温度状态 0 normal 1 red
     */
    public static void onTireFRTemperaturesState(int tirefrtemperaturesstate) {
        Log.d(TAG, "onTireFRTemperaturesState  " + tirefrtemperaturesstate);
        sClusterService.onTireFRTemperaturesState(tirefrtemperaturesstate);
    }

    /**
     * 轮胎温度（右前）
     *
     * @param tirefrtemperatures 轮胎温度
     */
    public static void onTireFRTemperatures(String tirefrtemperatures) {
        Log.d(TAG, "onTireFRTemperatures  " + tirefrtemperatures);
        sClusterService.onTireFRTemperatures(tirefrtemperatures);
    }

    /**
     * 轮胎温度单位（右前）
     *
     * @param tirefrtemperaturesunits 轮胎温度单位
     */
    public static void onTireFRTemperaturesUnits(String tirefrtemperaturesunits) {
        Log.d(TAG, "onTireFRTemperaturesUnits  " + tirefrtemperaturesunits);
        sClusterService.onTireFRTemperaturesUnits(tirefrtemperaturesunits);
    }

    /**
     * 轮胎状态（左后）
     *
     * @param tirebltirestate 轮胎状态 0 normal 1 red
     */
    public static void onTireBLTireState(int tirebltirestate) {
        Log.d(TAG, "onTireBLTireState  " + tirebltirestate);
        sClusterService.onTireBLTireState(tirebltirestate);
    }

    /**
     * 轮胎压力状态（左后）
     *
     * @param tireblpressurestate 胎压状态 0 normal 1 red
     */
    public static void onTireBLPressureState(int tireblpressurestate) {
        Log.d(TAG, "onTireBLPressureState  " + tireblpressurestate);
        sClusterService.onTireBLPressureState(tireblpressurestate);
    }

    /**
     * 轮胎压力（左后）
     *
     * @param tireblpressure 胎压
     */
    public static void onTireBLPressure(String tireblpressure) {
        Log.d(TAG, "onTireBLPressure  " + tireblpressure);
        sClusterService.onTireBLPressure(tireblpressure);
    }

    /**
     * 轮胎压力单位（左后）
     *
     * @param tireblpressureunits 胎压单位
     */
    public static void onTireBLPressureUnits(String tireblpressureunits) {
        Log.d(TAG, "onTireBLPressureUnits  " + tireblpressureunits);
        sClusterService.onTireBLPressureUnits(tireblpressureunits);
    }

    /**
     * 轮胎温度状态（左后）
     *
     * @param tirebltemperaturesstate 温度状态 0 normal 1 red
     */
    public static void onTireBLTemperaturesState(int tirebltemperaturesstate) {
        Log.d(TAG, "onTireBLTemperaturesState  " + tirebltemperaturesstate);
        sClusterService.onTireBLTemperaturesState(tirebltemperaturesstate);
    }

    /**
     * 轮胎温度（左后）
     *
     * @param tirebltemperatures 轮胎温度
     */
    public static void onTireBLTemperatures(String tirebltemperatures) {
        Log.d(TAG, "onTireBLTemperatures  " + tirebltemperatures);
        sClusterService.onTireBLTemperatures(tirebltemperatures);
    }

    /**
     * 轮胎温度单位（左后）
     *
     * @param tirebltemperaturesunits 轮胎温度单位
     */
    public static void onTireBLTemperaturesUnits(String tirebltemperaturesunits) {
        Log.d(TAG, "onTireBLTemperaturesUnits  " + tirebltemperaturesunits);
        sClusterService.onTireBLTemperaturesUnits(tirebltemperaturesunits);
    }

    /**
     * 轮胎状态（右后）
     *
     * @param tirebrtirestate 轮胎状态 0 normal 1 red
     */
    public static void onTireBRTireState(int tirebrtirestate) {
        Log.d(TAG, "onTireBRTireState  " + tirebrtirestate);
        sClusterService.onTireBRTireState(tirebrtirestate);
    }

    /**
     * 轮胎压力状态（右后）
     *
     * @param tirebrpressurestate 胎压状态 0 normal 1 red
     */
    public static void onTireBRPressureState(int tirebrpressurestate) {
        Log.d(TAG, "onTireBRPressureState  " + tirebrpressurestate);
        sClusterService.onTireBRPressureState(tirebrpressurestate);
    }

    /**
     * 轮胎压力（右后）
     *
     * @param tirebrpressure 胎压
     */
    public static void onTireBRPressure(String tirebrpressure) {
        Log.d(TAG, "onTireBRPressure  " + tirebrpressure);
        sClusterService.onTireBRPressure(tirebrpressure);
    }

    /**
     * 轮胎压力单位（右后）
     *
     * @param tirebrpressureunits 胎压单位
     */
    public static void onTireBRPressureUnits(String tirebrpressureunits) {
        Log.d(TAG, "onTireBRPressureUnits  " + tirebrpressureunits);
        sClusterService.onTireBRPressureUnits(tirebrpressureunits);
    }

    /**
     * 轮胎温度状态（右后）
     *
     * @param tirebrtemperaturesstate 温度状态 0 normal 1 red
     */
    public static void onTireBRTemperaturesState(int tirebrtemperaturesstate) {
        Log.d(TAG, "onTireBRTemperaturesState  " + tirebrtemperaturesstate);
        sClusterService.onTireBRTemperaturesState(tirebrtemperaturesstate);
    }

    /**
     * 轮胎温度（右后）
     *
     * @param tirebrtemperatures 轮胎温度
     */
    public static void onTireBRTemperatures(String tirebrtemperatures) {
        Log.d(TAG, "onTireBRTemperatures  " + tirebrtemperatures);
        sClusterService.onTireBRTemperatures(tirebrtemperatures);
    }

    /**
     * 轮胎温度单位（右后）
     *
     * @param tirebrtemperaturesunits 轮胎温度单位
     */
    public static void onTireBRTemperaturesUnits(String tirebrtemperaturesunits) {
        Log.d(TAG, "onTireBRTemperaturesUnits  " + tirebrtemperaturesunits);
        sClusterService.onTireBRTemperaturesUnits(tirebrtemperaturesunits);
    }

    //</editor-fold>
    /*------------------------------------------------------------------------tire end --------------------------------------------------------*/

    /**
     * 引擎盖状态(引擎盖没有故障)
     *
     * @param hoodengine true:打开 false:关闭
     */
    public static void onHoodEngine(boolean hoodengine) {
        Log.d(TAG, "onHoodEngine  " + hoodengine);
        sClusterService.onHoodEngine(hoodengine);
    }

    /**
     * 快速充电口盖状态
     *
     * @param hoodfastcharge 0:关闭 1:打开 2:异常
     */
    public static void onHoodFastCharge(int hoodfastcharge) {
        Log.d(TAG, "onHoodFastCharge  " + hoodfastcharge);
        sClusterService.onHoodFastCharge(hoodfastcharge);
    }

    /**
     * 慢充充电口盖状态
     *
     * @param hoodnormalcharge 0:关闭 1:打开 2:异常
     */
    public static void onHoodNormalCharge(int hoodnormalcharge) {
        Log.d(TAG, "onHoodNormalCharge  " + hoodnormalcharge);
        sClusterService.onHoodNormalCharge(hoodnormalcharge);
    }

    /**
     * 尾箱盖状态
     *
     * @param hoodtrunk true:打开 false:关闭
     */
    public static void onHoodTrunk(boolean hoodtrunk) {
        Log.d(TAG, "onHoodTrunk  " + hoodtrunk);
        sClusterService.onHoodTrunk(hoodtrunk);
    }

    //</editor-fold>

    /*------------------------------------------------------------------------里程 start --------------------------------------------------------*/

    //<editor-fold desc="里程卡片相关 ">

    /**
     * 本次里程
     *
     * @param thistimevalue 本次里程
     */
    public static void onThisTimeValue(String thistimevalue) {
        Log.d(TAG, "onThisTimeValue  " + thistimevalue);
        sClusterService.onThisTimeValue(thistimevalue);
    }

    /**
     * 本次里程单位
     *
     * @param thistimeunits 本次里程单位
     */
    public static void onThisTimeUnits(String thistimeunits) {
        Log.d(TAG, "onThisTimeUnits  " + thistimeunits);
        sClusterService.onThisTimeUnits(thistimeunits);
    }

    /**
     * 充电后里程
     *
     * @param afterchargingvalue 充电后里程
     */
    public static void onAfterChargingValue(String afterchargingvalue) {
        Log.d(TAG, "onAfterChargingValue  " + afterchargingvalue);
        sClusterService.onAfterChargingValue(afterchargingvalue);
    }

    /**
     * 充电后里程单位
     *
     * @param afterchargingunits 充电后里程单位
     */
    public static void onAfterChargingUnits(String afterchargingunits) {
        Log.d(TAG, "onAfterChargingUnits  " + afterchargingunits);
        sClusterService.onAfterChargingUnits(afterchargingunits);
    }

    /**
     * 小计里程A
     *
     * @param subtotalavalue 小计里程A
     */
    public static void onSubtotalAValue(String subtotalavalue) {
        Log.d(TAG, "onSubtotalAValue  " + subtotalavalue);
        sClusterService.onSubtotalAValue(subtotalavalue);
    }

    /**
     * 小计里程A单位
     *
     * @param subtotalaunits 小计里程A单位
     */
    public static void onSubtotalAUnits(String subtotalaunits) {
        Log.d(TAG, "onSubtotalAUnits  " + subtotalaunits);
        sClusterService.onSubtotalAUnits(subtotalaunits);
    }

    /**
     * 小计里程B
     *
     * @param subtotalbvalue 小计里程B
     */
    public static void onSubtotalBValue(String subtotalbvalue) {
        Log.d(TAG, "onSubtotalBValue  " + subtotalbvalue);
        sClusterService.onSubtotalBValue(subtotalbvalue);
    }

    /**
     * 小计里程B单位
     *
     * @param subtotalbunits 小计里程B单位
     */
    public static void onSubtotalBUnits(String subtotalbunits) {
        Log.d(TAG, "onSubtotalBUnits  " + subtotalbunits);
        sClusterService.onSubtotalBUnits(subtotalbunits);
    }

    /**
     * 总里程
     *
     * @param totalvalue 总里程
     */
    public static void onTotalValue(String totalvalue) {
        Log.d(TAG, "onTotalValue  " + totalvalue);
        sClusterService.onTotalValue(totalvalue);
    }

    /**
     * 总里程单位
     *
     * @param totalunits 总里程单位
     */
    public static void onTotalUnits(String totalunits) {
        Log.d(TAG, "onTotalUnits  " + totalunits);
        sClusterService.onTotalUnits(totalunits);
    }

    /**
     * 平均车速
     *
     * @param averagevalue 平均车速
     */
    public static void onAverageValue(String averagevalue) {
        Log.d(TAG, "onAverageValue  " + averagevalue);
        sClusterService.onAverageValue(averagevalue);
    }

    /**
     * 平均车速单位
     *
     * @param averageunits 平均车速单位
     */
    public static void onAverageUnits(String averageunits) {
        Log.d(TAG, "onAverageUnits  " + averageunits);
        sClusterService.onAverageUnits(averageunits);
    }

    /**
     * 行驶时长
     *
     * @param elapsedtimevalue 行驶时长
     */
    public static void onElapsedTimeValue(String elapsedtimevalue) {
        Log.d(TAG, "onElapsedTimeValue  " + elapsedtimevalue);
        sClusterService.onElapsedTimeValue(elapsedtimevalue);
    }

    /**
     * 行驶时长单位
     *
     * @param elapsedtimeunits 行驶时长单位
     */
    public static void onElapsedTimeUnits(String elapsedtimeunits) {
        Log.d(TAG, "onElapsedTimeUnits  " + elapsedtimeunits);
        sClusterService.onElapsedTimeUnits(elapsedtimeunits);
    }

    //</editor-fold>

    /*------------------------------------------------------------------------里程 end --------------------------------------------------------*/


    /*------------------------------------------------------------------------music card start --------------------------------------------------------*/

    public static void onMusicImageState(boolean musicimagestate) {
        Log.d(TAG, "onMusicImageState  " + musicimagestate);
        sClusterService.onMusicImageState(musicimagestate);
    }


    public static void onMusicPlayState(boolean musicplaystate) {
        Log.d(TAG, "onMusicPlayState  " + musicplaystate);
        sClusterService.onMusicPlayState(musicplaystate);
    }

    public static void onMusicSoundState(int musicsoundstate) {
        Log.d(TAG, "onMusicSoundState  " + musicsoundstate);
        sClusterService.onMusicSoundState(musicsoundstate);
    }

    public static void onMusicString1(String musicstring1) {
        Log.d(TAG, "onMusicString1  " + musicstring1);
        sClusterService.onMusicString1(musicstring1);
    }

    public static void onMusicString2(String musicstring2) {
        Log.d(TAG, "onMusicString2  " + musicstring2);
        sClusterService.onMusicString2(musicstring2);
    }

    public static void onMusicBarValue(int musicbarvalue) {
        Log.d(TAG, "onMusicBarValue  " + musicbarvalue);
        sClusterService.onMusicBarValue(musicbarvalue);
    }


    /*------------------------------------------------------------------------music card end --------------------------------------------------------*/





    /*------------------------------------------------------------------------sensor start --------------------------------------------------------*/


    /*------------------------------------------------------------------------毫米波雷达 start --------------------------------------------------------*/

    /**
     * 毫米波雷达总体状态
     * 0:normal 1:error||warning 3:预留
     *
     * @param millimeterwaveradarstate
     */
    public static void onMillimeterWaveRadarState(int millimeterwaveradarstate) {
        Log.d(TAG, "onMillimeterWaveRadarState  " + millimeterwaveradarstate);
        sClusterService.onMillimeterWaveRadarState(millimeterwaveradarstate);
    }


    /**
     * 前中毫米波雷达状态
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param millimeterwaveradarfc
     */
    public static void onMillimeterWaveRadarFC(int millimeterwaveradarfc) {
        Log.d(TAG, "onMillimeterWaveRadarFC  " + millimeterwaveradarfc);
        sClusterService.onMillimeterWaveRadarFC(millimeterwaveradarfc);
    }

    /**
     * 左前角毫米波雷达状态
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param millimeterwaveradarfl
     */
    public static void onMillimeterWaveRadarFL(int millimeterwaveradarfl) {
        Log.d(TAG, "onMillimeterWaveRadarFL  " + millimeterwaveradarfl);
        sClusterService.onMillimeterWaveRadarFL(millimeterwaveradarfl);
    }

    /**
     * 右前角毫米波雷达状态
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param millimeterwaveradarfr
     */
    public static void onMillimeterWaveRadarFR(int millimeterwaveradarfr) {
        Log.d(TAG, "onMillimeterWaveRadarFR  " + millimeterwaveradarfr);
        sClusterService.onMillimeterWaveRadarFR(millimeterwaveradarfr);
    }

    /**
     * 左后角中毫米波雷达状态
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param millimeterwaveradarrcl
     */
    public static void onMillimeterWaveRadarRCL(int millimeterwaveradarrcl) {
        Log.d(TAG, "onMillimeterWaveRadarRCL  " + millimeterwaveradarrcl);
        sClusterService.onMillimeterWaveRadarRCL(millimeterwaveradarrcl);
    }

    /**
     * 右后角中毫米波雷达状态
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param millimeterwaveradarrcr
     */
    public static void onMillimeterWaveRadarRCR(int millimeterwaveradarrcr) {
        Log.d(TAG, "onMillimeterWaveRadarRCR  " + millimeterwaveradarrcr);
        sClusterService.onMillimeterWaveRadarRCR(millimeterwaveradarrcr);
    }
    /*------------------------------------------------------------------------毫米波雷达 end --------------------------------------------------------*/

    /*------------------------------------------------------------------------超声波雷达 start --------------------------------------------------------*/

    /**
     * 超声波雷达总体状态
     *
     * @param ultrasonicradarstate
     */
    public static void onUltrasonicRadarState(int ultrasonicradarstate) {
        Log.d(TAG, "onUltrasonicRadarState  " + ultrasonicradarstate);
        sClusterService.onUltrasonicRadarState(ultrasonicradarstate);
    }

    /**
     * 前左中超声波雷达（FCL）
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param ultrasonicradarfcl
     */
    public static void onUltrasonicRadarFCL(int ultrasonicradarfcl) {
        Log.d(TAG, "onUltrasonicRadarFCL  " + ultrasonicradarfcl);
        sClusterService.onUltrasonicRadarFCL(ultrasonicradarfcl);
    }

    /**
     * 前右中超声波雷达（FCR）
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param ultrasonicradarfcr
     */
    public static void onUltrasonicRadarFCR(int ultrasonicradarfcr) {
        Log.d(TAG, "onUltrasonicRadarFCR  " + ultrasonicradarfcr);
        sClusterService.onUltrasonicRadarFCR(ultrasonicradarfcr);
    }

    /**
     * 前左超声波雷达（FOL）
     *
     * @param ultrasonicradarfol
     */
    public static void onUltrasonicRadarFOL(int ultrasonicradarfol) {
        Log.d(TAG, "onUltrasonicRadarFOL  " + ultrasonicradarfol);
        sClusterService.onUltrasonicRadarFOL(ultrasonicradarfol);
    }

    /**
     * 前右超声波雷达（FOR）
     *
     * @param ultrasonicradarfor
     */
    public static void onUltrasonicRadarFOR(int ultrasonicradarfor) {
        Log.d(TAG, "onUltrasonicRadarFOR  " + ultrasonicradarfor);
        sClusterService.onUltrasonicRadarFOR(ultrasonicradarfor);
    }

    /**
     * 左侧前超声波雷达（FSL）
     *
     * @param ultrasonicradarfsl
     */
    public static void onUltrasonicRadarFSL(int ultrasonicradarfsl) {
        Log.d(TAG, "onUltrasonicRadarFSL  " + ultrasonicradarfsl);
        sClusterService.onUltrasonicRadarFSL(ultrasonicradarfsl);
    }

    /**
     * 右侧前超声波雷达（FSR）
     *
     * @param ultrasonicradarfsr
     */
    public static void onUltrasonicRadarFSR(int ultrasonicradarfsr) {
        Log.d(TAG, "onUltrasonicRadarFSR  " + ultrasonicradarfsr);
        sClusterService.onUltrasonicRadarFSR(ultrasonicradarfsr);
    }

    /**
     * 后左中超声波雷达（RCL）
     *
     * @param ultrasonicradarrcl
     */
    public static void onUltrasonicRadarRCL(int ultrasonicradarrcl) {
        Log.d(TAG, "onUltrasonicRadarRCL  " + ultrasonicradarrcl);
        sClusterService.onUltrasonicRadarRCL(ultrasonicradarrcl);
    }

    /**
     * 后右中超声波雷达（RCR）
     *
     * @param ultrasonicradarrcr
     */
    public static void onUltrasonicRadarRCR(int ultrasonicradarrcr) {
        Log.d(TAG, "onUltrasonicRadarRCR  " + ultrasonicradarrcr);
        sClusterService.onUltrasonicRadarRCR(ultrasonicradarrcr);
    }

    /**
     * 后左超声波雷达（ROL）
     *
     * @param ultrasonicradarrol
     */
    public static void onUltrasonicRadarROL(int ultrasonicradarrol) {
        Log.d(TAG, "onUltrasonicRadarROL  " + ultrasonicradarrol);
        sClusterService.onUltrasonicRadarROL(ultrasonicradarrol);
    }

    /**
     * 后右超声波雷达（ROR）
     *
     * @param ultrasonicradarror
     */
    public static void onUltrasonicRadarROR(int ultrasonicradarror) {
        Log.d(TAG, "onUltrasonicRadarROR  " + ultrasonicradarror);
        sClusterService.onUltrasonicRadarROR(ultrasonicradarror);
    }

    /**
     * 左侧后超声波雷达（RSL）
     *
     * @param ultrasonicradarrsl
     */
    public static void onUltrasonicRadarRSL(int ultrasonicradarrsl) {
        Log.d(TAG, "onUltrasonicRadarRSL  " + ultrasonicradarrsl);
        sClusterService.onUltrasonicRadarRSL(ultrasonicradarrsl);
    }

    /**
     * 右侧后超声波雷达（RSR）
     *
     * @param ultrasonicradarrsr
     */
    public static void onUltrasonicRadarRSR(int ultrasonicradarrsr) {
        Log.d(TAG, "onUltrasonicRadarRSR  " + ultrasonicradarrsr);
        sClusterService.onUltrasonicRadarRSR(ultrasonicradarrsr);
    }

    /**
     * 车辆显示部位
     * 0:不显示 1:前半部 2:后半部 3:全显
     *
     * @param sensorfaultcarstate
     */
    public static void onSensorFaultCarState(int sensorfaultcarstate) {
        Log.d(TAG, "onSensorFaultCarState  " + sensorfaultcarstate);
        sClusterService.onSensorFaultCarState(sensorfaultcarstate);
    }
    /*------------------------------------------------------------------------超声波雷达 end--------------------------------------------------------------*/

    /*------------------------------------------------------------------------智能驾驶摄像头 start --------------------------------------------------------*/

    /**
     * 智能驾驶摄像头总体状态
     * 0:normal 1:error||warning 3:预留
     *
     * @param smartcamerastate
     */
    public static void onSmartCameraState(int smartcamerastate) {
        Log.d(TAG, "onSmartCameraState  " + smartcamerastate);
        sClusterService.onSmartCameraState(smartcamerastate);
    }

    /**
     * 驾驶员感知摄像头
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param smartcamerasite1
     */
    public static void onSmartCameraSite1(int smartcamerasite1) {
        Log.d(TAG, "onSmartCameraSite1  " + smartcamerasite1);
        sClusterService.onSmartCameraSite1(smartcamerasite1);
    }

    /**
     * 智能驾驶三目摄像头
     *
     * @param smartcamerasite2
     */
    public static void onSmartCameraSite2(int smartcamerasite2) {
        Log.d(TAG, "onSmartCameraSite2  " + smartcamerasite2);
        sClusterService.onSmartCameraSite2(smartcamerasite2);
    }

    /**
     * 智能驾驶单目摄像头
     *
     * @param smartcamerasite3
     */
    public static void onSmartCameraSite3(int smartcamerasite3) {
        Log.d(TAG, "onSmartCameraSite3  " + smartcamerasite3);
        sClusterService.onSmartCameraSite3(smartcamerasite3);
    }

    /**
     * 左前摄像头（预留）
     *
     * @param smartcamerasite4
     */
    public static void onSmartCameraSite4(int smartcamerasite4) {
        Log.d(TAG, "onSmartCameraSite4  " + smartcamerasite4);
        sClusterService.onSmartCameraSite4(smartcamerasite4);
    }

    /**
     * 左后摄像头（预留）
     *
     * @param smartcamerasite5
     */
    public static void onSmartCameraSite5(int smartcamerasite5) {
        Log.d(TAG, "onSmartCameraSite5  " + smartcamerasite5);
        sClusterService.onSmartCameraSite5(smartcamerasite5);
    }

    /**
     * 右前摄像头（预留）
     *
     * @param smartcamerasite6
     */
    public static void onSmartCameraSite6(int smartcamerasite6) {
        Log.d(TAG, "onSmartCameraSite6  " + smartcamerasite6);
        sClusterService.onSmartCameraSite6(smartcamerasite6);
    }

    /**
     * 右后摄像头（预留）
     *
     * @param smartcamerasite7
     */
    public static void onSmartCameraSite7(int smartcamerasite7) {
        Log.d(TAG, "onSmartCameraSite7  " + smartcamerasite7);
        sClusterService.onSmartCameraSite7(smartcamerasite7);
    }

    /**
     * 后摄像头（预留）
     *
     * @param smartcamerasite8
     */
    public static void onSmartCameraSite8(int smartcamerasite8) {
        Log.d(TAG, "onSmartCameraSite8  " + smartcamerasite8);
        sClusterService.onSmartCameraSite8(smartcamerasite8);
    }
    /*------------------------------------------------------------------------智能驾驶摄像头 end --------------------------------------------------------*/

    /*------------------------------------------------------------------------环视摄像头 start --------------------------------------------------------*/

    /**
     * 环视摄像头状态
     * 0:normal 1:error||warning 3:预留
     *
     * @param lookaroundcamerastate
     */
    public static void onLookAroundCameraState(int lookaroundcamerastate) {
        Log.d(TAG, "onLookAroundCameraState  " + lookaroundcamerastate);
        sClusterService.onLookAroundCameraState(lookaroundcamerastate);
    }

    /**
     * 前环视
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param onLookAroundCameraF
     */
    public static void onLookAroundCameraF(int onLookAroundCameraF) {
        Log.d(TAG, "onLookAroundCameraF  " + onLookAroundCameraF);
        sClusterService.onLookAroundCameraF(onLookAroundCameraF);
    }

    /**
     * 左环视
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param lookaroundcameral
     */
    public static void onLookAroundCameraL(int lookaroundcameral) {
        Log.d(TAG, "onLookAroundCameraL  " + lookaroundcameral);
        sClusterService.onLookAroundCameraL(lookaroundcameral);
    }

    /**
     * 后环视
     * 0:mormal(灰) 1:warning(黄) 2:error(红) 3:预留
     *
     * @param lookaroundcamerab
     */
    public static void onLookAroundCameraB(int lookaroundcamerab) {
        Log.d(TAG, "onLookAroundCameraB  " + lookaroundcamerab);
        sClusterService.onLookAroundCameraB(lookaroundcamerab);
    }

    /**
     * 右环视
     *
     * @param lookaroundcamerar
     */
    public static void onLookAroundCameraR(int lookaroundcamerar) {
        Log.d(TAG, "onLookAroundCameraR  " + lookaroundcamerar);
        sClusterService.onLookAroundCameraR(lookaroundcamerar);

    }

    /**
     * 传感器故障文字显示状态
     * false不显示 true显示
     *
     * @param textvisible
     */
    public static void onTextVisible(boolean textvisible) {
        Log.d(TAG, "onTextVisible  " + textvisible);
        sClusterService.onTextVisible(textvisible);
    }

    /**
     * 传感器故障文字内容
     *
     * @param textvalue
     */
    public static void onTextValue(String textvalue) {
        Log.d(TAG, "onTextValue  " + textvalue);
        sClusterService.onTextValue(textvalue);
    }

    /*------------------------------------------------------------------------环视摄像头 end--------------------------------------------------------*/


    /*------------------------------------------------------------------------sensor end--------------------------------------------------------*/





    /*------------------------------------------------------------------------warning start --------------------------------------------------------*/



    /*------------------------------------------------------------------------warning end --------------------------------------------------------*/


    //    public static void onImageGuidanceTexture(imageguidancetexture){
//
//    }
//    public static void onImageMusicTexture(imagemusictexture){
//
//    }
//    public static void onImageNaviTexture(imagenavitexture){
//
//    }



    /*------------------------------------------------------------------------能耗卡片 start --------------------------------------------------------*/


    /**
     * 平均能耗
     *
     * @param poweraverageenergyconsumption 最小值-20，最大值60
     */
    public static void onPowerAverageEnergyConsumption(int poweraverageenergyconsumption) {
        Log.d(TAG, "onPowerAverageEnergyConsumption  " + poweraverageenergyconsumption);
        sClusterService.onPowerAverageEnergyConsumption(poweraverageenergyconsumption);
    }

    public static void onPowerCurveValue(float powercurvevalue) {
        Log.d(TAG, "onPowerCurveValue  " + powercurvevalue);
        sClusterService.onPowerCurveValue(powercurvevalue);
    }

    /*------------------------------------------------------------------------能耗卡片 end --------------------------------------------------------*/




    /*------------------------------------------------------------------------充放电 start--------------------------------------------------------*/
    //<editor-fold desc="充放电 ">

    /**
     * 预约充电时间（小时）
     *
     * @param appointmenthour 小时 默认显示00 不足两位 前补0
     */
    public static void onAppointmentHour(String appointmenthour) {
        Log.d(TAG, "onAppointmentHour  " + appointmenthour);
        sClusterService.onAppointmentHour(appointmenthour);
    }

    /**
     * 预约充电时间（分钟）
     *
     * @param appointmentminute 分钟 默认显示00 不足两位 前补0
     */
    public static void onAppointmentMinute(String appointmentminute) {
        Log.d(TAG, "onAppointmentMinute  " + appointmentminute);
        sClusterService.onAppointmentMinute(appointmentminute);
    }

    /**
     * 充电持续时间（小时）
     *
     * @param completehour 小时 默认显示-- 不足两位 前补0
     */
    public static void onCompleteHour(String completehour) {
        Log.d(TAG, "onCompleteHour  " + completehour);
        sClusterService.onCompleteHour(completehour);
    }

    /**
     * 充电持续时间（分钟）
     *
     * @param completeminute 分钟 默认显示-- 不足两位 前补0
     */
    public static void onCompleteMinute(String completeminute) {
        Log.d(TAG, "onCompleteMinute  " + completeminute);
        sClusterService.onCompleteMinute(completeminute);
    }

    /**
     * 电池加热冷却时间（小时）
     *
     * @param heatbatteryhour 小时 默认显示--
     */
    public static void onHeatBatteryHour(String heatbatteryhour) {
        Log.d(TAG, "onHeatBatteryHour  " + heatbatteryhour);
        sClusterService.onHeatBatteryHour(heatbatteryhour);
    }

    /**
     * 电池加热冷却时间（分钟）
     *
     * @param heatbatteryminute 分钟 默认显示00 不足两位 前补0
     */
    public static void onHeatBatteryMinute(String heatbatteryminute) {
        Log.d(TAG, "onHeatBatteryMinute  " + heatbatteryminute);
        sClusterService.onHeatBatteryMinute(heatbatteryminute);
    }

    /**
     * 电源信息 电流
     *
     * @param powerinformationcurrent 电流
     */
    public static void onPowerInformationCurrent(String powerinformationcurrent) {
        Log.d(TAG, "onPowerInformationCurrent  " + powerinformationcurrent);
        sClusterService.onPowerInformationCurrent(powerinformationcurrent);
    }

    /**
     * 电源信息 电压
     *
     * @param powerinformationvoltage 电压
     */
    public static void onPowerInformationVoltage(String powerinformationvoltage) {
        Log.d(TAG, "onPowerInformationVoltage  " + powerinformationvoltage);
        sClusterService.onPowerInformationVoltage(powerinformationvoltage);
    }

    /**
     * 电源信息是否显示
     *
     * @param powerinformationvisible false:不显示 true:显示
     */
    public static void onPowerInformationVisible(boolean powerinformationvisible) {
        Log.d(TAG, "onPowerInformationVisible  " + powerinformationvisible);
        sClusterService.onPowerInformationVisible(powerinformationvisible);
    }

    /**
     * 充放电状态
     *
     * @param chargingState 充放电界面状态
     *                      Data[0]  : 充电枪未连接
     *                      Data[1]  : 充电准备界面
     *                      Data[2]  : 充电中界面
     *                      Data[3]  : 充电完成界面
     *                      Data[4]  : 充电故障界面
     *                      Data[5]  : 充电枪连接无法启动车辆界面
     *                      Data[6]  : 定时充电界面
     *                      Data[7]  : 电池加热界面
     *                      Data[8]  : 电池冷却界面
     *                      Data[9]  : 放电准备界面
     *                      Data[10] : 放电中界面
     *                      Data[11] : 放电故障界面
     *                      Data[12] : 放电停止界面
     */
    public static void onChargingState(int chargingState) {
        Log.d(TAG, "onChargingState  " + chargingState);
        sClusterService.onChargingState(chargingState);
    }

    /**
     * 预计充满剩余时间（小时）
     *
     * @param timeremaininghour 小时 默认显示00 不足两位 前补0
     */
    public static void onTimeRemainingHour(String timeremaininghour) {
        Log.d(TAG, "onTimeRemainingHour  " + timeremaininghour);
        sClusterService.onTimeRemainingHour(timeremaininghour);
    }

    /**
     * 预计充满剩余时间（分钟）
     *
     * @param timeremainingminute 分钟 默认显示00 不足两位 前补0
     */
    public static void onTimeRemainingMinute(String timeremainingminute) {
        Log.d(TAG, "onTimeRemainingMinute  " + timeremainingminute);
        sClusterService.onTimeRemainingMinute(timeremainingminute);
    }

    /**
     * 充放电上限值
     *
     * @param esocmaxchg 0-100
     */
    public static void onChargingEsocmaxchg(int esocmaxchg) {
        Log.d(TAG, "onChargingEsocmaxchg  " + esocmaxchg);
        sClusterService.onEsocmaxchg(esocmaxchg);
    }

    //</editor-fold>
    /*------------------------------------------------------------------------充放电 end-------------------------------------------------------*/



    /*-----------------------------------------------------------------------OTA start-------------------------------------------------------*/


    public static void onOtaProgressBar(int progressbar) {
        Log.d(TAG, "onOtaProgressBar  " + progressbar);
        sClusterService.onProgressBar(progressbar);

    }

    /**
     * ota状态
     *
     * @param otastate 0:非ota状态 1:ota仪表升级 2:ota大屏或其他组件升级 3:预留
     */
    public static void onOtaState(int otastate) {
        Log.d(TAG, "onOtaState  " + otastate);
        sClusterService.onOtaState(otastate);
    }


    /*-----------------------------------------------------------------------OTA end-------------------------------------------------------*/



    /*-----------------------------------------------------------------------通用 start-------------------------------------------------------*/
    //<editor-fold desc="common ">

    /**
     * 白天黑夜
     *
     * @param daynight
     */
    public static void onCommonDayNight(int daynight) {
        Log.d(TAG, "onCommonDayNight  " + daynight);
        sClusterService.onDayNight(daynight);
    }

    /**
     * 档位
     * 0:P
     * 1:R
     * 2:N
     * 3:D
     *
     * @param gear
     */
    public static void onCommonGear(int gear) {
        Log.d(TAG, "onCommonGear  " + gear);
        sClusterService.onGear(gear);
    }


    /**
     * 剩余电量
     *
     * @param electricquantity 剩余电量 0-100
     */
    public static void onCommonElectricQuantity(int electricquantity) {
        Log.d(TAG, "onCommonElectricQuantity  " + electricquantity);
        sClusterService.onElectricQuantity(electricquantity);
    }

    /**
     * 剩余里程
     *
     * @param endurancemileage 续航里程 0-999
     */
    public static void onCommonEnduranceMileage(int endurancemileage) {
        Log.d(TAG, "onCommonEnduranceMileage  " + endurancemileage);
        sClusterService.onEnduranceMileage(endurancemileage);
    }

    /**
     * 电量颜色
     *
     * @param electricitycolorcontrolprop 0：null 1：电量红色 2：电量橙色 3：电量绿色
     */
    public static void onCommonElectricityColorControlProp(int electricitycolorcontrolprop) {
        Log.d(TAG, "onCommonElectricityColorControlProp  " + electricitycolorcontrolprop);
        sClusterService.onElectricityColorControlProp(electricitycolorcontrolprop);
    }

    /**
     * 左转向
     *
     * @param turnleftlightcontroprop
     */
    public static void onCommonTurnLeftLightControProp(boolean turnleftlightcontroprop) {
        Log.d(TAG, "onCommonTurnLeftLightControProp  " + turnleftlightcontroprop);
        sClusterService.onTurnLeftLightControProp(turnleftlightcontroprop);
    }

    /**
     * 右转向
     *
     * @param turnrightlightcontroprop
     */
    public static void onCommonTurnRightLightControProp(boolean turnrightlightcontroprop) {
        Log.d(TAG, "onCommonTurnRightLightControProp  " + turnrightlightcontroprop);
        sClusterService.onTurnRightLightControProp(turnrightlightcontroprop);
    }

    /**
     * 车辆颜色配置
     *
     * @param carcolorcontroprop
     */
    public static void onCommonCarColorControProp(int carcolorcontroprop) {
        Log.d(TAG, "onCommonCarColorControProp  " + carcolorcontroprop);
        sClusterService.onCarColorControProp(carcolorcontroprop);
    }

    /**
     * 是否在off界面
     *
     * @param isoffcontroprop true：off界面 false：非off界面
     */
    public static void onCommonIsOffControProp(boolean isoffcontroprop) {
        Log.d(TAG, "onCommonIsOffControProp  " + isoffcontroprop);
        sClusterService.onIsOffControProp(isoffcontroprop);
    }

    /**
     * 是否在充电状态
     *
     * @param ischarging true:充电 false:未充电
     */
    public static void onCommonIsCharging(boolean ischarging) {
        Log.d(TAG, "onCommonIsCharging  " + ischarging);
        sClusterService.onIsCharging(ischarging);
    }

    /**
     * 车速表
     *
     * @param speed 0-999km/h  默认值10
     */
    public static void onCommonSpeed(int speed) {
        Log.d(TAG, "onCommonSpeed  " + speed);
        sClusterService.onSpeed(speed);
    }

    //</editor-fold>
    /*-----------------------------------------------------------------------通用 end-------------------------------------------------------*/


    //<editor-fold desc="车况，车门，前后盖，充电盖 ">

    /**
     * 车门状态（左后）
     *
     * @param doorbl true:打开 false:关闭
     */
    public static void onDoorBL(boolean doorbl) {
        Log.d(TAG, "onDoorBL  " + doorbl);
        sClusterService.onDoorBL(doorbl);
    }

    /**
     * 车门状态（右后）
     *
     * @param doorbr true:打开 false:关闭
     */
    public static void onDoorBR(boolean doorbr) {
        Log.d(TAG, "onDoorBR  " + doorbr);
        sClusterService.onDoorBR(doorbr);
    }

    /**
     * 车门状态（左前）
     *
     * @param doorfl true:打开 false:关闭
     */
    public static void onDoorFL(boolean doorfl) {
        Log.d(TAG, "onDoorFL  " + doorfl);
        sClusterService.onDoorFL(doorfl);
    }

    /**
     * 车门状态（左右）
     *
     * @param doorfr true:打开 false:关闭
     */
    public static void onDoorFR(boolean doorfr) {
        Log.d(TAG, "onDoorFR  " + doorfr);
        sClusterService.onDoorFR(doorfr);
    }


    //<editor-fold desc="底部栏信息，时间温度，续航模式等 ">

    /**
     * 当前时间
     *
     * @param time 0：00-24：23
     */
    public static void onNormalInfoTime(String time) {
        Log.d(TAG, "onTime  " + time);
        sClusterService.onTime(time);
    }


    /**
     * 车外温度
     *
     * @param temperature 0-999
     */
    public static void onNormalInfoTemperature(int temperature) {
        Log.d(TAG, "onTemperature  " + temperature);
        sClusterService.onTemperature(temperature);
    }

    /**
     * 车辆驾驶模式
     *
     * @param drivingmode 1：Standard 2：Eco 3:Sport
     */
    public static void onNormalInfoDrivingMode(int drivingmode) {
        Log.d(TAG, "onDrivingMode  " + drivingmode);
        sClusterService.onDrivingMode(drivingmode);
    }

    /**
     * ready模式是否开启
     * 默认值 false
     *
     * @param readyindicatorlight false：ready不开启 true：ready模式开启
     */
    public static void onNormalInfoReadyIndicatorLight(boolean readyindicatorlight) {
        Log.d(TAG, "onReadyIndicatorLight  " + readyindicatorlight);
        sClusterService.onReadyIndicatorLight(readyindicatorlight);
    }

    /**
     * 续航标准
     *
     * @param batterylifestandard 0：NEDC 1：WLTP
     */
    public static void onNormalInfoBatteryLifeStandard(int batterylifestandard) {
        Log.d(TAG, "onBatteryLifeStandard  " + batterylifestandard);
        sClusterService.onBatteryLifeStandard(batterylifestandard);
    }


    /**
     * 时间模式
     *
     * @param timepattern 0：24小时制 1：12小时制
     */
    public static void onNormalInfoTimePattern(int timepattern) {
        Log.d(TAG, "onTimePattern  " + timepattern);
        sClusterService.onTimePattern(timepattern);
    }

    /**
     * 控制时间显示上午还是下午
     *
     * @param morningorafternoon 0：上午 1：下午
     */
    public static void onNormalInfoMorningOrAfternoon(int morningorafternoon) {
        Log.d(TAG, "onMorningOrAfternoon  " + morningorafternoon);
        sClusterService.onMorningOrAfternoon(morningorafternoon);
    }

    //</editor-fold>


    /*-----------------------------------------------------------------------左右 小型osd start-------------------------------------------------------*/

    /**
     * 空调温度显示 默认值是16
     *
     * @param temperature
     */
    public static void onOverlayTemperature(float temperature) {
        Log.d(TAG, "onOverlayTemperature  " + temperature);
        sClusterService.onTemperature(temperature);
    }

    /**
     * 空调风量调节
     *
     * @param airvolume
     */
    public static void onOverlayAirVolume(int airvolume) {
        Log.d(TAG, "onOverlayAirVolume  " + airvolume);
        sClusterService.onAirVolume(airvolume);
    }

    /**
     * 空调温度osd是否显示
     *
     * @param temperaturevisible
     */
    public static void onOverlayTemperatureVisible(boolean temperaturevisible) {
        Log.d(TAG, "onOverlayTemperatureVisible  " + temperaturevisible);
        sClusterService.onTemperatureVisible(temperaturevisible);
    }

    /**
     * 空调风量osd是否显示
     *
     * @param airvolumevisible
     */
    public static void onOverlayAirVolumeVisible(boolean airvolumevisible) {
        Log.d(TAG, "onOverlayAirVolumeVisible  " + airvolumevisible);
        sClusterService.onAirVolumeVisible(airvolumevisible);
    }

    /**
     * 音量调节
     *
     * @param midvolume
     */
    public static void onOverlayMidVolume(float midvolume) {
        Log.d(TAG, "onOverlayMidVolume  " + midvolume);
        sClusterService.onMidVolume(midvolume);
    }

    /**
     * 是否静音
     *
     * @param midvolumesilence
     */
    public static void onOverlayMidVolumeSilence(boolean midvolumesilence) {
        Log.d(TAG, "onOverlayMidVolumeSilence  " + midvolumesilence);
        sClusterService.onMidVolumeSilence(midvolumesilence);
    }

    /**
     * 是否显示音量
     *
     * @param midvoicevisible
     */
    public static void onOverlayMidVoiceVisible(boolean midvoicevisible) {
        Log.d(TAG, "onOverlayMidVoiceVisible  " + midvoicevisible);
        sClusterService.onMidVoiceVisible(midvoicevisible);
    }

    /*-----------------------------------------------------------------------左右 小型osd end-------------------------------------------------------*/



    /*-----------------------------------------------------------------------中型 osd(雷达报警/ACC跟车时距和速度调节/强制下电/来电显示) start -------------------------------------------------------------*/


    /**
     * 是否出现D档低速超声波雷达报警
     *
     * @param lowspeedalarm
     */
    public static void onOverlayLowSpeedAlarm(boolean lowspeedalarm) {
        Log.d(TAG, "onOverlayLowSpeedAlarm  " + lowspeedalarm);
        sClusterService.onLowSpeedAlarm(lowspeedalarm);
    }

    /**
     * 倒计时出现的时间
     *
     * @param preventngearbymistaketime
     */
    public static void onOverlayPreventNGearByMistakeTime(int preventngearbymistaketime) {
        Log.d(TAG, "onOverlayPreventNGearByMistakeTime  " + preventngearbymistaketime);
        sClusterService.onPreventNGearByMistakeTime(preventngearbymistaketime);
    }

    /**
     * 是否出现行车中防止误挂N档提升
     *
     * @param preventngearbymistakevisible
     */
    public static void onOverlayPreventNGearByMistakeVisible(boolean preventngearbymistakevisible) {
        Log.d(TAG, "onOverlayPreventNGearByMistakeVisible  " + preventngearbymistakevisible);
        sClusterService.onPreventNGearByMistakeVisible(preventngearbymistakevisible);
    }

    /**
     * 是否出现车况异常提示
     *
     * @param abnormalvehiclecondition
     */
    public static void onOverlayAbnormalVehicleCondition(boolean abnormalvehiclecondition) {
        Log.d(TAG, "onOverlayAbnormalVehicleCondition  " + abnormalvehiclecondition);
        sClusterService.onAbnormalVehicleCondition(abnormalvehiclecondition);
    }

    /**
     * ACC操作引导界面是否显示
     *
     * @param accoperationguide
     */
    public static void onOverlayACCOperationGuide(boolean accoperationguide) {
        Log.d(TAG, "onOverlayACCOperationGuide  " + accoperationguide);
        sClusterService.onACCOperationGuide(accoperationguide);
    }

    /**
     * ACC跟车时距调节
     *
     * @param accdistanceadjust
     */
    public static void onOverlayACCDistanceAdjust(int accdistanceadjust) {
        Log.d(TAG, "onOverlayACCDistanceAdjust  " + accdistanceadjust);
        sClusterService.onACCDistanceAdjust(accdistanceadjust);
    }

    /**
     * ACC跟车时距调节界面是否显示
     *
     * @param accdistanceadjustvisible
     */
    public static void onOverlayACCDistanceAdjustVisible(boolean accdistanceadjustvisible) {
        Log.d(TAG, "onOverlayACCDistanceAdjustVisible  " + accdistanceadjustvisible);
        sClusterService.onACCDistanceAdjustVisible(accdistanceadjustvisible);
    }

    /**
     * ACC跟车速度调节
     *
     * @param accspeedadjust
     */
    public static void onOverlayACCSpeedAdjust(int accspeedadjust) {
        Log.d(TAG, "onOverlayACCSpeedAdjust  " + accspeedadjust);
        sClusterService.onACCSpeedAdjust(accspeedadjust);
    }

    /**
     * ACC跟车速度调节界面是否显示
     *
     * @param accspeedadjustvisible
     */
    public static void onOverlayACCSpeedAdjustVisible(boolean accspeedadjustvisible) {
        Log.d(TAG, "onOverlayACCSpeedAdjustVisible  " + accspeedadjustvisible);
        sClusterService.onACCSpeedAdjustVisible(accspeedadjustvisible);
    }

    /**
     * 强制下电提醒界面是否显示
     *
     * @param forcedpowerdownvisible
     */
    public static void onOverlayForcedPowerDownVisible(boolean forcedpowerdownvisible) {
        Log.d(TAG, "onOverlayForcedPowerDownVisible  " + forcedpowerdownvisible);
        sClusterService.onForcedPowerDownVisible(forcedpowerdownvisible);
    }

    /**
     * 强制下电提醒界面选中状态
     *
     * @param forcedpowerdownstate
     */
    public static void onOverlayForcedPowerDownState(int forcedpowerdownstate) {
        Log.d(TAG, "onOverlayForcedPowerDownState  " + forcedpowerdownstate);
        sClusterService.onForcedPowerDownState(forcedpowerdownstate);
    }

    /**
     * 来电显示的各个状态界面
     *
     * @param callstate
     */
    public static void onOverlayCallState(int callstate) {
        Log.d(TAG, "onOverlayCallState  " + callstate);
        sClusterService.onCallState(callstate);
    }

    /**
     * 来电界面是否显示
     *
     * @param callvisible
     */
    public static void onOverlayCallVisible(boolean callvisible) {
        Log.d(TAG, "onOverlayCallVisible  " + callvisible);
        sClusterService.onCallVisible(callvisible);
    }

    /**
     * 显示来电人的名字
     *
     * @param callerid
     */
    public static void onOverlayCallerID(String callerid) {
        Log.d(TAG, "onOverlayCallerID  " + callerid);
        sClusterService.onCallerID(callerid);
    }

    /**
     * 显示接通电话时间
     *
     * @param calltime
     */
    public static void onOverlayCallTime(String calltime) {
        Log.d(TAG, "onOverlayCallTime  " + calltime);
        sClusterService.onCallTime(calltime);
    }

    /**
     * 是否显示传感器故障界面
     *
     * @param sensorfailurevisible
     */
    public static void onOverlaySensorFailureVisible(boolean sensorfailurevisible) {
        Log.d(TAG, "onOverlaySensorFailureVisible  " + sensorfailurevisible);
        sClusterService.onSensorFailureVisible(sensorfailurevisible);
    }

    /*-----------------------------------------------------------------------中型 osd(雷达报警/ACC跟车时距和速度调节/强制下电/来电显示) end -------------------------------------------------------------*/


    /*--------------------------------------------------------------------------------ADAS start -------------------------------------------------------------------------------------------------------*/

    /**
     * 当前开启的是CC或ACC
     *
     * @param acciscc true:CC false:ACC
     */
    public static void onACCIsCC(boolean acciscc) {
        Log.d(TAG, "onACCIsCC  " + acciscc);
        sClusterService.onACCIsCC(acciscc);

    }

    /**
     * ACC目标车速
     *
     * @param accspeed
     */
    public static void onACCSpeed(int accspeed) {
        Log.d(TAG, "onACCSpeed  " + accspeed);
        sClusterService.onACCSpeed(accspeed);
    }

    /**
     * 图标状态（控制颜色）
     * 0:Normal（无） 1:Ready(灰) 2:Start(蓝、绿) 3:OpenFailure(黄) 4:error(红)
     *
     * @param accstate
     */
    public static void onACCState(int accstate) {
        Log.d(TAG, "onACCState  " + accstate);
        sClusterService.onACCState(accstate);
    }

    /**
     * AEB是否显示
     * true:显示 false:隐藏
     *
     * @param aebvisible
     */
    public static void onAdasAEBVisible(boolean aebvisible) {
        Log.d(TAG, "onAdasAEBVisible  " + aebvisible);
        sClusterService.onAdasAEBVisible(aebvisible);
    }

    /**
     * 左侧BSD显示状态
     * 0:null 1:gray 2:red
     *
     * @param bsdleft
     */
    public static void onBSDLeft(int bsdleft) {
        Log.d(TAG, "onBSDLeft  " + bsdleft);
        sClusterService.onBSDLeft(bsdleft);
    }

    /**
     * 右侧BSD显示状态
     *
     * @param bsdright 0:null 1:gray 2:red
     */
    public static void onBSDRight(int bsdright) {
        Log.d(TAG, "onBSDRight  " + bsdright);
        sClusterService.onBSDRight(bsdright);
    }

    /**
     * FCW是否显示
     * true:显示 false:隐藏
     *
     * @param fcwvisible
     */
    public static void onAdasFCWVisible(boolean fcwvisible) {
        Log.d(TAG, "onAdasFCWVisible  " + fcwvisible);
        sClusterService.onAdasFCWVisible(fcwvisible);
    }

    /**
     * 0:Normal 1:gray 2:blue 3:yelow 4:red
     *
     * @param lccstate LCC状态
     */
    public static void onAdasLCCState(int lccstate) {
        Log.d(TAG, "onAdasLCCState  " + lccstate);
        sClusterService.onAdasLCCState(lccstate);
    }

    /**
     * 示廓灯是否显示
     *
     * @param lightclearancelamp true:显示 false:隐藏
     */
    public static void onLightClearanceLamp(boolean lightclearancelamp) {
        Log.d(TAG, "onLightClearanceLamp  " + lightclearancelamp);
        sClusterService.onLightClearanceLamp(lightclearancelamp);
    }

    /**
     * 日间行车灯是否显示
     *
     * @param lightdippedheadlight
     */
    public static void onLightDippedHeadlight(boolean lightdippedheadlight) {
        Log.d(TAG, "onLightDippedHeadlight  " + lightdippedheadlight);
        sClusterService.onLightDippedHeadlight(lightdippedheadlight);
    }

    /**
     * 日间行车灯是否显示
     *
     * @param lightdrl
     */
    public static void onLightDRL(boolean lightdrl) {
        Log.d(TAG, "onLightDRL  " + lightdrl);
        sClusterService.onLightDRL(lightdrl);
    }

    /**
     * 远光灯是否显示
     *
     * @param lightfullbeamheadlight
     */
    public static void onLightFullBeamHeadlight(boolean lightfullbeamheadlight) {
        Log.d(TAG, "onLightFullBeamHeadlight  " + lightfullbeamheadlight);
        sClusterService.onLightFullBeamHeadlight(lightfullbeamheadlight);
    }

    /**
     * 后雾灯是否显示
     *
     * @param lightrearfoglamp
     */
    public static void onLightRearFogLamp(boolean lightrearfoglamp) {
        Log.d(TAG, "onLightRearFogLamp  " + lightrearfoglamp);
        sClusterService.onLightRearFogLamp(lightrearfoglamp);
    }

    /**
     * 刹车灯是否显示
     *
     * @param lightsoplamp
     */
    public static void onLightSopLamp(boolean lightsoplamp) {
        Log.d(TAG, "onLightSopLamp  " + lightsoplamp);
        sClusterService.onLightSopLamp(lightsoplamp);
    }

    public static void onBehindDistValue(String behinddistvalue) {

    }

    public static void onBehindCLColor(int behindclcolor) {

    }

    public static void onBehindCLDist(int behindcldist) {

    }

    public static void onBehindCRColor(int behindcrcolor) {

    }

    public static void onBehindCRDist(int behindcrdist) {

    }

    public static void onBehindOLColor(int behindolcolor) {

    }

    public static void onBehindOLDist(int behindoldist) {

    }

    public static void onBehindORColor(int behindorcolor) {

    }

    public static void onBehindORDist(int behindordist) {

    }

    public static void onBehindSLColor(int behindslcolor) {

    }

    public static void onBehindSLDist(int behindsldist) {

    }

    public static void onBehindSRColor(int behindsrcolor) {

    }

    public static void onBehindSRDist(int behindsrdist) {

    }

    public static void onFrontDistValue(String frontdistvalue) {

    }

    public static void onFrontCLColor(int frontclcolor) {

    }

    public static void onFrontCLDist(int frontcldist) {

    }

    public static void onFrontCRColor(int frontcrcolor) {

    }

    public static void onFrontCRDist(int frontcrdist) {

    }

    public static void onFrontOLColor(int frontolcolor) {

    }

    public static void onFrontOLDist(int frontoldist) {

    }

    public static void onFrontORColor(int frontorcolor) {

    }

    public static void onFrontORDist(int frontordist) {

    }

    public static void onFrontSLColor(int frontslcolor) {

    }

    public static void onFrontSLDist(int frontsldist) {

    }

    public static void onFrontSRColor(int frontsrcolor) {

    }

    public static void onFrontSRDist(int frontsrdist) {

    }

    public static void onRCTALeftVisible(boolean rctaleftvisible) {

    }

    public static void onRCTARightVisible(boolean rctarightvisible) {

    }

    public static void onAdasRCWVisible(boolean rcwvisible) {

    }

    public static void onLeftC0(float leftc0) {

    }

    public static void onLeftC1(float leftc1) {

    }

    public static void onLeftC2(float leftc2) {

    }

    public static void onLeftC3(float leftc3) {

    }

    public static void onLeftType(int lefttype) {

    }

    public static void onLeftLeftC0(float leftleftc0) {

    }

    public static void onLeftLeftC1(float leftleftc1) {

    }

    public static void onLeftLeftC2(float leftleftc2) {

    }

    public static void onLeftLeftC3(float leftleftc3) {

    }

    public static void onLeftLeftType(int leftlefttype) {

    }

    public static void onRightC0(float rightc0) {

    }

    public static void onRightC1(float rightc1) {

    }

    public static void onRightC2(float rightc2) {

    }

    public static void onRightC3(float rightc3) {

    }

    public static void onRightType(int righttype) {

    }

    public static void onRightRightC0(float rightrightc0) {

    }

    public static void onRightRightC1(float rightrightc1) {

    }

    public static void onRightRightC2(float rightrightc2) {

    }

    public static void onRightRightC3(float rightrightc3) {

    }

    public static void onRightRightType(int rightrighttype) {

    }

    public static void onVehlnfo1Angle(int vehlnfo1angle) {

    }

    public static void onVehlnfo1X(int vehlnfo1x) {

    }

    public static void onVehlnfo1Y(float vehlnfo1y) {

    }

    public static void onVehlnfo1Color(int vehlnfo1color) {

    }

    public static void onVehlnfo1Type(int vehlnfo1type) {

    }

    public static void onVehlnfo2Angle(int vehlnfo2angle) {

    }

    public static void onVehlnfo2X(int vehlnfo2x) {

    }

    public static void onVehlnfo2Y(float vehlnfo2y) {

    }

    public static void onVehlnfo2Color(int vehlnfo2color) {

    }

    public static void onVehlnfo2Type(int vehlnfo2type) {

    }

    public static void onVehlnfo3Angle(int vehlnfo3angle) {

    }

    public static void onVehlnfo3X(int vehlnfo3x) {

    }

    public static void onVehlnfo3Y(float vehlnfo3y) {

    }

    public static void onVehlnfo3Color(int vehlnfo3color) {

    }

    public static void onVehlnfo3Type(int vehlnfo3type) {

    }

    public static void onVehlnfo4Angle(int vehlnfo4angle) {

    }

    public static void onVehlnfo4X(int vehlnfo4x) {

    }

    public static void onVehlnfo4Y(float vehlnfo4y) {

    }

    public static void onVehlnfo4Color(int vehlnfo4color) {

    }

    public static void onVehlnfo4Type(int vehlnfo4type) {

    }

    public static void onVehlnfo5Angle(int vehlnfo5angle) {

    }

    public static void onVehlnfo5X(int vehlnfo5x) {

    }

    public static void onVehlnfo5Y(float vehlnfo5y) {

    }

    public static void onVehlnfo5Color(int vehlnfo5color) {

    }

    public static void onVehlnfo5Type(int vehlnfo5type) {

    }

    public static void onVehlnfo6Angle(int vehlnfo6angle) {

    }

    public static void onVehlnfo6X(int vehlnfo6x) {

    }

    public static void onVehlnfo6Y(float vehlnfo6y) {

    }

    public static void onVehlnfo6Color(int vehlnfo6color) {

    }

    public static void onVehlnfo6Type(int vehlnfo6type) {

    }

    public static void onVehlnfo7Angle(int vehlnfo7angle) {

    }

    public static void onVehlnfo7X(int vehlnfo7x) {

    }

    public static void onVehlnfo7Y(float vehlnfo7y) {

    }

    public static void onVehlnfo7Color(int vehlnfo7color) {

    }

    public static void onVehlnfo7Type(int vehlnfo7type) {

    }

    public static void onVehlnfo8Angle(int vehlnfo8angle) {

    }

    public static void onVehlnfo8X(int vehlnfo8x) {

    }

    public static void onVehlnfo8Y(float vehlnfo8y) {

    }

    public static void onVehlnfo8Color(int vehlnfo8color) {

    }

    public static void onVehlnfo8Type(int vehlnfo8type) {

    }

    public static void onVehlnfo9Angle(int vehlnfo9angle) {

    }

    public static void onVehlnfo9X(int vehlnfo9x) {

    }

    public static void onVehlnfo9Y(float vehlnfo9y) {

    }

    public static void onVehlnfo9Color(int vehlnfo9color) {

    }

    public static void onVehlnfo9Type(int vehlnfo9type) {

    }

    public static void onVehlnfo10Angle(int vehlnfo10angle) {

    }

    public static void onVehlnfo10X(int vehlnfo10x) {

    }

    public static void onVehlnfo10Y(float vehlnfo10y) {

    }

    public static void onVehlnfo10Color(int vehlnfo10color) {

    }

    public static void onVehlnfo10Type(int vehlnfo10type) {

    }

    public static void onVehlnfo11Angle(int vehlnfo11angle) {

    }

    public static void onVehlnfo11X(int vehlnfo11x) {

    }

    public static void onVehlnfo11Y(float vehlnfo11y) {

    }

    public static void onVehlnfo11Color(int vehlnfo11color) {

    }

    public static void onVehlnfo11Type(int vehlnfo11type) {

    }

    public static void onParkSpaceALLState(int parkspaceallstate) {

    }

    public static void onParkSpaceL1State(int parkspacel1state) {

    }

    public static void onParkSpaceL2State(int parkspacel2state) {

    }

    public static void onParkSpaceL3State(int parkspacel3state) {

    }

    public static void onParkSpaceR1State(int parkspacer1state) {

    }

    public static void onParkSpaceR2State(int parkspacer2state) {

    }

    public static void onParkSpaceR3State(int parkspacer3state) {

    }

    public static void onLeftLeftStart(int leftleftstart) {

    }

    public static void onLeftLeftRange(int leftleftrange) {

    }

    public static void onLeftStart(int leftstart) {

    }

    public static void onLeftRange(int leftrange) {

    }

    public static void onRightRightStart(int rightrightstart) {

    }

    public static void onRightRightRange(int rightrightrange) {

    }

    public static void onRightStart(int rightstart) {

    }

    public static void onRightRange(int rightrange) {

    }

    public static void onALCState(int alcstate) {

    }

    public static void onALCX(int alcx) {

    }

    public static void onALCY(int alcy) {

    }

    public static void onAdasHold(boolean hold) {

    }

    public static void onTSRForbid(int value) {

    }

    public static void onTSRWarning(int value) {

    }

    public static void onTSRRateLimitingType(int value) {

    }

    public static void onTSRRateLimitingValue(int value) {

    }

    public static void onALCAngle(int value) {

    }

    public static void onACCDisableChangeCard(boolean show) {

    }

    public static void onMidVolumeVisible(boolean show) {

    }

    public static void onAirVolumeState(int state) {

    }

    public static void onColorTest(int value) {

    }

}