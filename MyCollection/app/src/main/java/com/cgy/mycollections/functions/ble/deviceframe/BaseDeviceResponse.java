package com.cgy.mycollections.functions.ble.deviceframe;


import java.io.Serializable;

/**
 * Created by zhouzd on 2018/6/13.
 */
public class BaseDeviceResponse implements Serializable {
    private String command;

    protected byte[] bytes; //接收到的payload数据，在校验数据的时候保存起来

    boolean isSuccess = false;//是否指令成功
    int errorCode = -1;//错误码
    private String status;//error success  undefined
    private String msg;
    private String code; //"0" succcess,"1" error
    private String respData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        if ("1".equals(code))//1代表成功
            setSuccess(true);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRespData() {
        return respData;
    }

    public void setRespData(String respData) {
        this.respData = respData;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
