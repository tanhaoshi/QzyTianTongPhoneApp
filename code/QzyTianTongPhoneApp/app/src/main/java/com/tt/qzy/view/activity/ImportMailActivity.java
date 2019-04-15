package com.tt.qzy.view.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.ImportMailAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.layout.ClearEditText;
import com.tt.qzy.view.layout.SideBar;
import com.tt.qzy.view.presenter.activity.ImportMailPresenter;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.view.ImportMailView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ImportMailActivity extends AppCompatActivity implements ImportMailView,ImportMailAdapter.OnItemClickListener{

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

    private List<MallListModel> sourceDateList = new ArrayList<>();

    private ImportMailAdapter adapter;
    private KProgressHUD mHUD;

    private final List<Long> mLongList = new ArrayList<>();
    private final List<Integer> mIntegers = new ArrayList<>();

    private ImportMailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        mPresenter = new ImportMailPresenter(this);
        mPresenter.onBindView(this);
        initProgress();
        ButterKnife.bind(this);
        initView();
        loadData(true);
    }

    @OnClick({R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                selectAllMail();
                break;
            case R.id.base_tv_toolbar_right:
                saveContactsMailList(ImportMailActivity.this,mLongList,mIntegers,sourceDateList);
                break;
        }
    }

    private void selectAllMail(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MallListModel> mallListModels = new ArrayList<>();
                mallListModels.addAll(sourceDateList);
                for(int i=0;i<mallListModels.size();i++){
                    mLongList.add(sourceDateList.get(i).getId());
                    mIntegers.add(i);
                }
                saveContactsMailList(ImportMailActivity.this,mLongList,mIntegers,sourceDateList);
            }
        }).start();
    }

    private void initView() {
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_select_contacts));
        base_iv_back.setVisibility(View.VISIBLE);
        base_iv_back.setText(getResources().getString(R.string.TMT_SELECT_ALL));
        base_tv_toolbar_right.setVisibility(View.VISIBLE);
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_mall_list));
        base_tv_toolbar_right.setText(getResources().getString(R.string.TMT_yes));

        mSideBar.setTextView(mDialog);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new ImportMailAdapter(this, sourceDateList);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        final PinyinComparator pinyinComparator = new PinyinComparator();
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //模糊搜索完毕后会出现一个新的List 我应当把这个List保存下来。
                mPresenter.filterData(sourceDateList,s.toString(),pinyinComparator,adapter);
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
    public void onItemClick(View view, int position,boolean isFlag,Long id,List<MallListModel> listModels) {
        if(isFlag){
            mLongList.add(id);
            mIntegers.add(position);
        }else{
            mLongList.remove(id);
            mIntegers.remove(Integer.valueOf(position));
        }
        sourceDateList = listModels;
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
        mPresenter.getContactsMallList(this);
    }

    private void initProgress() {
        mHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setDetailsLabel(getString(R.string.loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void getContactsMallList(List<MallListModel> listModels) {
        this.sourceDateList = listModels;
        Collections.sort(sourceDateList);
        adapter.updateList(sourceDateList);
    }

    private void saveContactsMailList(Activity context , List<Long> mLongList , List<Integer> mIntegers,List<MallListModel> listModels){
        if(null != mLongList && mLongList.size() > 0){
            for(int i=0;i<mLongList.size();i++){
                MailListDao mailListDao = new MailListDao();
                mailListDao.setPhone(sourceDateList.get(mIntegers.get(i)).getPhone());
                mailListDao.setName(sourceDateList.get(mIntegers.get(i)).getName());
                MailListManager.getInstance(context).insertMailListSignal(mailListDao,context);
            }
            importLocalLinkName();
//            NToast.shortToast(ImportMailActivity.this,"导入成功!");
            finish();
        }else{
//            NToast.shortToast(ImportMailActivity.this,"请选中导入的联系人!");
        }
    }

    public void importLocalLinkName(){
        List<MailListDao> listModels = MailListManager.getInstance(ImportMailActivity.this).queryMailList();
        for(MailListDao mallListModel : listModels){
            String phone = mallListModel.getPhone();
            String name = mallListModel.getName();
            List<CallRecordDao> daoList = CallRecordManager.getInstance(ImportMailActivity.this).queryKeyOnPhoneNumber(phone);
            if(null != daoList && daoList.size() > 0){
                for(CallRecordDao callRecordDao : daoList){
                    callRecordDao.setName(name);
                    CallRecordManager.getInstance(ImportMailActivity.this).updateRecordName(callRecordDao);
                }
            }
            List<ShortMessageDao> shortMessageDaos = ShortMessageManager.getInstance(ImportMailActivity.this).queryPrimaryOfPhone(phone);
            if(null != daoList && daoList.size() > 0){
                for(ShortMessageDao shortMessageDao : shortMessageDaos){
                    shortMessageDao.setName(name);
                    ShortMessageManager.getInstance(ImportMailActivity.this).updateShortMessageName(shortMessageDao);
                }
            }
        }
    }
}
