package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.mvp.model.bean.LoginBean;
import com.bw.movie.mvp.model.bean.RegisterBean;

/**
 * Created by mumu on 2018/11/5.
 */

public interface ILoginAndRegisterView extends IView {
    void onLoginSuccessed(LoginBean loginBean);

    void onRegisterSuccessed(RegisterBean registerBean);

    void onLoginFaild(Throwable t);

    void onRegisterFaild(Throwable t);
}
