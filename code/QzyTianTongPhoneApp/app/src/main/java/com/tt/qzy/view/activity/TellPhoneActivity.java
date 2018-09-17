package com.tt.qzy.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;


import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;

import com.qzy.utils.TimeToolUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.presenter.TellPhoneActivityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TellPhoneActivity extends AppCompatActivity {

    public static final int msg_calling_time = 1;
    public static final int msg_calling_time_remove = 2;

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @BindView(R.id.text_state)
    TextView text_state;

    private TellPhoneActivityPresenter mTellPhoneActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_phone);
        ButterKnife.bind(this);
        String number = getIntent().getStringExtra("diapadNumber");
        phoneNumber.setText(number);
        if (!TextUtils.isEmpty(number) && number.length() >= 3) {

        }
        mTellPhoneActivityPresenter = new TellPhoneActivityPresenter(this);
        EventBusUtils.register(this);
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
     * 刷新通话时长
     */
    private void showCallingTime(int time) {
        text_state.setText(TimeToolUtils.getFormatHMS(time));
    }

    /**
     * 展示拨打中
     */
    private void showDailing() {
        text_state.setText(getString(R.string.TMT_dial_dialing));
    }

    private Handler mHandler = new Handler() {


        private int time = 0;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_calling_time:
                    time += 1000;
                    showCallingTime(time);
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    break;
                case msg_calling_time_remove:
                    if (mHandler.hasMessages(1)) {
                        mHandler.removeMessages(1);
                    }
                    time = 0;
                    showDailing();

                    break;
            }
        }
    };


    /**
     * 通话状态
     */
    private void onCallingState() {
        mHandler.sendEmptyMessage(msg_calling_time);
    }

    /**
     * 挂断状态
     */
    private void onEndCallState() {
        mHandler.sendEmptyMessage(msg_calling_time_remove);
    }

    /**
     * 更新天通电话状态
     *
     * @param cmd
     */
    private void updatePhoneState(PhoneCmd cmd) {
        KLog.i("phone state = " + mTellPhoneActivityPresenter.getTtPhoneState(cmd).ordinal());
        switch (mTellPhoneActivityPresenter.getTtPhoneState(cmd)) {
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
