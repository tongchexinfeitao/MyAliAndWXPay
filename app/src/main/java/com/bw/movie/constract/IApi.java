package com.bw.movie.constract;

import com.bw.movie.mvp.model.bean.LoginBean;
import com.bw.movie.mvp.model.bean.RegisterBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mumu on 2018/11/2.
 */

public interface IApi {

    @FormUrlEncoded
    @POST("user/v1/registerUser")
    Observable<RegisterBean> register(@FieldMap HashMap<String,Object> map);

    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<LoginBean> login(@Field("phone") String mobile, @Field("pwd") String password);


}
