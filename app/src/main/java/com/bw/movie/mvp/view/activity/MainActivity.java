package com.bw.movie.mvp.view.activity;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.mvp.model.bean.LoginBean;
import com.bw.movie.mvp.model.bean.RegisterBean;
import com.bw.movie.mvp.presenter.LoginPresenter;
import com.bw.movie.mvp.view.ILoginAndRegisterView;
import com.bw.movie.utils.EncryptUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity<LoginPresenter> implements ILoginAndRegisterView {
    //账号
    private String phone = "15501186623";
    //密码
    private String pwd = "123456";
    LoginBean loginBean;
    private Handler mHandler = new Handler();

    @Override
    protected void initData() {
    }

    @Override
    protected LoginPresenter providePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 可以改成自己的账号和密码注册
     * @param view
     */
    public void register(View view) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nickName", "mumu");
        hashMap.put("phone", phone);
        hashMap.put("pwd", EncryptUtil.encrypt(pwd));
        hashMap.put("pwd2", EncryptUtil.encrypt(pwd));
        hashMap.put("sex", "1");
        hashMap.put("birthday", "1980-02-25");
        hashMap.put("imei", "123456");
        hashMap.put("ua", "小米/红米");
        hashMap.put("screenSize", "5.0");
        hashMap.put("os", "android");
        hashMap.put("email", "849616168@qq.com");
        presenter.register(hashMap);
    }

    public void login(View view) {
        presenter.login(phone, EncryptUtil.encrypt(pwd));
    }

    @Override
    public void onLoginSuccessed(LoginBean loginBean) {
        this.loginBean = loginBean;
        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterSuccessed(RegisterBean registerBean) {
        Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFaild(Throwable e) {
        Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterFaild(Throwable t) {
        Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_LONG).show();

    }

    @Override
    public Context context() {
        return this;
    }

    /**
     * 用户登录过后，通过这个方法可以获得 购票下单接口需要的userId、sessionId、以及sign；
     * 注意： 购票下单接口， 需要的SchelerId  这里为75，购票数量为1，
     * 得到sign之后，然后才能正确使用 购票下单接口，然后就可以获取到orderId
     * ，拿到orderId之后就可以去调用服务器的支付接口，拿到微信支付和支付宝支付需要的数据
     *
     * @param view
     */
    public void buyTicket(View view) {
        //按照接口文档的要求，按顺序拼接字段进行MD5加密来获取sign
        String sign = EncryptUtil.MD5(loginBean.getResult().getUserId() + "" + "751" + "movie");
        Log.e("tag", "sign == " + sign);
        Log.e("tag", "sessionId == " + loginBean.getResult().getSessionId());
        Log.e("tag", "userId == " + loginBean.getResult().getUserId());

    }


    /**
     * 微信支付
     * ps：因为涉及到时间戳的问题，需要使用最新数据来配置
     * @param view
     */
    public void wxPay(View view) {
        //注册微信
        final IWXAPI api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        api.registerApp("wxb3852e6a6b7d9516");
        //构造微信支付请求
        PayReq request = new PayReq();
        request.appId = "wxb3852e6a6b7d9516";
        request.partnerId = "1510865081";
        request.prepayId = "wx091134438452595fc41cf08a2895195891";
        request.packageValue = "Sign=WXPay";
        request.nonceStr = "sixvXwX2GwHjeSZO";
        request.timeStamp = "1541734475";
        request.sign = "2765384FBCE500EB62B5044157EEA1B3";
        //发送请求信息，调起微信支付(手机上必须装了微信才能调的起来)
        api.sendReq(request);
    }

    /**
     * 支付宝支付
     * @param view
     */
    public void aliPay(View view) {
        //orderInfo必须来之服务器
        final String orderInfo ="alipay_sdk=alipay-sdk-java-3.1.0&app_id=2018080760951276&biz_content=%7B%22out_trade_no%22%3A%2220181108144828261%22%2C%22subject%22%3A%22%E5%85%AB%E7%BB%B4%E7%A7%BB%E5%8A%A8%E9%80%9A%E4%BF%A1%E5%AD%A6%E9%99%A2-%E7%BB%B4%E5%BA%A6%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.16%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.bwstudent.com%2FpayApi%2FaliPay%2FaliNotification&sign=jo%2Bjm8LNsJOxUvvMWtsp1f%2BkytGRDgTZgWufQg%2FDszRYx%2F7zJGPpMbNl8GbtQnt7pZWTN%2BL9RudgBv4%2BAyZ%2BPQflbzi8NJMR1NHyrLu4zTMI9Pe9jl3apM5COQCnSwyXPS1QtxqalVpDeHPGoJH%2FKJXD76WA67LH0Z%2FZ79MnpkvkzyHbYKB3HB7EF5eqJSQnE8%2B%2BxkTUGPiYK%2FIwhLXrnIhxhwtZ5XJhgGqK%2Ff1AtjVWLOziAiPjUDOhqNCuyk7pkmmDzA0wWCbTxPM2lquyQ%2BJtSobpHJATMynbCRj2aLF0HNpfH1uCSYTuYlJBIIplbou%2FA3h1xpBFvhdMhvu7%2Bw%3D%3D&sign_type=RSA2&timestamp=2018-11-08+14%3A52%3A52&version=1.0";
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //构造PayTask
                PayTask alipay = new PayTask(MainActivity.this);
                //通过payV2方法发起支付请求，返回map类型（官方文档返回的是string，可能没及时更新，以这个为准）
                final Map<String, String> map = alipay.payV2(orderInfo, true);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //根据 resultStatus 字段获取支付状态 ，9000为支付成功
                        String resultStatus = map.get("resultStatus");
                        if("9000".equals(resultStatus)){
                            Toast.makeText(MainActivity.this, "支付宝支付成功", Toast.LENGTH_LONG).show();
                            Log.e("tag", "resultStatus == " + resultStatus);
                        }else{
                            Toast.makeText(MainActivity.this, "支付宝支付失败请重试", Toast.LENGTH_LONG).show();
                            Log.e("tag", "resultStatus == " + resultStatus);
                        }
                    }
                });

            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
