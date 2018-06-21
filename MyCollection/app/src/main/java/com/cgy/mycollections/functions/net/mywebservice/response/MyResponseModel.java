package com.cgy.mycollections.functions.net.mywebservice.response;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * 用户角色返回
 * Created by SmileXie on 16/7/15.
 */

@Root(name = "microPayResponse")
@Namespace(reference = "http://www.witontek.com/ehospital/")
public class MyResponseModel {

    @Element(name = "return")
    public String result;

}
