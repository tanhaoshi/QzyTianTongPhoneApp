package com.tt.qzy.view.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.tt.qzy.view.MainActivity;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.SettingsActivity;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.layout.CircleImageView;
import com.tt.qzy.view.layout.NiftyExpandDialog;
import com.tt.qzy.view.presenter.fragment.MainFragmentPresenter;
import com.tt.qzy.view.service.TimerService;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.MainFragmentView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainFragment extends Fragment implements MainFragmentView{

    @BindView(R.id.connect)
    TextView connect;
    @BindView(R.id.imageContect)
    CircleImageView mCircleImageView;
    @BindView(R.id.tmt_noEntry)
    TextView tmt_noEntry;
    @BindView(R.id.battery)
    TextView battery;
    @BindView(R.id.main_latitude)
    TextView main_latitude;
    @BindView(R.id.main_longitude)
    TextView main_longitude;
    @BindView(R.id.main_location)
    SwitchCompat main_location;
    @BindView(R.id.sc_settin_testxinlv)
    SwitchCompat sc_settin_testxinlv;
    @BindView(R.id.sc_settin_data)
    SwitchCompat sc_settin_data;
    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.circle_loading_view)
    AnimatedCircleLoadingView mAnimatedCircleLoadingView;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    private MainFragmentPresenter mPresneter;
    private MainActivity mainActivity;

    private Intent mIntent;

    private boolean isServerChange;

    public MainFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setRetainInstance(true);
        mPresneter = new MainFragmentPresenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mPresneter.onBindView(this);
        initView();
        return view;
    }

    private void initView() {

        if (!NetworkUtil.isWifiEnabled(getActivity())) {
            connect.setText(getActivity().getResources().getString(R.string.TMT_click_connect));
        } else {

//            mPresneter.checkConnectedSate();
        }

        mainActivity = (MainActivity)getActivity();

        setConnectStateView(mainActivity.isConnectStatus());

        if(mainActivity.isConnectStatus()){
            if(mainActivity.isConnectBeiDou()){
                main_location.setChecked(true);
            }else{
                main_location.setChecked(false);
            }
        }

        initListener();
    }

    private void initListener(){
        main_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mainActivity.isConnectStatus()){
                    if(isChecked){
                        mPresneter.requestGpsPosition(true);
                    }else{
                        main_latitude.setText("");
                        main_longitude.setText("");
                        mPresneter.requestGpsPosition(false);
                    }
                }else{
                    NToast.shortToast(getActivity(), getString(R.string.TMT_connect_tiantong_please));
                    main_latitude.setText("");
                    main_longitude.setText("");
                    main_location.setChecked(false);
                }
            }
        });
        sc_settin_testxinlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SPUtils.containsShare(getActivity(), Constans.CRY_HELP_PHONE)){
                    sc_settin_testxinlv.setChecked(false);
                    NToast.shortToast(getActivity(),getString(R.string.TMT_remind));
                    if(sc_settin_testxinlv.isChecked()){
                        if(AppUtils.isServiceRunning("com.tt.qzy.view.service.TimerService",getActivity())){
                            Intent intent = new Intent(getActivity(), TimerService.class);
                            getActivity().stopService(intent);
                        }
                    }
                    return;
                }
                if(mainActivity.isConnectStatus()){
                    if(mainActivity.tt_isSignal){
                        if(sc_settin_testxinlv.isChecked()){
                            mPresneter.dialPhone(SPUtils.getShare(getActivity(),Constans.CRY_HELP_PHONE,"").toString());
                            mIntent = new Intent(getActivity(),TimerService.class);
                            getActivity().startService(mIntent);
//                            mPresneter.requestGpsPosition(true);
                            main_location.setChecked(true);
                        }else{
                            getActivity().stopService(mIntent);
//                            mPresneter.requestGpsPosition(false);
                            main_location.setChecked(false);
                        }
                    }else{
                        NToast.shortToast(getActivity(), getString(R.string.TMT_THE_DEVICE_NOT_INTERNET_NOW));
                        sc_settin_testxinlv.setChecked(false);
                    }
                }else{
                    NToast.shortToast(getActivity(), getString(R.string.TMT_connect_tiantong_please));
                    sc_settin_testxinlv.setChecked(false);
                }
            }
        });

        sc_settin_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mainActivity.isConnectStatus()){
                    if(isChecked){
                          mainActivity.stopService();
//                        mPresneter.requestEnableData(true);
                    }else{
//                        mPresneter.requestEnableData(false);
                    }
                }else{
                    NToast.shortToast(getActivity(), getString(R.string.TMT_connect_tiantong_please));
                    sc_settin_data.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.main_editors, R.id.main_settings, R.id.tmt_noEntry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_editors:
                if(mainActivity.isConnectStatus()){
                    mPresneter.stopConnect();
                }else{
                    mPresneter.startConnect();
                }
                break;
            case R.id.main_settings:
                Intent settings_intent = new Intent(getActivity(), SettingsActivity.class);
                settings_intent.putExtra("connect",mainActivity.isConnectStatus());
                settings_intent.putExtra("isSim",mainActivity.tt_isSim);
                settings_intent.putExtra("isSignal",mainActivity.tt_isSignal);
                settings_intent.putExtra("baterly",mainActivity.tt_baterly);
                startActivity(settings_intent);
                break;
            case R.id.tmt_noEntry:
                break;
        }
    }

    /** 设置连接天通状态显示 */
    private void setConnectStateView(boolean isConnected) {
        if (isConnected) {
            mCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.yilianjie));
            connect.setText(getResources().getString(R.string.TMT_connect_succeed));
        } else {
            mCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.weilianjie));
            connect.setText(getResources().getString(R.string.TMT_click_connect));
        }
        mainActivity.connectTianTongControl(isConnected);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == 1) {
            String content = data.getStringExtra("code");
            connect.setText(content);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresneter.release();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void getTtPhonePosition(TtPhonePositionProtos.TtPhonePosition ttPhonePosition) {
        if(ttPhonePosition.getResponseStatus()){
            if(TextUtils.isEmpty(ttPhonePosition.getLatItude()) || TextUtils.isEmpty(ttPhonePosition.getLongItude())){
                main_latitude.setText("");
                main_longitude.setText("");
                return;
            }else{
                main_latitude.setText(AppUtils.decimalDouble(Double.valueOf(ttPhonePosition.getLatItude())));
                main_longitude.setText(AppUtils.decimalDouble(Double.valueOf(ttPhonePosition.getLongItude())));
            }

            SPUtils.removeShare(getActivity(),Constans.LONGITUDE_VALUE);
            SPUtils.removeShare(getActivity(),Constans.LATITUDE_VALUE);

            SPUtils.putShare(getActivity(),Constans.LONGITUDE_VALUE,main_longitude.getText().toString());
            SPUtils.putShare(getActivity(),Constans.LATITUDE_VALUE,main_latitude.getText().toString());

        }else{
            NToast.shortToast(getActivity(),getActivity().getString(R.string.TMT_gps_position_filed));
        }
    }

    @Override
    public void getTtBeiDouSwitch(TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou) {
        if(ttOpenBeiDou.getResponseStatus()){
            main_location.setChecked(true);
        }else{
            main_location.setChecked(false);
        }
    }

    @Override
    public void connectedState(boolean isConnected) {
        setConnectStateView(isConnected);
    }

    @Override
    public void upgradleServerApp() {
        final NiftyExpandDialog dialogBuilder = NiftyExpandDialog.getInstance(getActivity()).initDialogBuilder();
        dialogBuilder.serverUpdateDismiss(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewTransition(true);
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
    }

    public void viewTransition(boolean isChange){
        if(isChange){
            mScrollView.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
            mAnimatedCircleLoadingView.startDeterminate();
            mainActivity.statusLayout.setVisibility(View.GONE);
            mainActivity.getBottomBar().setVisibility(View.GONE);
        }else{
            mScrollView.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
            mainActivity.statusLayout.setVisibility(View.VISIBLE);
            mainActivity.getBottomBar().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void serverAppUpgradlePercent(Integer i) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(140);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        mAnimatedCircleLoadingView.setPercent(i);
    }

    @Override
    public void isServerUpdate(boolean isStatus) {
        this.isServerChange = isStatus;
        if(isStatus){
            viewTransition(false);
            NToast.shortToast(getActivity(),getActivity().getString(R.string.TMT_start_again_connect));
        }else{
            NToast.shortToast(getActivity(),getActivity().getString(R.string.TMT_update_failure_alert));
        }
    }

    @Override
    public void upgradleNonconnect() {
        viewTransition(false);
    }

    @Override
    public void getSetverInitMobileStatus(boolean isInitStatus) {
        sc_settin_data.setChecked(isInitStatus);
    }

    @Override
    public void getMobileDataShow(boolean isShow) {
        if(isShow){
            sc_settin_data.setChecked(true);
            mainActivity.img4.setVisibility(View.VISIBLE);
        }else{
            sc_settin_data.setChecked(false);
            mainActivity.img4.setVisibility(View.GONE);
        }
    }

    @Override
    public void getServerSosStatus(boolean isSwitch) {
        sc_settin_testxinlv.setChecked(isSwitch);
        main_location.setChecked(isSwitch);
        if (isSwitch == false) {
            if (AppUtils.isServiceRunning("com.tt.qzy.view.service.TimerService", getActivity())) {
                if (mIntent != null) {
                    getActivity().stopService(mIntent);
                } else {
                    mIntent = new Intent(getActivity(), TimerService.class);
                    getActivity().stopService(mIntent);
                }
            }
        }
    }

    private void changePercent(final int i){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAnimatedCircleLoadingView.setPercent(i);
                if(i == 100){
                    NToast.longToast(getActivity(),getActivity().getString(R.string.TMT_UPDATE_SUCCEED_FOR_WAIT));
                }
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
}
