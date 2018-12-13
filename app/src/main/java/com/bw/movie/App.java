package com.bw.movie;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by mumu on 2018/11/5.
 */

public class App extends Application {
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this,"wxb3852e6a6b7d9516", false);

    @Override
    public void onCreate() {
        super.onCreate();
// 将该app注册到微信
        msgApi.registerApp("wxb3852e6a6b7d9516");
    }
}
