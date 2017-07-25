package com.gm.baseproject.frame.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gm.baseproject.BuildConfig;

import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/25 10:51
 * 修改人：zhanggangmin
 * 修改时间：2017/7/25 10:51
 * 修改备注：
 */
public enum OkHttpBuilder {
    INSTANCE;
    private static final long DEFAULT_TIMEOUT = 10;//超时时间

    private OkHttpClient okHttpClient;

    OkHttpBuilder() {
        //Retrofit框架连接超时 和cookie配置
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            //chrome://inspect/#devices  调试网络抓包用
            httpBuilder.addNetworkInterceptor(new StethoInterceptor());
        }
        okHttpClient=httpBuilder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
