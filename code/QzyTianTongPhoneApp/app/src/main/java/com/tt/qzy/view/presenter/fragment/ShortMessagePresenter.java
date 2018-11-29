package com.tt.qzy.view.presenter.fragment;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.ShortMessageModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.view.ShortMessageView;

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

public class ShortMessagePresenter extends BasePresenter<ShortMessageView>{

    private Context mContext;

    // "昨天" 标题进行添加一次的控制
    private boolean isYesterday = true;

    // "更早" 标题进行添加一次的控制
    private boolean isEarlier = true;

    public ShortMessagePresenter(Context context){
        this.mContext = context;
    }

    /**
     * 删除全部短信
     */
    public void clearMessage(){
        ShortMessageManager.getInstance(mContext).deleteShortMessageList();
    }

    public void getShortMessageDataList(final int offset,final int limit){
        Observable.create(new ObservableOnSubscribe<List<ShortMessageDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShortMessageDao>> e){
                List<ShortMessageDao> daoList = ShortMessageManager.getInstance(mContext).queryList();
                mView.get().getDaoListSize(daoList.size());
                List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(mContext).limitShortMessageList(offset,limit);
                mView.get().getListSize(messageDaoList.size());
                e.onNext(arrangementData(messageDaoList));
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
                        mView.get().getShortMessageData(value);
                        onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.i("look over shortmessage presenter error string value = " + e.getMessage());
                        mView.get().showError(e.getMessage().toString(),true);
                    }

                    @Override
                    public void onComplete() {
                        mView.get().hideProgress();
                    }
                });
    }

    public void getLoadShortMessageFresh(final int offset,final int limit){
        Observable.create(new ObservableOnSubscribe<List<ShortMessageDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShortMessageDao>> e){
                List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(mContext).limitShortMessageList(offset,limit);
                mView.get().getListSize(messageDaoList.size());
                e.onNext(arrangementData(messageDaoList));
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
                        mView.get().getLoadRefresh(value);
                        onComplete();
                    }
                    @Override
                    public void onError(Throwable e) {
                        KLog.i("look over shortmessage presenter error string value = " + e.getMessage());
                        mView.get().showError(e.getMessage().toString(),true);
                    }
                    @Override
                    public void onComplete() {
                        mView.get().hideProgress();
                    }
                });
    }

    public void getLoadShortMessageMore(final int offset,final int limit){
        Observable.create(new ObservableOnSubscribe<List<ShortMessageDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShortMessageDao>> e){
                List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(mContext).limitShortMessageList(offset,limit);
                mView.get().getListSize(messageDaoList.size());
                e.onNext(arrangementData(messageDaoList));
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
                        mView.get().getLoadRefresh(value);
                        onComplete();
                    }
                    @Override
                    public void onError(Throwable e) {
                        KLog.i("look over shortmessage presenter error string value = " + e.getMessage());
                        mView.get().showError(e.getMessage().toString(),true);
                    }
                    @Override
                    public void onComplete() {
                        mView.get().hideProgress();
                    }
                });
    }


    public List<ShortMessageDao> dataMerging(List<TtShortMessageProtos.TtShortMessage.ShortMessage> list){

        List<ShortMessageDao> shortMessagList = handlerAgrementData(list);

        List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(mContext).queryList();
        //当协议数据大小为0或集合为空
        if(shortMessagList.size() == 0 || shortMessagList == null){
            //当数据库数据大于0且不等于空
            if(messageDaoList.size() > 0 && messageDaoList != null){
                //返回数据库数据给UI显示
                return arrangementData(messageDaoList);
            }
        }
        //如果协议数据大于数据库中的数据
        if(shortMessagList.size() > messageDaoList.size()){
            //删除本地
            ShortMessageManager.getInstance(mContext).deleteShortMessageList();
            //再者将协议数据插入到本地
            ShortMessageManager.getInstance(mContext).insertShortMessageList(shortMessagList,mContext);
            //当协议数据和数据库中数据相等情况下 返回协议数据给UI
        } else if(shortMessagList.size() == messageDaoList.size()){
            return arrangementData(shortMessagList);
            //当协议数据小于数据库中数据,将其数据库中数据删除，提供协议数据供UI显示
        }else if(shortMessagList.size() < messageDaoList.size()){
            //删除本地
            ShortMessageManager.getInstance(mContext).deleteShortMessageList();
            //再者将协议数据插入到本地
            ShortMessageManager.getInstance(mContext).insertShortMessageList(shortMessagList,mContext);
            //返回协议数据
            return arrangementData(shortMessagList);
        }

        return arrangementData(messageDaoList);
    }

    public List<ShortMessageDao> handlerAgrementData(List<TtShortMessageProtos.TtShortMessage.ShortMessage> list){
        List<ShortMessageDao> shortMessageDaos = new ArrayList<>();
        if(list.size() > 0){
            for(TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage : list){
                shortMessageDaos.add(new ShortMessageDao(shortMessage.getNumberPhone(),shortMessage.getMessage(),
                        shortMessage.getTime(),String.valueOf(shortMessage.getType()),shortMessage.getName(),
                        shortMessage.getId(),shortMessage.getIsRead()));
            }
        }
        return shortMessageDaos;
    }

    public List<ShortMessageDao> arrangementData(List<ShortMessageDao> list){
        List<ShortMessageDao> mModelList = new ArrayList<>();
        if(list.size()>0){
            sortData(list);
            mModelList.add(new ShortMessageDao("","","",1,"今天","",""));
            for(ShortMessageDao shortMessageModel : list){
                if(DateUtil.isToday(shortMessageModel.getTime())){
                    mModelList.add(shortMessageModel);
                }else if(DateUtil.isYesterday(shortMessageModel.getTime())){
                    if(isYesterday){
                        mModelList.add(new ShortMessageDao("","","",1,"昨天","",""));
                        isYesterday = false;
                    }
                    mModelList.add(shortMessageModel);
                }else{
                    if(isYesterday){
                        mModelList.add(new ShortMessageDao("","","",1,"昨天","",""));
                        isYesterday = false;
                    }
                    if(isEarlier){
                        mModelList.add(new ShortMessageDao("","","",1,"更早","",""));
                        isEarlier = false;
                    }
                    mModelList.add(shortMessageModel);
                }
            }
        }
        mView.get().getDateSize(mModelList.size() - list.size());
        isYesterday = true;
        isEarlier = true;
        return mModelList;
    }

    private List<ShortMessageDao> sortData(List<ShortMessageDao> mList) {
        Collections.sort(mList, new Comparator<ShortMessageDao>() {
            @Override
            public int compare(ShortMessageDao lhs, ShortMessageDao rhs) {
                Date date1 = DateUtil.stringToDate(lhs.getTime());
                Date date2 = DateUtil.stringToDate(rhs.getTime());
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }
}
