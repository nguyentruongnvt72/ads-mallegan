package com.mallegan.ads.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("getid/{param}")
    Call<List<AdsModel>> 
    callAds(@Path("param") String parameterValue);
}
