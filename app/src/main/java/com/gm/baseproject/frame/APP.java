package com.gm.baseproject.frame;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gm.baseproject.BuildConfig;

import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/20 17:55
 * 修改人：zhanggangmin
 * 修改时间：2017/7/20 17:55
 * 修改备注：
 */
public class APP extends Application {
    private static final long DEFAULT_TIMEOUT = 10;//超时时间
    public static final String HOST = "http://10.10.10.240/intelligent-warehouse-server-test/api";//生产
    //    public static final String HOST = "http://10.10.10.240/intelligent-warehouse-server/api";//测试
    public static final String DEBUGHOST = "http://192.10.14.142/intelligent-warehouse-server-test/api";
    Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient client;
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        //Retrofit框架连接超时 和cookie配置
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            //chrome://inspect/#devices  调试网络抓包用
            Stetho.initializeWithDefaults(this);
            httpBuilder.addNetworkInterceptor(new StethoInterceptor());
            retrofitBuilder.baseUrl(DEBUGHOST);
//            路由日志调试功能
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)

        } else {
            retrofitBuilder.baseUrl(HOST);
        }
        client = httpBuilder.build();
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.client(client);
        retrofitBuilder.build();
        ARouter.init(this);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
