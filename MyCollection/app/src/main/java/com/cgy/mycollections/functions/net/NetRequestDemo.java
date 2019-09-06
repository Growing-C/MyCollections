package com.cgy.mycollections.functions.net;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.net.mywebservice.SignUtils;
import com.cgy.mycollections.functions.net.mywebservice.request.MyRequestBody;
import com.cgy.mycollections.functions.net.mywebservice.request.MyRequestEnvelope;
import com.cgy.mycollections.functions.net.mywebservice.request.CommonRequestModel;
import com.cgy.mycollections.functions.net.mywebservice.request.models.MicroPayParams;
import com.cgy.mycollections.functions.net.mywebservice.response.MyResponseEnvelope;
import com.cgy.mycollections.functions.net.webservice.RetrofitGenerator;
import com.cgy.mycollections.functions.net.webservice.request.RequestBody;
import com.cgy.mycollections.functions.net.webservice.request.RequestEnvelope;
import com.cgy.mycollections.functions.net.webservice.request.RequestModel;
import com.cgy.mycollections.functions.net.webservice.response.ResponseEnvelope;
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
    private static String lock = new String();

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
                int len;
                int downSize = 0;// 已下载文件大小
                int totalSize;// 文件总 长度
                URL url;
                try {
                    url = new URL("http://d2.witon.us/minipay/dev/test/download/web-20171213-231653.zip");

                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setConnectTimeout(5000);
                    conn.connect();
                    // 获取到文件的大小
                    totalSize = conn.getContentLength();
                    System.out.println("totalSize:" + totalSize);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("start down load~~~~~~~~200");
                        InputStream is = conn.getInputStream();
                        String filePath = getExternalCacheDir() + "/app.zip";
                        System.out.println("filePath:" + filePath);
                        FileOutputStream fos = new FileOutputStream(filePath);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];

                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            downSize += len;
                            // 获取当前下载量
                            System.out.println("progress:" + (downSize * 100 / totalSize));
                        }
                        fos.close();
                        bis.close();
                        is.close();
                        System.out.println("down load ok~");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
