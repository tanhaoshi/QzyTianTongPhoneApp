package com.qzy.tt.phone.data;

import android.content.Context;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.phone.data.impl.IAllTtPhoneDataListener;
import com.qzy.tt.phone.data.impl.IMainFragment;
import com.qzy.tt.phone.data.impl.ITtPhoneDataListener;
import com.qzy.tt.phone.data.impl.ITtPhoneHandlerManager;
import com.qzy.tt.phone.data.impl.ITtPhoneManager;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.socks.library.KLog;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.bean.WifiSettingModel;
import com.tt.qzy.view.presenter.manager.SyncManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 数据管理类
 */
public class TtPhoneDataManager implements ITtPhoneHandlerManager, ITtPhoneManager {

    private Context mContext;

    //静态操作类
    private static TtPhoneDataManager instance;

    //netty管理类
    private PhoneNettyManager phoneNettyManager;

    //mainfragment 回调
    private IMainFragment iMainFragment;

    //baseActivityPersenter 回调
    private ITtPhoneDataListener iTtPhoneDataListener;

    //数据同步操作类
    private SyncManager mSyncManager;

    public static TtPhoneDataManager getInstance() {
        if (instance == null) {
            instance = new TtPhoneDataManager();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context, PhoneNettyManager manager) {
        if (instance == null) {
            instance = new TtPhoneDataManager();
        }
        phoneNettyManager = manager;
        mContext = context;
        setAllDataListener();

        initSyncDataModel(context);
    }

    private TtPhoneDataManager() {

    }

    /**
     * 初始化同步数据
     *
     * @param context
     */
    private void initSyncDataModel(Context context) {
        mSyncManager = new SyncManager(context);
    }

    /**
     * 所有数据监听
     */
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
                                if (iMainFragment != null) {
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
                if (mSyncManager != null && !connected) {
                    mSyncManager.setServerDisconnected();
                }

            }

            @Override
            public void isTtSignalStrength(int signalLevel) {
                if (iTtPhoneDataListener != null) {
                    iTtPhoneDataListener.isTtSignalStrength(signalLevel);
                }

            }

            @Override
            public void isTtSimCard(boolean isIn) {
                if (iTtPhoneDataListener != null) {
                    iTtPhoneDataListener.isTtSimCard(isIn);
                }
            }

            @Override
            public void isTtPhoneBattery(int level, int scal) {
                if (iTtPhoneDataListener != null) {
                    iTtPhoneDataListener.isTtPhoneBattery(level, scal);
                }
            }

            @Override
            public void syncCallRecord(TtCallRecordProtos.TtCallRecordProto ttCallRecordProto) {
                if (mSyncManager != null) {
                    mSyncManager.syncCallRecord(ttCallRecordProto);
                }
            }

            @Override
            public void syncShortMessage(TtShortMessageProtos.TtShortMessage ttShortMessage) {
                if (mSyncManager != null) {
                    mSyncManager.syncShortMessage(ttShortMessage);
                }
            }

            @Override
            public void syncShortMessageSignal(int protoId, GeneratedMessageV3 messageV3, TtShortMessageProtos.TtShortMessage.ShortMessage ttShortMessage) {
                if (mSyncManager != null) {
                    mSyncManager.syncShortMessageSignal(protoId, messageV3, ttShortMessage);
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


    /**
     * 释放资源
     */
    public void free() {
        if (mSyncManager != null) {
            mSyncManager.release();
        }
        instance = null;
    }


    @Override
    public void connectTtPhoneServer(String ip, int port) {
        phoneNettyManager.connect(port, ip);
    }

    @Override
    public void disconnectTtPhoneServer() {
        phoneNettyManager.stop();
    }

    @Override
    public void getTtPhoneSosState() {
        phoneNettyManager.requestServerSosStatus();
    }


    @Override
    public void closeTtPhoneSos() {

        phoneNettyManager.requestServerSosClose();
    }

    @Override
    public void openTtPhoneGps(TtBeidouOpenBean ttBeidouOpenBean) {
        phoneNettyManager.requestGpsPosition(ttBeidouOpenBean);
    }

    @Override
    public void closeTtPhoneGps(TtBeidouOpenBean ttBeidouOpenBean) {
        phoneNettyManager.requestGpsPosition(ttBeidouOpenBean);
    }

    @Override
    public void setTtPhoneSosValue(SosSendMessageModel sosSendMessageModel) {

        phoneNettyManager.requestSosSendMessage(sosSendMessageModel);
    }

    @Override
    public void setWifiPasswd(WifiSettingModel wifiSettingModel) {

        phoneNettyManager.requestServerWifipassword(wifiSettingModel);
    }

    @Override
    public void setDateAndTime(DatetimeModel datetimeModel) {
        phoneNettyManager.requestServerDatetime(datetimeModel);
    }

    @Override
    public void setResetFactorySettings() {

        phoneNettyManager.requestServerRecoverSystem();
    }

    @Override
    public void openUsbMode(TtBeidouOpenBean ttBeidouOpenBean) {
        phoneNettyManager.openBeidou(ttBeidouOpenBean);
    }

    @Override
    public void closeUsbMode(TtBeidouOpenBean ttBeidouOpenBean) {

        phoneNettyManager.openBeidou(ttBeidouOpenBean);
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


    @Override
    public SyncManager getSyncManager() {
        return mSyncManager;
    }


}
