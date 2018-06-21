package com.cgy.mycollections.functions.net.mywebservice.response.bean;

/**
 * Created by RB-cgy on 2018/6/15.
 */
public class ResponseWrapper<T extends BaseBean> {
    public String message;//结果提示
    public String result;//SUCCESS或 ERROR

    public T data;
}
