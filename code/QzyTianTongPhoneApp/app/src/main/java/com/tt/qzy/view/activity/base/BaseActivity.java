package com.tt.qzy.view.activity.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.TimerSendProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSimCards;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateBackListener;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.layout.BatteryView;
import com.tt.qzy.view.presenter.activity.BaseActivityPresenter;
import com.tt.qzy.view.service.TimerService;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.BaseMainView;
import com.tt.qzy.view.view.base.BaseView;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<M extends BaseView> extends AppCompatActivity implements BaseMainView {

    public BatteryView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;
    public ImageView img5;
    public RelativeLayout statusLayout;
    public TextView percentBaterly;

    private Unbinder m;
    private BaseActivityPresenter mPresenter;

    // 记录是否初始化所有操作
    public boolean isInitView = false;

    public boolean tt_status = false;
    public boolean tt_beidou_status = false;
    public boolean tt_call_status = false;
    public boolean tt_isSim = false;
    public boolean tt_isSignal = false;
    public int tt_baterly = 0;

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        TtPhoneApplication.getInstance().addActivity(this);
        mPresenter = new BaseActivityPresenter(this);
        mPresenter.onBindView(this);
        if (!isInitView) {
            initStatusBar();
            initView();
            initData();
        }

        // 设置电话设备占用回调
        setTtPhoneCallStateBackListener();
    }

    private void initStatusBar() {
        img1 = (BatteryView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        statusLayout = (RelativeLayout) findViewById(R.id.statusLayout);
        percentBaterly = (TextView) findViewById(R.id.percent);
        percentBaterly.setText("0%");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        m = ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        m.unbind();
        TtPhoneApplication.getInstance().removeActivity(this);
    }

    /**
     * 设置数据回调
     */
    public void setDataListener() {
        mPresenter.setTtPhoneDataListener();
    }

    public abstract int getContentView();

    public abstract void initView();

    public abstract void initData();

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SUCCESS:
                connectTianTongSuccess();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_FAILED:
                connectTianTongFailed();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SIGNAL:
                onTiantongInfoReceiver(mPresenter.getTianTongSignalValue(event.getObject()));
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SEND_BATTERY:
                onBatteryInfoReceiver(mPresenter.getBatteryLevel(event.getObject()), mPresenter.getBatteryScal(event.getObject()));
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SIM_CARD:
                getTianTongSimcardStatsu(mPresenter.getTianTongSimStatus(event.getObject()));
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_BEIDOU:
                getTianTongConnectBeiDou(mPresenter.getTianTongConnectBeiDou(event.getObject()));
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_STATE:
                onTianTongCallStatus(event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_NONCONNECT:
                recoverView();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_TIMER_MESSAGE:
                break;
        }
    }*/

    /**
     * 设置电话设备占用回调
     */
    private void setTtPhoneCallStateBackListener() {
        TtPhoneDataManager.getInstance().setITtPhoneCallStateBackListener("BaseActivity", new ITtPhoneCallStateBackListener() {
            @Override
            public void onPhoneCallStateBack(PhoneCmd phoneCmd) {
                onTianTongCallStatus(phoneCmd);
            }
        });
    }


    /**
     * 解析定时发送
     */
    private void responseTimerSend(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        TimerSendProtos.TimerSend timerSend = (TimerSendProtos.TimerSend) cmd.getMessage();
        TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery = timerSend.getBatterValue();
        TtPhoneSignalProtos.PhoneSignalStrength phoneSignalStrength = timerSend.getSigalStrength();
        TtPhonePositionProtos.TtPhonePosition ttPhonePosition = timerSend.getTtPhoneGpsPosition();
        TtPhoneSimCards.TtPhoneSimCard ttPhoneSimCard = timerSend.getTtPhoneSimcard();
        onTiantongInfoReceiver(phoneSignalStrength.getSignalStrength());
        onBatteryInfoReceiver(ttPhoneBattery.getLevel(), ttPhoneBattery.getScale());

    }

    /**
     * 主动断开时将view进行改变
     */
    private void recoverView() {
        img1.setPower(0);
        img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_noconnect));
        img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        img5.setImageDrawable(getResources().getDrawable(R.drawable.search_nonetwork));
        percentBaterly.setText(0 + "%");
        stopTimerService();
    }

    private void stopTimerService() {
        if (AppUtils.isServiceRunning("com.tt.qzy.view.service.TimerService", BaseActivity.this)) {
            //服务存活
            Intent intent = new Intent(BaseActivity.this, TimerService.class);
            stopService(intent);
        }
    }

    /**
     * 连接设备成功
     */
    public void connectTianTongControl(boolean connectState) {
        tt_status = connectState;
        if (tt_status) {
            SPUtils.putShare(BaseActivity.this, Constans.TTM_STATUS, tt_status);
            img5.setImageDrawable(getResources().getDrawable(R.drawable.search_network));
        } else {
            SPUtils.putShare(BaseActivity.this, Constans.TTM_STATUS, tt_status);
            img5.setImageDrawable(getResources().getDrawable(R.drawable.search_nonetwork));
            recoverView();
        }
    }

    /**
     * 设备有无插入sim卡
     */
    private void getTianTongSimcardStatsu(boolean status) {
        if (status) {
            tt_isSim = true;
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        } else {
            tt_isSim = false;
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_noconnect));
            stopTimerService();
        }
    }

    /**
     * 设备是否连接上北斗卫星
     */
    private void getTianTongConnectBeiDou(boolean isConnect) {
        tt_beidou_status = isConnect;
        if (isConnect) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.satellite_connect));
        } else {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.satellite_noconnect));
        }
    }

    private void onBatteryInfoReceiver(int intLevel, int intScale) {
        int percent = intLevel * 100 / intScale;
        img1.setPower(percent);
        percentBaterly.setText(percent + "%");
        tt_baterly = percent;
    }

    /**
     * 更新设备信号强度
     * AP 信号格数（共 4 档）和 rssi 对应关系建议方案：
     * RSSI  格数  dBm
     * 33     3    -95
     * ≥8    3    ≥-120
     * 5-7    2    -123~-121
     * 2-4    1    -126~-124
     * 0-1    0    -128~-127
     * 97 限制服务 -141，格数建议显示特殊符号或汉字，如显示不了显示 0 格
     * 98 告警服务 -140，格数建议显示感叹号或汉字，如显示不了显示 0 格
     * 99  无服务  -142，格数建议显示特殊符号或汉字，如显示不了显示 0 格
     *
     * @param intLevel
     */
    private void onTiantongInfoReceiver(int intLevel) {
        if (intLevel == 97) {
            tt_isSignal = false;
            stopTimerService();
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        } else if (intLevel == 98) {
            tt_isSignal = false;
            stopTimerService();
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        } else if (intLevel == 99) {
            tt_isSignal = false;
            stopTimerService();
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        } else if (intLevel >= 0 && intLevel <= 1) {
            tt_isSignal = true;
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_one));
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        } else if (intLevel >= 2 && intLevel <= 4) {
            tt_isSignal = true;
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_two));
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        } else if (intLevel >= 5 && intLevel <= 7) {
            tt_isSignal = true;
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_three));
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        } else if (intLevel >= 8) {
            tt_isSignal = true;
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_four));
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        }
    }

    public void onTianTongCallStatus(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        CallPhoneBackProtos.CallPhoneBack callPhoneBack = (CallPhoneBackProtos.CallPhoneBack) cmd.getMessage();
        if (callPhoneBack.getIsCalling()) {
            if (callPhoneBack.getIp().equals(CommonData.getInstance().getLocalWifiIp())) {
                tt_call_status = true;
                // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE));
                Intent intent = new Intent("com.qzy.tt.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE");
                sendBroadcast(intent);
            } else {
                tt_call_status = true;
            }
        } else {
            tt_call_status = false;
        }
    }

    public boolean isConnectStatus() {
        return tt_status;
    }

    public boolean isConnectBeiDou() {
        return tt_beidou_status;
    }

    public boolean isCallStatus() {
        return tt_call_status;
    }

    @Override
    public void isTtPhoneBattery(int level, int scal) {
        onBatteryInfoReceiver(level, scal);
    }

    @Override
    public void isTtSimCard(boolean isIn) {
        getTianTongSimcardStatsu(isIn);
    }

    @Override
    public void isTtSignalStrength(int signalLevel) {
        onTiantongInfoReceiver(signalLevel);
    }

}
