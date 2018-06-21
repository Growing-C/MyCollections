package com.cgy.mycollections.functions.net.mywebservice.request.models;

/**
 * 反扫下单接口请求参数
 * Created by RB-cgy on 2018/6/14.
 */
public class MicroPayParams {
    public String hospital_id;
    public String hospital_area_id;
    public String auth_code;
    public String order_amount;
    public String biz_id;
    public String data_src;
    public String cashier_id;
    public String sign;


    //查询支付结果
    public String trade_no;

}
