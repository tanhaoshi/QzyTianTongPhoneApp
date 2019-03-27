package com.tt.qzy.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.qzy.phone.pcm.AllLocalPcmManager;
import com.qzy.tt.phone.service.TtPhoneService;
import com.socks.library.KLog;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.bean.VersionCodeModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.fragment.AidlPhoneFragment;
import com.tt.qzy.view.fragment.MailListFragment;
import com.tt.qzy.view.fragment.MainFragment;
import com.tt.qzy.view.fragment.ShortMessageFragment;
import com.tt.qzy.view.layout.BadgeView;
import com.tt.qzy.view.layout.NiftyExpandDialog;
import com.tt.qzy.view.presenter.activity.MainActivityPresenter;
import com.tt.qzy.view.receiver.HomeKeyListenerHelper;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.MainActivityView;
import com.xdandroid.hellodaemon.IntentWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainActivityView> implements MainActivityView,HomeKeyListenerHelper.HomeKeyListener{

    @BindView(R.id.shortMessage)
    Button shortMessage;
    @BindView(R.id.putOut)
    Button putOut;
    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.circle_loading_view)
    AnimatedCircleLoadingView mCircleLoadingView;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.bottomBar)
    FrameLayout bottomBar;

    private MainFragment mMainFragment;

    private AidlPhoneFragment mAidlPhoneFragment;

    private ShortMessageFragment mShortMessageFragment;

    private MailListFragment mMailListFragment;

    private MainActivityPresenter mPresenter;
    private AlertDialog dateDialog;

    private BadgeView callBadgeView;
    private BadgeView shortMessageBadgeView;
    private HomeKeyListenerHelper mHelper;

    public FrameLayout getBottomBar() {
        return bottomBar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            mMainFragment         = (MainFragment)fragmentManager.findFragmentByTag("mMainFragment");
            mAidlPhoneFragment    = (AidlPhoneFragment)fragmentManager.findFragmentByTag("mAidlPhoneFragment");
            mShortMessageFragment = (ShortMessageFragment)fragmentManager.findFragmentByTag("mShortMessageFragment");
            mMailListFragment     = (MailListFragment)fragmentManager.findFragmentByTag("mMailListFragment");
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mPresenter = new MainActivityPresenter(this);
        mPresenter.onBindView(this);
        mHelper = new HomeKeyListenerHelper(MainActivity.this);
        mHelper.registerHomeKeyListener(this);
        showMainFragment();
        initBadeView();
    }

    @Override
    public void initData() {
    }

    public void showMainFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mMainFragment == null){
            mMainFragment = mMainFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mMainFragment,"mMainFragment");
        }
        commitShowFragment(fragmentTransaction,mMainFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.statusColor));
    }

    public void showAidlPhoneFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mAidlPhoneFragment == null){
            mAidlPhoneFragment = mAidlPhoneFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mAidlPhoneFragment,"mAidlPhoneFragment");
        }
        commitShowFragment(fragmentTransaction,mAidlPhoneFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
    }

    public void showShortMeesageFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mShortMessageFragment == null){
            mShortMessageFragment = mShortMessageFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mShortMessageFragment,"mShortMessageFragment");
        }
        commitShowFragment(fragmentTransaction,mShortMessageFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
    }

    public void showMailListFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mMailListFragment == null){
            mMailListFragment = mMailListFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mMailListFragment,"mMailListFragment");
        }
        commitShowFragment(fragmentTransaction,mMailListFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment){
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        hideFragment(fragmentTransaction,mMainFragment);
        hideFragment(fragmentTransaction,mAidlPhoneFragment);
        hideFragment(fragmentTransaction,mShortMessageFragment);
        hideFragment(fragmentTransaction,mMailListFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction,Fragment fragment){
        if(fragment!=null){
            fragmentTransaction.hide(fragment);
        }
    }

    private void initBadeView(){
        callBadgeView = new BadgeView(this,putOut);
        shortMessageBadgeView = new BadgeView(this,shortMessage);
    }

    public void remind(String size,BadgeView badge){
        if("0".equals(size)){
            badge.hide();
        }else{
            badge.setText(size);
            badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badge.setTextColor(Color.WHITE);
            badge.setBadgeBackgroundColor(Color.RED);
            badge.setTextSize(10);
            badge.setBadgeMargin(0);
            badge.show();
        }
    }

    @OnClick({R.id.tab_main,R.id.tab_aidl, R.id.tab_messager, R.id.tab_mail})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tab_main:
                showMainFragment();
                break;
            case R.id.tab_aidl:
                if(tt_status){
                    showAidlPhoneFragment();
                    callBadgeView.hide();
                    updateMainAlert();
                }else{
                    NToast.shortToast(this,getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
            case R.id.tab_messager:
                if(tt_status){
                    showShortMeesageFragment();
                }else{
                    NToast.shortToast(this,getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
            case R.id.tab_mail:
                if(tt_status){
                    showMailListFragment();
                }else{
                    NToast.shortToast(this,getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
        }
    }

    private void updateMainAlert(){
        SPUtils.putShare(this,Constans.RECORD_ISREAD,0);
        List<CallRecordDao> callRecordDaos = new ArrayList<>();
        List<CallRecordDao> list = CallRecordManager.getInstance(MainActivity.this).queryAllRecordList();
        for(CallRecordDao callRecordDao : list){
            if("3".equals(callRecordDao.getState())){
                callRecordDaos.add(callRecordDao);
                callRecordDao.setState("1");
                CallRecordManager.getInstance(MainActivity.this).updateRecordName(callRecordDao);
            }
        }
        mPresenter.requestServerPhoneStatus(callRecordDaos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SPUtils.putShare(MainActivity.this,Constans.AUTO_EXITS,true);
        startService();
        Integer recordCount = (Integer)SPUtils.getShare(MainActivity.this, Constans.RECORD_ISREAD,0);
        remind(String.valueOf(recordCount),callBadgeView);
        Integer shortMessageCount = (Integer)SPUtils.getShare(MainActivity.this,Constans.SHORTMESSAGE_ISREAD,0);
        remind(String.valueOf(shortMessageCount),shortMessageBadgeView);
    }

    public void showRecordRead(){
        Integer shortMessageCount = (Integer)SPUtils.getShare(MainActivity.this,Constans.SHORTMESSAGE_ISREAD,0);
        remind(String.valueOf(shortMessageCount),shortMessageBadgeView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getAppversionRequest();
    }

    /**
     * start service
     *
     * dispose permission of xiao mi
     */
    private void startService() {
        if(mPresenter.checkPermissionExist()){
//            if(!AppUtils.isServiceRunning("com.qzy.tt.phone.service.TtPhoneService",MainActivity.this)){
                startService(new Intent(this, TtPhoneService.class));
//            }
        }else{
            mPresenter.requestPermission(Build.BRAND,this, Manifest.permission.RECORD_AUDIO);
            startService(new Intent(this, TtPhoneService.class));
        }


        //设置数据
        setDataListener();
    }

    /*
     * stop service
     */
    public void stopService() {
        stopService(new Intent(this, TtPhoneService.class));
    }

    public void release(){
        SPUtils.removeShare(MainActivity.this,Constans.AUTO_EXITS);
        mHelper.unregisterHomeKeyListener();
        if(AllLocalPcmManager.instance != null){
            AllLocalPcmManager.instance.free();
        }else{
            AllLocalPcmManager.getInstance(MainActivity.this).free();
        }
        NiftyExpandDialog.getInstance(MainActivity.this).release();
        mPresenter.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.unregisterHomeKeyListener();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            initDateDialog();
        }
        return super.onKeyDown(keyCode,event);
    }

    private void initDateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View v = inflater.inflate(R.layout.dialog_datetime_settings, null);
        final TextView custom_input = (TextView) v.findViewById(R.id.custom_input);
        final TextView custom_cannel = (TextView)v.findViewById(R.id.custom_cannel);
        final TextView custom_yes = (TextView)v.findViewById(R.id.custom_yes);
        final TextView title = (TextView)v.findViewById(R.id.title);
        dateDialog = builder.create();
        dateDialog.setView(inflater.inflate(R.layout.customied_dialog_style, null));
        dateDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dateDialog.show();
        dateDialog.getWindow().setContentView(v);
        dateDialog.getWindow().setGravity(Gravity.CENTER);
        title.setText(getString(R.string.TMT_hint));
        custom_input.setText(getString(R.string.TMT_confirm_exit));
        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                release();
                dateDialog.dismiss();
//                TraceServiceImpl.stopService();
                finishAffinity();
                System.exit(0);
            }
        });
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

    @Override
    public void getAppVersionCode(VersionCodeModel versionCodeModel) {
        if(mPresenter.checkUpdate(versionCodeModel)){
            niftyDialog(versionCodeModel);
        }
    }

    private void niftyDialog(final VersionCodeModel versionCodeModel){
        final NiftyExpandDialog dialogBuilder = NiftyExpandDialog.getInstance(MainActivity.this).initDialogBuilder();
        dialogBuilder.nonCanceDismiss(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDownLoader(versionCodeModel);
                        dialogBuilder.niftyDismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.niftyDismiss();
                    }
                })
                .show();
        TextView content = (TextView)dialogBuilder.getNiftyDialogBuilder().findViewById(R.id.content);
        content.setText(versionCodeModel.getData().getChangeContent());
    }

    public void startDownLoader(VersionCodeModel versionCodeModel){
        mPresenter.startUploader(versionCodeModel.getData().getLoadUrl());
    }

    public void viewTransition(boolean isChange){
        if(isChange){
            mLinearLayout.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
        }else{
            mLinearLayout.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStartDownloader() {
        viewTransition(true);
        mCircleLoadingView.startDeterminate();
    }

    @Override
    public void onProgressPercent(int progressPercent) {
        mCircleLoadingView.setPercent(progressPercent);
    }

    @Override
    public void onCompelete(String installPath) {
        AppUtils.installApk(MainActivity.this,installPath);
    }

    @Override
    public void onError(String errorMessage) {
        NToast.shortToast(MainActivity.this,errorMessage);
    }

    @Override
    public void showRecordCallRead(boolean isShow, int count) {
        if(isShow){
            Integer integer = (Integer)SPUtils.getShare(MainActivity.this,Constans.RECORD_ISREAD,0);
            remind(String.valueOf(integer),callBadgeView);
        }
    }

    @Override
    public void showShortMessageRead(boolean isShow, int count) {
        if(isShow){
            Integer integer = (Integer)SPUtils.getShare(MainActivity.this,Constans.SHORTMESSAGE_ISREAD,0);
            remind(String.valueOf(integer),shortMessageBadgeView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentWrapper.onBackPressed(this);
    }

    @Override
    public void onHomeKeyShortPressed() {
        if(AllLocalPcmManager.instance != null){
            AllLocalPcmManager.instance.free();
            SPUtils.removeShare(MainActivity.this,Constans.AUTO_EXITS);
        }else{
            AllLocalPcmManager.getInstance(MainActivity.this).free();
        }
    }

    @Override
    public void onHomeKeyLongPressed() {

    }
}
