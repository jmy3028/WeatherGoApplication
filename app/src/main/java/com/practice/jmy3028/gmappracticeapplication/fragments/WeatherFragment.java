package com.practice.jmy3028.gmappracticeapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.jmy3028.gmappracticeapplication.R;
import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 말풍선을 눌렀을때 나오는 Fragment
 */

public class WeatherFragment extends Fragment {


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
        return inflater.inflate(R.layout.weather_fragment, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView sunrisetext = (TextView) view.findViewById(R.id.sunrise_text);

        Bundle bundle = getArguments();
        WeatherMain data = (WeatherMain) bundle.getSerializable("data");

        sunrisetext.setText(String.valueOf(data.getSys().getSunrise()));

    }
}
