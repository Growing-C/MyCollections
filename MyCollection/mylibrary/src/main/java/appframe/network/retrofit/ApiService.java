package appframe.network.retrofit;

import com.witon.mylibrary.model.LoginInfoBean;

import appframe.network.request.LoginParams;
import appframe.network.request.RequestParams;
import appframe.network.response.MResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 请求接口，所有的请求应该都在这儿
 * Created by RB-cgy on 2016/9/28.
 */
public interface ApiService {//这是一个demo  实际应该在工程中添加这个类

    //登陆
//    @Headers({"Content-Type: application/json", "Accept: application/json"})// headers似乎可加可不
    @POST("")
    Observable<MResponse<LoginInfoBean>> login(@Body RequestParams<LoginParams> params);//@Body 的参数也可以使用okhttp3.RequestBody
}
