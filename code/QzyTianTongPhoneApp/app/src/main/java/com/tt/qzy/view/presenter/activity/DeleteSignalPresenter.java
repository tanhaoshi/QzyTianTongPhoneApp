package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.DeleteSignalView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeleteSignalPresenter extends BasePresenter<DeleteSignalView>{

    private Context mContext;

    public DeleteSignalPresenter(Context context){
        this.mContext = context;
    }

    public void getRecordHistroyList(){
        Observable.create(new ObservableOnSubscribe<List<CallRecordDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CallRecordDao>> e){
                List<CallRecordDao> messageDaoList = CallRecordManager.getInstance(mContext).queryCallRecordList();
                e.onNext(messageDaoList);
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
                        mView.get().getRecordHistroy(value);
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
}
