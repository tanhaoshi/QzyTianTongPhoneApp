package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.SelectContactsAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.bean.SortModel;
import com.tt.qzy.view.layout.ClearEditText;
import com.tt.qzy.view.presenter.activity.SelectContactsPresenter;
import com.tt.qzy.view.utils.PingyinContacts;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.layout.SideBar;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinUtils;
import com.tt.qzy.view.view.SelectContactView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectContactsActivity extends AppCompatActivity implements SelectContactsAdapter.OnItemClickListener
 ,SelectContactView{

    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_iv_back)
    TextView base_iv_back;
    @BindView(R.id.base_tv_toolbar_right)
    TextView base_tv_toolbar_right;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.dialog)
    TextView mDialog;
    @BindView(R.id.sideBar)
    SideBar mSideBar;
    @BindView(R.id.custom_input)
    ClearEditText mClearEditText;

    private List<MallListModel> SourceDateList = new ArrayList<>();
    private SelectContactsAdapter adapter;
    private KProgressHUD mHUD;

    private String selectContacts="";
    private static final String RESPONSE_FLAG = "back";
    private static final int RESPONSE_CODE = 1;

    private SelectContactsPresenter mContactsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        mContactsPresenter = new SelectContactsPresenter(this);
        mContactsPresenter.onBindView(this);
        initProgress();
        ButterKnife.bind(this);
        initView();
        loadData(true);
    }

    @OnClick({R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                if(!TextUtils.isEmpty(selectContacts.trim())){
                    Intent intent = new Intent();
                    intent.putExtra(RESPONSE_FLAG,selectContacts);
                    setResult(RESPONSE_CODE,intent);
                    finish();
                }else{
                    NToast.shortToast(this,getResources().getString(R.string.TMT_please_select));
                }
                break;
        }
    }

    private void initView() {
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_select_contacts));
        base_iv_back.setVisibility(View.VISIBLE);
        base_iv_back.setText(getResources().getString(R.string.TMT_cannel));
        base_tv_toolbar_right.setVisibility(View.VISIBLE);
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_mall_list));
        base_tv_toolbar_right.setText(getResources().getString(R.string.TMT_yes));

        mSideBar.setTextView(mDialog);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new SelectContactsAdapter(this, SourceDateList);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onItemClick(View view, int position,boolean isFlag) {
        if(isFlag){
            selectContacts = SourceDateList.get(position).getName();
        }
    }

    @Override
    public void loadData(List<MallListModel> listModels) {
        this.SourceDateList = listModels;
        Collections.sort(SourceDateList);
        adapter.updateList(SourceDateList);
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
        NToast.shortToast(this,msg);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showProgress(true);
        mContactsPresenter.getMallList(this);
    }

    private void initProgress() {
        mHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel(getString(R.string.loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }
}
