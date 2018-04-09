package com.circle.circle.Utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
public class JsonUtil {
    private static final String INNER_URL = "http://10.0.2.2:8080/circle/";
    /** TAG */
    private final String TAG = getClass().getSimpleName();

    public static JSONObject doPost(String action, List paramList) {

        // 1.创建请求对象
        HttpPost httpPost = new HttpPost(INNER_URL+action);
        // post请求方式数据放在实体类中
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);
            httpPost.setEntity(entity);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        // 2.创建客户端对象
        HttpClient httpClient = new DefaultHttpClient();
        // 3.客户端带着请求对象请求服务器端
        try {
            // 服务器端返回请求的数据
            HttpResponse httpResponse = httpClient.execute(httpPost);
            // 解析请求返回的数据
            if (httpResponse != null
                    && httpResponse.getStatusLine().getStatusCode() == 200) {
                String element = EntityUtils.toString(httpResponse.getEntity(),
                        HTTP.UTF_8);
                if (element.startsWith("{")) {
                    try {
                        return new JSONObject(element);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
