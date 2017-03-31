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

    private Context context;
    private List<DtModel> mParentList;
    private List<ListModel> mChildList;
    private HashMap<DtModel,List<ListModel>> mChildHashMap;

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(childPosition);
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

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_group,parent,false);

            TextView dtTextView = (TextView) convertView.findViewById(R.id.dt_text);

            groupViewHolder.dtTextView = dtTextView;

            convertView.setTag(groupViewHolder);

        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        DtModel dtModel = mParentList.get(groupPosition);

        groupViewHolder.dtTextView.setText(dtModel.getmDtdata());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;

        if(convertView == null){
            childViewHolder = new ChildViewHolder();

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_child,parent,false);

            TextView dtTextView = (TextView) convertView.findViewById(R.id.dt_text);

            groupViewHolder.dtTextView = dtTextView;

            convertView.setTag(groupViewHolder);

        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        DtModel dtModel = mParentList.get(groupPosition);

        groupViewHolder.dtTextView.setText(dtModel.getmDtdata());


        return convertView;

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



