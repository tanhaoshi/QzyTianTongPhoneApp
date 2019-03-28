package com.tt.qzy.view.presenter.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

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
import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.socks.library.KLog;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.bean.VersionCodeModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.network.NetService;
import com.tt.qzy.view.network.NetWorkUtils;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.presenter.manager.SyncManager;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.view.MainActivityView;


import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivityPresenter extends BasePresenter<MainActivityView>{

    public static final String DEVICE_BRAND = "Xiaomi";
    public static final int    PERMISSION_REQUEST_CODE = 1;

    private Context mContext;

    public MainActivityPresenter(Context context){
        this.mContext = context;
       // EventBus.getDefault().register(this);
        setSyncListener();
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
                             mView.get().getAppVersionCode(codeModel);
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
                        KLog.i(" look over progress percent = " + progressPercent);
                        mView.get().onProgressPercent((int)progressPercent);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        KLog.i(" download complete !");
                        mView.get().onCompelete(installPath+"/"+Constans.fileName);
                    }

                    @Override
                    public void onError(Error error) {
                        mView.get().onError(error.toString());
                    }
                });
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_LOCAL_RECORD_CALL_HISTROY:
                Integer integer = (Integer) event.getObject();
                mView.get().showRecordCallRead(true,integer);
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_LOCAL_SHORT_MESSAGE_HISTROY:
                Integer integers = (Integer)event.getObject();
                mView.get().showShortMessageRead(true,integers);
                break;
        }
    }*/

    /**
     * 设置同步数据回调接口
     */
    private void setSyncListener(){
        TtPhoneDataManager.getInstance().setISyncDataListener(new SyncManager.ISyncDataListener() {
            @Override
            public void onCallingLogSyncFinish(int count) {
                mView.get().showRecordCallRead(true,count);
            }

            @Override
            public void onShortMsgSyncFinish(int count) {
                mView.get().showShortMessageRead(true,count);
            }

            @Override
            public void onDisposeAlertSyncFinish(int recordCount) {
                mView.get().showShortMessageRead(true,recordCount);
            }
        });
    }

    /**
     * 将服务端的系统数据库修改未接状态
     */
    public void requestServerPhoneStatus(List<CallRecordDao> callRecordDaos){
        TtCallRecordProtos.TtCallRecordProto.Builder listRecorder = TtCallRecordProtos.TtCallRecordProto.newBuilder();
        for (CallRecordDao callInfo : callRecordDaos) {
            KLog.e("callInfo = " + callInfo.toString());
            TtCallRecordProtos.TtCallRecordProto.CallRecord callRecord = TtCallRecordProtos.TtCallRecordProto.CallRecord.newBuilder()
                    .setId(callInfo.getServerId())
                    .setPhoneNumber(callInfo.getPhoneNumber())
                    .setDate(callInfo.getDate())
                    .setName(callInfo.getName())
                    .setType(Integer.valueOf(callInfo.getState()))
                    .setDuration(callInfo.getDuration())
                    .build();
            listRecorder.addCallRecord(callRecord);
        }
       // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_RECORD_CALL_STATUS, listRecorder.build()));
    }

    public void release(){
       // EventBus.getDefault().unregister(this);
        mContext = null;
    }

    /** 应该先检测录音权限是否存在  exits */
    public boolean checkPermissionExist(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public void requestPermission(String brand, Activity activity, String... permissions){
        KLog.i("look up is not Xiaomi");
        if(DEVICE_BRAND.equals(brand)){
            KLog.i("is xiaomi so start request permission");
            ActivityCompat.requestPermissions(activity,permissions,PERMISSION_REQUEST_CODE);
        }else{
            return;
        }
    }
}
