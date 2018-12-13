package com.bw.movie.mvp.model;

import com.bw.movie.constract.IApi;
import com.bw.movie.mvp.model.bean.RegisterBean;
import com.bw.movie.utils.RetrofitManager;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by mumu on 2018/11/7.
 */

public class RegisterModel {
    public Observable<RegisterBean> register(HashMap<String ,Object> hashMap) {
        return RetrofitManager.getInstance().create(IApi.class).register(hashMap);
    }
}
