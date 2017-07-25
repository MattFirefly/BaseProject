package com.gm.baseproject.frame.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/25 10:44
 * 修改人：zhanggangmin
 * 修改时间：2017/7/25 10:44
 * 修改备注：
 */
public enum  ServiceBuilder {
    INSTANCE;


    private final Retrofit.Builder retrofitBuilder;


    ServiceBuilder() {
        retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpBuilder.INSTANCE.getOkHttpClient());

    }

    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }
}
