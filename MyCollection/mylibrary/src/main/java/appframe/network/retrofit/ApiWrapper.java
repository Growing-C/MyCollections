package appframe.network.retrofit;


import com.witon.mylibrary.model.LoginInfoBean;

import appframe.network.request.LoginParams;
import appframe.network.request.RequestParams;
import appframe.network.response.MResponse;
import io.reactivex.Observable;

/**
 * 请求接口封装
 * Created by RB-cgy on 2016/9/28.
 */
public class ApiWrapper extends Api {//这是一个demo  实际应该在工程中添加这个类
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
        return getService(null, null);//baseUrl在此处定
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
    public Observable<MResponse<LoginInfoBean>> syncLogin(String uName, String uPass, String dVersion) {
        LoginParams loginParams = new LoginParams();
        loginParams.user_name = uName;
        loginParams.user_password = uPass;//admin

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
    public Observable<MResponse<LoginInfoBean>> login(String uName, String uPass, String dVersion) {
        return applySchedulers(syncLogin(uName, uPass, dVersion));
    }
}
