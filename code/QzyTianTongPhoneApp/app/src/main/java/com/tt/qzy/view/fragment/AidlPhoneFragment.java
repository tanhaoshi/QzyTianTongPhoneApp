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

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.ContactsActivity;
import com.tt.qzy.view.adapter.CallRecordAdapter;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.layout.dialpad.InputPwdView;
import com.tt.qzy.view.layout.dialpad.MyInputPwdUtil;
import com.tt.qzy.view.presenter.fragment.AidlPhoneFragmentPersenter;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.CallRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidlPhoneFragment extends Fragment implements PopWindow.OnDismissListener,InputPwdView.InputPwdListener
      ,CallRecordAdapter.OnItemClickListener,CallRecordView{

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
    private List<CallRecordDao> mModelList = new ArrayList<>();

    private AidlPhoneFragmentPersenter mPersenter;
    private KProgressHUD mHUD;

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
        mPersenter = new AidlPhoneFragmentPersenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aidl_phone, container, false);
        ButterKnife.bind(this, view);
        mPersenter.onBindView(this);
        initView();
        initAdapter();
        loadData(true);
        return view;
    }

    private void initAdapter(){
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
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
        initProgress();
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
        //拨打电话
        mPersenter.dialPhone(diapadNumber);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), ContactsActivity.class);
        intent.putExtra("phoneNumber",mModelList.get(position).getPhoneNumber());
        startActivity(intent);
    }

    private void initProgress(){
        mHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("加载中...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void onLongClick(int position) {

    }

    @Override
    public void callRecordHistroy(List<CallRecordDao> list) {
        mModelList = list;
        mCallRecordAdapter.setData(mModelList);
    }

    @Override
    public void showProgress(boolean isTrue) {
        mHUD.show();
    }

    @Override
    public void hideProgress() {
        mHUD.dismiss();
    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {
        mHUD.dismiss();
        NToast.shortToast(getActivity(),msg);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        //showProgress(true);
        //mPersenter.requestCallRecord();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPersenter.release();
    }
}
