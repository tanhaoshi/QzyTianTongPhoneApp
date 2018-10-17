package com.tt.qzy.view.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.phone.common.CommonData;
import com.socks.library.KLog;
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

    private String phone = "";

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

        phone = phoneNumber;
        KLog.i("look up phone is : "+phone);
        KLog.i("start call phone ...");
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_STATE:
                parseCallBack(event.getObject());
                break;
        }
    }

    /**
     * 解析与处理 电话状态
     * @param o
     */
    private void parseCallBack(Object o){
        PhoneCmd cmd = (PhoneCmd) o;
        CallPhoneBackProtos.CallPhoneBack callPhoneBack = (CallPhoneBackProtos.CallPhoneBack)cmd.getMessage();
        KLog.i("reciver state is : "+callPhoneBack.getIsCalling());
        if(!callPhoneBack.getIsCalling()){
            Intent intent = new Intent(mContext, TellPhoneActivity.class);
            intent.putExtra("diapadNumber", phone);
            mContext.startActivity(intent);
        }else{
            NToast.shortToast(mContext,"当前天通猫正被占用!");
        }
    }

    public void getCallHistroy(){
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e){
                List<CallRecordDao> listDao = CallRecordManager.getInstance(mContext).queryCallRecordList();
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
                    KLog.i("look at error : " +e.getMessage().toString());
                    mView.get().showError(e.getMessage().toString(),true);
                }

                @Override
                public void onComplete() {
                    mView.get().hideProgress();
                }
            });
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
