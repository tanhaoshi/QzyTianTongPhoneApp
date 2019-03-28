package com.qzy.tt.phone.data;

import android.content.Context;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.phone.data.impl.IAllTtPhoneDataListener;
import com.qzy.tt.phone.data.impl.IMainAboutListener;
import com.qzy.tt.phone.data.impl.IMainFragment;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateBackListener;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateLisenter;
import com.qzy.tt.phone.data.impl.ITtPhoneDataListener;
import com.qzy.tt.phone.data.impl.ITtPhoneHandlerManager;
import com.qzy.tt.phone.data.impl.ITtPhoneManager;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.SMAgrementModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.bean.WifiSettingModel;
import com.tt.qzy.view.presenter.manager.SyncManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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


    //同步数据回调
    private SyncManager.ISyncDataListener iSyncDataListener;

    //短信数据回调
    private SyncManager.ISyncMsgDataListener iSyncMsgDataListener;

    //存储电话状态
    private HashMap<String, ITtPhoneCallStateLisenter> hashMapCallState = new HashMap<>();

    //存储设备电话占用
    private HashMap<String, ITtPhoneCallStateBackListener> hashMapCallStateBack = new HashMap<>();

    private IMainAboutListener mIMainAboutListener;

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

        if (iSyncDataListener != null) {
            mSyncManager.setiSyncDataListener(iSyncDataListener);
        }

        if (iSyncMsgDataListener != null) {
            mSyncManager.setiSyncMsgDataListener(iSyncMsgDataListener);
        }
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
            public void isTtPhoneGpsPositon(PhoneCmd phoneCmd) {
                if (iMainFragment != null) {
                    iMainFragment.isTtPhoneGpsPosition(phoneCmd);
                }
            }

            @Override
            public void isTtPhoneServerVersion(PhoneCmd phoneCmd) {
                if (mIMainAboutListener != null) {
                    mIMainAboutListener.getServerVersion(phoneCmd);
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

            @Override
            public void onTtPhoneCallState(PhoneCmd phoneCmd) {
                Iterator iter = hashMapCallState.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    //Object key = entry.getKey();
                    ITtPhoneCallStateLisenter val = (ITtPhoneCallStateLisenter) entry.getValue();
                    if (val != null) {
                        val.onTtPhoneCallState(phoneCmd);
                    }
                }


            }

            @Override
            public void onPhoneCallStateBack(PhoneCmd phoneCmd) {
                Iterator iter = hashMapCallStateBack.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    //Object key = entry.getKey();
                    ITtPhoneCallStateBackListener val = (ITtPhoneCallStateBackListener) entry.getValue();
                    if (val != null) {
                        LogUtils.i("onPhoneCallStateBack phonCmd = " + phoneCmd.getProtoId());
                        val.onPhoneCallStateBack(phoneCmd);
                    }
                }
            }

            @Override
            public void onServerTtPhoneSosState(PhoneCmd phoneCmd) {
                if (iMainFragment != null) {
                    iMainFragment.isServerSosStatus(phoneCmd);
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

    @Override
    public void removeTtPhoneDataListener() {
        this.iTtPhoneDataListener = null;
    }

    @Override
    public void setTtPhoneCallStateLisenter(String tag, ITtPhoneCallStateLisenter lisenter) {

        hashMapCallState.put(tag, lisenter);

    }


    @Override
    public void removeTtPhoneCallStateLisenter(String tag) {
        hashMapCallState.remove(tag);
    }

    @Override
    public void setITtPhoneCallStateBackListener(String tag, ITtPhoneCallStateBackListener lisenter) {
        hashMapCallStateBack.put(tag, lisenter);
    }

    @Override
    public void removeITtPhoneCallStateBackListener(String tag) {
        hashMapCallStateBack.remove(tag);
    }

    @Override
    public void setIMainAboutListener(IMainAboutListener iMainAboutListener) {
        mIMainAboutListener = iMainAboutListener;
    }

    @Override
    public void removeIMainAboutListener() {
        mIMainAboutListener = null;
    }

    /**
     * 注册MainFragment数据回调接口
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
    public void dialTtPhone(String phoneNumber) {
        phoneNettyManager.dialPhone(phoneNumber);
    }

    @Override
    public void hangupTtPhone() {
        phoneNettyManager.endCall();
    }

    @Override
    public void answerTtPhone() {
        phoneNettyManager.acceptCall();
    }

    @Override
    public void requestCallRecord() {
        phoneNettyManager.requestCallRecord();
    }

    @Override
    public void requestShortMessage() {
        phoneNettyManager.requestShortMessage();
    }

    @Override
    public void requestServerShortMessageStatus(SMAgrementModel smAgrementModel) {
        phoneNettyManager.requestServerShortMessageStatus(smAgrementModel);
    }

    @Override
    public void requestServerTtPhoneVersion() {
        phoneNettyManager.requestServerVersion();
    }


    @Override
    public SyncManager getSyncManager() {
        return mSyncManager;
    }

    @Override
    public void setISyncDataListener(SyncManager.ISyncDataListener listener) {
        iSyncDataListener = listener;
        if (mSyncManager != null) {
            mSyncManager.setiSyncDataListener(listener);
        }
    }

    @Override
    public void removeISyncDataListener() {
        iSyncDataListener = null;
        if (mSyncManager != null) {
            mSyncManager.setiSyncDataListener(iSyncDataListener);
        }
    }

    @Override
    public void setISyncMsgDataListener(SyncManager.ISyncMsgDataListener listener) {
        iSyncMsgDataListener = listener;
        if (mSyncManager != null) {
            mSyncManager.setiSyncMsgDataListener(listener);
        }
    }

    @Override
    public void removeISyncMsgDataListener() {
        iSyncMsgDataListener = null;
        if (mSyncManager != null) {
            mSyncManager.setiSyncMsgDataListener(iSyncMsgDataListener);
        }
    }


}
