package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.qzy.data.PhoneCmd;

import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.SmsBean;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.ISendShortMessage;
import com.qzy.utils.LogUtils;
import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.MsgAdapter;
import com.tt.qzy.view.bean.MsgModel;
import com.tt.qzy.view.bean.SMAgrementModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.manager.SyncManager;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendShortMessageActivity extends AppCompatActivity implements ISendShortMessage {

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

    private List<MsgModel> msgList = new ArrayList<>();
    private MsgAdapter adapter;
    private static final int REQUEST_CODE = 99;

    private String phone = "";
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_short_message);
        ButterKnife.bind(this);
        // EventBusUtils.register(this);
        initView();
        initAdapter();
        initMsgs();

        setShortMsgSyncListener();
        setSendShortMsgStateListener();
    }

    private void initView() {
        //sms_main_quantity.setText(getResources().getString(R.string.TMT_short_message));
        mImageView.setVisibility(View.VISIBLE);
        sms_et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initMsgs() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mImageView.setVisibility(View.GONE);
            List<MailListDao> mailListDaos = MailListManager.getInstance(SendShortMessageActivity.this).
                    getByPhoneList(intent.getStringExtra(Constans.SHORT_MESSAGE_PHONE));

            if (mailListDaos.size() > 0) {
                sms_et_name.setText(mailListDaos.get(0).getName());
                sms_et_name.setFocusable(false);
                name = mailListDaos.get(0).getName();
                phone = intent.getStringExtra(Constans.SHORT_MESSAGE_PHONE);
            } else {
                sms_et_name.setText(intent.getStringExtra(Constans.SHORT_MESSAGE_PHONE));
                sms_et_name.setFocusable(false);
                phone = intent.getStringExtra(Constans.SHORT_MESSAGE_PHONE);
            }

            List<ShortMessageDao> daoList =
                    ShortMessageManager.getInstance(this).queryShortMessageCondition(intent.getStringExtra(Constans.SHORT_MESSAGE_PHONE));

            LogUtils.i("look short message = " + JSON.toJSONString(daoList));

            for (ShortMessageDao shortMessageDao : daoList) {

                MsgModel msgModel = new MsgModel(shortMessageDao.getMessage(), Integer.valueOf(shortMessageDao.getState()));

                msgList.add(msgModel);

                if (!shortMessageDao.getIsStatus()) {
                    disposeChangeRead(shortMessageDao);
                }
            }

            adapter.setData(msgList);

//            if(daoList.size() == 0){
//                return;
//            }else{
//                EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.
//                        EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SHORT_MESSAGE,new
//                        SMAgrementModel(daoList.get(daoList.size() - 1).getServerId())));
//
//                ShortMessageDao shortMessageDao = daoList.get(daoList.size() -1);
//
//                shortMessageDao.setIsStatus(true);
//
//                ShortMessageManager.getInstance(SendShortMessageActivity.this).updateShortMessageName(shortMessageDao);
//
//                updateMainAlert();
//            }
        }
    }

    private void disposeChangeRead(ShortMessageDao shortMessageDao) {
        // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_SHORT_MESSAGE,new SMAgrementModel(shortMessageDao.getServerId())));

        SMAgrementModel smAgrementModel = new SMAgrementModel(shortMessageDao.getServerId());
        TtPhoneDataManager.getInstance().requestServerShortMessageStatus(smAgrementModel);
        shortMessageDao.setIsStatus(true);

        ShortMessageManager.getInstance(SendShortMessageActivity.this).updateShortMessageName(shortMessageDao);

        updateMainAlert();

    }

    private void updateMainAlert() {
        Integer integer = (Integer) SPUtils.getShare(this, Constans.SHORTMESSAGE_ISREAD, 0);

        if (integer > 0) {
            integer--;
        } else {
            return;
        }

        SPUtils.putShare(this, Constans.SHORTMESSAGE_ISREAD, integer);

        // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_LOCAL_SHORT_MESSAGE_HISTROY,Integer.valueOf(Integer.valueOf(integer))));
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
                if((Boolean)SPUtils.getShare(SendShortMessageActivity.this,Constans.TTM_STATUS,false)){
                    sendMessage(MsgModel.TYPE_RECEIVE);
                }else{
                    NToast.shortToast(SendShortMessageActivity.this,"设备未入网,请先入网!");
                }
                break;
            case R.id.sms_base_tv_toolbar_right:
                Intent intent = new Intent(SendShortMessageActivity.this, SelectContactsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    /**
     * 发送短信
     *
     * @param model
     */
    private void sendMessage(int model) {
        if (!CommonData.getInstance().isConnected()) {
            NToast.shortToast(this, R.string.TMT_connect_tiantong_please);
            return;
        }

        String receive = phone;

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
            //EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS, new SmsBean(receive, content)));
            SmsBean smsBean = new SmsBean(receive, content);
            TtPhoneDataManager.getInstance().sendSmsTtPhone(smsBean);
        }

        ShortMessageDao shortMessageDao = new ShortMessageDao(phone, content,
                DateUtil.backTimeFomat(new Date()), 0, "", String.valueOf(MsgModel.TYPE_RECEIVE), name, true);

        ShortMessageManager.getInstance(SendShortMessageActivity.this).insertShortMessage(shortMessageDao, SendShortMessageActivity.this);
    }
/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_SMS_STATE:
                parseSmsState(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SHORT_MESSAGE:
                parseSmsreciver(event.getObject());
                break;
        }
    }*/

    /**
     * 设置短信同步接口回调
     */
    private void setShortMsgSyncListener() {
        TtPhoneDataManager.getInstance().setISyncMsgDataListener("SendShortMessageActivity",new SyncManager.ISyncMsgDataListener() {
            @Override
            public void onShorMsgSignalSyncFinish(PhoneCmd phoneCmd) {
                parseSmsreciver(phoneCmd);
            }
        });
    }


    /**
     * 发送短信状态回调
     */
    private void setSendShortMsgStateListener() {
        TtPhoneDataManager.getInstance().setISendShortMessage(this);
    }

    /**
     * 解析短信状态
     *
     * @param object
     */
    private void parseSmsState(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = (TtPhoneSmsProtos.TtPhoneSms) cmd.getMessage();
        if(CommonData.getInstance().getLocalWifiIp() != null){
            if(CommonData.getInstance().getLocalWifiIp().length() > 0){
                if(ttPhoneSms.getIp() != null){
                    if(CommonData.getInstance().getLocalWifiIp().equals(ttPhoneSms.getIp())){
                        if(ttPhoneSms.getIsSendSuccess()){
                            NToast.shortToast(this, R.string.TMT_sendMessage_success);
                        }else{
                            NToast.shortToast(this, R.string.TMT_sendMessage_failed);
                        }
                    }
                }
            }
        }
    }

    /**
     * 解析收到短息
     *
     * @param object
     */
    private void parseSmsreciver(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage =
                (TtShortMessageProtos.TtShortMessage.ShortMessage) cmd.getMessage();
        MsgModel msgModel = new MsgModel(shortMessage.getMessage(), shortMessage.getType());
        msgList.add(msgList.size(), msgModel);
        adapter.setData(msgList);
        if (msgList.size() - 1 != 0) {
            mRecyclerView.scrollToPosition(msgList.size() - 1);
            LinearLayoutManager mLayoutManager =
                    (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mLayoutManager.scrollToPositionWithOffset(msgList.size() - 1, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 1) {
            String s = data.getStringExtra("back");
            this.phone = data.getStringExtra("phone");
            sms_et_name.setText(s);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // EventBusUtils.unregister(this);
    }

    @Override
    public void isSendShotMessageStatus(Object o) {
        parseSmsState(o);
    }
}
