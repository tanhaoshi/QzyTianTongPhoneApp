package com.tt.qzy.view.presenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.qzy.data.PhoneCmd;

import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.AidlContactsActivity;
import com.tt.qzy.view.activity.ContactsActivity;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.bean.ProtobufMessageModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.CallRecordView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class AidlPhoneFragmentPersenter extends BasePresenter<CallRecordView> {

    private Context mContext;

    // "昨天" 标题进行添加一次的控制
    private boolean isYesterday = true;

    // "更早" 标题进行添加一次的控制
    private boolean isEarlier = true;

    private String phone = "";

    public AidlPhoneFragmentPersenter(Context context) {
        mContext = context;
        //EventBus.getDefault().register(this);
        registerReceiver();
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    public void dialPhone(String phoneNumber) {
        if (!CommonData.getInstance().isConnected()) {
            NToast.shortToast(mContext, R.string.TMT_connect_tiantong_please);
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            NToast.shortToast(mContext, R.string.TMT_dial_number_notmull);
            return;
        }

        phone = phoneNumber;

        // EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber));
        dialPhoneToServer(phoneNumber);
        String name = getPhoneKeyForName(phone);

        if (null != name && name.length() > 0) {
            CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, name, "", "2", DateUtil.backTimeFomat(new Date()), 20);

            CallRecordManager.getInstance(mContext).insertCallRecord(callRecordDao, mContext);

        } else {
            CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, "", "", "2", DateUtil.backTimeFomat(new Date()), 20);

            CallRecordManager.getInstance(mContext).insertCallRecord(callRecordDao, mContext);
        }
    }

    /**
     * 底层打电话接口
     *
     * @param phoneMumber
     */
    private void dialPhoneToServer(String phoneMumber) {
        TtPhoneDataManager.getInstance().dialTtPhone(phoneMumber);
    }

    public String getPhoneKeyForName(String phone) {
        List<MailListDao> listModels = MailListManager.getInstance(mContext).getByPhoneList(phone);
        String name;
        if (listModels.size() > 0) {
            name = listModels.get(0).getName();
        } else {
            name = "";
        }
        return name;
    }

  /*  @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE:
                Intent intent = new Intent(mContext, TellPhoneActivity.class);
                intent.putExtra("diapadNumber", phone);
                mContext.startActivity(intent);
                break;
        }
    }*/

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qzy.tt.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE");
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        mContext.unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            KLog.i("action = " + action);
            if (action.equals("com.qzy.tt.EVENT_BUS_TYPE_CONNECT_TIANTONG__CALL_PHONE")) {
                Intent intent1 = new Intent(mContext, TellPhoneActivity.class);
                intent1.putExtra("diapadNumber", phone);
                mContext.startActivity(intent1);
            }
        }
    };

    public void getCallHistroy(final int offset, final int limit) {
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e) {
                List<CallRecordDao> callRecordDaos = CallRecordManager.getInstance(mContext).queryCallRecordList();
                mView.get().getDaoListSize(callRecordDaos.size());
                List<CallRecordDao> listDao = CallRecordManager.getInstance(mContext).limitCallRecordList(offset, limit);
                mView.get().getListSize(listDao.size());
                e.onNext(arrangementData(listDao));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CallRecordDao>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<CallRecordDao> value) {
                        mView.get().loadRefresh(value);
                        onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.i(" look over error message = " + e.getMessage().toString());
                        mView.get().showError(e.getMessage().toString(), true);
                    }

                    @Override
                    public void onComplete() {
                        mView.get().hideProgress();
                    }
                });
    }

    public void getRefresh(final int offset, final int limit) {
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e) {
                List<CallRecordDao> listDao = CallRecordManager.getInstance(mContext).limitCallRecordList(offset, limit);
                mView.get().getListSize(listDao.size());
                e.onNext(arrangementData(listDao));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CallRecordDao>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<CallRecordDao> value) {
                        mView.get().loadRefresh(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getLoadMore(final int offset, final int limit) {
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e) {
                List<CallRecordDao> listDao = CallRecordManager.getInstance(mContext).limitCallRecordList(offset, limit);
                mView.get().getListSize(listDao.size());
                e.onNext(arrangementData(listDao));
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CallRecordDao>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<CallRecordDao> value) {
                        mView.get().loadMore(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public List<CallRecordDao> arrangementData(List<CallRecordDao> list) {
        List<CallRecordDao> mModelList = new ArrayList<>();
        if (list.size() > 0) {
            sortData(list);
            mModelList.add(new CallRecordDao("", "", "", "", "", 1, "今天"));
            for (CallRecordDao recordModel : list) {
                if (DateUtil.isToday(recordModel.getDate())) {
                    mModelList.add(recordModel);
                } else if (DateUtil.isYesterday(recordModel.getDate())) {
                    if (isYesterday) {
                        mModelList.add(new CallRecordDao("", "", "", "", "", 1, "昨天"));
                        isYesterday = false;
                    }
                    mModelList.add(recordModel);
                } else {
                    if (isYesterday) {
                        mModelList.add(new CallRecordDao("", "", "", "", "", 1, "昨天"));
                        isYesterday = false;
                    }
                    if (isEarlier) {
                        mModelList.add(new CallRecordDao("", "", "", "", "", 1, "更早"));
                        isEarlier = false;
                    }
                    mModelList.add(recordModel);
                }
            }
        }
        mView.get().getDateSize(mModelList.size() - list.size());
        isYesterday = true;
        isEarlier = true;
        return mModelList;
    }

    private List<CallRecordDao> sortData(List<CallRecordDao> mList) {
        Collections.sort(mList, new Comparator<CallRecordDao>() {
            @Override
            public int compare(CallRecordDao lhs, CallRecordDao rhs) {
                Date date1 = DateUtil.stringToDate(lhs.getDate());
                Date date2 = DateUtil.stringToDate(rhs.getDate());
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }

    public void startTargetActivity(Context context, String phone) {
        Intent intent = new Intent(context, AidlContactsActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    public void deleteAllRecord() {

        ProtobufMessageModel protobufMessageModel = new ProtobufMessageModel();

        protobufMessageModel.setDelete(true);

       /* EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.
                EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_DELETE_SIGNAL_MESSAFGE
                ,protobufMessageModel));*/

        CallRecordManager.getInstance(mContext).deleteRecordList();

        NToast.shortToast(mContext, "删除成功!");
    }

    public void release() {

        unregisterReceiver();
        //EventBus.getDefault().unregister(this);
    }

}
