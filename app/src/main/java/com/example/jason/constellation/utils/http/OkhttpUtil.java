package com.example.jason.constellation.utils.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtil {

    //放回String数据
    public static String getJson(String path) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //放回byte数据
    public static byte[] getByte(String path) {
        OkHttpClient client1 = new OkHttpClient();
        Request request1 = new Request.Builder().url(path).build();
        Call call1 = client1.newCall(request1);
        Response response1 = null;
        try {
            response1 = call1.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return response1.body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
