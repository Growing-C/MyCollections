package com.cgy.mycollections.functions.net.retrofit;

import com.cgy.mycollections.functions.net.retrofit.params.DynamicLinkParam;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Description :
 * Author :cgy
 * Date :2019/9/27
 */
public interface ApiService {

    //创建firebase短链接
    @POST()
    Observable<Object> createShortDynamicLinks(@Url String url,@Body DynamicLinkParam params);

}
