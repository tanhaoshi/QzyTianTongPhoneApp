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
import com.tt.qzy.view.activity.ContactsActivity;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.adapter.CallRecordAdapter;
import com.tt.qzy.view.bean.CallRecordModel;
import com.tt.qzy.view.bean.ShortMessageModel;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.layout.dialpad.InputPwdView;
import com.tt.qzy.view.layout.dialpad.MyInputPwdUtil;
import com.tt.qzy.view.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidlPhoneFragment extends Fragment implements PopWindow.OnDismissListener,InputPwdView.InputPwdListener
      ,CallRecordAdapter.OnItemClickListener{

    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MyInputPwdUtil myInputPwdUtil;
    private PopWindow mPopWindow;
    private CallRecordAdapter mCallRecordAdapter;
    private List<CallRecordModel> mModelList;

    // "昨天" 标题进行添加一次的控制
    private boolean isYesterday = true;

    // "更早" 标题进行添加一次的控制
    private boolean isEarlier = true;

    public AidlPhoneFragment() {
    }

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
        View view = inflater.inflate(R.layout.fragment_aidl_phone, container, false);
        ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initAdapter(){
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<CallRecordModel> list = new ArrayList<>();
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃9秒","2018-8-27 14:33:24",0,""));
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒","2018-8-24 14:33:24",0,""));
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒","2018-8-29 14:33:24",0,""));
        list.add(new CallRecordModel("181-2644-0000","广东深圳","响铃8秒","2018-8-30 14:33:24",0,""));
        mModelList = new ArrayList<>();
        //判断通讯记录列表中 是否有数据 有数据下面的操作才有含义，没有数据的话就不用处理。
        if(list.size() >= 1){
            sortData(list);
            mModelList.add(new CallRecordModel("","","","",1,"今天"));
            for(CallRecordModel recordModel : list){
                if(DateUtil.isToday(recordModel.getDate())){
                    mModelList.add(recordModel);
                }else if(DateUtil.isYesterday(recordModel.getDate())){
                    if(isYesterday){
                        mModelList.add(new CallRecordModel("","","","",1,"昨天"));
                        isYesterday = false;
                    }
                    mModelList.add(recordModel);
                }else{
                    if(isYesterday){
                        mModelList.add(new CallRecordModel("","","","",1,"昨天"));
                        isYesterday = false;
                    }
                    if(isEarlier){
                        mModelList.add(new CallRecordModel("","","","",1,"更早"));
                        isEarlier = false;
                    }
                    mModelList.add(recordModel);
                }
            }
        }
        mCallRecordAdapter = new CallRecordAdapter(mModelList,getActivity());
        mCallRecordAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCallRecordAdapter);
    }

    private void initView() {
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_phone_histroy));
        base_tv_toolbar_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.more));
        myInputPwdUtil = new MyInputPwdUtil(getActivity());
        myInputPwdUtil.getMyInputDialogBuilder().setAnimStyle(R.style.dialog_anim);
        myInputPwdUtil.setListener(this);
    }

    private List<CallRecordModel> sortData(List<CallRecordModel> mList) {
        Collections.sort(mList, new Comparator<CallRecordModel>() {
            @Override
            public int compare(CallRecordModel lhs, CallRecordModel rhs) {
                Date date1 = DateUtil.stringToDate(lhs.getDate());
                Date date2 = DateUtil.stringToDate(rhs.getDate());
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
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
                    mPopWindow.showAtLocation(getActivity().findViewById(R.id.recyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    mPopWindow.showAtLocation(getActivity().findViewById(R.id.recyclerView),
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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), ContactsActivity.class);
        intent.putExtra("phoneNumber",mModelList.get(position).getPhoneNumber());
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {

    }
}
