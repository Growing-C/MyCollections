package com.cgy.mycollections.functions.net.retrofit;

import com.cgy.mycollections.functions.net.retrofit.params.DynamicLinkParam;

import io.reactivex.Observable;

/**
 * Description :
 * Author :cgy
 * Date :2019/9/27
 */
public class ApiServiceImpl {

    private static ApiServiceImpl mInstance;

    private ApiServiceImpl() {
        Api.getInstance().setBaseUrl("https://firebasedynamiclinks.googleapis.com/");
    }

    public static ApiServiceImpl getInstance() {
        synchronized (ApiServiceImpl.class) {
            if (mInstance == null) {
                mInstance = new ApiServiceImpl();
            }
            return mInstance;
        }
    }

    /**
     * 初始化apiService
     *
     * @return
     */
    public ApiService prepareApiService() {
        return Api.getInstance().createApi(ApiService.class);
    }

    private <T> Observable<T> applySchedulers(Observable<T> source) {
        return Api.getInstance().applySchedulers(source);
    }

    //<editor-fold desc="请求内容 ">

    /**
     * 创建动态链接短链接
     *
     * @param url
     * @param domainUriPrefix
     * @param link
     * @param androidPkg
     * @param iosBundle
     * @param suffix
     * @return
     */
    public Observable<Object> createShortDynamicLinks(String url, String domainUriPrefix, String link, String androidPkg,
                                                      String iosBundle, String suffix) {
        DynamicLinkParam param = new DynamicLinkParam();
        param.setDomainUriPrefix(domainUriPrefix);
        param.setLink(link);
        param.setAndroidPackageName(androidPkg);
        param.setIosBundleId(iosBundle);
        param.setSuffix(suffix);
        return applySchedulers(prepareApiService().createShortDynamicLinks(param));
    }
    //</editor-fold">
}
