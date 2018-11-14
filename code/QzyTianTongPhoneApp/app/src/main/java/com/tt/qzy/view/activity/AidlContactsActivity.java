package com.tt.qzy.view.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.adapter.AidlContactsAdapter;
import com.tt.qzy.view.adapter.ContactsAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.layout.PopContactsWindow;
import com.tt.qzy.view.presenter.activity.AidlContactsPresenter;
import com.tt.qzy.view.view.AidlContactsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AidlContactsActivity extends BaseActivity<AidlContactsView> implements AidlContactsView{

    private AidlContactsPresenter mPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.loding_more)
    TextView loding_more;
    @BindView(R.id.take_up)
    TextView take_up;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.record)
    LinearLayout mLinearLayout;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.send)
    TextView send;

    private List<CallRecordDao> mModelList = new ArrayList<>();
    private List<CallRecordDao> openList = new ArrayList<>();
    private AidlContactsAdapter contactsAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_aidl_contacts;
    }

    @Override
    public void initView() {
        mPresenter = new AidlContactsPresenter(this);
        mPresenter.onBindView(this);
        getIntentDataValue();
        base_tv_toolbar_right.setVisibility(View.GONE);
        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_contacts));
        base_iv_back.setImageDrawable(getResources().getDrawable(R.drawable.iv_back));
    }

    private void getIntentDataValue(){
        Intent intent = getIntent();
        if(null != intent.getExtras()){
            String phone = intent.getStringExtra("phone");
            mModelList = mPresenter.getKeyOnPhoneList(phone);
            userName.setText(mModelList.get(0).getName());
            mLinearLayout.setVisibility(View.GONE);
            email.setText("");
            qq.setText("");
            send.setText("");
        }
    }

    @Override
    public void initData() {
        initAdapter();
    }

    private void initAdapter(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        contactsAdapter = new AidlContactsAdapter(this);
        mRecyclerView.setAdapter(contactsAdapter);
        setAdapter(mModelList);
    }

    private void setAdapter(List<CallRecordDao> list){
        if(list.size() > 3){
            loding_more.setVisibility(View.VISIBLE);
            openList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                openList.add(list.get(i));
            }
            contactsAdapter.setMsgs(openList);
        }else {
            loding_more.setVisibility(View.GONE);
            contactsAdapter.setMsgs(mModelList);
        }
    }

    @OnClick({R.id.loding_more,R.id.take_up,R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.loding_more:
                contactsAdapter.setMsgs(mModelList);
                loding_more.setVisibility(View.GONE);
                take_up.setVisibility(View.VISIBLE);
                break;
            case R.id.take_up:
                contactsAdapter.setMsgs(openList);
                loding_more.setVisibility(View.VISIBLE);
                take_up.setVisibility(View.GONE);
                break;
            case R.id.base_iv_back:
                finish();
                break;
        }
    }

    @Override
    public void showProgress(boolean isTrue) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
