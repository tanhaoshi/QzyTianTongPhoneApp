package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.qzy.utils.LogUtils;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.db.CallRecordDaoDao;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.dao.CallRecordDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class CallRecordManager {

    public static volatile CallRecordManager sCallRecordManager;

    private DaoSession daoSession;

    private CallRecordManager(Context context){

        daoSession = TtPhoneApplication.getInstance().getDaoSession();
    }

    public static CallRecordManager getInstance(Context context){
        if(sCallRecordManager == null){
            synchronized (CallRecordManager.class){
                if(sCallRecordManager == null){
                    sCallRecordManager = new CallRecordManager(context);
                }
            }
        }

        return sCallRecordManager;
    }

    public List<CallRecordDao> queryAllRecordList(){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        QueryBuilder<CallRecordDao> qb = dao.queryBuilder().orderDesc();
        List<CallRecordDao> list = qb.list();
        return list;
    }

    public List<CallRecordDao> queryCallRecordList() {
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        QueryBuilder<CallRecordDao> qb = dao.queryBuilder().orderDesc().where(
                new WhereCondition.StringCondition(
                         " date in " + "(select max(date) from CALL_RECORD_DAO group by PHONE_NUMBER)")
        );
        List<CallRecordDao> list = qb.list();
        return list;
    }

    public List<CallRecordDao> queryKeyOnPhoneNumber(String phone){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        if(phone == null || "".equals(phone) || phone.length() == 0){
            return null;
        }
        QueryBuilder<CallRecordDao> qb = dao.queryBuilder().orderDesc().where(CallRecordDaoDao.Properties.PhoneNumber.eq(phone));
        List<CallRecordDao> list = qb.list();
        return list;
    }

    public void updateRecordName(CallRecordDao callRecordDao){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        dao.update(callRecordDao);
    }

    public void deleteRecordList(){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        dao.deleteAll();
    }

    public void deleteLinkPhone(List<CallRecordDao> callRecordDao){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        dao.deleteInTx(callRecordDao);
    }

    public void insertCallRecordList(List<CallRecordDao> callRecordDaos, Context context) {
        if (callRecordDaos == null || callRecordDaos.isEmpty()) {
            return;
        }
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        CallRecordDaoDao callRecordDao = daoSession.getCallRecordDaoDao();
//        callRecordDao.insertOrReplaceInTx(callRecordDaos);
        callRecordDao.insertInTx(callRecordDaos);
    }

    public void insertCallRecord(CallRecordDao dao, Context context){
        if( null == dao){
            return;
        }
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        CallRecordDaoDao callRecordDao = daoSession.getCallRecordDaoDao();
        callRecordDao.insert(dao);
    }

    public List<CallRecordDao> limitCallRecordList(int offset, int limit){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        List<CallRecordDao> list = dao.queryBuilder().orderDesc(CallRecordDaoDao.Properties.Date)
                .where(new WhereCondition.StringCondition(
                " date in " + "(select max(date) from CALL_RECORD_DAO group by PHONE_NUMBER)")).offset(offset).limit(limit).list();
        return list;
    }

    public List<CallRecordDao> fuzzySearch(String value){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        QueryBuilder<CallRecordDao> db = dao.queryBuilder().where(CallRecordDaoDao.Properties.Name.like("%"+value+"%"))
                .where(new WhereCondition.StringCondition(
                        " date in " + "(select max(date) from CALL_RECORD_DAO group by NAME)"));
        List<CallRecordDao> daoList = db.list();
        LogUtils.i("look at list size = " + daoList.size());
        if(daoList.size() > 0){
            return daoList;
        }else{
            QueryBuilder<CallRecordDao> queryBuilder = dao.queryBuilder().where(CallRecordDaoDao.Properties.PhoneNumber.
                    like("%"+value+"%"))
                    .where(new WhereCondition.StringCondition(
                    " date in " + "(select max(date) from CALL_RECORD_DAO group by PHONE_NUMBER)"));
            List<CallRecordDao> list = queryBuilder.list();
            LogUtils.i("look at list size = " + list.size());
            return list;
        }
    }

    public void deleteShortMessageOfPrimaryKey(Long id){
        CallRecordDaoDao dao = daoSession.getCallRecordDaoDao();
        dao.deleteByKey(id);
    }
}
