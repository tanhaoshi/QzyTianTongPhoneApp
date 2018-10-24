package com.tt.qzy.view.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.phone.common.CommonData;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.layout.BatteryView;
import com.tt.qzy.view.presenter.activity.BaseActivityPresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.BaseMainView;
import com.tt.qzy.view.view.base.BaseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<M extends BaseView> extends AppCompatActivity implements BaseMainView{

    public BatteryView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;
    public ImageView img5;
    public RelativeLayout statusLayout;

    private Unbinder m;
    private BaseActivityPresenter mPresenter;

    public boolean tt_status = false;
    public boolean tt_beidou_status = false;
    public boolean tt_call_status = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new BaseActivityPresenter(this);
        mPresenter.onBindView(this);
        initStatusBar();
        initView();
        initData();
    }

    private void initStatusBar(){
        img1 = (BatteryView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        statusLayout = (RelativeLayout) findViewById(R.id.statusLayout);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        m = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        m.unbind();
    }

    public abstract int getContentView();

    public abstract void initView();

    public abstract void initData();

    @Subscribe(threadMode = ThreadMode.MAIN)
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
        }
    }

    /**
     * 连接天通猫成功
     */
    private void connectTianTongSuccess(){
        tt_status = true;
        SPUtils.putShare(BaseActivity.this, Constans.TTM_STATUS,tt_status);
        img5.setImageDrawable(getResources().getDrawable(R.drawable.search_nerwork));
        img4.setImageDrawable(getResources().getDrawable(R.drawable.phone_network));
    }

    /**
     * 连接天通失败
     */
    private void connectTianTongFailed(){
        tt_status = false;
        SPUtils.putShare(BaseActivity.this, Constans.TTM_STATUS,tt_status);
        img5.setImageDrawable(getResources().getDrawable(R.drawable.search_nonerwork));
        img4.setImageDrawable(getResources().getDrawable(R.drawable.phone_nonetwork));
    }

    /**
     * 天通猫有无插入sim卡
     */
    private void getTianTongSimcardStatsu(boolean status){
        if(status){
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        }else{
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_noconnect));
        }
    }

    /**
     * 天通猫是否连接上北斗卫星
     */
    private void getTianTongConnectBeiDou(boolean isConnect){
        tt_beidou_status = isConnect;
        if(isConnect){
            img3.setImageDrawable(getResources().getDrawable(R.drawable.satellite_connect));
        }else{
            img3.setImageDrawable(getResources().getDrawable(R.drawable.satellite_noconnect));
        }
    }

    private void onBatteryInfoReceiver(int intLevel, int intScale) {
        int percent = intLevel * 100 / intScale;
        img1.setPower(percent);
    }

    /**
     * 更新天通猫信号强度
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
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        } else if (intLevel == 98) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        } else if (intLevel == 99) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        } else if (intLevel >= 0 && intLevel <= 1) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_one));
        }else if (intLevel >= 2 && intLevel <= 4) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_two));
        }else if (intLevel >= 5 && intLevel <= 7) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_three));
        }else if (intLevel >= 8) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_four));
        }
    }

    public void onTianTongCallStatus(Object o){
        PhoneCmd cmd = (PhoneCmd) o;
        CallPhoneBackProtos.CallPhoneBack callPhoneBack = (CallPhoneBackProtos.CallPhoneBack)cmd.getMessage();
        KLog.i("tt_call_status is = " + callPhoneBack.getIsCalling());
        if(callPhoneBack.getIsCalling()){
            if(callPhoneBack.getIp().equals(CommonData.getInstance().getLocalWifiIp())){
                tt_call_status = true;
                EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE));

            }else{
                tt_call_status = true;
               /// NToast.shortToast(this,"当前天通猫被占用");
            }
        }else{
            tt_call_status = false;
            //EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE));
        }
    }

    public boolean isConnectStatus(){
        return tt_status;
    }

    public boolean isConnectBeiDou(){
        return tt_beidou_status;
    }

    public boolean isCallStatus(){
        return tt_call_status;
    }

}
