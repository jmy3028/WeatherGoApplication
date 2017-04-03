package com.practice.jmy3028.gmappracticeapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.practice.jmy3028.gmappracticeapplication.DtModel;
import com.practice.jmy3028.gmappracticeapplication.ListModel;
import com.practice.jmy3028.gmappracticeapplication.R;
import com.practice.jmy3028.gmappracticeapplication.adapters.ListFragmentAdapter;
import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;
import com.practice.jmy3028.gmappracticeapplication.model2.Example;
import com.practice.jmy3028.gmappracticeapplication.model2.List;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jmy3028 on 17. 3. 22.
 */

public class ListFragment extends Fragment {

    private java.util.List<String> mDtData;
    private java.util.List<ListModel> mListData;
    private HashMap<String, java.util.List<ListModel>> mGroupData;
    private String mResult;
    private ListFragmentAdapter mAdapter;
    private ExpandableListView mListView;


    //날씨에 대한 모든 데이터들을 이쪽에서 받기
    public static ListFragment newInstance(Example data) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = getArguments();
        final Example example = (Example) bundle.getSerializable("data");
        mDtData = new ArrayList<>();
        for (int i = 0; i < example.getList().size(); i++) {
            mResult = example.getList().get(i).getDtTxt();
            mDtData.add(mResult);

            mListData = new ArrayList<>();
            mListData.add(new ListModel(example.getList().get(i).getWeather().get(0).getMain(),
                    example.getList().get(i).getMain().getTemp(),
                    example.getList().get(i).getWind().getSpeed(),
                    example.getList().get(i).getWind().getDeg(),
                    example.getList().get(i).getMain().getPressure(),
                    example.getList().get(i).getMain().getHumidity()));
        }




        mGroupData = new HashMap<>();
        mGroupData.put(mResult,mListData);


        mListView = (ExpandableListView) view.findViewById(R.id.expanded_list);
        mAdapter = new ListFragmentAdapter(mDtData,mGroupData);

        mListView.setAdapter(mAdapter);
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //그룹이 열릴때

                int groupCount = mAdapter.getGroupCount();


                // 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
                for (int i = 0; i < groupCount; i++) {
                    if (!(i == groupPosition))
                        mListView.collapseGroup(i);
                }


            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            //그룹이 닫힐때
            }
        });

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //차일드가 선택 되었을 때
                return false;
            }
        });
    }
}
