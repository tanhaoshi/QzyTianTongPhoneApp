package com.tt.qzy.view.application;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tt.qzy.view.BuildConfig;
import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.trace.TraceServiceImpl;
import com.tt.qzy.view.utils.CrashHandler;
import com.xdandroid.hellodaemon.DaemonEnv;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yj.zhang on 2018/8/25.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class TtPhoneApplication extends Application {

    private final static String dbName = "tiantong_db";

    public static TtPhoneApplication sApp;

    private DaoSession daoSession;

    private PendingIntent restartIntent;

    public List<Activity> mActivities = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        catchCrashExeption();
        initAppRefresh();
        initPRDownloader();
        initGreenDao();
    }

    public static TtPhoneApplication getInstance(){
        return sApp;
    }

    private void initAppRefresh(){
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    public void initPRDownloader(){
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(this, config);
    }

    private void initGreenDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, dbName);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private void catchCrashExeption(){
        CrashHandler.CrashUploader crashUploader = new CrashHandler.CrashUploader() {

            @Override
            public void uploadCrashMessage(ConcurrentHashMap<String, Object> info) {
            }
        };
        //默認調用false false為我們自定義的捕捉異常
        CrashHandler.getInstance().init(this, crashUploader, restartIntent,false);

    }

    private void initDemonService(){
        DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);
    }

    public void addActivity(Activity activity){
        if(!mActivities.contains(activity)){
            mActivities.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if(!mActivities.contains(activity)){
            mActivities.remove(activity);
        }
    }

    public void removeAllActivity(){
        for(Activity activity : mActivities){
            if(activity != null){
                activity.finish();
            }
        }
    }

}
