package com.tt.qzy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.LoadMannagerAdapter;
import com.tt.qzy.view.adapter.OffLineMapAdapter;
import com.tt.qzy.view.bean.OffLineMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadManagerFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public LoadManagerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoadManagerFragment newInstance() {
        LoadManagerFragment fragment = new LoadManagerFragment();
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
        View view = inflater.inflate(R.layout.fragment_load_manager, container, false);
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
        LoadMannagerAdapter adapter = new LoadMannagerAdapter(mList);
        mRecyclerView.setAdapter(adapter);
    }
}
