package com.interview.pandasoft.Api;

import com.interview.pandasoft.Model.LikeModel;
import com.interview.pandasoft.Model.LoginModel;
import com.interview.pandasoft.Model.RefreshTokenModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("news")
    Call<ResponseBody> getNews(@Header("Authorization")String token);

    @POST("login")
    Call<ResponseBody> login(@Body LoginModel loginModel);

    @POST("like")
    Call<ResponseBody> like(@Body LikeModel likeModel);

    @POST("refresh")
    Call<ResponseBody> refreshToken(@Body RefreshTokenModel refreshTokenModel);

}
