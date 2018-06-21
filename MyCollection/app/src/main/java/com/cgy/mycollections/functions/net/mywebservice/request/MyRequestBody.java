package com.cgy.mycollections.functions.net.mywebservice.request;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by SmileXie on 16/7/15.
 */
@Root(name = "soap:Body", strict = false)
public class MyRequestBody {

    @Element(required = false)
    public CommonRequestModel microPay;//反扫创建订单


    @Element(name = "qryPayStatus", required = false)
    public CommonRequestModel queryPayStatus;//查询订单状态

    @Element(name = "reverseOrder", required = false)
    public CommonRequestModel reverseOrder;//订单撤销
}
