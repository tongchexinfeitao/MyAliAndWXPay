package com.bw.movie.mvp.view.fragment;

import com.bw.movie.base.IView;
import com.bw.movie.mvp.model.bean.LoginBean;

/**
 * Created by mumu on 2018/11/5.
 */

public interface IRegisterView extends IView{
    void onSuccessed(LoginBean loginBean);

    void onFaid(Throwable t);

}
