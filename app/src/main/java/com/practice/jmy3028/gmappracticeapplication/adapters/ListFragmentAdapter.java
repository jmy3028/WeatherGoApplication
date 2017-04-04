package com.practice.jmy3028.gmappracticeapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.jmy3028.gmappracticeapplication.DtModel;
import com.practice.jmy3028.gmappracticeapplication.ListModel;
import com.practice.jmy3028.gmappracticeapplication.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jmy3028 on 17. 3. 31.
 */

public class ListFragmentAdapter extends BaseExpandableListAdapter {

    private List<com.practice.jmy3028.gmappracticeapplication.model2.List> mParentList;

    public ListFragmentAdapter( List<com.practice.jmy3028.gmappracticeapplication.model2.List> mParentList) {
        this.mParentList = mParentList;
    }

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mParentList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;

        if(convertView == null){
            groupViewHolder = new GroupViewHolder();

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_group,parent,false);

            TextView dtTextView = (TextView) convertView.findViewById(R.id.dt_text);

            groupViewHolder.dtTextView = dtTextView;

            convertView.setTag(groupViewHolder);

        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        com.practice.jmy3028.gmappracticeapplication.model2.List dtModel = mParentList.get(groupPosition);

        groupViewHolder.dtTextView.setText(dtModel.getDtTxt());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;

        if(convertView == null){
            childViewHolder = new ChildViewHolder();

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_child,parent,false);

            ImageView mWeatherImage = (ImageView) convertView.findViewById(R.id.weather_image);
            TextView mWeatherText = (TextView) convertView.findViewById(R.id.weather_text);
            TextView mTempText = (TextView) convertView.findViewById(R.id.temp_text);
            TextView mWindSpeedText = (TextView) convertView.findViewById(R.id.windSpeed_text);
            TextView mWindDirText = (TextView) convertView.findViewById(R.id.windDir_text);
            TextView mPressureText = (TextView) convertView.findViewById(R.id.pressure_text);
            TextView mHumidityText = (TextView) convertView.findViewById(R.id.humidity_text);


            childViewHolder.mWeatherImage = mWeatherImage;
            childViewHolder.mWeatherText = mWeatherText;
            childViewHolder.mTempText = mTempText;
            childViewHolder.mWindSpeedText = mWindSpeedText;
            childViewHolder.mWindDirText = mWindDirText;
            childViewHolder.mPressureText = mPressureText;
            childViewHolder.mHumidityText = mHumidityText;


            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        com.practice.jmy3028.gmappracticeapplication.model2.List list = mParentList.get(groupPosition);

        //이미지 받아오기
//        childViewHolder.mWeatherImage.;

        //데이터값을 넣어서 화면에 뿌려주는 공간
        childViewHolder.mWeatherText.setText(list.getWeather().get(0).getMain());
        childViewHolder.mTempText.setText(String.valueOf(list.getMain().getTemp()));
        childViewHolder.mWindSpeedText.setText(String.valueOf(list.getWind().getSpeed()));
        childViewHolder.mWindDirText.setText(String.valueOf(list.getWind().getDeg()));
        childViewHolder.mPressureText.setText(String.valueOf(list.getMain().getPressure()));
        childViewHolder.mHumidityText.setText(String.valueOf(list.getMain().getHumidity()));


        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class GroupViewHolder{
        TextView dtTextView;
    }

    private static class ChildViewHolder{
        ImageView mWeatherImage;
        TextView mWeatherText;
        TextView mTempText;
        TextView mWindSpeedText;
        TextView mWindDirText;
        TextView mPressureText;
        TextView mHumidityText;
    }
}



