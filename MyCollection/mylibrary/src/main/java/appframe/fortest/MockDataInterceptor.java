package appframe.fortest;

import android.text.TextUtils;

import java.io.IOException;

import appframe.ProjectConfig;
import appframe.utils.LogUtils;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 自定义okHttp拦截器，可定制接口伪造http响应数据,用于在服务端还未开发完成时进行测试
 * Created by RB-cgy on 2016/9/29.
 */
public class MockDataInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        String path = chain.request().url().uri().getPath();
        LogUtils.d("intercept: path=" + path);
        response = interceptRequestWhenDebug(chain, path);
        if (null == response) {
            LogUtils.d("intercept: null == response");
            response = chain.proceed(chain.request());
        }
        return response;
    }

    /**
     * 测试环境下拦截需要的接口请求，伪造数据返回
     *
     * @param chain 拦截器链
     * @param path  请求的路径path
     * @return 伪造的请求Response，有可能为null
     */
    private Response interceptRequestWhenDebug(Chain chain, String path) {
        Response response = null;
        if (ProjectConfig.isDebugMode()) {
            LogUtils.d("interceptRequestWhenDebug------");
            Request request = chain.request();
//            if (path.contains(NetPathConstants.URL_LOGIN)) {//登陆
//                //伪造查询订单接口响应数据
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_GET_VALID_CODE)) {//发送验证码
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_REGISTER)) {//注册
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_UPDATE_PASSWORD)) {//修改密码
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_QUERY_HOSPITAL_LIST)) {//查询医院列表
//                response = getMockResponse(request, "mock/QueryHospitalList.json");
//            } else if (path.contains(NetPathConstants.URL_QUERY_ALL_DEPART)) {//查询医院科室列表
//                response = getMockResponse(request, "mock/QueryDepartList.json");
//            } else
//            if (path.contains(NetPathConstants.URL_QUERY_SCHEDULE_BY_PERIOD)) {//查询时间段排班
//                response = getMockResponse(request, "mock/DepartmentScheduleByPeriod.json");
//            }
// else if (path.contains(NetPathConstants.URL_QUERY_USER_INFO)) {//查询用户信息
//                response = getMockResponse(request, "mock/UserInfo.json");
//            } else if (path.contains(NetPathConstants.URL_MODIFY_USER_INFO)) {//修改用户信息
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_CHANGE_HEAD_PIC)) {//修改用户头像
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_QUERY_PATIENT_LIST)) {//查询常用就诊人
//                response = getMockResponse(request, "mock/PatientList.json");
//            } else if (path.contains(NetPathConstants.URL_QUERY_REPORT_LIST)) {//查询报告列表
//                response = getMockResponse(request, "mock/QueryReportList.json");
//            } else if (path.contains(NetPathConstants.URL_QUERY_REPORT_DETAIL)) {//查询报告详情
//                response = getMockResponse(request, "mock/QueryReportDetail.json");
//            } else if (path.contains(NetPathConstants.URL_UPDATE_DEFAULT_PATIENT)) {//修改默认就诊人
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_UPDATE_PATIENT_INFO)) {//新增/在线建卡/修改就诊人
//                response = getMockResponse(request, "mock/Login.json");
//            } else if (path.contains(NetPathConstants.URL_DELETE_PATIENT)) {//删除就诊人
//                response = getMockResponse(request, "mock/Login.json");
//            }
//            if (path.contains(NetPathConstants.URL_QUERY_PREPAID_RECORD)) {//查询住院已缴费
//                response = getMockResponse(request, "mock/HospitalizationCost.json");
//            } else if (path.contains(NetPathConstants.URL_CREATE_ORDER)) {//创建订单
//                response = getMockResponse(request, "mock/CreateOrder.json");
//            }
//            if (path.contains(NetPathConstants.URL_QUERY_PAID_RECORD)) {//查询已缴费记录
//                response = getMockResponse(request, "mock/QueryPaidRecord.json");
//            } else if (path.contains(NetPathConstants.URL_QUERY_PAID_RECORD_DETAIL)) {//查询门诊已缴费记录详情
//                response = getMockResponse(request, "mock/QueryPaidRecordDetail.json");
//            }
//            if (path.contains(NetPathConstants.URL_QUERY_REGISTER)) {//查询我的预约挂号列表
//                response = getMockResponse(request, "mock/QueryRegisterList.json");
//            } elseif (path.contains(NetPathConstants.URL_GET_APPRAISE_ITEMS)) {//查询门诊已缴费记录详情
//                response = getMockResponse(request, "mock/GetAppraiseItems.json");
//            }
//            if (path.contains(NetPathConstants.URL_QUERY_OUTPATIENT_FEE_DETAIL)) {//查询门诊缴费费用明细
//                response = getMockResponse(request, "mock/OutpatientFeeDetail.json");
//            }
        }
        return response;
    }

    /**
     * 伪造活动列表接口响应
     *
     * @param request 用户的请求
     * @return 伪造的活动列表HTTP响应
     */
    private Response getMockResponse(Request request, String path) {
        LogUtils.d("getMockResponse");
        Response response;
        String data = MockDataGenerator.getMockDataFromJsonFile(null,path);
        response = getHttpSuccessResponse(request, data);
        return response;
    }

    /**
     * 根据数据JSON字符串构造HTTP响应，在JSON数据不为空的情况下返回200响应，否则返回500响应
     *
     * @param request  用户的请求
     * @param dataJson 响应数据，JSON格式
     * @return 构造的HTTP响应
     */
    private Response getHttpSuccessResponse(Request request, String dataJson) {
        Response response;
        if (TextUtils.isEmpty(dataJson)) {
            LogUtils.d("getHttpSuccessResponse: dataJson is empty!");
            response = new Response.Builder()
                    .code(500)
                    .protocol(Protocol.HTTP_1_0)
                    .request(request)
                    //必须设置protocol&request，否则会抛出异常
                    .build();
        } else {
            response = new Response.Builder()
                    .code(200)
                    .message(dataJson)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .addHeader("Content-Type", "application/json")
                    .body(ResponseBody.create(MediaType.parse("application/json"), dataJson)).build();
        }
        return response;
    }

    private Response getHttpFailedResponse(Chain chain, int errorCode, String errorMsg) {
        if (errorCode < 0) {
            throw new IllegalArgumentException("httpCode must not be negative");
        }
        Response response;
        response = new Response.Builder()
                .code(errorCode)
                .message(errorMsg)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .build();
        return response;
    }
}
