package com.practice.jmy3028.gmappracticeapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 2017-03-22.
 */

public class RetrofitUtil {

    private Retrofit mRetrofit;

    private GetApi mGetApi;

    public RetrofitUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(GetApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetApi = mRetrofit.create(GetApi.class);
    }

    public GetApi getUserApi() {
        return mGetApi;
    }

}
