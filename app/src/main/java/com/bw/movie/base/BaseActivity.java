package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mumu on 2018/11/6.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ButterKnife.bind(this);
        presenter = providePresenter();
        attachIView();
        initView();
        initListener();
        initData();
    }

    private void attachIView() {
        if (presenter != null) {
            presenter.attach(this);
        }
    }

    private void dettachIView() {
        if (presenter != null) {
            presenter.dettach();
        }
    }

    //初始化数据
    protected abstract void initData();

    //初始化数据
    protected void initListener() {
    }

    //初试化布局
    protected void initView() {
    }

    //让子类提供一个presenter
    protected abstract P providePresenter();

    //提供布局id
    protected abstract int provideLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dettachIView();
    }
}
