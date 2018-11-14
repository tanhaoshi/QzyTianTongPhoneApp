package com.tt.qzy.view.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SosSettingsActivity extends BaseActivity {

    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.cryhelp_phone)
    EditText cryhelp_phone;
    @BindView(R.id.cryhelp_message)
    EditText cryhelp_message;
    @BindView(R.id.cryhelp_timer)
    EditText cryhelp_timer;

    @Override
    public int getContentView() {
        return R.layout.activity_sos_settings;
    }

    @Override
    public void initView() {
        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
        base_tv_toolbar_title.setText(getString(R.string.TMT_sos_setting));
        base_iv_back.setImageDrawable(getResources().getDrawable(R.drawable.iv_back));
        base_tv_toolbar_right.setVisibility(View.GONE);
    }

    @OnClick({R.id.base_iv_back,R.id.btn_yes})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.btn_yes:
                saveCryhelpInfo();
                break;
        }
    }

    private void saveCryhelpInfo(){
        if(!TextUtils.isEmpty(cryhelp_phone.getText().toString()) && cryhelp_phone.getText().toString().length()>0){
            SPUtils.putShare(SosSettingsActivity.this, Constans.CRY_HELP_PHONE,cryhelp_phone.getText().toString());
        }else{
            NToast.shortToast(SosSettingsActivity.this,getString(R.string.TMT_is_empty_null));
            return;
        }

        if(!TextUtils.isEmpty(cryhelp_message.getText().toString()) && cryhelp_message.getText().toString().length()>0){
            SPUtils.putShare(SosSettingsActivity.this,Constans.CRY_HELP_SHORTMESSAGE,cryhelp_message.getText().toString());
        }else{
            SPUtils.putShare(SosSettingsActivity.this,Constans.CRY_HELP_SHORTMESSAGE,getString(R.string.TMT_COME_ON_HELP_ME));
        }

        if(!TextUtils.isEmpty(cryhelp_timer.getText().toString()) && cryhelp_timer.getText().toString().length()>0){
            SPUtils.putShare(SosSettingsActivity.this,Constans.CRY_HELP_TIMETIMER,cryhelp_timer.getText().toString());
        }else{
            String defaultValue = "60";
            SPUtils.putShare(SosSettingsActivity.this,Constans.CRY_HELP_TIMETIMER,defaultValue);
        }

        EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SOS_SENDMESSAGE,new
                SosSendMessageModel(cryhelp_message.getText().toString(),cryhelp_phone.getText().toString(),Integer.valueOf(cryhelp_timer.getText().toString()))));

        NToast.shortToast(SosSettingsActivity.this,getString(R.string.TMT_save_succeed));

        finish();
    }

    @Override
    public void initData() {
         if(SPUtils.containsShare(SosSettingsActivity.this,Constans.CRY_HELP_PHONE)){
             cryhelp_phone.setText(SPUtils.getShare(SosSettingsActivity.this,
                     Constans.CRY_HELP_PHONE,getString(R.string.TMT_please_input_phone)).toString());
         }else{
             cryhelp_phone.setHint(getString(R.string.TMT_please_input_phone));
         }

         if(SPUtils.containsShare(SosSettingsActivity.this,Constans.CRY_HELP_SHORTMESSAGE)){
             cryhelp_message.setText(SPUtils.getShare(SosSettingsActivity.this,
                     Constans.CRY_HELP_SHORTMESSAGE,getString(R.string.TMT_please_input_shortMessage)).toString());
         }else{
             cryhelp_phone.setHint(getString(R.string.TMT_please_input_shortMessage));
         }

         if(SPUtils.containsShare(SosSettingsActivity.this,Constans.CRY_HELP_TIMETIMER)){
             cryhelp_timer.setText(SPUtils.getShare(SosSettingsActivity.this,
                     Constans.CRY_HELP_TIMETIMER,getString(R.string.TMT_please_input_timetimer)).toString());
         }else{
             cryhelp_timer.setHint(getString(R.string.TMT_please_input_timetimer));
         }

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
