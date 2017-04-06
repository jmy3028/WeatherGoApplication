package com.practice.jmy3028.gmappracticeapplication.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.jmy3028.gmappracticeapplication.R;
import com.practice.jmy3028.gmappracticeapplication.databinding.WeatherFragmentBinding;
import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 말풍선을 눌렀을때 나오는 Fragment
 */

public class WeatherFragment extends Fragment {


    private WeatherFragmentBinding mBinding;
    private WeatherMain mData;
    private Bitmap bitmap;

    //날씨에 대한 모든 데이터들을 이쪽에서 받기
    public static WeatherFragment newInstance(WeatherMain  data) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.weather_fragment,
                container,false);

        return mBinding.getRoot();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = (ImageView) view.findViewById(R.id.wind_dir_image);


        //fragment 엑티비티에서 데이터 값을 가져옴.
        mData = (WeatherMain) getArguments().getSerializable("data");

        //정보 변환 작업
        SimpleDateFormat formatter = new SimpleDateFormat ( "HH:mm", Locale.KOREA );
        String weather = getWeather(mData.getWeather().get(0).getIcon());
        Matrix rotateMatrix = new Matrix();

        //이미지 생성
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_main);


        mBinding.sunriseText.setText(formatter.format(mData.getSys().getSunrise() * 1000L));
        mBinding.setText.setText(formatter.format(mData.getSys().getSunset() * 1000L));
        mBinding.windSpeedText.setText(String.format("%s m/s", mData.getWind().getSpeed()));
        rotateMatrix.postRotate(mData.getWind().getDeg()); // -360~360 회전
        //이미지 회전시킨 것 적용 시켜서 뿌려주기
        Bitmap sideInversionImg = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, false);
        imageView.setImageBitmap(sideInversionImg);

        mBinding.weatherText.setText(String.valueOf(weather));
        mBinding.tempText.setText(String.format("%.2f ℃",mData.getMain().getTemp() - 273.15));
        mBinding.pressureText.setText(String.valueOf(mData.getMain().getPressure()));
        mBinding.humidityText.setText(String.valueOf(mData.getMain().getHumidity() + " %"));
        mBinding.visibilityText.setText(String.format("%s m",mData.getVisibility()));


    }

    public String getWeather(String data){

        if(data.equals("01d") || data.equals("01n")) {
            mData.getWeather().get(0).setMain("맑은 날씨");
        }else if(data.equals("02d") || data.equals("02n")) {
            mData.getWeather().get(0).setMain("구름 조금");
        }else if(data.equals("03d") || data.equals("03n")) {
            mData.getWeather().get(0).setMain("많은 구름");
        }else if(data.equals("04d") || data.equals("04n")) {
            mData.getWeather().get(0).setMain("먹구름");
        }else if(data.equals("09d") || data.equals("09n")) {
            mData.getWeather().get(0).setMain("소량의 비");
        }else if(data.equals("10d") || data.equals("10n")) {
            mData.getWeather().get(0).setMain("비");
        }else if(data.equals("11d") || data.equals("11n")) {
            mData.getWeather().get(0).setMain("뇌우");
        }else if(data.equals("13d") || data.equals("13n")) {
            mData.getWeather().get(0).setMain("눈");
        }else {
            mData.getWeather().get(0).setMain("안개");
        }
        return mData.getWeather().get(0).getMain();
    }
}
