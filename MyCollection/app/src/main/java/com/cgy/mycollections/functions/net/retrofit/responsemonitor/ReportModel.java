package com.cgy.mycollections.functions.net.retrofit.responsemonitor;

/**
 * Description :问题上传时候使用的 bean
 * Author :cgy
 * Date :2018/11/28
 */
public class ReportModel {
    public RequestModel request;
    public ResponseModel response;

    public ReportModel() {
        request = new RequestModel();
        response = new ResponseModel();
    }

    public class RequestModel {
        public String url;
        public String headers;
        public String requestBody;
    }

    public class ResponseModel {
        public String url;
        public String responseTime;//请求所花时间如 53ms
        public String headers;
        public String responseBody;
    }


    @Override
    public String toString() {
        return "request-----------------------------\nurl:\n" + request.url + "\nheaders:\n" + request.headers
                + "\nrequestBody:\n" + request.requestBody
                + "\nend request-------------------------------------\n\n"
                + "response-----------------------------\nurl:\n" + response.url + "\nheaders:\n" + response.headers
                + "\nresponseBody:\n" + response.responseBody
                + "\nend response-------------------------------------\n";
    }
}
