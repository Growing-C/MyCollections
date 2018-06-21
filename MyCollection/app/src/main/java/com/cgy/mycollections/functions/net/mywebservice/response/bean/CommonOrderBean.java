package com.cgy.mycollections.functions.net.mywebservice.response.bean;

/**
 * 创建订单  ， 订单状态查询，  订单撤销 返回
 * Created by RB-cgy on 2018/6/15.
 */
public class CommonOrderBean extends BaseBean {
    //反扫下单  支付结果查询
    public String trade_no;//医路通订单号
    public String wt_trade_no;//平台订单号
    public String channel;//支付渠道
    public String time_end;//支付成功时间

    //订单撤销
    public String re_call;//是否需要重新调用  N 不需要，Y 需要继续调用

}
