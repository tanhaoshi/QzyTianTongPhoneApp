package com.tt.qzy.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
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


import com.alibaba.fastjson.JSON;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.qzy.tt.phone.service.TtPhoneService;
import com.socks.library.KLog;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.bean.VersionCodeModel;
import com.tt.qzy.view.fragment.AidlPhoneFragment;
import com.tt.qzy.view.fragment.MailListFragment;
import com.tt.qzy.view.fragment.MainFragment;
import com.tt.qzy.view.fragment.ShortMessageFragment;
import com.tt.qzy.view.layout.BadgeView;
import com.tt.qzy.view.layout.NiftyExpandDialog;
import com.tt.qzy.view.presenter.activity.MainActivityPresenter;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.MainActivityView;
import com.xdandroid.hellodaemon.IntentWrapper;


import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainActivityView> implements MainActivityView{

    @BindView(R.id.shortMessage)
    Button button;
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

    public FrameLayout getBottomBar() {
        return bottomBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        showMainFragment();
//        remind(button,"12");
        mPresenter = new MainActivityPresenter(this);
        mPresenter.onBindView(this);
    }

    @Override
    public void initData() {
    }

    public void showMainFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mMainFragment == null){
            mMainFragment = mMainFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mMainFragment);
        }
        commitShowFragment(fragmentTransaction,mMainFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.statusColor));
    }

    public void showAidlPhoneFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mAidlPhoneFragment == null){
            mAidlPhoneFragment = mAidlPhoneFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mAidlPhoneFragment);
        }
        commitShowFragment(fragmentTransaction,mAidlPhoneFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
    }

    public void showShortMeesageFragmnet(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mShortMessageFragment == null){
            mShortMessageFragment = mShortMessageFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mShortMessageFragment);
        }
        commitShowFragment(fragmentTransaction,mShortMessageFragment);

        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
    }

    public void showMailListFragmnet(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mMailListFragment == null){
            mMailListFragment = mMailListFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mMailListFragment);
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

    public void remind(View view,String size){
        BadgeView badge = new BadgeView(this, view);
        badge.setText(size);
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge.setTextColor(Color.WHITE);
        badge.setBadgeBackgroundColor(Color.RED);
        badge.setTextSize(12);
        badge.setBadgeMargin(3);
        badge.show();
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
                }else{
                    NToast.shortToast(this,"不可操作,请连接天通猫!");
                }
                break;
            case R.id.tab_messager:
                if(tt_status){
                    showShortMeesageFragmnet();
                }else{
                    NToast.shortToast(this,"不可操作,请连接天通猫!");
                }
                break;
            case R.id.tab_mail:
                if(tt_status){
                    showMailListFragmnet();
                }else{
                    NToast.shortToast(this,"不可操作,请连接天通猫!");
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getAppversionRequest();
        startService();
    }

    /**
     * start service
     */
    private void startService() {
        startService(new Intent(this, TtPhoneService.class));
    }

    /**
     * stop service
     */
    public void stopService() {
        stopService(new Intent(this, TtPhoneService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // CommonData.relase();
        NiftyExpandDialog.getInstance(MainActivity.this).release();
//        stopService();
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
        title.setText("提示");
        custom_input.setText("温馨提示:您确定是否退出吗?");
        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog.dismiss();
                finish();
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
        KLog.i("getAppVersion value = " +JSON.toJSONString(versionCodeModel));
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
    public void onBackPressed() {
        super.onBackPressed();
        IntentWrapper.onBackPressed(this);
    }

}
