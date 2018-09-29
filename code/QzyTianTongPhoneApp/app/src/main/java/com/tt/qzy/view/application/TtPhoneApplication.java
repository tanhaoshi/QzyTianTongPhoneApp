package com.tt.qzy.view.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.socks.library.KLog;
import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.manager.DBManager;

/**
 * Created by yj.zhang on 2018/8/25.
 */

public class TtPhoneApplication extends Application {

//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;
//    private DaoMaster.DevOpenHelper mHelper;
//    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        KLog.init(true, "qzy_tt_phone");
        //setDataBase();
    }

//    private void setDataBase(){
//        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
//        db = mHelper.getWritableDatabase();
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }

}
