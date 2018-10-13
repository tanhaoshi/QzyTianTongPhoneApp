package com.tt.qzy.view.presenter.manager;

import android.content.Context;

import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.socks.library.KLog;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SyncManager {

    private Context mContext;

    public SyncManager(Context context){
        this.mContext = context;
    }

    public void syncCallRecord(TtCallRecordProtos.TtCallRecordProto ttCallRecordProto ){
        handleCallRecord(ttCallRecordProto.getCallRecordList());
    }

    public void syncShortMessage(TtShortMessageProtos.TtShortMessage ttShortMessage){
        handleShortMessage(ttShortMessage.getShortMessageList());
    }

    public void syncShortMessageSignal(TtShortMessageProtos.TtShortMessage.ShortMessage ttShortMessage){
        handleShortMessageSignal(ttShortMessage);
    }

    private void handleCallRecord(final List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e){
                    dataMerging(list);
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
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void dataMerging(List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        List<CallRecordDao> callRecordDaos = handlerCallRecordAgrementData(list);
        CallRecordManager.getInstance(mContext).deleteRecordList();
        CallRecordManager.getInstance(mContext).insertCallRecordList(callRecordDaos,mContext);
    }

    public List<CallRecordDao> handlerCallRecordAgrementData(List<TtCallRecordProtos.TtCallRecordProto.CallRecord> list){
        List<CallRecordDao> callRecordDaos = new ArrayList<>();
        if(list.size() > 0){
            for(TtCallRecordProtos.TtCallRecordProto.CallRecord callRecord : list){
                callRecordDaos.add(new CallRecordDao(callRecord.getPhoneNumber(),callRecord.getName(),
                        callRecord.getAddress(),String.valueOf(callRecord.getType()),callRecord.getDate(),callRecord.getDuration()));
            }
        }
        return callRecordDaos;
    }

    private void handleShortMessage(final List<TtShortMessageProtos.TtShortMessage.ShortMessage> messageList){
        Observable.create(new ObservableOnSubscribe<List<ShortMessageDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShortMessageDao>> e){
                dateMerging(messageList);
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ShortMessageDao>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(List<ShortMessageDao> value) {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void dateMerging(List<TtShortMessageProtos.TtShortMessage.ShortMessage> list){
        List<ShortMessageDao> shortMessagList = handlerShortMessageAgrementData(list);
        ShortMessageManager.getInstance(mContext).deleteShortMessageList();
        ShortMessageManager.getInstance(mContext).insertShortMessageList(shortMessagList,mContext);
    }

    public List<ShortMessageDao> handlerShortMessageAgrementData(List<TtShortMessageProtos.TtShortMessage.ShortMessage> list){
        List<ShortMessageDao> shortMessageDaos = new ArrayList<>();
        if(list.size() > 0){
            for(TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage : list){
                shortMessageDaos.add(new ShortMessageDao(shortMessage.getNumberPhone(),shortMessage.getMessage(),
                        shortMessage.getTime(),String.valueOf(shortMessage.getType()),shortMessage.getName()));
            }
        }
        return shortMessageDaos;
    }

    private void handleShortMessageSignal(final TtShortMessageProtos.TtShortMessage.ShortMessage ttShortMessage){
       Observable.create(new ObservableOnSubscribe<ShortMessageDao>() {
           @Override
           public void subscribe(ObservableEmitter<ShortMessageDao> e) throws Exception {
               ShortMessageManager.getInstance(mContext).insertShortMessage(meragingShortMessage(ttShortMessage),mContext);
           }
       }).subscribeOn(Schedulers.io())
         .unsubscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<ShortMessageDao>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ShortMessageDao value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private ShortMessageDao meragingShortMessage(TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage){
        KLog.i("look at shortMessage : " + shortMessage.getMessage());
        KLog.i("look at name : " + shortMessage.getName());
        KLog.i("look at numberPhone : " + shortMessage.getNumberPhone());
        ShortMessageDao shortMessageDao = new ShortMessageDao(shortMessage.getNumberPhone(),shortMessage.getMessage(),
                DateUtil.backTimeFomat(new Date()),String.valueOf(shortMessage.getType()),shortMessage.getName());
        return shortMessageDao;
    }
}
