package com.bw.movie.mvp.model;


import com.bw.movie.constract.IApi;
import com.bw.movie.mvp.model.bean.LoginBean;
import com.bw.movie.utils.RetrofitManager;

import io.reactivex.Observable;

/**
 * Created by mumu on 2018/11/5.
 */

public class LoginModel {

    public Observable<LoginBean> login(String mobile, String password) {
        IApi iLoginApi = RetrofitManager.getInstance().create(IApi.class);
        Observable<LoginBean> loginBeanObservable = iLoginApi.login(mobile, password);
        return loginBeanObservable;
    }
}
