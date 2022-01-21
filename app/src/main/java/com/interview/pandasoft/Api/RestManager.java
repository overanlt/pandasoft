package com.interview.pandasoft.Api;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RestManager {

    private ApiService apiService;
    public ApiService getEndpoint(){
        if(apiService == null){
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://5c065a3fc16e1200139479cc.mockapi.io/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//    logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

}