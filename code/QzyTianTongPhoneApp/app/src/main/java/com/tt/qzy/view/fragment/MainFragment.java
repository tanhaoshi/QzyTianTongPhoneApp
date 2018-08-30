package com.tt.qzy.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.SettingsActivity;
import com.tt.qzy.view.activity.UserEditorsActivity;
import com.tt.qzy.view.evenbus.MainFragmentEvenbus;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment{

    @BindView(R.id.connect)
    TextView connect;
    @BindView(R.id.imageContect)
    CircleImageView mCircleImageView;
    @BindView(R.id.tmt_noEntry)
    TextView tmt_noEntry;
    @BindView(R.id.battery)
    TextView battery;

    public MainFragment() {
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mBatInfoReveiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mBatInfoReveiver);
    }

    private void initView() {
        if(!NetworkUtil.isWifiEnabled(getActivity())){
            //未连接情况下
            connect.setText(getResources().getString(R.string.TMT_click_connect));
            mCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.weilianjie));
        }else{
            //连接wifi情况下 再次判断wifi名字
            String wifiName = NetworkUtil.getConnectWifiSsid(getActivity());
            //是否等于我们规定好的wifi
            if(wifiName.length() >= 5){
               if(Constans.STANDARD_WIFI_NAME.equals(wifiName.substring(1,6))){
                   connect.setText(getResources().getString(R.string.TMT_connect_sussces));
                   mCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.yilianjie));
               }else{
                   //连接着wifi 但是并不是连接到天通指定wifi
                   connect.setText(getResources().getString(R.string.TMT_click_connect));
                   mCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.weilianjie));
                   NToast.shortToast(getActivity(),"请连接天通wifi!");
               }
            }else{
                NToast.shortToast(getActivity(),"请连接天通wifi!");
            }
        }
    }

    @OnClick({R.id.main_editors,R.id.main_settings,R.id.tmt_noEntry})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_editors:
                //先判断wifi开关是否打开 跳转至打开wifi界面
                if(!NetworkUtil.isWifiEnabled(getActivity())){
                    WifiManager wfManager = (WifiManager)getActivity().getSystemService(getActivity().WIFI_SERVICE);
                    wfManager.setWifiEnabled(true);
                    Intent intent = new Intent();
                    intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                    startActivity(intent);
                }else{
                    //判断是否连接着天通指定的wifi
                    if(!Constans.STANDARD_WIFI_NAME.equals(NetworkUtil.getConnectWifiSsid(getActivity()).substring(1,6))){
                        NToast.shortToast(getActivity(),"请连接天通指定的wifi");
                        Intent intent = new Intent();
                        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getActivity(), UserEditorsActivity.class);
                        startActivityForResult(intent,99);
                    }
                }
                break;
            case R.id.main_settings:
                Intent settings_intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settings_intent);
                break;
            case R.id.tmt_noEntry:
                break;
        }
    }

    private BroadcastReceiver mBatInfoReveiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(intent.ACTION_BATTERY_CHANGED.equals(action))
            {
                int level = intent.getIntExtra("level",0);
                int scale = intent.getIntExtra("scale",100);
                onBatteryInfoReceiver(level, scale);
            }
        }
    };

    private void onBatteryInfoReceiver(int intLevel, int intScale) {
        int percent = intLevel*100/ intScale;
        battery.setText(percent+"%");
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MainFragmentEvenbus evenbus){
        if(evenbus.isChange() && evenbus.getFlag() == 1){
            connect.setText(getResources().getString(R.string.TMT_connect_sussces));
            mCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.yilianjie));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == 1){
            String content = data.getStringExtra("code");
            connect.setText(content);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
