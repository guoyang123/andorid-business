package com.neuedu.utils;




import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtils {
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();

    /**
     * get请求.
     * @param url
     * @param callback
     * */
    public static void get(String url, OkHttpCallback callback) {
        callback.url = url;
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static void getImage(String url, OkHttpCallback callback) {
        callback.url = url;
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * post请求.
     * */
    public static void post(String url, String json, OkHttpCallback callback) {
        callback.url = url;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        CLIENT.newCall(request).enqueue(callback);
    }
    /**
     * 图片上传
     * */
    public static void upload(String url, String filePath, OkHttpCallback callback)  {
        java.io.File file = new File(filePath);

        okhttp3.RequestBody formBody = new okhttp3.MultipartBody.Builder()
                .setType(okhttp3.MultipartBody.FORM)
                .addFormDataPart("userpic",
                        file.getName(),
                        okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), file))

                .addFormDataPart("type","user_upload")
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(formBody).build();
        CLIENT.newCall(request).enqueue(callback);
    }

    /**
     *
     * */
    public static void downFile(String url,final String saveDir, OkHttpCallback callback) {
        callback.url = url;
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);

    }



}

