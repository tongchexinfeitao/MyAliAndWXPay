package com.bw.movie.utils;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.connection.ConnectInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mumu on 2018/11/2.
 */

public class RetrofitManager {
    private static final String BASE_URL = "http://172.17.8.100/movieApi/";
    private Retrofit mRetrofit;

    private static final class SINGLE_INSTANCE {
        private static final RetrofitManager _INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SINGLE_INSTANCE._INSTANCE;
    }

    private RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                //设置公共的url部分
                .baseUrl(BASE_URL)
                //配置解析方式为Gson
                .addConverterFactory(GsonConverterFactory.create())
                //retrofit支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置okhttpClient
                .client(buildOkhttpClient())
                .build();
    }


    private OkHttpClient buildOkhttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(new CommonInterceptor(null))
                .build();
    }


    public Retrofit getmRetrofit() {
        return mRetrofit;
    }

    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }


    public class CommonInterceptor implements Interceptor {

        //公共请求参数
        private HashMap<String, String> publicParam = new HashMap<>();

        public CommonInterceptor(HashMap<String, String> publicParam) {
            this.publicParam = publicParam;
        }

        //设置公共请求参数
        public void setPublicParam(HashMap hashMap) {
            publicParam = hashMap;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String url = request.url().toString();

            if (request.method().equalsIgnoreCase("GET")) {
                //只有需要拼接公共请求参数的时候才去操作
                if (publicParam != null && publicParam.size() > 0) {
                    for (HashMap.Entry<String, String> entry : publicParam.entrySet()) {
                        //不会重复拼接相同的key value
                        if (!url.contains(entry.getKey())) {
                            url += "&" + entry.getKey() + "=" + entry.getValue();
                        }
                    }
                    //考虑到了之前的url是否有无参数
                    if (!url.contains("?")) {
                        url = url.replaceFirst("&", "?");
                    }
                    Log.e("tag", "url = = " + url);
                    Request newRequest = request.newBuilder().url(url).build();
                    return chain.proceed(newRequest);
                }
            } else {
                if (publicParam != null && publicParam.size() > 0) {
                    //只有是表单形式才去处理
                    if (request.body() != null && request.body() instanceof FormBody) {
                        RequestBody body = request.body();
                        FormBody.Builder builder = new FormBody.Builder();
                        FormBody formBody = (FormBody) body;
                        //拼接原有的key和value
                        for (int i = 0; i < formBody.size(); i++) {
                            builder.add(formBody.encodedName(i), formBody.encodedValue(i));
                        }
                        //拼接公共的Key和value
                        for (HashMap.Entry<String, String> entry : publicParam.entrySet()) {
                            builder.add(entry.getKey(), entry.getValue());
                        }
                        FormBody newFormBody = builder.build();
                        Log.e("tag", "url = = " + url);
                        Request newRequest = request.newBuilder().post(newFormBody).build();
                        Response proceed = chain.proceed(newRequest);
                        return proceed;
                    }
                }
            }
            Request newRequest = request.newBuilder()
                    .addHeader("ak", "0110010010000").build();
            Log.e("tag", "url = = " + url);
            return chain.proceed(newRequest);
        }
    }
}
