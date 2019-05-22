package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.qzy.utils.LogUtils;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.bean.VersionCodeModel;
import com.tt.qzy.view.network.NetService;
import com.tt.qzy.view.network.NetWorkUtils;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.view.CheckUpdateView;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CheckUpdatePresenter extends BasePresenter<CheckUpdateView>{

    private Context mContext;

    public CheckUpdatePresenter(Context context){
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
                            VersionCodeModel codeModel = JSON.parseObject(value.string(),VersionCodeModel.class);
                            mView.get().getCheckUpdate(codeModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i("look over cause of error = " + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public boolean checkUpdate(VersionCodeModel versionCodeModel){
        if(versionCodeModel != null){
            if(AppUtils.getVersionCode(mContext) < Integer.valueOf(versionCodeModel.getData().getVersionCode())){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void startUploader(String downloaderUrl){

        int downloadIdOne = 0;

        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.pause(downloadIdOne);
            return;
        }

        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.resume(downloadIdOne);
            return;
        }

        final String installPath = AppUtils.getRootDirPath(TtPhoneApplication.getInstance());

        downloadIdOne = PRDownloader.download(downloaderUrl, installPath, Constans.fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        mView.get().onStartDownloader();
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        LogUtils.i(" look over progress percent = " + progressPercent);
                        mView.get().onProgressPercent((int)progressPercent);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        LogUtils.i(" download complete !");
                        mView.get().onCompelete(installPath+"/"+Constans.fileName);
                    }

                    @Override
                    public void onError(Error error) {
                        mView.get().onError(error.toString());
                    }
                });
    }
}
