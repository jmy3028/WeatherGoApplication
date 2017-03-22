package com.practice.jmy3028.gmappracticeapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.practice.jmy3028.gmappracticeapplication.fragments.ListFragment;
import com.practice.jmy3028.gmappracticeapplication.fragments.WeatherFragment;

public class FragmentsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WeatherFragment weatherFragment;
    private ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);


        viewPager = (ViewPager) findViewById(R.id.frgment_view_pager);

        weatherFragment = new WeatherFragment();
        listFragment = new ListFragment();


        FragmentsPagerAdapter pagerAdapter = new FragmentsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
    }

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
