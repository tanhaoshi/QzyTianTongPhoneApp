package com.qzy.tt.phone.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.MessageEvent;
import com.qzy.eventbus.NettyStateModel;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.phone.R;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.common.IFragmentChange;
import com.qzy.tt.phone.eventbus.CallingModel;
import com.qzy.tt.phone.eventbus.CommandClientModel;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.qzy.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallingFragment extends Fragment {

    @BindView(R.id.txtv_phone_number)
    TextView txtv_phone_number;

    @BindView(R.id.txtv_phone_state)
    TextView txtv_phone_state;

    @BindView(R.id.btn_hangup)
    ImageView btn_hangup;

    private IFragmentChange fragmentChange;

    public CallingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        view.setClickable(true);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        fragmentChange = (IFragmentChange) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (event instanceof CallingModel) {
            CallingModel callingModel = (CallingModel) event;
            txtv_phone_number.setText(callingModel.getPhone_number());
        } else if (event instanceof CommandClientModel) {
            CommandClientModel commandModel = (CommandClientModel) event;
            handlerCommadView(commandModel);
        }

    }

    /**
     * 处理服务端过来的命令
     *
     * @param commandModel
     */
    private void handlerCommadView(CommandClientModel commandModel) {

        PhoneCmd cmd = commandModel.getCmd();
        switch (cmd.getProtoId()) {
            case PrototocalTools.IProtoClientIndex.call_phone_state:
                updatePhoneState(cmd);
                break;
        }
    }


    /**
     * 更新天通电话状态
     *
     * @param cmd
     */
    private void updatePhoneState(PhoneCmd cmd) {
        CallPhoneStateProtos.CallPhoneState callPhoneState = (CallPhoneStateProtos.CallPhoneState) cmd.getMessage();
        LogUtils.e("call Phone State = " + callPhoneState.getPhoneStateValue());
        if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL) {
            fragmentChange.hideCallingView();
            setRecorderState(false);
        } else if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL) {
            txtv_phone_state.setText("通话中...");
            setRecorderState(true);
        } else if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.RING) {
            txtv_phone_state.setText("接通中...");
        } else if (callPhoneState.getPhoneState() == CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP) {
            fragmentChange.hideCallingView();
            setRecorderState(false);
        }
    }

    /**
     * 挂断电话接口
     */
    private void hangupCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.localWifiIp)
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.HUANGUP)
                .build();
        EventBus.getDefault().post(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
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


    @OnClick({R.id.btn_hangup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hangup:
                hangupCall();
                // fragmentChange.hideCallingView();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
