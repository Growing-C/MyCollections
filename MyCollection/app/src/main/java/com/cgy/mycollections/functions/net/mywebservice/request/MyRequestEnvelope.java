package com.cgy.mycollections.functions.net.mywebservice.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


/**
 * 用户角色请求Envelope
 * Created by SmileXie on 16/7/15.
 */
@Root(name = "soap:Envelope")
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
//        @Namespace(reference = "http://schemas.xmlsoap.org/wsdl/", prefix = "wsdl"),
//        @Namespace(reference = "http://www.witontek.com/ehospital", prefix = "tns"),
//        @Namespace(reference = "http://schemas.xmlsoap.org/soap/http", prefix = "nsl"),
//        @Namespace(reference = "http://schemas.xmlsoap.org/wsdl/soap/", prefix = "soap"),
//        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "enc"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soap")
})
public class MyRequestEnvelope {
    @Element(name = "soap:Body", required = false)
    public MyRequestBody body;

}
