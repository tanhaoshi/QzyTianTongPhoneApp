package com.qzy.tt.phone.data;

import com.qzy.tt.phone.data.impl.IAllTtPhoneDataListener;
import com.qzy.tt.phone.data.impl.IMainFragment;
import com.qzy.tt.phone.data.impl.ITtPhoneDataListener;
import com.qzy.tt.phone.data.impl.ITtPhoneHandlerManager;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.qzy.utils.AndroidVoiceManager;
import com.socks.library.KLog;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.WifiSettingModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 数据管理类
 */
public class TtPhoneDataManager implements ITtPhoneHandlerManager {

    private static TtPhoneDataManager instance;

    private PhoneNettyManager phoneNettyManager;

    private IMainFragment iMainFragment;

    private ITtPhoneDataListener iTtPhoneDataListener;

    public static TtPhoneDataManager getInstance() {
        return instance;
    }

    /**
     * 初始化
     */
    public static void init(PhoneNettyManager manager) {
        KLog.i("是否初始化!");
        instance = new TtPhoneDataManager(manager);
    }

    private TtPhoneDataManager(PhoneNettyManager manager) {
        phoneNettyManager = manager;
        setAllDataListener();
    }

    /** 所有数据监听 */
    private void setAllDataListener() {
        phoneNettyManager.getmCmdHandler().setmAllDataListener(new IAllTtPhoneDataListener() {
            @Override
            public void isTtServerConnected(final boolean connected) {
                Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                        observableEmitter.onNext(connected);
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if(iMainFragment != null){
                                    iMainFragment.isTtServerConnected(aBoolean);
                                }

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }

            @Override
            public void isTtSignalStrength(int signalLevel) {
                if(iTtPhoneDataListener != null){
                    iTtPhoneDataListener.isTtSignalStrength(signalLevel);
                }

            }

            @Override
            public void isTtSimCard(boolean isIn) {
                if(iTtPhoneDataListener != null){
                    iTtPhoneDataListener.isTtSimCard(isIn);
                }
            }

            @Override
            public void isTtPhoneBattery(int level, int scal) {
                if(iTtPhoneDataListener != null){
                    iTtPhoneDataListener.isTtPhoneBattery(level,scal);
                }
            }
        });
    }


    /**
     * 注册数据回调
     *
     * @param iTtPhoneDataListener
     */
    public void setTtPhoneDataListener(ITtPhoneDataListener iTtPhoneDataListener) {
        this.iTtPhoneDataListener = iTtPhoneDataListener;
    }

    /**
     * 注册MainFragment数据回调接口就
     */
    public void setMainFragmentListener(IMainFragment iMainFragment) {
        this.iMainFragment = iMainFragment;
    }


    public void free() {
        instance = null;
    }


    @Override
    public void connectTtPhoneServer(String ip, int port) {
        phoneNettyManager.connect(port, ip);
    }

    @Override
    public void openTtPhoneSos() {

    }

    @Override
    public void closeTtPhoneSos() {

    }

    @Override
    public void openTtPhoneGps() {

    }

    @Override
    public void closeTtPhoneGps() {

    }

    @Override
    public void setTtPhoneSosValue(String phoneNumber, String text, SosSendMessageModel sosSendMessageModel) {

    }

    @Override
    public void setWifiPasswd(WifiSettingModel wifiSettingModel) {

    }

    @Override
    public void setDateAndTime(DatetimeModel datetimeModel) {

    }

    @Override
    public void setResetFactorySettings() {

    }

    @Override
    public void openUsbMode() {

    }

    @Override
    public void closeUsbMode() {

    }

    @Override
    public void dialTtPhone() {

    }

    @Override
    public void hangupTtPhone() {

    }

    @Override
    public void answerTtPhone() {

    }

}
