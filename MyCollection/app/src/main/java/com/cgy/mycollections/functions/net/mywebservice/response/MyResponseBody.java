package com.cgy.mycollections.functions.net.mywebservice.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by SmileXie on 16/7/15.
 */
@Root(name = "Body")
public class MyResponseBody {

    @Element(required = false)
    public MyResponseModel microPayResponse;//反扫下单

    @Element(required = false)
    public MyResponseModel qryPayStatusResponse;//查询订单状态

    @Element(required = false)
    public MyResponseModel reverseOrderResponse;//订单撤销

}
