package com.practice.jmy3028.gmappracticeapplication.api;


import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;
import com.practice.jmy3028.gmappracticeapplication.model2.Example;
import com.practice.jmy3028.gmappracticeapplication.model2.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jmy3028 on 17. 3. 22.
 */

public interface GetApi {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";



    @GET("weather")
    Call<WeatherMain> latlon(@Query("APPID") String APPID,
                             @Query("lat") double lat,
                             @Query("lon") double lon);

    @GET("forecast")
    Call<Example> latlon2(@Query("APPID") String APPID,
                          @Query("lat") double lat,
                          @Query("lon") double lon);

}
