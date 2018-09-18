package com.tt.qzy.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PhoneStateUtils;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.utils.TimeToolUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.layout.dialpad.InputPwdViewCall;
import com.tt.qzy.view.presenter.TellPhoneActivityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TellPhoneIncomingActivity extends AppCompatActivity {


    @BindView(R.id.txtv_phoneNumber)
    TextView txtv_phoneNumber;

    @BindView(R.id.btn_accept)
    ImageView btn_accept;

    @BindView(R.id.btn_endcall)
    ImageView btn_endcall;

    private String phoneNumber = "";

    private TellPhoneActivityPresenter mTellPhoneActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_phone_incoming);
        ButterKnife.bind(this);
        phoneNumber= getIntent().getStringExtra("diapadNumber");
        txtv_phoneNumber.setText(phoneNumber);
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() >= 3) {

        }
        mTellPhoneActivityPresenter = new TellPhoneActivityPresenter(this);
        EventBusUtils.register(this);
    }


    @OnClick({R.id.btn_accept, R.id.btn_endcall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                onCallingState();
                break;
            case R.id.btn_endcall:
                onEndCallState();
                break;
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE:
                updatePhoneState((PhoneCmd) event.getObject());
                break;
        }
    }

    /**
     * 通话状态
     */
    private void onCallingState() {
        Intent intent = new Intent(TellPhoneIncomingActivity.this, TellPhoneActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("diapadNumber", phoneNumber);
        startActivity(intent);

        mTellPhoneActivityPresenter.acceptCall();
        finish();
    }

    /**
     * 挂断状态
     */
    private void onEndCallState() {
        mTellPhoneActivityPresenter.endCall();
        finish();
    }

    /**
     * 更新天通电话状态
     *
     * @param cmd
     */
    private void updatePhoneState(PhoneCmd cmd) {
        KLog.i("phone state = " + PhoneStateUtils.getTtPhoneState(cmd).ordinal());
        switch (PhoneStateUtils.getTtPhoneState(cmd)) {
            case NOCALL:
                onEndCallState();
                break;
            case RING:
                break;
            case CALL:
                onCallingState();
                break;
            case HUANGUP:
                onEndCallState();
                break;
            case INCOMING:
                break;
            case UNRECOGNIZED:
                onEndCallState();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }
}
