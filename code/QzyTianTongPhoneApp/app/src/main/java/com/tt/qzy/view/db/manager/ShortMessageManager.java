package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.socks.library.KLog;
import com.tt.qzy.view.db.CallRecordDaoDao;
import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.ShortMessageDaoDao;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class ShortMessageManager {

   public static ShortMessageManager sShortMessageManager;

   private DaoMaster daoMaster;
   private DaoSession daoSession;

   private ShortMessageManager(Context context){

       daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());

       daoSession = daoMaster.newSession();
   }

   public static ShortMessageManager getInstance(Context context){
       if(sShortMessageManager == null){
           sShortMessageManager = new ShortMessageManager(context);
       }
       return sShortMessageManager;
   }

    public List<ShortMessageDao> queryShortMessageList() {
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        QueryBuilder<ShortMessageDao> qb = dao.queryBuilder().orderDesc().where(
                new WhereCondition.StringCondition(
                        " _id in " + "(select min(_id) from SHORT_MESSAGE_DAO group by NUMBER_PHONE)")
        );
        List<ShortMessageDao> list = qb.list();
        return list;
    }

    public List<ShortMessageDao> queryShortMessageLists(){
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        QueryBuilder<ShortMessageDao> qb = dao.queryBuilder().orderDesc().where(
                new WhereCondition.StringCondition(
                        " _id in " + "(select min(_id) from SHORT_MESSAGE_DAO group by NUMBER_PHONE)")
        );
        List<ShortMessageDao> list = qb.list();
        return list;
    }

    public void deleteShortMessageList(){
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        dao.deleteAll();
    }

    public void insertShortMessageList(List<ShortMessageDao> shortMessageDaos, Context context) {
        if (shortMessageDaos == null || shortMessageDaos.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShortMessageDaoDao shortMessageDaoDao = daoSession.getShortMessageDaoDao();
        shortMessageDaoDao.insertInTx(shortMessageDaos);
    }

    public void insertShortMessage(ShortMessageDao shortMessageDao,Context context){
        if (shortMessageDao == null) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ShortMessageDaoDao shortMessageDaoDao = daoSession.getShortMessageDaoDao();
        shortMessageDaoDao.insert(shortMessageDao);
    }

    public List<ShortMessageDao> queryShortMessageCondition(String phone){
       ShortMessageDaoDao daoDao = daoSession.getShortMessageDaoDao();
       QueryBuilder<ShortMessageDao> qb = daoDao.queryBuilder().
               where(ShortMessageDaoDao.Properties.NumberPhone.eq(phone)).orderAsc(ShortMessageDaoDao.Properties.Time);
       List<ShortMessageDao> list = qb.list();
       return list;
    }

    public void deleteShortMessageOfPrimaryKey(Long id){
       ShortMessageDaoDao daoDao = daoSession.getShortMessageDaoDao();
       daoDao.deleteByKey(id);
    }
}
