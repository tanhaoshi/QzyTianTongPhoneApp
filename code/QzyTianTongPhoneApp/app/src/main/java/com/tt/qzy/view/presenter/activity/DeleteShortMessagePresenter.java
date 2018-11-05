package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.socks.library.KLog;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.DeleteShortMessageView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeleteShortMessagePresenter extends BasePresenter<DeleteShortMessageView>{

    private Context mContext;

    public DeleteShortMessagePresenter(Context context){
        this.mContext = context;
    }

    public void getShortMessageList(){
        Observable.create(new ObservableOnSubscribe<List<ShortMessageDao>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ShortMessageDao>> e){
                List<ShortMessageDao> messageDaoList = ShortMessageManager.getInstance(mContext).queryShortMessageList();
                e.onNext(messageDaoList);
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
                            mView.get().getShortMessageList(value);
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
