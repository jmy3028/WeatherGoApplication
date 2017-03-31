package com.practice.jmy3028.gmappracticeapplication.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.jmy3028.gmappracticeapplication.R;
import com.practice.jmy3028.gmappracticeapplication.databinding.WeatherFragmentBinding;
import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 말풍선을 눌렀을때 나오는 Fragment
 */

public class WeatherFragment extends Fragment {


    private WeatherFragmentBinding mBinding;

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



        WeatherMain mData = (WeatherMain) getArguments().getSerializable("data");

        mBinding.sunriseText.setText(String.valueOf(mData.getSys().getSunrise()));
        mBinding.setText.setText(String.valueOf(mData.getSys().getSunset()));
        mBinding.windSpeedText.setText(String.valueOf(mData.getWind().getSpeed()));
        mBinding.windDirText.setText(String.valueOf(mData.getWind().getDeg()));
        mBinding.weatherText.setText(String.valueOf(mData.getWeather().get(0).getMain()));
        mBinding.tempText.setText(String.valueOf(mData.getMain().getTemp()));
        mBinding.pressureText.setText(String.valueOf(mData.getMain().getPressure()));
        mBinding.humidityText.setText(String.valueOf(mData.getMain().getHumidity()));
        mBinding.visibilityText.setText(String.valueOf(mData.getVisibility()));
    }
}
