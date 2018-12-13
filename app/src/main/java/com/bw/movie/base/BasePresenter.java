package com.bw.movie.base;

import android.content.Context;

/**
 * Created by mumu on 2018/11/6.
 */

public abstract class BasePresenter<V extends IView> {

    protected V iView;

    public BasePresenter() {
        initModel();
    }

    public abstract void initModel();

    public void attach(V iView) {
        this.iView = iView;
    }

    public void dettach() {
        this.iView = null;
    }

    protected Context context() {
        if (iView != null) {
            return iView.context();
        }
        return null;
    }
}
