package com.tt.qzy.view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import com.tt.qzy.view.fragment.AidlPhoneFragment;
import com.tt.qzy.view.fragment.MailListFragment;
import com.tt.qzy.view.fragment.MainFragment;
import com.tt.qzy.view.fragment.ShortMessageFragment;
import com.tt.qzy.view.layout.BadgeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ShortMessageFragment.OnKeyDownListener{

    @BindView(R.id.search_close_btn)
    Button button;

    private MainFragment mMainFragment;

    private AidlPhoneFragment mAidlPhoneFragment;

    private ShortMessageFragment mShortMessageFragment;

    private MailListFragment mMailListFragment;

    //用于记录当前显示的fragment;
    private Fragment mFragment;
    // 记录是正常退出 还是 我们隐藏的按钮出现 back消失.
    private boolean isOnkeyDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        showMainFragment();
        ButterKnife.bind(this);
        remind(button,"12");
    }

    public void showMainFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mMainFragment == null){
            mMainFragment = mMainFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mMainFragment);
        }
        commitShowFragment(fragmentTransaction,mMainFragment);
    }

    public void showAidlPhoneFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mAidlPhoneFragment == null){
            mAidlPhoneFragment = mAidlPhoneFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mAidlPhoneFragment);
        }
        commitShowFragment(fragmentTransaction,mAidlPhoneFragment);
    }

    public void showShortMeesageFragmnet(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mShortMessageFragment == null){
            mShortMessageFragment = mShortMessageFragment.newInstance();
            mShortMessageFragment.setOnKeyDownListener(this);
            fragmentTransaction.add(R.id.fragmentContent, mShortMessageFragment);
        }
        commitShowFragment(fragmentTransaction,mShortMessageFragment);
    }

    public void showMailListFragmnet(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(mMailListFragment == null){
            mMailListFragment = mMailListFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mMailListFragment);
        }
        commitShowFragment(fragmentTransaction,mMailListFragment);
    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment){
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        mFragment = fragment;
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
                showAidlPhoneFragment();
                break;
            case R.id.tab_messager:
                showShortMeesageFragmnet();
                break;
            case R.id.tab_mail:
                showMailListFragmnet();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startServcie();
    }

    /**
     * 开启服务
     */
    private void startServcie() {
        //startService(new Intent(this, TtPhoneService.class));
    }

    /**
     * 停止服务
     */
    private void stopServcie() {
        //stopService(new Intent(this, TtPhoneService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // CommonData.relase();
        stopServcie();
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mFragment instanceof ShortMessageFragment) {
            if(isOnkeyDown){
                ((ShortMessageFragment) mFragment).onKeyDown(keyCode, event);
                isOnkeyDown = false;
                return true;
            }else{
                return super.onKeyDown(keyCode,event);
            }
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void setOnkeyDown(boolean isKeyDown) {
         this.isOnkeyDown = isKeyDown;
    }
}
