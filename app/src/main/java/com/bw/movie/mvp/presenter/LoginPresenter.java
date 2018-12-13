package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.mvp.model.LoginModel;
import com.bw.movie.mvp.model.RegisterModel;
import com.bw.movie.mvp.model.bean.LoginBean;
import com.bw.movie.mvp.model.bean.RegisterBean;
import com.bw.movie.mvp.view.ILoginAndRegisterView;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mumu on 2018/11/5.
 */

public class LoginPresenter extends BasePresenter<ILoginAndRegisterView> {
    private LoginModel loginModel;
    private RegisterModel registerModel;


    @Override
    public void initModel() {
        loginModel = new LoginModel();
        registerModel = new RegisterModel();
    }

    public void register(HashMap<String ,Object> hashMap) {
        registerModel.register(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterBean>() {
                    @Override
                    public void accept(RegisterBean registerBean) throws Exception {
                        if (registerBean != null & "0000".equals(registerBean.getStatus())) {
                            if (iView != null)
                                iView.onRegisterSuccessed(registerBean);
                            return;
                        }
                        if (iView != null)
                            iView.onRegisterFaild(new Throwable("注册失败服务未响应"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iView != null)
                            iView.onRegisterFaild(new Throwable("注册失败网络异常"));
                    }
                });
    }

    public void login(String mobile, String password) {
        loginModel.login(mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        if (loginBean != null & "0000".equals(loginBean.getStatus())) {
                            if (iView != null)
                                iView.onLoginSuccessed(loginBean);
                            return;
                        }
                        if (iView != null)
                            iView.onLoginFaild(new Throwable("服务未响应"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iView != null)
                            iView.onLoginFaild(new Throwable("网络异常"));
                    }
                });
    }




}
