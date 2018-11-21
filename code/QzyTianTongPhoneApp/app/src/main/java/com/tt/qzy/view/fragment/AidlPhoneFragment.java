package com.tt.qzy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.socks.library.KLog;
import com.tt.qzy.view.MainActivity;
import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.CallRecordAdapter;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.layout.ClearEditText;
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
      ,CallRecordAdapter.OnItemClickListener,CallRecordView,PopWindow.OpenPictureListener{

    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.custom_input)
    ClearEditText mClearEditText;

    private MyInputPwdUtil myInputPwdUtil;
    private PopWindow mPopWindow;
    private CallRecordAdapter mCallRecordAdapter;
    private List<CallRecordDao> mModelList = new ArrayList<>();

    private AidlPhoneFragmentPersenter mPersenter;
    private KProgressHUD mHUD;

    private int offset = 0;
    private int daoListSize = 0;
    private int dateSize = 0;

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
        return view;
    }

    private void initAdapter(){
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
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                int count = daoListSize - offset;
                if(count > 0){
                    if(count < 20){
                        mPersenter.getLoadMore(0,offset+count);
                        refreshlayout.finishLoadmore(200);
                    }else{
                        mPersenter.getLoadMore(0,offset+20);
                        refreshlayout.finishLoadmore(200);
                    }
                }else if(count == 0){
                    NToast.shortToast(getActivity(),getString(R.string.TMT_dataisnull));
                    refreshlayout.finishLoadmore(200);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPersenter.getRefresh(0,offset);
                refreshlayout.finishRefresh(200);
            }
        });

        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                List<CallRecordDao> list = CallRecordManager.getInstance(getActivity()).fuzzySearch(s.toString());
                mCallRecordAdapter.setData(list);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                    mPopWindow.setOpenPictureListener(this);
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
    public void inputString(final String diapadNumber) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if(!mainActivity.isCallStatus()){
            mPersenter.dialPhone(diapadNumber);
        }else{
            NToast.shortToast(getActivity(),getString(R.string.TMT_be_occupied));
        }
    }

    @Override
    public void onClick(int position,final String diapadNumber) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if(!mainActivity.isCallStatus()){
            mPersenter.dialPhone(diapadNumber);
        }else{
            NToast.shortToast(getActivity(),getString(R.string.TMT_be_occupied));
        }
    }

    private void initProgress(){
        mHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel(getString(R.string.TMT_loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void onLongClick(int position) {
    }

    @Override
    public void onClickSeeDetails(int position) {
        mPersenter.startTargetActivity(getActivity(),mModelList.get(position).getPhoneNumber());
    }

    @Override
    public void getListSize(int listSize) {
        offset = listSize;
    }

    @Override
    public void getDaoListSize(int daoListSize) {
        this.daoListSize = daoListSize;
    }

    @Override
    public void getDateSize(int dateSize) {
        this.dateSize = dateSize;
    }

    @Override
    public void loadRefresh(List<CallRecordDao> list) {
        mModelList.clear();
        mModelList = list;
        mCallRecordAdapter.setData(mModelList);
    }

    @Override
    public void loadMore(List<CallRecordDao> list) {
        mModelList.clear();
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
        showProgress(true);
        mPersenter.getCallHistroy(0,20);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPersenter.release();
    }

    @Override
    public void deleteAllRecord() {
        mPersenter.deleteAllRecord();
        if(null != mCallRecordAdapter){
            mCallRecordAdapter.notifyDataSetChanged();
        }
    }
}
