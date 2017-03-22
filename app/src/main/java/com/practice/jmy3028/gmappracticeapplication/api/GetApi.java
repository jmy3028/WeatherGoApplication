package com.practice.jmy3028.gmappracticeapplication.api;


import com.practice.jmy3028.gmappracticeapplication.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jmy3028 on 17. 3. 22.
 */

public interface GetApi {

    String BASE_URL = "api.openweathermap.org/data/2.5/";

    String BASE_APPID = "1de08f4347f09c9540f906a810f95b03";

    @GET("weather")
    Call<WeatherModel> latlon(@Query("APPID") String BASE_APPID,
                              @Query("lat") double lat,
                              @Query("lon") double lon);


}
