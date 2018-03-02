package com.example.jason.constellation.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
    //过时的网络加载
    public static boolean isConnected(Context context) {// 网络连接管理者
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 通过管理者获取网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        // 判断网络是否联通
        if (info != null) {
            return info.isConnected();
        }
        return false;
    }

    public static byte[] loadData(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len = 0;
                byte[] b = new byte[1024];
                while ((len = is.read(b)) != -1) {
                    bos.write(b, 0, len);
                    bos.flush();
                }
                bos.close();
                is.close();
                return bos.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
