package com.tt.qzy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.SendShortMessageActivity;
import com.tt.qzy.view.adapter.CallRecordAdapter;
import com.tt.qzy.view.adapter.EarlierAdapter;
import com.tt.qzy.view.adapter.EarlierMessageAdapter;
import com.tt.qzy.view.adapter.ShortMessageAdapter;
import com.tt.qzy.view.adapter.YesterDayMessageAdapter;
import com.tt.qzy.view.adapter.YesterdayRecordAdapter;
import com.tt.qzy.view.bean.CallRecordModel;
import com.tt.qzy.view.bean.ShortMessageModel;
import com.tt.qzy.view.layout.PopShortMessageWindow;
import com.tt.qzy.view.layout.PopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShortMessageFragment extends Fragment implements PopWindow.OnDismissListener{

    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.todayRecyclerView)
    RecyclerView todayRecyclerView;
    @BindView(R.id.yesterdayRecyclerView)
    RecyclerView yesterdayRecyclerView;
    @BindView(R.id.earlierRecyclerView)
    RecyclerView earlierRecyclerView;

    private PopShortMessageWindow mPopShortMessageWindow;

    public ShortMessageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ShortMessageFragment newInstance() {
        ShortMessageFragment fragment = new ShortMessageFragment();
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
        View view = inflater.inflate(R.layout.fragment_short_message, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_phone_histroy));
        base_tv_toolbar_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.more));

        todayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<ShortMessageModel> list = new ArrayList<>();
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        todayRecyclerView.setAdapter(new ShortMessageAdapter(list));

        yesterdayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<ShortMessageModel> list1 = new ArrayList<>();
        list1.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        list1.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        list1.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        list1.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"今天 14:31"));
        yesterdayRecyclerView.setAdapter(new YesterDayMessageAdapter(list1));

        earlierRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<ShortMessageModel> list2 = new ArrayList<>();
        list2.add(new ShortMessageModel("181-2644-0000","广东深圳","响铃9秒"));
        list2.add(new ShortMessageModel("181-2644-0000","广东深圳","响铃8秒"));
        list2.add(new ShortMessageModel("181-2644-0000","广东深圳","响铃8秒"));
        list2.add(new ShortMessageModel("181-2644-0000","广东深圳","响铃8秒"));
        earlierRecyclerView.setAdapter(new EarlierMessageAdapter(list2));
    }

    @OnClick({R.id.base_tv_toolbar_right,R.id.fab})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_tv_toolbar_right:
                if(mPopShortMessageWindow == null){
                    mPopShortMessageWindow = new PopShortMessageWindow(getActivity());
                    mPopShortMessageWindow.setOnDismissListener(this);
                    setWindowAttibus(0.5f);
                    mPopShortMessageWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    mPopShortMessageWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.fab:
                Intent intent = new Intent(getActivity(), SendShortMessageActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDismiss() {
        setWindowAttibus(1f);
    }

    private void setWindowAttibus(float color) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = color;
        getActivity().getWindow().setAttributes(lp);
    }
}
