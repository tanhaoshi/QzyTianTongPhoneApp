package com.tt.qzy.view.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.phone.common.CommonData;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.CallRecordView;

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

    public void getCallHistroy(){
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e) throws Exception {
                e.onNext(arrangementData(CallRecordManager.getInstance(mContext).queryCallRecordList()));
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

    public List<CallRecordDao> arrangementData(List<CallRecordDao> list){
        List<CallRecordDao> mModelList = new ArrayList<>();
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃9秒","2018-8-27 14:33:24",0,""));
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃8秒","2018-8-24 14:33:24",0,""));
        list.add(new CallRecordDao("181-2644-0000","","广东深圳","响铃8秒","2018-8-29 14:33:24",0,""));
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

}
