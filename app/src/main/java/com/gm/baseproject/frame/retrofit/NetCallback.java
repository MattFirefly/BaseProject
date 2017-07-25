package com.gm.baseproject.frame.retrofit;


/**
 * 项目名称：JAVAEightANDRXJAVA
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/19 11:35
 * 修改人：zhanggangmin
 * 修改时间：2017/7/19 11:35
 * 修改备注：
 */

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetCallback<T> {

    public Callback GetCallback() {
        Callback<T> callback = new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(response.body());
                } else {
                    if (response.code() == 520) {
                        HashMap result = new Gson().fromJson(String.valueOf(response.errorBody().source()),
                                new TypeToken<HashMap>() {
                                }.getType());
                        EventBus.getDefault().post(result);
                    } else {
                        EventBus.getDefault().post("服务器异常");
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof SocketTimeoutException) {
                    EventBus.getDefault().post("服务器响应超时");
                }
                if (t instanceof ConnectException) {
                    EventBus.getDefault().post("服务器请求超时");
                }
                if (t instanceof JsonParseException || t instanceof JSONException || t instanceof ParseException) {
                    EventBus.getDefault().post("解析异常");
                }

            }
        };
        return callback;
    }
}
