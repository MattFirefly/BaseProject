package com.gm.baseproject.frame.retrofit;

import com.gm.baseproject.entity.Login;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/21 17:50
 * 修改人：zhanggangmin
 * 修改时间：2017/7/21 17:50
 * 修改备注：
 */
public interface ZNCkApi {
    String DEBUGHOST = "http://192.10.14.142/intelligent-warehouse-server-test/api/";

    String HOST = "http://10.10.10.240/intelligent-warehouse-server-test/api/";//生产

    @GET("wh/check_apply/person_check_store_count")
    Call<ResponseBody> statistics(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("usr/user/login")
    Call<Login> login(@FieldMap Map<String,String> map);
}
