package com.practice.jmy3028.gmappracticeapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.jmy3028.gmappracticeapplication.fragments.ListFragment;
import com.practice.jmy3028.gmappracticeapplication.fragments.WeatherFragment;
import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;
import com.practice.jmy3028.gmappracticeapplication.model2.Example;

public class DetailFragment extends Fragment {

    private ViewPager viewPager;
    private WeatherFragment weatherFragment;
    private ListFragment listFragment;

    private WeatherMain mResult;
    private Example mResult2;


    public DetailFragment() {
    }

    public static DetailFragment createDetailFragment(WeatherMain result, Example result2) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", result);
        bundle.putSerializable("result2", result2);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weatherFragment = new WeatherFragment();
        listFragment = new ListFragment();

        viewPager = (ViewPager) view.findViewById(R.id.frgment_view_pager);

        //메인엑티비티에서 data값을 전달 받음.
        if (getArguments() != null) {
            mResult = (WeatherMain) getArguments().getSerializable("result");
            mResult2 = (Example) getArguments().getSerializable("result2");

            weatherFragment = WeatherFragment.newInstance(mResult);
            listFragment = ListFragment.newInstance(mResult2);
            DetailFragment.FragmentsPagerAdapter pagerAdapter = new DetailFragment.FragmentsPagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(pagerAdapter);
        }
    }


    //PagerView를 활용해서 두개의 fragment 를 분할 해서 표현함.
    private class FragmentsPagerAdapter extends FragmentPagerAdapter {

        public FragmentsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return weatherFragment;
                case 1:
                    return listFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
