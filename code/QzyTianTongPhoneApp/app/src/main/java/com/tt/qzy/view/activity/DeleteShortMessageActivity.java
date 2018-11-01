package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.DeleteShortMessageAdapter;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.activity.DeleteShortMessagePresenter;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.DeleteShortMessageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteShortMessageActivity extends AppCompatActivity implements DeleteShortMessageView,DeleteShortMessageAdapter
         .OnItemClickListener{

    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_iv_back)
    TextView base_iv_back;
    @BindView(R.id.base_tv_toolbar_right)
    TextView base_tv_toolbar_right;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private KProgressHUD mHUD;

    private List<ShortMessageDao> mDaoList = new ArrayList<>();
    private DeleteShortMessagePresenter mPresenter;
    private DeleteShortMessageAdapter mShortMessageAdapter;

    private Long id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_short_message);
        ButterKnife.bind(this);
        mPresenter = new DeleteShortMessagePresenter(this);
        mPresenter.onBindView(this);
        initView();
        initData();
    }

    private void initView() {
        initProgress();
        base_tv_toolbar_title.setText(getString(R.string.TMT_select_shortMessage));
        LinearLayoutManager manager = new LinearLayoutManager(DeleteShortMessageActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    private void initData(){
        mShortMessageAdapter = new DeleteShortMessageAdapter(this,mDaoList);
        mShortMessageAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mShortMessageAdapter);
        showProgress(true);
        loadData(true);
    }

    @OnClick({R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                if(null == id){
                    NToast.shortToast(this,getString(R.string.TMT_please_select_shortMessage));
                }else{
                    ShortMessageManager.getInstance(DeleteShortMessageActivity.this).deleteShortMessageOfPrimaryKey(id);
                    NToast.shortToast(DeleteShortMessageActivity.this,getString(R.string.TMT_delete_succeed));
                    finish();
                }
                break;
        }
    }

    @Override
    public void getShortMessageList(List<ShortMessageDao> list) {
        mShortMessageAdapter.setData(list);
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
        NToast.shortToast(DeleteShortMessageActivity.this,msg);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mPresenter.getShortMessageList();
    }

    private void initProgress(){
        mHUD = KProgressHUD.create(DeleteShortMessageActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel(getString(R.string.loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void onItemClick(View view, int position, boolean isFlag, Long id) {
        if(isFlag){
            this.id = id;
        }else{
            this.id = null;
        }
    }
}
