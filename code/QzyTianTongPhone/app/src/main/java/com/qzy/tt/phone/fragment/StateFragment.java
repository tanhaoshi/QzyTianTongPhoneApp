package com.qzy.tt.phone.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.MessageEvent;
import com.qzy.eventbus.NettyStateModel;
import com.qzy.intercom.util.IPUtil;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.phone.R;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.sws.WebRtcTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StateFragment extends Fragment {
    @BindView(R.id.txt_server_state)
    TextView txt_server_state;

    @BindView(R.id.btn_recorder)
    Button btn_recorder;

    @BindView(R.id.txt_tt_server_state)
    TextView txt_tt_server_state;

    @BindView(R.id.txt_ip)
    TextView txt_ip;

    @BindView(R.id.et_db_value)
    EditText et_db_value;

    public StateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    private void initView() {
        txt_ip.setText("本机ip:" + CommonData.localWifiIp);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event instanceof NettyStateModel) {
            NettyStateModel nettyStateModel = (NettyStateModel) event;
            CommonData.isConnected = nettyStateModel.isConnected();
            if (CommonData.isConnected) {
                txt_server_state.setText("手机-天通猫连接状态：已连接");
            } else {
                txt_server_state.setText("手机-天通猫连接状态：未连接");
            }
        } else if (event instanceof CommandModel) {
            CommandModel commandModel = (CommandModel) event;
            if (commandModel.getMsgTag().equals(StateFragment.class.getSimpleName())) {
                return;
            }
            CommonData.isStartRecorder = commandModel.isStartRecorder();
            if (CommonData.isStartRecorder) {
                btn_recorder.setText("停止录音");
            } else {
                btn_recorder.setText("开始录音");
            }
        }

    }

    @OnClick({R.id.btn_recorder, R.id.btn_set_db})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_recorder:
                setRecorderState(!CommonData.isStartRecorder);
                break;
            case R.id.btn_set_db:
                String text = et_db_value.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    //WebRtcTools.dbValue = Integer.parseInt(text);
                    setDb(Integer.parseInt(text));
                }
                break;
        }
    }

    /**
     * 设置增益值
     *
     * @param dbvalue
     */
    private void setDb(int dbvalue) {
        ChangePcmPlayerDbProtos.ChangePcmPlayerDb db = ChangePcmPlayerDbProtos.ChangePcmPlayerDb.newBuilder()
                .setDb(dbvalue)
                .build();
        EventBus.getDefault().post(new PhoneCmd(PrototocalTools.IProtoServerIndex.chang_pcmplayer_db, db));

    }


    /**
     * 开启录音方法
     *
     * @param state
     */
    private void setRecorderState(boolean state) {
        CommandModel model = new CommandModel(StateFragment.class.getSimpleName());
        model.setStartRecorder(state);
        EventBus.getDefault().post(model);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
