package com.tt.qzy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.AllAreaAdapter;
import com.tt.qzy.view.adapter.OffLineMapAdapter;
import com.tt.qzy.view.bean.AllArea;
import com.tt.qzy.view.bean.OffLineMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.expanded_menu)
    ExpandableListView expListView;

    private List<AllArea> areaList;

    public CityListFragment() {
        // Required empty public constructor
    }

    public static CityListFragment newInstance() {
        CityListFragment fragment = new CityListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<OffLineMap> mList = new ArrayList<>();
        mList.add(new OffLineMap("全国基础包","地图102.0MB"));
        mList.add(new OffLineMap("深圳市","46.4MB"));
        mList.add(new OffLineMap("北京市","84.4MB"));
        mList.add(new OffLineMap("上海市","84.4MB"));
        mList.add(new OffLineMap("广州市","55.5MB"));
        OffLineMapAdapter adapter = new OffLineMapAdapter(mList);
        mRecyclerView.setAdapter(adapter);

        areaList = new ArrayList<>();
        List<OffLineMap> mList1 = new ArrayList<>();
        mList1.add(new OffLineMap("安庆市","地图10.7MB"));
        mList1.add(new OffLineMap("合肥市","地图9.4MB"));
        mList1.add(new OffLineMap("芜湖市","地图8.4MB"));
        mList1.add(new OffLineMap("铜陵市","地图7.4MB"));
        mList1.add(new OffLineMap("黄山市","地图5.5MB"));
        areaList.add(new AllArea("安徽省","地图131.0MB",mList1));
        List<OffLineMap> mList2 = new ArrayList<>();
        mList2.add(new OffLineMap("长沙市","地图10.7MB"));
        mList2.add(new OffLineMap("株洲市","地图9.4MB"));
        mList2.add(new OffLineMap("衡阳市","地图8.4MB"));
        mList2.add(new OffLineMap("郴州市","地图7.4MB"));
        mList2.add(new OffLineMap("永州市","地图5.5MB"));
        areaList.add(new AllArea("湖南省","地图126.0MB",mList2));
        AllAreaAdapter allareaAdapter = new AllAreaAdapter(areaList,getActivity());
        expListView.setAdapter(allareaAdapter);
    }
}
