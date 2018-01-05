package appframe.network.retrofit;


import appframe.app.Constants;
import appframe.app.NetPathConstants;
import appframe.fortest.MockUserBean;
import appframe.network.request.LoginParams;
import appframe.network.request.RequestParams;
import appframe.network.response.MResponse;
import io.reactivex.Observable;

/**
 * 请求接口封装
 * Created by RB-cgy on 2016/9/28.
 */
public class ApiWrapper extends Api {
    private static ApiWrapper mInstance;

    private ApiWrapper() {
    }

    public static ApiWrapper getInstance() {
        synchronized (ApiWrapper.class) {
            if (mInstance == null) {
                mInstance = new ApiWrapper();
            }
            return mInstance;
        }
    }

    /**
     * 根据服务器地址初始化apiService
     *
     * @return
     */
    private ApiService prepareApiService() {
        return getService(NetPathConstants.SERVER_URL);//baseUrl在此处定义
    }

    /**
     * 使用代理，专门用来自动处理token过期事件
     *
     * @return
     */
    private ApiService getApiServiceProxy() {
        return getProxy(prepareApiService());
    }

    /**
     * 同步登陆，仅在{ ProxyHandler}中使用
     *
     * @param uName
     * @param uPass
     * @return
     */
    public Observable<MResponse<MockUserBean>> syncLogin(String uName, String uPass, String dVersion) {
        LoginParams loginParams = new LoginParams();
        loginParams.user_name = uName;
        loginParams.user_password = uPass;//admin
        loginParams.d_version = dVersion;
        //以下都是固定参数
        loginParams.hospital_id = Constants.HOSPITAL_ID;
        loginParams.client_system = "Android";
        loginParams.client_version = Constants.APP_VER;//app版本
        loginParams.client_serianum = Constants.DEVICE_ID;//登录设备号标示
        loginParams.client_osversion = Constants.SYSTEM_VER;//登录设备号标示

        RequestParams<LoginParams> requestParams = new RequestParams<>();
        requestParams.requestToken = "";
        requestParams.requestData = loginParams;

        return prepareApiService().login(requestParams);
    }

    /**
     * 异步登陆实现
     *
     * @param uName
     * @param uPass
     * @return
     */
    public Observable<MResponse<MockUserBean>> login(String uName, String uPass, String dVersion) {
        return applySchedulers(syncLogin(uName, uPass, dVersion));
    }

}
