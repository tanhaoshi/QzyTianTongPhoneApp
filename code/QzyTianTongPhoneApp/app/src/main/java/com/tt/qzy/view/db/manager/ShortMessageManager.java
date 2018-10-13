package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.tt.qzy.view.db.CallRecordDaoDao;
import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.ShortMessageDaoDao;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;

import org.greenrobot.greendao.query.QueryBuilder;

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
        QueryBuilder<ShortMessageDao> qb = dao.queryBuilder();
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
}
