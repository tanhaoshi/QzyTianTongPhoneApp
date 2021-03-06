package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.WifiSettingModel;
import com.tt.qzy.view.presenter.activity.SettingsPresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.SettingsView;


import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity<SettingsView> implements SettingsView {

    private AlertDialog wifiDialog;
    private AlertDialog dateDialog;

    @BindView(R.id.setting_map)
    TextView setting_map;
    @BindView(R.id.usb_swtich)
    SwitchCompat mSwitchCompat;

    private SettingsPresenter mPresenter;

    private boolean isConnect;
    private boolean isSim;
    private boolean isSignal;
    private int baterly = 0;
    private int signalValue = 99;

    @Override
    public int getContentView() {
        return R.layout.activity_settings;
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            isConnect = intent.getBooleanExtra("connect", false);
            isSim = intent.getBooleanExtra("isSim", false);
            isSignal = intent.getBooleanExtra("isSignal", false);
            baterly = intent.getIntExtra("baterly", 0);
            signalValue = intent.getIntExtra("signalValue",99);
        }
    }

    public void initView() {
        ButterKnife.bind(this);
        mPresenter = new SettingsPresenter(this);
        mPresenter.onBindView(this);
        getIntentData();
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((Boolean) SPUtils.getShare(SettingsActivity.this, Constans.TTM_STATUS, false)) {
                    if (isChecked) {
                        mPresenter.openUsbSwtich(true);
                    } else {
                        mPresenter.openUsbSwtich(false);
                    }
                } else {
                    NToast.shortToast(SettingsActivity.this, getString(R.string.TMT_connect_tiantong_please));
                    mSwitchCompat.setChecked(false);
                }
            }
        });
        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
        if (isConnect) {
            img5.setImageDrawable(getResources().getDrawable(R.drawable.search_network));
        } else {
            img5.setImageDrawable(getResources().getDrawable(R.drawable.search_nonetwork));
        }
        if (isSim) {
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        } else {
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_noconnect));
        }
        if (isSignal) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_one));
        } else {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        }
        percentBaterly.setText(baterly + "%");
        img1.setPower(baterly);
        signal.setText("- ("+String.valueOf(signalValue)+") dBm");
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.settings_sos, R.id.setting_map, R.id.setting_about, R.id.main_quantity, R.id.settings_wifi,
            R.id.settings_date_time, R.id.setting_factroy_reset, R.id.setting_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_sos:
                if (isSignal) {
                    jumpSosSetting();
                } else {
                    NToast.shortToast(this, getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
            case R.id.setting_map:
                Intent intent = new Intent(SettingsActivity.this, OffLineMapActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_about:
                jumpMainAboutSetting();
                break;
            case R.id.main_quantity:
                finish();
                break;
            case R.id.settings_wifi:
                if (isSignal) {
                    initWIFIDialog();
                } else {
                    NToast.shortToast(this, getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
            case R.id.settings_date_time:
                if (isSignal) {
                    initDateDialog();
                } else {
                    NToast.shortToast(this, getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
            case R.id.setting_factroy_reset:
                if (isSignal) {
                    initSystemResetDialog();
                } else {
                    NToast.shortToast(this, getString(R.string.TMT_THE_DEVICE_NOT_OPERATION));
                }
                break;
            case R.id.setting_check:
                Intent checkUpdateIntent = new Intent(SettingsActivity.this, CheckUpdateActivity.class);
                startActivity(checkUpdateIntent);
                break;
        }
    }

    private void jumpSosSetting() {
        Intent intent = new Intent(SettingsActivity.this, SosSettingsActivity.class);
        intent.putExtra("connect", isConnect);
        intent.putExtra("isSim", isSim);
        intent.putExtra("isSignal", isSignal);
        intent.putExtra("baterly", baterly);
        intent.putExtra("signalValue",signalValue);
        startActivity(intent);
    }

    private void jumpMainAboutSetting(){
        Intent about_intent = new Intent(SettingsActivity.this, MainAboutActivity.class);
        about_intent.putExtra("connect", isConnect);
        about_intent.putExtra("isSim", isSim);
        about_intent.putExtra("isSignal", isSignal);
        about_intent.putExtra("baterly", baterly);
        about_intent.putExtra("signalValue",signalValue);
        startActivity(about_intent);
    }

    public void initWIFIDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
        View v = inflater.inflate(R.layout.customied_dialog_style, null);
        final EditText custom_input = (EditText) v.findViewById(R.id.custom_input);
        final TextView custom_cannel = (TextView) v.findViewById(R.id.custom_cannel);
        final TextView custom_yes = (TextView) v.findViewById(R.id.custom_yes);
        wifiDialog = builder.create();
        wifiDialog.setView(inflater.inflate(R.layout.customied_dialog_style, null));
        wifiDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        wifiDialog.show();
        wifiDialog.getWindow().setContentView(v);
        wifiDialog.getWindow().setGravity(Gravity.BOTTOM);

        custom_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSoftInputFromWindow(custom_input);
            }
        });

        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiDialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(custom_input.getText().toString())) {
                    NToast.shortToast(SettingsActivity.this, getString(R.string.TMT_wifi_password_notnull));
                    return;
                }

                if (custom_input.getText().toString().length() < 8) {
                    NToast.shortToast(SettingsActivity.this, getString(R.string.TMT_wifi_length_greatethan_eight));
                    return;
                }

                //EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_WIFI_PASSWORD, new WifiSettingModel(custom_input.getText().toString())));
                WifiSettingModel wifiSettingModel = new WifiSettingModel(custom_input.getText().toString());
                if (TtPhoneDataManager.getInstance() != null) {
                    TtPhoneDataManager.getInstance().setWifiPasswd(wifiSettingModel);
                }
                NToast.shortToast(SettingsActivity.this, getResources().getString(R.string.TMT_share));
                wifiDialog.dismiss();
            }
        });
    }

    private void initDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
        View v = inflater.inflate(R.layout.dialog_datetime_settings, null);
        final TextView custom_input = (TextView) v.findViewById(R.id.custom_input);
        final TextView custom_cannel = (TextView) v.findViewById(R.id.custom_cannel);
        final TextView custom_yes = (TextView) v.findViewById(R.id.custom_yes);
        dateDialog = builder.create();
        dateDialog.setView(inflater.inflate(R.layout.customied_dialog_style, null));
        dateDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dateDialog.show();
        dateDialog.getWindow().setContentView(v);
        dateDialog.getWindow().setGravity(Gravity.BOTTOM);
        custom_input.setText(getString(R.string.TMT_setting_content_server_date) + DateUtil.backTimeFomat(new Date()));
        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__REQUEST_SERVER_TIME_DATE, new DatetimeModel(DateUtil.backTimeFomat(new Date()))));
                DatetimeModel datetimeModel = new DatetimeModel(DateUtil.backTimeFomat(new Date()));
                if (TtPhoneDataManager.getInstance() != null) {
                    TtPhoneDataManager.getInstance().setDateAndTime(datetimeModel);
                }

                NToast.shortToast(SettingsActivity.this, getResources().getString(R.string.TMT_date_sync_succeed));
                dateDialog.dismiss();
            }
        });
    }

    private void initSystemResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
        View v = inflater.inflate(R.layout.dialog_datetime_settings, null);
        final TextView custom_input = (TextView) v.findViewById(R.id.custom_input);
        final TextView custom_cannel = (TextView) v.findViewById(R.id.custom_cannel);
        final TextView custom_yes = (TextView) v.findViewById(R.id.custom_yes);
        final TextView title = (TextView) v.findViewById(R.id.title);
        dateDialog = builder.create();
        dateDialog.setView(inflater.inflate(R.layout.customied_dialog_style, null));
        dateDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dateDialog.show();
        dateDialog.getWindow().setContentView(v);
        dateDialog.getWindow().setGravity(Gravity.BOTTOM);
        title.setText(getString(R.string.TMT_DEVICE_SYSTEM_RECOVER_SETTING));
        custom_input.setText(getString(R.string.TMT_ALERT_OF_DIALOG_CONTENT));
        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TtPhoneDataManager.getInstance() != null) {
                    TtPhoneDataManager.getInstance().setResetFactorySettings();
                }
                mPresenter.recoverSystem(SettingsActivity.this);
                NToast.shortToast(SettingsActivity.this, getString(R.string.TMT_DEVICE_IS_RECOVER_SUCCEED));
                dateDialog.dismiss();
            }
        });
    }

    /**
     * 设置edittext  当软键盘弹出时自动向上弹 避免内容被覆盖。
     *
     * @param editText
     */
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.release();
        mPresenter = null;
    }
}
