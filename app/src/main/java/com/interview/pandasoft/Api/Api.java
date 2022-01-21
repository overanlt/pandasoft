package com.interview.pandasoft.Api;

import android.content.Context;

import com.google.gson.Gson;
import com.interview.pandasoft.Model.ApiResponeModel;
import com.interview.pandasoft.Model.DataApiResponse;
import com.interview.pandasoft.Model.LikeModel;
import com.interview.pandasoft.Model.LoginModel;
import com.interview.pandasoft.Model.RefreshTokenModel;
import com.interview.pandasoft.Preference.TokenPreference;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {

    public interface onApiResponse{
        void _onApiResponse(ApiResponeModel response);
    }

    public void LoginApi(Context context, LoginModel model, onApiResponse _response){
        Call<ResponseBody> call = new RestManager().getEndpoint().login(model);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        ApiResponeModel apiResponeModel = new Gson().fromJson(response.body().string(),ApiResponeModel.class);
                        TokenPreference.SaveAccessToken(apiResponeModel.getAccess_token(),context);
                        _response._onApiResponse(apiResponeModel);
                    } catch (Exception e) {
                        _response._onApiResponse(new ApiResponeModel(0,0,"exception on api unsuccess",null,null));
                    }
                }else {
                    _response._onApiResponse(new ApiResponeModel(response.code(),0,"unsuccess",null,null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _response._onApiResponse(new ApiResponeModel(0,0,"unsuccess",null,null));
            }
        });
    }

    public void GetNewsApi(Context context,onApiResponse _response){
        String access_token = TokenPreference.RetrievedAccessToken(context);
        Call<ResponseBody> call = new RestManager().getEndpoint().getNews(access_token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        ApiResponeModel apiResponeModel = new Gson().fromJson(response.body().string(),ApiResponeModel.class);
                        _response._onApiResponse(apiResponeModel);
                    } catch (Exception e) {
                        _response._onApiResponse(new ApiResponeModel(0,0,"exception on api unsuccess",null,null));
                    }
                } else if(response.code() == 401){
                    new Api().RefreshAccessToken(new RefreshTokenModel(access_token), new onApiResponse() {
                        @Override
                        public void _onApiResponse(ApiResponeModel response) {
                            if(response.getStatus() == 200 && response.getAccess_token()!=null){
                                TokenPreference.SaveAccessToken(response.getAccess_token(),context);
                                new Api().GetNewsApi(context, new onApiResponse() {
                                    @Override
                                    public void _onApiResponse(ApiResponeModel response) {
                                        if(response.getStatus()== 200){
                                            _response._onApiResponse(response);
                                        }else {
                                            _response._onApiResponse(new ApiResponeModel(response.getStatus(),0,"unsuccess",null,null));
                                        }
                                    }
                                });
                            }else {
                                _response._onApiResponse(new ApiResponeModel(response.getStatus(),0,"unsuccess",null,null));
                            }
                        }
                    });
                } else {
                    _response._onApiResponse(new ApiResponeModel(response.code(),0,"unsuccess",null,null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _response._onApiResponse(new ApiResponeModel(0,0,"unsuccess",null,null));
            }
        });
    }

    private void RefreshAccessToken(RefreshTokenModel model,onApiResponse _response){
        Call<ResponseBody> call = new RestManager().getEndpoint().refreshToken(model);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        ApiResponeModel apiResponeModel = new Gson().fromJson(response.body().string(),ApiResponeModel.class);
                        _response._onApiResponse(apiResponeModel);
                    } catch (Exception e) {
                        _response._onApiResponse(new ApiResponeModel(0,0,"exception on api unsuccess",null,null));
                    }
                }else {
                    _response._onApiResponse(new ApiResponeModel(response.code(),0,"unsuccess",null,null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _response._onApiResponse(new ApiResponeModel(0,0,"unsuccess",null,null));
            }
        });
    }

    public void LikeApi(LikeModel model,onApiResponse _response){
        Call<ResponseBody> call = new RestManager().getEndpoint().like(model);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        ApiResponeModel apiResponeModel = new Gson().fromJson(response.body().string(),ApiResponeModel.class);
                        _response._onApiResponse(apiResponeModel);
                    } catch (Exception e) {
                        _response._onApiResponse(new ApiResponeModel(0,0,"exception on api unsuccess",null,null));
                    }
                }else {
                    _response._onApiResponse(new ApiResponeModel(response.code(),0,"unsuccess",null,null));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                _response._onApiResponse(new ApiResponeModel(0,0,"unsuccess",null,null));

            }
        });
    }
}
