package com.colortu.colortu_module.colortu_base.core.http;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author : Code23
 * @time : 2020/12/22
 * @module : PostParams
 * @describe :post请求体参数
 */
public class PostParams {
    private Gson gson;
    private HashMap hashMap;

    //登录验证接口参数
    private String tenantCode = "000001";
    private String account = "watch";
    private String password = "Idfd454XdliEEssEW";
    private String type = "account";

    public PostParams() {
        gson = new Gson();
        this.hashMap = new HashMap<>();
    }

    public PostParams add(String key, Object value) {
        if (key != null && key != "" && value != null && value != "") {
            hashMap.put(key, value);
        }
        return this;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void clearPostParams() {
        hashMap.clear();
    }

    public RequestBody getVerifyBody() {
        RequestBody requestBody = new FormBody.Builder()
                .add("tenantCode", tenantCode).add("account", account)
                .add("password", password).add("type", type)
                .build();
        return requestBody;
    }

    public RequestBody getGsonRequestBody(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return requestBody;
    }

    public RequestBody getGsonRequestBody(Object obj) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), gson.toJson(obj));
        return requestBody;
    }

    public RequestBody getGsonRequestBody() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(hashMap));
        return requestBody;
    }

    public static RequestBody requestBodyJson(String[] keys, Object[] values) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            Object value = values[i];
            try {//减小数据中某一项对于所有数据的影响
                jsonObject.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
        return requestBody;
    }
}
