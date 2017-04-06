package com.practice.jmy3028.gmappracticeapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
    private com.practice.jmy3028.gmappracticeapplication.model2.List mList;
    private Bitmap bitmap;
    private Context mContext;


    public ListFragmentAdapter(Context context, List<com.practice.jmy3028.gmappracticeapplication.model2.List> mParentList) {
        this.mParentList = mParentList;
        this.mContext = context;
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
            ImageView mWindImage = (ImageView) convertView.findViewById(R.id.wind_image);
            TextView mPressureText = (TextView) convertView.findViewById(R.id.pressure_text);
            TextView mHumidityText = (TextView) convertView.findViewById(R.id.humidity_text);


            childViewHolder.mWeatherImage = mWeatherImage;
            childViewHolder.mWeatherText = mWeatherText;
            childViewHolder.mTempText = mTempText;
            childViewHolder.mWindSpeedText = mWindSpeedText;
            childViewHolder.mWindImage = mWindImage;
            childViewHolder.mPressureText = mPressureText;
            childViewHolder.mHumidityText = mHumidityText;


            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        mList = mParentList.get(groupPosition);

        String weather = getWeather(mList.getWeather().get(0).getIcon());

        //이미지 받아오기
//        childViewHolder.mWeatherImage.;

        //데이터값을 넣어서 화면에 뿌려주는 공간
        childViewHolder.mWeatherText.setText(String.valueOf(weather));
        childViewHolder.mTempText.setText(String.format("%.2f ℃",mList.getMain().getTemp() - 273.15));
        childViewHolder.mWindSpeedText.setText(String.format("%s m/s", mList.getWind().getSpeed()));
        Matrix rotateMatrix = new Matrix();

        //이미지 생성
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_main);
        rotateMatrix.postRotate((float) mList.getWind().getDeg()); // -360~360 회전
        //이미지 회전시킨 것 적용 시켜서 뿌려주기
        Bitmap sideInversionImg = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, false);
        childViewHolder.mWindImage.setImageBitmap(sideInversionImg);

        childViewHolder.mPressureText.setText(String.valueOf(mList.getMain().getPressure()));
        childViewHolder.mHumidityText.setText(String.valueOf(mList.getMain().getHumidity() + " %"));


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
        ImageView mWindImage;
        TextView mPressureText;
        TextView mHumidityText;
    }

    public String getWeather(String data){

        if(data.equals("01d") || data.equals("01n")) {
            mList.getWeather().get(0).setMain("맑은 날씨");
        }else if(data.equals("02d") || data.equals("02n")) {
            mList.getWeather().get(0).setMain("구름 조금");
        }else if(data.equals("03d") || data.equals("03n")) {
            mList.getWeather().get(0).setMain("많은 구름");
        }else if(data.equals("04d") || data.equals("04n")) {
            mList.getWeather().get(0).setMain("먹구름");
        }else if(data.equals("09d") || data.equals("09n")) {
            mList.getWeather().get(0).setMain("소량의 비");
        }else if(data.equals("10d") || data.equals("10n")) {
            mList.getWeather().get(0).setMain("비");
        }else if(data.equals("11d") || data.equals("11n")) {
            mList.getWeather().get(0).setMain("뇌우");
        }else if(data.equals("13d") || data.equals("13n")) {
            mList.getWeather().get(0).setMain("눈");
        }else {
            mList.getWeather().get(0).setMain("안개");
        }
        return mList.getWeather().get(0).getMain();
    }
}



