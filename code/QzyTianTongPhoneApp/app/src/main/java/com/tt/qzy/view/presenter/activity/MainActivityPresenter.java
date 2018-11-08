package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.socks.library.KLog;
import com.tt.qzy.view.network.NetService;
import com.tt.qzy.view.network.NetWorkUtils;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.MainActivityView;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivityPresenter extends BasePresenter<MainActivityView>{

    private Context mContext;

    public MainActivityPresenter(Context context){
        this.mContext = context;
    }

    public void getAppversionRequest(){
         NetWorkUtils.getInstance()
                 .createService(NetService.class)
                 .getAppVersion()
                 .subscribeOn(Schedulers.io())
                 .unsubscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<ResponseBody>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                         
                     }

                     @Override
                     public void onNext(ResponseBody value) {
                         try {
                             KLog.i(" look over data value = "+value.string());
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onError(Throwable e) {
                         KLog.i("look over cause of error = " + e.getMessage());
                     }

                     @Override
                     public void onComplete() {

                     }
                 });
    }
}
