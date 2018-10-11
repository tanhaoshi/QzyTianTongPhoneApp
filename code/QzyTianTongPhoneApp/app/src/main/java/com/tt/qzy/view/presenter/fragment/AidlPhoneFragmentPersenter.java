package com.tt.qzy.view.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.phone.common.CommonData;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.CallRecordView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

public class AidlPhoneFragmentPersenter extends BasePresenter<CallRecordView>{

    private Context mContext;

    // "昨天" 标题进行添加一次的控制
    private boolean isYesterday = true;

    // "更早" 标题进行添加一次的控制
    private boolean isEarlier = true;

    public AidlPhoneFragmentPersenter(Context context) {
        mContext = context;
        EventBus.getDefault().register(this);
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

        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber));

        Intent intent = new Intent(mContext, TellPhoneActivity.class);
        intent.putExtra("diapadNumber", phoneNumber);
        mContext.startActivity(intent);
    }

    /**
     * 请求通话记录
     */
    public void requestCallRecord(){
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_CALL_RECORD));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_RECORD:
                parseCallRecord(event.getObject());
                break;
        }
    }

    /**
     * 解析与处理 协议数据
     * @param o
     */
    private void parseCallRecord(Object o){
        PhoneCmd cmd = (PhoneCmd) o;
        TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = (TtCallRecordProtos.TtCallRecordProto)cmd.getMessage();
        //如果我从协议获得的数据与我数据库中的数据不同步的话,我要将其,数据库中的数据进行删除，然后重新将协议中获得的数据添加到数据库中
        //处理协议的数据应该放入到线程当中去处理
        getCallHistroy(ttCallRecordProto.getCallRecordList());
    }

    public void getCallHistroy(final List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e){

                List<CallRecordDao> callRecordDaos = handlerAgrementData(list);

                if(callRecordDaos.size() == 0 || callRecordDaos == null){

                }

                List<CallRecordDao> listDao = CallRecordManager.getInstance(mContext).queryCallRecordList();

                if(callRecordDaos.size() > listDao.size()){
                    //如果协议过来的数据大于我们数据库当中的数据 要进行删除
                    CallRecordManager.getInstance(mContext).deleteRecordList();
                    //删除后,然后批量进行添加
                    //其实每次过来都应该同步一次 然后我展示的话，就展示
                    CallRecordManager.getInstance(mContext).insertCallRecordList(callRecordDaos,mContext);
                } else if(callRecordDaos.size() == listDao.size()){

                }

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
                    mView.get().callRecordHistroy(value);
                    onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    mView.get().showError(e.getMessage().toString(),true);
                }

                @Override
                public void onComplete() {
                    mView.get().hideProgress();
                }
            });
    }

    public List<CallRecordDao> handlerAgrementData(List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        List<CallRecordDao> callRecordDaos = new ArrayList<>();
        if(list.size() > 0){
            for(TtCallRecordProtos.TtCallRecordProto.CallRecord callRecord : list){
                callRecordDaos.add(new CallRecordDao(callRecord.getPhoneNumber(),callRecord.getName(),
                        callRecord.getAddress(),callRecord.getState(),callRecord.getDate()));
            }
        }
        return callRecordDaos;
    }

    public List<CallRecordDao> arrangementData(List<CallRecordDao> list){
        List<CallRecordDao> mModelList = new ArrayList<>();
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃9秒","2018-8-27 14:33:24",0,""));
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃8秒","2018-8-24 14:33:24",0,""));
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃8秒","2018-8-29 14:33:24",0,""));
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃8秒","2018-8-30 14:33:24",0,""));
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃8秒","2018-8-30 14:33:24",0,""));
        if(list.size() > 0){
            sortData(list);
            mModelList.add(new CallRecordDao("","","","","",1,"今天"));
            for(CallRecordDao recordModel : list){
                if(DateUtil.isToday(recordModel.getDate())){
                    mModelList.add(recordModel);
                }else if(DateUtil.isYesterday(recordModel.getDate())){
                    if(isYesterday){
                        mModelList.add(new CallRecordDao("","","","","",1,"昨天"));
                        isYesterday = false;
                    }
                    mModelList.add(recordModel);
                }else{
                    if(isYesterday){
                        mModelList.add(new CallRecordDao("","","","","",1,"昨天"));
                        isYesterday = false;
                    }
                    if(isEarlier){
                        mModelList.add(new CallRecordDao("","","","","",1,"更早"));
                        isEarlier = false;
                    }
                    mModelList.add(recordModel);
                }
            }
        }
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

    public void release(){
        EventBus.getDefault().unregister(this);
    }

}
