package com.bmbm.latte_core.net;

import com.bmbm.latte_core.app.ConfigKeys;
import com.bmbm.latte_core.app.Latte;
import com.bmbm.latte_core.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 封装Retrofit对象的创建过程
 * Created by wxm on 2017/9/19.
 */

public final class RestCreator {
    // wxm:构建okhttp
    private static final class OkhttpHolder {
        // wxm:连接超时时间
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfig(ConfigKeys.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptors() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    // wxm:构建全局Retrofit
    private static final class RetrofitHolder {
        private static final String BASE_URL = Latte.getConfig(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    // wxm:Service接口
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }


}
