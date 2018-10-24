package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.presenter.activity.SettingsPresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.SettingsView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity<SettingsView> implements SettingsView{

    private AlertDialog dialog;

    @BindView(R.id.setting_map)
    TextView setting_map;
    @BindView(R.id.usb_swtich)
    SwitchCompat mSwitchCompat;

    private SettingsPresenter mPresenter;


    @Override
    public int getContentView() {
        return R.layout.activity_settings;
    }

    public void initView() {
        ButterKnife.bind(this);
        mPresenter = new SettingsPresenter(this);
        mPresenter.onBindView(this);
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if((Boolean) SPUtils.getShare(SettingsActivity.this, Constans.TTM_STATUS,false)){
                    if(isChecked){
                        mPresenter.openTianTongBeidou(true);
                    }else{
                        mPresenter.openTianTongBeidou(false);
                    }
                }else{
                    NToast.shortToast(SettingsActivity.this,getString(R.string.TMT_connect_tiantong_please));
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.settings_sos,R.id.setting_map,R.id.setting_about,R.id.main_quantity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.settings_sos:
//                ininDialog();
                jumpSosSetting();
                break;
            case R.id.setting_map:
                Intent intent = new Intent(SettingsActivity.this,OffLineMapActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_about:
                Intent about_intent = new Intent(SettingsActivity.this,MainAboutActivity.class);
                startActivity(about_intent);
                break;
            case R.id.main_quantity:
                finish();
                break;
        }
    }

    private void jumpSosSetting(){
        Intent intent = new Intent(SettingsActivity.this,SosSettingsActivity.class);
        startActivity(intent);
    }

    public void ininDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
        View v = inflater.inflate(R.layout.customied_dialog_style, null);
        final EditText custom_input = (EditText)v.findViewById(R.id.custom_input);
        final TextView custom_cannel = (TextView)v.findViewById(R.id.custom_cannel);
        final TextView custom_yes = (TextView)v.findViewById(R.id.custom_yes);
        dialog = builder.create();
        dialog.setView(inflater.inflate(R.layout.customied_dialog_style, null));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        custom_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSoftInputFromWindow(custom_input);
            }
        });

        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NToast.shortToast(SettingsActivity.this,getResources().getString(R.string.TMT_share));
                dialog.dismiss();
            }
        });
    }

    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void usbSwtich(boolean isOpen) {

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
    protected void onStop() {
        super.onStop();
        mPresenter.release();
    }
}
