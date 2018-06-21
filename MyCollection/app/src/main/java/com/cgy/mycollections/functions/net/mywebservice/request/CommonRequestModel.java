package com.cgy.mycollections.functions.net.mywebservice.request;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * 获取具体信息需要传的参数
 * Created by SmileXie on 16/7/15.
 */

//通用request model 主要数据在  inputStr里面  用json
//@Root(name = "microPay")
@Namespace(reference = "http://www.witontek.com/ehospital/")
public class CommonRequestModel {

//    @Attribute(name = "xmlns")
//    public String requestAttribute;

    @Element(name = "inputStr", required = false)
    public String inputStr;     //json串

//    @Element(name = "hospital_id", required = false)
//    public String hospital_id;     //城市名字
//    @Element(name = "hospital_area_id", required = false)
//    public String hospital_area_id;
//    @Element(name = "auth_code", required = false)
//    public String auth_code;
//    @Element(name = "order_amount", required = false)
//    public String order_amount;
//    @Element(name = "biz_id", required = false)
//    public String biz_id;
//    @Element(name = "data_src", required = false)
//    public String data_src;
//    @Element(name = "cashier_id", required = false)
//    public String cashier_id;
//    @Element(name = "sign", required = false)
//    public String sign;

}
