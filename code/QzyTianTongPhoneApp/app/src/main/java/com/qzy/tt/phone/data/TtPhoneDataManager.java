package com.qzy.tt.phone.data;

import android.content.Context;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtPhoneUpdateResponseProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.phone.data.impl.IAllTtPhoneDataListener;
import com.qzy.tt.phone.data.impl.IMainAboutListener;
import com.qzy.tt.phone.data.impl.IMainFragment;
import com.qzy.tt.phone.data.impl.IPhoneStateListener;
import com.qzy.tt.phone.data.impl.ISendShortMessage;
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
    private HashMap<String, SyncManager.ISyncDataListener> hashMapSyncData = new HashMap<>();

    //短信数据回调
    private HashMap<String, SyncManager.ISyncMsgDataListener> hashMapSyncMsgData = new HashMap<>();

    //存储电话状态
    private HashMap<String, ITtPhoneCallStateLisenter> hashMapCallState = new HashMap<>();

    //存储设备电话占用
    private HashMap<String, ITtPhoneCallStateBackListener> hashMapCallStateBack = new HashMap<>();

    //关于服务器版本回调
    private IMainAboutListener mIMainAboutListener;

    //短信发送状态回调
    private ISendShortMessage iSendShortMessage;

    //查询当前电话状态回调
    private IPhoneStateListener mIPhoneStateListener;

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

        if (hashMapSyncData != null) {
            mSyncManager.setiSyncDataListener(hashMapSyncData);
        }

        if (hashMapSyncMsgData != null) {
            mSyncManager.setiSyncMsgDataListener(hashMapSyncMsgData);
        }
    }

    /**
     * 所有数据监听
     */
    private void setAllDataListener() {
        phoneNettyManager.getmCmdHandler().setmAllDataListener(new IAllTtPhoneDataListener() {
            @Override
            public void isTtServerConnected(final boolean connected) {
                Flowable.just(connected)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (iMainFragment != null) {
                                    iMainFragment.isTtServerConnected(aBoolean);
                                }
                            }
                        });
                if (mSyncManager != null && !connected) {
                    mSyncManager.setServerDisconnected();
                }
            }

            @Override
            public void isTtSignalStrength(int signalLevel, int signalDbm) {
                if (iTtPhoneDataListener != null) {
                    int[] array = new int[2];
                    array[0] = signalLevel;
                    array[1] = signalDbm;
                    Flowable.just(array)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<int[]>() {
                                @Override
                                public void accept(int[] ints) throws Exception {
                                    iTtPhoneDataListener.isTtSignalStrength(ints[0], ints[1]);
                                }
                            });

                }

            }

            @Override
            public void isTtSimCard(boolean isIn) {
                if (iTtPhoneDataListener != null) {
                    Flowable.just(isIn)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    iTtPhoneDataListener.isTtSimCard(aBoolean);
                                }
                            });
                }
            }

            @Override
            public void isTtPhoneBattery(int level, int scal) {
                if (iTtPhoneDataListener != null) {
                    int[] array = new int[2];
                    array[0] = level;
                    array[1] = scal;
                    Flowable.just(array)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<int[]>() {
                                @Override
                                public void accept(int[] ints) throws Exception {
                                    iTtPhoneDataListener.isTtPhoneBattery(ints[0], ints[1]);
                                }
                            });
                }
            }

            @Override
            public void isTtPhoneGpsPositon(PhoneCmd phoneCmd) {
                if (iMainFragment != null) {
                    Flowable.just(phoneCmd)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PhoneCmd>() {
                                @Override
                                public void accept(PhoneCmd phoneCmd) throws Exception {
                                    iMainFragment.isTtPhoneGpsPosition(phoneCmd);
                                }
                            });
                }
            }

            @Override
            public void isTtPhoneServerVersion(PhoneCmd phoneCmd) {
                if (mIMainAboutListener != null) {
                    Flowable.just(phoneCmd)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PhoneCmd>() {
                                @Override
                                public void accept(PhoneCmd phoneCmd) throws Exception {
                                    mIMainAboutListener.getServerVersion(phoneCmd);
                                }
                            });

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
            public synchronized void onPhoneCallStateBack(PhoneCmd phoneCmd) {
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
                    Flowable.just(phoneCmd)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PhoneCmd>() {
                                @Override
                                public void accept(PhoneCmd phoneCmd) throws Exception {
                                    iMainFragment.isServerSosStatus(phoneCmd);
                                }
                            });

                }
            }

            @Override
            public void onServerTtPhoneSmsSendState(PhoneCmd phoneCmd) {
                if (iSendShortMessage != null) {
                    Flowable.just(phoneCmd)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<PhoneCmd>() {
                                @Override
                                public void accept(PhoneCmd phoneCmd) throws Exception {
                                    iSendShortMessage.isSendShotMessageStatus(phoneCmd);
                                }
                            });

                }
            }

            @Override
            public void onUpdatePercent(Integer percent) {
                if(iMainFragment != null){
                    Flowable.just(percent)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    iMainFragment.updatePercent(integer);
                                }
                            });
                }

            }

            @Override
            public void selectCureenPhoneState(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState) {
                if (mIPhoneStateListener != null) {
                    Flowable.just(phoneState)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<CallPhoneStateProtos.CallPhoneState.PhoneState>() {
                                @Override
                                public void accept(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState) throws Exception {
                                    mIPhoneStateListener.selectPhoneState(phoneState);
                                }
                            });

                }
            }

            @Override
            public void IsServerUpdate(Object o) {
                if(iMainFragment != null){
                    Flowable.just(o)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Object>() {
                                @Override
                                public void accept(Object o) throws Exception {
                                    iMainFragment.isUpdateServer(o);
                                }
                            });

                }

            }

            @Override
            public void updateError(Object o) {
                if(iMainFragment != null){
                    Flowable.just(o)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Object>() {
                                @Override
                                public void accept(Object o) throws Exception {
                                    iMainFragment.serverUpdateError(o);
                                }
                            });

                }

            }

            @Override
            public void updateServerSucceed(Object o) {
                if(iMainFragment != null){
                    Flowable.just(o)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Object>() {
                                @Override
                                public void accept(Object o) throws Exception {
                                    iMainFragment.updateServerSucceed(o);
                                }
                            });

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

    @Override
    public void deleteDeviceCallRecord(Object o) {
        phoneNettyManager.requestServerDeleteMessage(o);
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
        if (phoneNettyManager != null) {
            phoneNettyManager.connect(port, ip);
        }
    }

    @Override
    public void checkServerIsUpdate(Object updateResponse) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerVersion(updateResponse);
        }

    }

    @Override
    public void startSendPackage() {
        if (phoneNettyManager != null) {
            phoneNettyManager.startUpload();
        }

    }

    @Override
    public void disconnectTtPhoneServer() {
        if (phoneNettyManager != null) {
            phoneNettyManager.stop();
        }
    }

    @Override
    public void getTtPhoneSosState() {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerSosStatus();
        }


    }


    @Override
    public void requestTtPhoneSos(boolean isOpen) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerSosSwitch(isOpen);
        }

    }

    @Override
    public void openTtPhoneGps(TtBeidouOpenBean ttBeidouOpenBean) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestGpsPosition(ttBeidouOpenBean);
        }

    }

    @Override
    public void closeTtPhoneGps(TtBeidouOpenBean ttBeidouOpenBean) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestGpsPosition(ttBeidouOpenBean);
        }

    }

    @Override
    public void setTtPhoneSosValue(SosSendMessageModel sosSendMessageModel) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestSosSendMessage(sosSendMessageModel);
        }

    }

    @Override
    public void requestTtPhoneSosValue() {
        if (phoneNettyManager != null) {
            phoneNettyManager.requesSosMessageValue();
        }

    }

    @Override
    public void setWifiPasswd(WifiSettingModel wifiSettingModel) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerWifipassword(wifiSettingModel);
        }

    }

    @Override
    public void setDateAndTime(DatetimeModel datetimeModel) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerDatetime(datetimeModel);
        }

    }

    @Override
    public void setResetFactorySettings() {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerRecoverSystem();
        }

    }

    @Override
    public void openUsbMode(TtBeidouOpenBean ttBeidouOpenBean) {
        if (phoneNettyManager != null) {
            phoneNettyManager.openBeidou(ttBeidouOpenBean);
        }

    }

    @Override
    public void closeUsbMode(TtBeidouOpenBean ttBeidouOpenBean) {
        if (phoneNettyManager != null) {
            phoneNettyManager.openBeidou(ttBeidouOpenBean);
        }

    }

    @Override
    public void dialTtPhone(String phoneNumber) {
        if (phoneNettyManager != null) {
            phoneNettyManager.dialPhone(phoneNumber);
        }

    }

    @Override
    public void selectCureentPhoneState() {
        if (phoneNettyManager != null) {
            phoneNettyManager.selectPhoneState();
        }
    }

    @Override
    public void hangupTtPhone() {
        if (phoneNettyManager != null) {
            phoneNettyManager.endCall();
        }
    }

    @Override
    public void answerTtPhone() {
        if (phoneNettyManager != null) {
            phoneNettyManager.acceptCall();
        }
    }

    @Override
    public void sendSmsTtPhone(SmsBean smsBean) {
        if (phoneNettyManager != null) {
            phoneNettyManager.sendSms(smsBean);
        }

    }

    @Override
    public void requestCallRecord() {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestCallRecord();
        }

    }

    @Override
    public void requestShortMessage() {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestShortMessage();
        }

    }

    @Override
    public void requestServerShortMessageStatus(SMAgrementModel smAgrementModel) {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerShortMessageStatus(smAgrementModel);
        }

    }

    @Override
    public void requestServerTtPhoneVersion() {
        if (phoneNettyManager != null) {
            phoneNettyManager.requestServerVersion();
        }
    }

    @Override
    public SyncManager getSyncManager() {
        return mSyncManager;
    }

    @Override
    public void setISyncDataListener(String tag, SyncManager.ISyncDataListener listener) {
        if (hashMapSyncData == null) {
            hashMapSyncData = new HashMap<>();
        }
        hashMapSyncData.put(tag, listener);
        if (mSyncManager != null) {
            mSyncManager.setiSyncDataListener(hashMapSyncData);
        }
    }


    @Override
    public void removeISyncDataListener() {
        hashMapSyncData = null;
        if (mSyncManager != null) {
            mSyncManager.removeiSyncDataListener();
        }
    }

    @Override
    public SyncManager.ISyncDataListener getISyncDataListener(String tag) {
        if (hashMapSyncData != null) {
            return hashMapSyncData.get(tag);
        }
        return null;
    }

    @Override
    public void setISyncMsgDataListener(String tag, SyncManager.ISyncMsgDataListener listener) {
        if (hashMapSyncMsgData == null) {
            hashMapSyncMsgData = new HashMap<>();
        }
        hashMapSyncMsgData.put(tag, listener);
        if (mSyncManager != null) {
            mSyncManager.setiSyncMsgDataListener(hashMapSyncMsgData);
        }
    }

    @Override
    public void removeISyncMsgDataListener(String tag) {
        hashMapSyncMsgData = null;
        if (mSyncManager != null) {
            mSyncManager.setiSyncMsgDataListener(hashMapSyncMsgData);
        }
    }

    @Override
    public void setISendShortMessage(ISendShortMessage listener) {
        iSendShortMessage = listener;
    }

    @Override
    public void removeISendShortMessage() {
        iSendShortMessage = null;
    }


}
