package com.colortu.colortu_module.colortu_base.core.http;

import androidx.annotation.Nullable;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @name : NetworkManager
 * @Parameters :
 * @describe :okhttp+retrofit+rxjava网络请求
 */
public class NetworkManager {
    public static String Username = "watch";
    public static String Password = "3dfgk45pdfgrlkdlfvbmla==";

    private static NetworkManager instance;
    private Retrofit retrofit;

    public static NetworkManager instance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager() {
        initNetwork();
    }

    private void initNetwork() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //打印请求参数，请求结果
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        String credential = Credentials.basic(Username, Password);
                        return response.request().newBuilder().header("Authorization", credential).build();
                    }
                })
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(BaseConstant.HomeWorkBaseUrl)
                //切换BaseUrl
                .callFactory(new CallFactoryProxy(okHttpClient) {
                    @Nullable
                    @Override
                    protected HttpUrl getNewUrl(String baseUrlName, Request request) {
                        if (baseUrlName.equals("listen")) {
                            String oldUrl = request.url().toString();
                            String newUrl = oldUrl.replace(BaseConstant.HomeWorkBaseUrl, BaseConstant.ListenBaseUrl);
                            return HttpUrl.get(newUrl);
                        }
                        return null;
                    }
                })
                //使用Rxjava对回调数据进行处理
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //响应结果的解析器，包含gson，xml，protobuf
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
