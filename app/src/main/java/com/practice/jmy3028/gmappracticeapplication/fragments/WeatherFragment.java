package com.practice.jmy3028.gmappracticeapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.jmy3028.gmappracticeapplication.R;
import com.practice.jmy3028.gmappracticeapplication.api.GetApi;
import com.practice.jmy3028.gmappracticeapplication.api.RetrofitUtil;

/**
 * 말풍선을 눌렀을때 나오는 Fragment
 */

public class WeatherFragment extends Fragment {

    private GetApi mGetApi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGetApi = new RetrofitUtil().getUserApi();

    }
}
