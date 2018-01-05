package appframe.network.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import appframe.network.response.MResponse;
import appframe.utils.LogUtils;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    //converter包中唯一和retrofit自带的converter不同的代码只有convert方法
    @Override
    public Object convert(ResponseBody value) throws IOException {
        //ResponseData中的流只能使用一次，由于下面所说的服务器原因，我们先将流中的数据读出在byte数组中。这个方法中已经关闭了ResponseBody,所以不需要再关闭了
        byte[] valueBytes = value.bytes();
        JsonReader jsonReader = null, errorJsonReader = null;
        try {//try中的为正常流程代码
            InputStreamReader charReader = new InputStreamReader(new ByteArrayInputStream(valueBytes), "UTF-8");//后面的charset根据服务端的编码来定
            jsonReader = gson.newJsonReader(charReader);//原来是使用value.charStream
            // 由于服务端的顽固不化，responseData为空时一定要用“”，和我们设定的泛型类型可能不同，会导致gson解析错误，所以我们catch gson解析错误
            Object obj = adapter.read(jsonReader);

            handlerTokenTimeOut(obj);
            return obj;
        } catch (JsonSyntaxException e) {//当catch到这个错误说明gson解析错误，基本肯定是服务端responseData的问题，此时我们自己解析
            System.out.println("--------------------------------------------------------------------");
            InputStreamReader errorReader = new InputStreamReader(new ByteArrayInputStream(valueBytes), "UTF-8");//后面的charset根据服务端的编码来定
            errorJsonReader = gson.newJsonReader(errorReader);

            MResponse errorResponse = new MResponse();
            errorResponse.responseData = null;//设为null

            errorJsonReader.beginObject();
            while (errorJsonReader.hasNext()) {
                String nextName = errorJsonReader.nextName();
                if ("responseCode".equals(nextName)) {//响应状态
                    errorResponse.responseCode = errorJsonReader.nextString();
                } else if ("responseMessage".equals(nextName)) {//响应信息
                    errorResponse.responseMessage = errorJsonReader.nextString();
                } else {
                    errorJsonReader.skipValue();//跳过所有其他的值
                }
            }
            errorJsonReader.endObject();

            System.out.println("errorResponse.responseCode:" + errorResponse.responseCode);
            System.out.println("--------------------------------------------------------------------");

            handlerTokenTimeOut(errorResponse);
            return errorResponse;
        } finally {
            if (jsonReader != null)
                jsonReader.close();
            if (errorJsonReader != null)
                errorJsonReader.close();
        }
    }

    /**
     * 处理token过期的情况
     *
     * @param obj
     */
    private void handlerTokenTimeOut(Object obj) {
        if (obj instanceof MResponse) {//token过期则抛出异常
            if (((MResponse) obj).responseCode.equals("token_timeout")) {
                LogUtils.d("GsonResponseBodyConverter-----------> token_timeout");
                throw new IllegalArgumentException("token_timeout");//直接抛出这个异常，处理代码见 ProxyHandler
            }
        }
    }
}