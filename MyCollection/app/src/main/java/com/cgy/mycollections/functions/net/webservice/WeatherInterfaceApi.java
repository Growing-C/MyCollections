package com.cgy.mycollections.functions.net.webservice;


import com.cgy.mycollections.functions.net.mywebservice.request.MyRequestEnvelope;
import com.cgy.mycollections.functions.net.mywebservice.response.MyResponseEnvelope;
import com.cgy.mycollections.functions.net.webservice.request.RequestEnvelope;
import com.cgy.mycollections.functions.net.webservice.response.ResponseEnvelope;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 接口请求
 * Created by SmileXie on 16/7/15.
 */
public interface WeatherInterfaceApi {

    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://WebXml.com.cn/getWeatherbyCityName"})//请求的Action，类似于方法名
    @POST("WeatherWebService.asmx")
    Call<ResponseEnvelope> getWeatherbyCityName(@Body RequestEnvelope requestEnvelope);

    //反扫下单
    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://www.witontek.com/ehospital/microPay"})//请求的Action，类似于方法名
    @POST("miniPayServiceV2")//miniPayServiceV2?wsdl/microPay
    Call<MyResponseEnvelope> createOrder(@Body MyRequestEnvelope requestEnvelope);

    //查询订单状态
    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://www.witontek.com/ehospital/qryPayStatus"})//请求的Action，类似于方法名
    @POST("miniPayServiceV2")//miniPayServiceV2?wsdl/microPay
    Call<MyResponseEnvelope> queryPayStatus(@Body MyRequestEnvelope requestEnvelope);

    //订单撤销
    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://www.witontek.com/ehospital/reverseOrder"})//请求的Action，类似于方法名
    @POST("miniPayServiceV2")//miniPayServiceV2?wsdl/microPay
    Call<MyResponseEnvelope> reverseOrder(@Body MyRequestEnvelope requestEnvelope);

}
