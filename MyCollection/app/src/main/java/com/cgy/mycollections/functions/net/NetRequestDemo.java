package com.cgy.mycollections.functions.net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.HttpUrlConnectionUtil;
import com.cgy.mycollections.utils.encrypt.SignUtils;
import com.cgy.mycollections.functions.net.mywebservice.request.MyRequestBody;
import com.cgy.mycollections.functions.net.mywebservice.request.MyRequestEnvelope;
import com.cgy.mycollections.functions.net.mywebservice.request.CommonRequestModel;
import com.cgy.mycollections.functions.net.mywebservice.request.models.MicroPayParams;
import com.cgy.mycollections.functions.net.mywebservice.response.MyResponseEnvelope;
import com.cgy.mycollections.functions.net.retrofit.Api;
import com.cgy.mycollections.functions.net.retrofit.ApiServiceImpl;
import com.cgy.mycollections.functions.net.webservice.RetrofitGenerator;
import com.cgy.mycollections.functions.net.webservice.request.RequestBody;
import com.cgy.mycollections.functions.net.webservice.request.RequestEnvelope;
import com.cgy.mycollections.functions.net.webservice.request.RequestModel;
import com.cgy.mycollections.functions.net.webservice.response.ResponseEnvelope;

import appframe.utils.L;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import appframe.network.retrofit.callback.ApiCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetRequestDemo extends AppCompatActivity {

    public static final String SQ_HOSPITAL_ID = "sqsfcyyadmin";
    public static final String SIGN_KEY = "8f57591a40ef9b1cd35e553d0151b58d";

    String tradeNo;
    String mBizId = "biz_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_request_demo);
    }


    //<editor-fold desc="webService ">

    //1.创建订单 retrofit+webservice
    public void createOrder(View v) {
        MyRequestEnvelope requestEnvelope = new MyRequestEnvelope();
        MyRequestBody requestBody = new MyRequestBody();

        CommonRequestModel requestModel = new CommonRequestModel();
//        requestModel.requestAttribute = "http://www.witontek.com/ehospital/";

//        requestModel.inputStr = "{\"sign\":\"78EB2D69A2E321DC056560F7912AEFFD\",\"hospital_id\":\"rbzhyyadmin\",\"biz_id\":\"111\",\"trade_no\":null}";

        mBizId = UUID.randomUUID().toString();

        MicroPayParams payParams = new MicroPayParams();
        payParams.hospital_id = SQ_HOSPITAL_ID;
        payParams.auth_code = "134727187749422289";//支付授权码
        payParams.order_amount = "0.01";
        payParams.biz_id = mBizId;//客户端自定义 唯一，不超过32位
        payParams.data_src = "hisSrc";//支付类型来源
        payParams.cashier_id = "1000001(张三)";//收银员代码，不超过32个字符
        payParams.sign = SignUtils.sign(payParams, SIGN_KEY);//签名

        Gson gson = new Gson();
        requestModel.inputStr = gson.toJson(payParams);
        System.out.println("inputStr:" + requestModel.inputStr);

        requestBody.microPay = requestModel;
        requestEnvelope.body = requestBody;

        Call<MyResponseEnvelope> call = RetrofitGenerator.getWeatherInterfaceApi().createOrder(requestEnvelope);
        call.enqueue(new Callback<MyResponseEnvelope>() {
            @Override
            public void onResponse(Call<MyResponseEnvelope> call, Response<MyResponseEnvelope> response) {
                System.out.println("onResponsep-----------------------");
                if (response != null) {
                    MyResponseEnvelope responseEnvelope = response.body();
                    System.out.println("result:" + responseEnvelope.body.microPayResponse.result);
//                    {"message":"请求签名不正确!","result":"ERROR"}
                }
            }

            @Override
            public void onFailure(Call<MyResponseEnvelope> call, Throwable t) {
                System.out.println("onFailure-----------------------");
            }
        });
    }

    /**
     * 查询订单状态  retrofit+webservice
     *
     * @param v
     */
    public void queryOrderStatus(View v) {
        MyRequestEnvelope requestEnvelope = new MyRequestEnvelope();
        MyRequestBody requestBody = new MyRequestBody();

        CommonRequestModel requestModel = new CommonRequestModel();

//        requestModel.inputStr = "{\"sign\":\"78EB2D69A2E321DC056560F7912AEFFD\",\"hospital_id\":\"rbzhyyadmin\",\"biz_id\":\"111\",\"trade_no\":null}";

        MicroPayParams payParams = new MicroPayParams();
        payParams.hospital_id = SQ_HOSPITAL_ID;
//        payParams.trade_no = "7ffad409e755b50f";// trade_no和biz_id二选一
//        payParams.biz_id = "4a995119c725431285c04d647109d05f";
        payParams.biz_id = mBizId;
        payParams.sign = SignUtils.sign(payParams, SIGN_KEY);//签名

        Gson gson = new Gson();
        requestModel.inputStr = gson.toJson(payParams);

        System.out.println("inputStr:" + requestModel.inputStr);

        requestBody.queryPayStatus = requestModel;
        requestEnvelope.body = requestBody;

        Call<MyResponseEnvelope> call = RetrofitGenerator.getWeatherInterfaceApi().queryPayStatus(requestEnvelope);
        call.enqueue(new Callback<MyResponseEnvelope>() {
            @Override
            public void onResponse(Call<MyResponseEnvelope> call, Response<MyResponseEnvelope> response) {
                System.out.println("onResponsep-----------------------");
                if (response != null) {
                    MyResponseEnvelope responseEnvelope = response.body();
                    System.out.println("result:" + responseEnvelope.body.qryPayStatusResponse.result);
                }
            }

            @Override
            public void onFailure(Call<MyResponseEnvelope> call, Throwable t) {
                System.out.println("onFailure-----------------------");
            }
        });
    }

    //订单撤销 retrofit+webservice
    public void reverseOrder(View v) {
        MyRequestEnvelope requestEnvelope = new MyRequestEnvelope();
        MyRequestBody requestBody = new MyRequestBody();

        CommonRequestModel requestModel = new CommonRequestModel();

        MicroPayParams payParams = new MicroPayParams();
        payParams.hospital_id = SQ_HOSPITAL_ID;
//        payParams.trade_no = "7ffad409e755b50f77";
        payParams.biz_id = mBizId;//与订单号二选一
        payParams.sign = SignUtils.sign(payParams, SIGN_KEY);//签名

        Gson gson = new Gson();
        requestModel.inputStr = gson.toJson(payParams);

        requestBody.reverseOrder = requestModel;
        requestEnvelope.body = requestBody;

        Call<MyResponseEnvelope> call = RetrofitGenerator.getWeatherInterfaceApi().reverseOrder(requestEnvelope);
        call.enqueue(new Callback<MyResponseEnvelope>() {
            @Override
            public void onResponse(Call<MyResponseEnvelope> call, Response<MyResponseEnvelope> response) {
                System.out.println("onResponsep-----------------------");
                if (response != null) {
                    MyResponseEnvelope responseEnvelope = response.body();
                    System.out.println("result:" + responseEnvelope.body.reverseOrderResponse.result);
                }
            }

            @Override
            public void onFailure(Call<MyResponseEnvelope> call, Throwable t) {
                System.out.println("onFailure-----------------------");
            }
        });
    }

    /**
     * 查询天气 retrofit+webservice
     *
     * @param v
     */
    public void queryWeather(View v) {
        RequestEnvelope requestEnvelop = new RequestEnvelope();
        RequestBody requestBody = new RequestBody();
        RequestModel requestModel = new RequestModel();
        requestModel.theCityName = "南通";
        requestModel.cityNameAttribute = "http://WebXml.com.cn/";
        requestBody.getWeatherbyCityName = requestModel;
        requestEnvelop.body = requestBody;
        Call<ResponseEnvelope> call = RetrofitGenerator.getWeatherInterfaceApi().getWeatherbyCityName(requestEnvelop);
        call.enqueue(new Callback<ResponseEnvelope>() {
            @Override
            public void onResponse(Call<ResponseEnvelope> call, Response<ResponseEnvelope> response) {
                ResponseEnvelope responseEnvelope = response.body();
                if (responseEnvelope != null) {
                    List<String> weatherResult = responseEnvelope.body.getWeatherbyCityNameResponse.result;
                }
            }

            @Override
            public void onFailure(Call<ResponseEnvelope> call, Throwable t) {

            }

        });
    }

    //</editor-fold>

    //<editor-fold desc="HttpURLConnection ">

    /**
     * 测试连接  http get  使用 HttpURLConnection
     *
     * @param view
     */
    public void get(View view) {//http get
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("https://rbtest.witontek.com/wpay/alipay/app_test.json");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// 此处的urlConnection对象实际上是根据URL的
                    if (200 == urlConnection.getResponseCode()) {
                        //得到输入流
                        InputStream is = urlConnection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while (-1 != (len = is.read(buffer))) {
                            baos.write(buffer, 0, len);
                            baos.flush();
                        }
                        String s = baos.toString("utf-8");
                        baos.close();
                        is.close();
                        JSONObject jsonObject = new JSONObject(s);

                        System.out.println("--------------------------s:" + jsonObject.getString("orderStr"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static final String CHARSET = "utf-8";// 编码方式

    private static final int TIME_OUT = 10 * 1000;// 超时时间
    private static final String lock = "lock";

    public void post(View view) {//http post
        new Thread() {
            public void run() {
                synchronized (lock) {
                    try {
                        JSONObject requestData = new JSONObject();
                        requestData.put("mobile", "15051286108");
                        requestData.put("password", "123456");
                        JSONObject inputJson = new JSONObject();
                        inputJson.put("requestData", requestData);
                        System.out.println("inputJson:" + inputJson.toString());

                        URL url = new URL("http://139.224.15.30:8080/hrss/rest/user/login");
                        HttpURLConnection conn = (HttpURLConnection) url
                                .openConnection();
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestMethod("POST");
                        conn.setConnectTimeout(TIME_OUT);

                        conn.connect();
                        OutputStream out = conn.getOutputStream();
                        out.write(inputJson.toString().getBytes());
                        out.flush();
                        out.close();

                        int code = conn.getResponseCode();
                        if (code == HttpURLConnection.HTTP_OK) {
                            byte[] bit = readInputStream(conn
                                    .getInputStream());
                            String string = new String(bit, CHARSET);
                            System.out.println("outputString:" + string);
                        } else {
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    //测试钉钉webhook自定义机器人
    public void dingding(View view) {//http post
        new Thread() {
            public void run() {
                synchronized (lock) {
                    try {
//                        {
//                            "msgtype": "text",
//                            "text": {
//                                "content": "我就是我,  @1825718XXXX 是不一样的烟火"
//                             },
//                            "at": {
//                              "atMobiles": [
//                              "1825718XXXX"
//                               ],
//                              "isAtAll": false
//                             }
//                          }
                        JSONObject requestData = new JSONObject();
                        requestData.put("content", "我就是我, 是不一样的烟火");

                        JSONObject atData = new JSONObject();
                        atData.put("isAtAll", false);
                        JSONArray atMobiles = new JSONArray();
                        atMobiles.put(0, "15051286108");
                        atData.put("atMobiles", atMobiles);

                        //text类型
                        JSONObject inputJson = new JSONObject();
                        inputJson.put("text", requestData);
                        inputJson.put("msgtype", "text");
                        inputJson.put("at", atData);

                        //link类型
//                        String testLink="{\"msgtype\":\"link\",\"link\":{\"text\":\"tttttttt\",\"title\":\"testTitle\",\"picUrl\":\"\",\"messageUrl\": \"https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.Rqyvqo&treeId=257&articleId=105735&docType=1\"}}";
//                        inputJson=new JSONObject(testLink);
                        System.out.println("inputJson:" + inputJson.toString());

                        URL url = new URL("https://oapi.dingtalk.com/robot/send?access_token=acd6eda87cfe17dcbf12863338787d3bd1dd7fa931b0a3a741f5ded5d6af34b4");
                        HttpURLConnection conn = (HttpURLConnection) url
                                .openConnection();

                        conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestMethod("POST");
                        conn.setConnectTimeout(TIME_OUT);

                        conn.connect();
                        OutputStream out = conn.getOutputStream();
                        out.write(inputJson.toString().getBytes());
                        out.flush();
                        out.close();

                        int code = conn.getResponseCode();
                        if (code == HttpURLConnection.HTTP_OK) {
                            byte[] bit = readInputStream(conn
                                    .getInputStream());
                            String string = new String(bit, CHARSET);
                            System.out.println("outputString:" + string);
                        } else {
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    public void downLoad(View view) {//http download data
        new Thread() {
            @Override
            public void run() {
                if (getExternalCacheDir() != null)
                    HttpUrlConnectionUtil.downLoad(
                            "http://d2.witon.us/minipay/dev/test/download/web-20171213-231653.zip",
                            getExternalCacheDir().getPath());
            }
        }.start();
    }

    /**
     * 禅道上传图片
     *
     * @param imgPath
     * @return
     */
    public static synchronized String uploadImage(String imgPath) {
        String msg;
        File file = new File(imgPath);
        if (!file.exists()) {
            msg = "文件不存在！";
            return msg;
        }

        try {
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";//边界 可以自定义

            URL url = new URL("http://192.168.32.116/zentao/index.php?m=file&f=ajaxUpload&t=json&uid=odts3hcdp9l5fjng1p874t0082&dir=image");
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();

//            conn.setRequestProperty("Cookie", ZentaoAccountKeeper.sZentaoCookie);//提交bug必须有cookie
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 设置字符编码连接参数
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(TIME_OUT);

            conn.connect();

            //上传文件 这里面的 boundary必须有，不过可以自定义
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            out.writeBytes(twoHyphens + boundary + end);
            out.writeBytes("Content-Disposition: form-data; name=\"imgFile\"; filename=\""
                    + file.getName() + "\"\r\n");// 这里的name由接口给出  可变

            out.writeBytes(end);
            byte[] bufferOut = new byte[2048];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            out.writeBytes(end);
            out.writeBytes("--" + boundary + "\r\n");

            in.close();

            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                byte[] bit = readInputStream(conn
                        .getInputStream());
                String string = new String(bit, CHARSET);

                JSONObject response = new JSONObject(string);
                int result = response.optInt("error");
                if (result == 0) {
                    msg = "上传成功";
                    String urla = response.optString("url");
                    return urla;
                } else {
                    JSONObject message = response.optJSONObject("message");

                    msg = "上传失败" + message.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        msg = "上传图片失败";
        return msg;
    }
    //</editor-fold">

    //<editor-fold desc="retrofit ">

    /**
     * rest方式获取短链接
     *
     * @param v
     */
    public void getDynamicLinks(View v) {
//        String apiKey = "AIzaSyCw6Qu7W_Gsis_FuH7aPwUsFjr-MxvIKok";
//        String getShortLinkUrl = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=" + apiKey;

        String requestUrl = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=AIzaSyCw6Qu7W_Gsis_FuH7aPwUsFjr-MxvIKok";

        String domainUriPrefix = "https://yolotest.page.link";
        String DEEP_LINK_URL = "https://eco.blockchainlock.io/shareid=dfa";
        String androidPkgName = "com.linkstec.blockchains.bclwallet";
        String iosBundleId = null;
        String suffix = "SHORT";
        L.e("start createShortDynamicLinks");
//        Api.getInstance().setBaseUrl("https://firebasedynamiclinks.googleapis.com/");
        Api.getInstance().setBaseUrl(requestUrl);
        ApiServiceImpl.getInstance().createShortDynamicLinks(requestUrl,
                domainUriPrefix, DEEP_LINK_URL, androidPkgName, null, suffix)
                .subscribe(new ApiCallback<Object>() {
                    @Override
                    public void onSuccess(Object model) {
                        L.e("getDynamicLinks onSuccess:" + model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        L.e("getDynamicLinks onFailure:" + code + msg);

                    }

                    @Override
                    public void onFinish() {

                        L.e("getDynamicLinks onFinish ");
                    }
                });
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
////                POST https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=api_key
////                Content-Type: application/json
////
////                {
////                    "dynamicLinkInfo": {
////                    "domainUriPrefix": "https://blockchainlock.page.link",
////                            "link": "https://www.example.com/",
////                            "androidInfo": {
////                        "androidPackageName": "com.example.android"
////                    },
////                    "iosInfo": {
////                        "iosBundleId": "com.example.ios"
////                    },
////                "suffix": {
////                    "option": "SHORT" or "UNGUESSABLE"
////                }
////                }
////                }
//                String apiKey = "AIzaSyClrhlCSQ0tmo7JTAWFCOtRmiWYGjdqBKc";
//                String getShortLinkUrl = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=" + apiKey;
//
//                String domainUriPrefix = "https://blockchainlock.page.link";
////                String DEEP_LINK_URL = "http://eco.blockchainlock.io/bcllock/share/index.html?shareId=testsss";
//                String DEEP_LINK_URL = "https://eco.blockchainlock.io/";
//                try {
//                    String testLongLink = "https://blockchainlock.page.link/share?amv=0&apn=io.blockchainlock.deeplink&link=http%3A%2F%2Feco.blockchainlock.io%2Fbcllock%2Fshare%2Findex.html%3FshareId%3D414";
//                    JSONObject requestData = new JSONObject();
////                    requestData.put("longDynamicLink",testLongLink);
//
//                    JSONObject dynamicLinkInfo = new JSONObject();
//                    dynamicLinkInfo.put("domainUriPrefix", domainUriPrefix);
////                    dynamicLinkInfo.put("link", DEEP_LINK_URL);
//                    dynamicLinkInfo.put("link", Uri.parse(DEEP_LINK_URL));
//
//                    JSONObject androidInfo = new JSONObject();
//                    androidInfo.put("androidPackageName", "io.blockchainlock.deeplink");
//                    dynamicLinkInfo.put("androidInfo", androidInfo);
//
//                    JSONObject suffix = new JSONObject();
//                    suffix.put("option", "SHORT");
//                    dynamicLinkInfo.put("suffix", suffix);
//
//                    requestData.put("dynamicLinkInfo", dynamicLinkInfo);
//
//                    L.e("getDynamicLinks Uri.parse(DEEP_LINK_URL):" + Uri.parse(DEEP_LINK_URL));
//                    L.e("getDynamicLinks url:" + getShortLinkUrl);
//                    L.e("getDynamicLinks inputJson:" + requestData.toString());
//
//                    URL url = new URL(getShortLinkUrl);
//                    HttpURLConnection conn = (HttpURLConnection) url
//                            .openConnection();
//
//                    conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
//                    conn.setDoOutput(true);
//                    conn.setDoInput(true);
//                    conn.setRequestMethod("POST");
//                    conn.setConnectTimeout(TIME_OUT);
//
//                    conn.connect();
//                    OutputStream out = conn.getOutputStream();
//                    out.write(requestData.toString().getBytes());
//                    out.flush();
//                    out.close();
//
//                    int code = conn.getResponseCode();
//
//                    L.e("getDynamicLinks result code:" + code);
//                    L.e("getDynamicLinks result msg:" + conn.getResponseMessage());
//                    String result = "code:" + code;
//                    if (code == HttpURLConnection.HTTP_OK) {
//                        byte[] bit = readInputStream(conn
//                                .getInputStream());
//                        result = new String(bit, CHARSET);
//                        System.out.println("outputString:" + result);
//                    } else {
//                    }
//                    e.onNext(result);
//                } catch (Exception ex) {
//                    e.onError(ex);
//                }
//                e.onComplete();
//            }
//        }).compose(RxUtil.applySchedulersJobUI())
//                .subscribe(new ApiCallback<String>() {
//                    @Override
//                    public void onSuccess(String model) {
//                        L.e("getDynamicLinks onSuccess:" + model);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        L.e("getDynamicLinks onFailure:" + code + msg);
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                        L.e("getDynamicLinks onFinish ");
//                    }
//                });
    }


    //</editor-fold>

    /**
     * 读取输入流数据，返回一个byte数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);// 把buffer里的数据写入流中

        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();

        return data;
    }


}
