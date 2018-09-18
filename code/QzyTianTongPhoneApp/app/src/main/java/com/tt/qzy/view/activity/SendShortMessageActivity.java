package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.SmsBean;
import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.MsgAdapter;
import com.tt.qzy.view.bean.MsgModel;
import com.tt.qzy.view.utils.NToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendShortMessageActivity extends AppCompatActivity {

    @BindView(R.id.sms_base_tv_toolbar_right)
    ImageView mImageView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.sms_main_quantity)
    TextView sms_main_quantity;
    @BindView(R.id.custom_input)
    EditText custom_input;

    @BindView(R.id.sms_et_name)
    EditText sms_et_name;

    private static List<MsgModel> msgList = new ArrayList<>();
    private MsgAdapter adapter;
    private static final int REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_short_message);
        ButterKnife.bind(this);
        EventBusUtils.register(this);
        initView();
        // initMsgs();
        initAdapter();
    }

    private void initView() {
        //sms_main_quantity.setText(getResources().getString(R.string.TMT_short_message));
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
    }

    private void initMsgs() {
        MsgModel msg1 = new MsgModel("Hello guy", MsgModel.TYPE_RECEIVED);
        MsgModel msg2 = new MsgModel("Hello.Who is that?", MsgModel.TYPE_SENT);
        MsgModel msg3 = new MsgModel("I am Jack.Nice talking to you.", MsgModel.TYPE_RECEIVED);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MsgAdapter(msgList);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.sms_main_quantity, R.id.send, R.id.sms_base_tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sms_main_quantity:
                finish();
                break;
            case R.id.send:
                sendMessage(MsgModel.TYPE_SENT);
                break;
            case R.id.sms_base_tv_toolbar_right:
                //Intent intent = new Intent(SendShortMessageActivity.this,SelectContactsActivity.class);
                // startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }

    /**
     * 发送短信
     *
     * @param model
     */
    private void sendMessage(int model) {
        if(!CommonData.getInstance().isConnected()){
            NToast.shortToast(this, R.string.TMT_connect_tiantong_please);
            return;
        }

        String receive = sms_et_name.getText().toString().trim();
        if (TextUtils.isEmpty(receive)) {
            NToast.shortToast(this, R.string.TMT_short_message_receiver_notnull);
            return;
        }

        String content = custom_input.getText().toString();
        if (!"".equals(content)) {
            MsgModel msg = new MsgModel(content, model);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size() - 1);
            mRecyclerView.scrollToPosition(msgList.size() - 1);
            custom_input.setText("");
            model = model == MsgModel.TYPE_RECEIVED ? MsgModel.TYPE_SENT : MsgModel.TYPE_RECEIVED;
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS, new SmsBean(receive, content)));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS_STATE:
                parseSmsState(event.getObject());
                break;
        }
    }

    /**
     * 解析短信状态
     *
     * @param object
     */
    private void parseSmsState(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = (TtPhoneSmsProtos.TtPhoneSms) cmd.getMessage();
        if (ttPhoneSms.getIsSendSuccess()) {
            NToast.shortToast(this, R.string.TMT_sendMessage_success);
        }else{
            NToast.shortToast(this, R.string.TMT_sendMessage_failed);
        }

        if (ttPhoneSms.getIsReceiverSuccess()) {
            NToast.shortToast(this, R.string.TMT_sendMessage_receiver);
        }else{
            NToast.shortToast(this, R.string.TMT_sendMessage_receiver_failed);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 1) {
            String s = data.getStringExtra("back");
            sms_et_name.setText(s);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }
}
