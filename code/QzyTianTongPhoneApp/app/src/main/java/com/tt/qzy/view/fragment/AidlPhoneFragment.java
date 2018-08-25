package com.tt.qzy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.adapter.CallRecordAdapter;
import com.tt.qzy.view.adapter.EarlierAdapter;
import com.tt.qzy.view.adapter.YesterdayRecordAdapter;
import com.tt.qzy.view.bean.CallRecordModel;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.layout.dialpad.InputPwdView;
import com.tt.qzy.view.layout.dialpad.MyInputPwdUtil;
import com.tt.qzy.view.utils.NToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidlPhoneFragment extends Fragment implements PopWindow.OnDismissListener,InputPwdView.InputPwdListener{

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
    @BindView(R.id.content)
    LinearLayout mLinearLayout;

    private MyInputPwdUtil myInputPwdUtil;

    private PopWindow mPopWindow;

    public AidlPhoneFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AidlPhoneFragment newInstance() {
        AidlPhoneFragment fragment = new AidlPhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_aidl_phone, container, false);
        ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initAdapter(){
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<CallRecordModel> list = new ArrayList<>();
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃9秒"));
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        todayRecyclerView.setAdapter(new CallRecordAdapter(list));

        yesterdayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<CallRecordModel> list1 = new ArrayList<>();
        list1.add(new CallRecordModel("181-2644-0000","广东深圳","响铃9秒"));
        list1.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        list1.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        list1.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        yesterdayRecyclerView.setAdapter(new YesterdayRecordAdapter(list1));

        earlierRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<CallRecordModel> list2 = new ArrayList<>();
        list2.add(new CallRecordModel("181-2644-0000","广东深圳","响铃9秒"));
        list2.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        list2.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        list2.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒"));
        earlierRecyclerView.setAdapter(new EarlierAdapter(list2));
    }

    private void initView() {
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_phone_histroy));
        base_tv_toolbar_right.setImageDrawable(getActivity().getDrawable(R.drawable.more));
        myInputPwdUtil = new MyInputPwdUtil(getActivity());
        myInputPwdUtil.getMyInputDialogBuilder().setAnimStyle(R.style.dialog_anim);
        myInputPwdUtil.setListener(this);
    }

    @OnClick({R.id.fab,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab:
                myInputPwdUtil.show();
                break;
            case R.id.base_tv_toolbar_right:
                if(mPopWindow == null){
                    mPopWindow = new PopWindow(getActivity());
                    mPopWindow.setOnDismissListener(this);
                    setWindowAttibus(0.5f);
                    mPopWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    mPopWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
        }
    }

    @Override
    public void onDismiss() {
        setWindowAttibus(1f);
    }

    private void setWindowAttibus(float color){
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = color;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void inputString(String diapadNumber) {
        Intent intent = new Intent(getActivity(), TellPhoneActivity.class);
        intent.putExtra("diapadNumber",diapadNumber);
        getActivity().startActivity(intent);
    }
}
