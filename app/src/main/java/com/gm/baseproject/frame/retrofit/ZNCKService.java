package com.gm.baseproject.frame.retrofit;

import com.gm.baseproject.BuildConfig;

import retrofit2.Retrofit;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/21 18:06
 * 修改人：zhanggangmin
 * 修改时间：2017/7/21 18:06
 * 修改备注：
 */
public enum ZNCKService {
    INSTANCE;

    private ZNCkApi znckService;

    ZNCKService() {
        Retrofit.Builder retrofitBuilder = ServiceBuilder.INSTANCE.getRetrofitBuilder();
        if (BuildConfig.DEBUG) {
            znckService = retrofitBuilder.baseUrl(ZNCkApi.DEBUGHOST).build().create(ZNCkApi.class);
        } else {
            znckService = retrofitBuilder.baseUrl(ZNCkApi.HOST).build().create(ZNCkApi.class);
        }
    }

    public ZNCkApi getZNCkApi() {
        return znckService;
    }
}
