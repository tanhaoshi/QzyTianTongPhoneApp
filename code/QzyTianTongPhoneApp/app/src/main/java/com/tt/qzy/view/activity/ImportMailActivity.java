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
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.utils.ThreadUtils;
import com.tt.qzy.view.view.ImportMailView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    private List<MallListModel> sourceDateList = new LinkedList<>();

    private ImportMailAdapter adapter;
    private KProgressHUD mHUD;

    private final List<Long> mLongList = new LinkedList<>();
    private final List<Integer> mIntegers = new LinkedList<>();

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

    @OnClick({R.id.base_iv_back,R.id.base_tv_toolbar_right,R.id.base_tv_toolbar_title})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                selectAllMail();
                break;
            case R.id.base_tv_toolbar_right:
                mHUD.show();
                saveContactsMailList(ImportMailActivity.this,mLongList,mIntegers,sourceDateList);
                break;
            case R.id.base_tv_toolbar_title:
                //1561708553807 1561708557684
                for(int i=0; i<5000; i++){
                    MallListModel mallListModel1 = new MallListModel("153672588:"+i,
                            "张"+AppUtils.getRandomWord()+AppUtils.getRandomWord());
                    sourceDateList.add(mallListModel1);
                }
                adapter.updateList(sourceDateList);
                break;
        }
    }

    private void selectAllMail(){
        ExecutorService executorService = ThreadUtils.getCachedPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<MallListModel> mallListModels = new ArrayList<>();
                mallListModels.addAll(sourceDateList);
                for(int i=0;i<mallListModels.size();i++){
                    mLongList.add(sourceDateList.get(i).getId());
                    mIntegers.add(i);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.selectAll();
                    }
                });
            }
        });
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

    private void saveContactsMailList(final Activity context ,final List<Long> mLongList , final List<Integer> mIntegers,
                                      final List<MallListModel> listModels){
        io.reactivex.Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                if(null != mLongList && mLongList.size() > 0){
                    try{
                        MailListManager.getInstance(context).deleteAllMail(context);
                        final List<MailListDao> mailListDaos = new LinkedList<>();
                        for(int i=0;i<mLongList.size();i++){
                            MailListDao mailListDao = new MailListDao
                                    (sourceDateList.get(mIntegers.get(i)).getPhone(),
                                            sourceDateList.get(mIntegers.get(i)).getName()
                                            ,null,null,null,null,
                                            null,null);
                            mailListDaos.add(mailListDao);
                        }
                        MailListManager.getInstance(context).insertMailListList(mailListDaos,context);
                    }finally {
                        importLocalLinkName();
                        observableEmitter.onNext(true);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        KLog.i("onError message = " + throwable.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void importLocalLinkName(){
        try{
            checkEquallyLink();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mHUD.dismiss();
                    finish();
                }
            });
        }finally {

        }
    }

    private void checkEquallyLink(){

        List<CallRecordDao> daoList = CallRecordManager.getInstance(ImportMailActivity.this).queryCallRecordList();
        List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(this).queryShortMessageList();
        Map<String, Integer> callRecordMap   = new HashMap<String,Integer>();
        Map<String,Integer>  shortMessageMap = new HashMap<String,Integer>();

        if(daoList != null && daoList.size() > 0){

            for(CallRecordDao callRecordDao : daoList){

                callRecordMap.put(callRecordDao.getPhoneNumber(),1);

            }

            for(MallListModel mallListModel : sourceDateList){

                Integer temp = callRecordMap.get(mallListModel.getPhone());

                if(temp != null){
                    List<CallRecordDao> keyList = CallRecordManager.getInstance(ImportMailActivity.this)
                            .queryKeyOnPhoneNumber(mallListModel.getPhone());

                    if(null != keyList && keyList.size() > 0){
                        List<CallRecordDao> buildRecordDao = new LinkedList<>();
                        for(CallRecordDao callRecordDao : daoList){
                            callRecordDao.setName(mallListModel.getName());
                            buildRecordDao.add(callRecordDao);
                        }
                        CallRecordManager.getInstance(ImportMailActivity.this).updateTxRecordName(buildRecordDao);
                    }
                }
            }
        }

        if(messageDaoList != null && messageDaoList.size()>0){

            for(ShortMessageDao shortMessageDao : messageDaoList){

                shortMessageMap.put(shortMessageDao.getNumberPhone(),1);
            }

            for(MallListModel mallListModel : sourceDateList){

                Integer temp = shortMessageMap.get(mallListModel.getPhone());

                if(temp != null){

                    List<ShortMessageDao> shortMessageDaos = ShortMessageManager.getInstance(ImportMailActivity.this)
                            .queryPrimaryOfPhone(mallListModel.getPhone());

                    if(null != shortMessageDaos && shortMessageDaos.size() > 0){

                        List<ShortMessageDao> buildShortMessageDao = new LinkedList<>();
                        for(ShortMessageDao shortMessageDao : shortMessageDaos){

                            shortMessageDao.setName(mallListModel.getName());

                            buildShortMessageDao.add(shortMessageDao);
                        }

                        ShortMessageManager.getInstance(ImportMailActivity.this).updateShortMessageList(buildShortMessageDao);
                    }

                }
            }
        }
    }
}
