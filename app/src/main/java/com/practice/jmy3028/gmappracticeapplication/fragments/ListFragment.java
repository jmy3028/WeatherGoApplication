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

import java.util.HashMap;

/**
 * Created by jmy3028 on 17. 3. 22.
 */

public class ListFragment extends Fragment {

    private java.util.List<DtModel> mDtData;
    private java.util.List<ListModel> mListData;
    private HashMap<DtModel, java.util.List<ListModel>> mGroupData;


    //날씨에 대한 모든 데이터들을 이쪽에서 받기
    public static ListFragment newInstance(Example data) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data2",data);
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

        Example example = (Example) getArguments().getSerializable("data2");



        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.expanded_list);
        ListFragmentAdapter adapter = new ListFragmentAdapter();

        listView.setAdapter(adapter);
    }
}
