package com.tt.qzy.view.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtCallRecordProtos;
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
        getCallHistroy(ttCallRecordProto.getCallRecordList());
    }

    public void getCallHistroy(final List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e){
                e.onNext(dataMerging(list));
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

    //数据合并处理
    private List<CallRecordDao> dataMerging(List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        //通过协议获取到的数据
        List<CallRecordDao> callRecordDaos = handlerAgrementData(list);
        //查询本地数据库所获得的数据
        List<CallRecordDao> listDao = CallRecordManager.getInstance(mContext).queryCallRecordList();
        //当协议数据大小为0或集合为空
        if(callRecordDaos.size() == 0 || callRecordDaos == null){
            //当数据数据大于0且不等于空
            if(listDao.size() > 0 && listDao != null){
                //返回数据库数据给UI显示
                return arrangementData(listDao);
            }
        }
        //如果协议数据大于数据库中的数据
        if(callRecordDaos.size() > listDao.size()){
            //删除本地
            CallRecordManager.getInstance(mContext).deleteRecordList();
            //再者将协议数据插入到本地
            CallRecordManager.getInstance(mContext).insertCallRecordList(callRecordDaos,mContext);
            //当协议数据和数据库中数据相等情况下 返回协议数据给UI
        } else if(callRecordDaos.size() == listDao.size()){
            return arrangementData(callRecordDaos);
            //当协议数据小于数据库中数据,将其数据库中数据删除，提供协议数据供UI显示
        }else if(callRecordDaos.size() < listDao.size()){
            //删除本地
            CallRecordManager.getInstance(mContext).deleteRecordList();
            //再者将协议数据插入到本地
            CallRecordManager.getInstance(mContext).insertCallRecordList(callRecordDaos,mContext);
            //返回协议数据
            return arrangementData(callRecordDaos);
        }

        return arrangementData(listDao);
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
