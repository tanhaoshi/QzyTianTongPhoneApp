package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.tt.qzy.view.db.CallRecordDaoDao;
import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.MailListDaoDao;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class CallRecordManager {

    public static CallRecordManager sCallRecordManager;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private CallRecordManager(Context context){

        daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());

        daoSession = daoMaster.newSession();
    }

    public static CallRecordManager getInstance(Context context){

        if(sCallRecordManager == null){

            sCallRecordManager = new CallRecordManager(context);
        }

        return sCallRecordManager;
    }

    public List<CallRecordDao> queryCallRecordList() {
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        QueryBuilder<CallRecordDao> qb = dao.queryBuilder();
        List<CallRecordDao> list = qb.list();
        return list;
    }

    public void deleteRecordList(){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        dao.deleteAll();
    }

    public void insertCallRecordList(List<CallRecordDao> callRecordDaos, Context context) {
        if (callRecordDaos == null || callRecordDaos.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CallRecordDaoDao callRecordDao = daoSession.getCallRecordDaoDao();
        callRecordDao.insertInTx(callRecordDaos);
    }
}
