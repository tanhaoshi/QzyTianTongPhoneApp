package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.tt.qzy.view.application.TtPhoneApplication;
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

   public static volatile ShortMessageManager sShortMessageManager;

   private DaoSession daoSession;

   private ShortMessageManager(Context context){
       daoSession = TtPhoneApplication.getInstance().getDaoSession();
   }

   public static ShortMessageManager getInstance(Context context){
       if(sShortMessageManager == null){
           synchronized (ShortMessageManager.class){
               if(sShortMessageManager == null){
                   sShortMessageManager = new ShortMessageManager(context);
               }
           }
       }
       return sShortMessageManager;
   }

    public List<ShortMessageDao> queryShortMessageList() {
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        QueryBuilder<ShortMessageDao> qb = dao.queryBuilder().orderDesc(ShortMessageDaoDao.Properties.Time).where(
                new WhereCondition.StringCondition(
                        " time in " + "(select max(time) from SHORT_MESSAGE_DAO group by NUMBER_PHONE)")
        );
        List<ShortMessageDao> list = qb.list();
        return list;
    }

    public List<ShortMessageDao> queryPrimaryOfPhone(String phone){
       ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
       if(phone == null || "".equals(phone) || phone.length() == 0){
           return null;
       }
       QueryBuilder<ShortMessageDao> queryBuilder = dao.queryBuilder().orderDesc().where(ShortMessageDaoDao.Properties.NumberPhone.eq(phone));
       List<ShortMessageDao> shortMessageDaos = queryBuilder.list();
       return shortMessageDaos;
    }

    public List<ShortMessageDao> queryList(){
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        QueryBuilder<ShortMessageDao> qb = dao.queryBuilder().orderDesc(ShortMessageDaoDao.Properties.Time);
        List<ShortMessageDao> daoList = qb.list();
        return daoList;
    }

    public void updateShortMessageName(ShortMessageDao shortMessageDao){
       ShortMessageDaoDao daoDao = daoSession.getShortMessageDaoDao();
       daoDao.update(shortMessageDao);
    }

    public void updateShortMessageList(List<ShortMessageDao> messageDaoList){
       ShortMessageDaoDao daoDao = daoSession.getShortMessageDaoDao();
       daoDao.updateInTx(messageDaoList);
    }

    public List<ShortMessageDao> limitShortMessageList(int offset,int limit){
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        List<ShortMessageDao> list = dao.queryBuilder().
                orderDesc(ShortMessageDaoDao.Properties.Time).where(new WhereCondition.StringCondition(
                " time in " + "(select max(time) from SHORT_MESSAGE_DAO group by NUMBER_PHONE)")).offset(offset).limit(limit).list();
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
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        ShortMessageDaoDao shortMessageDaoDao = daoSession.getShortMessageDaoDao();
        shortMessageDaoDao.insertInTx(shortMessageDaos);
    }

    public void insertShortMessage(ShortMessageDao shortMessageDao,Context context){
        if (shortMessageDao == null) {
            return;
        }
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
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

    public List<ShortMessageDao> fuzzySearch(String value){
        ShortMessageDaoDao dao = daoSession.getShortMessageDaoDao();
        QueryBuilder<ShortMessageDao> db = dao.queryBuilder().where(ShortMessageDaoDao.Properties.Name.like("%"+value+"%"))
                .where(new WhereCondition.StringCondition(
                        " TIME in " + "(select max(TIME) from SHORT_MESSAGE_DAO group by NAME)"));
        List<ShortMessageDao> daoList = db.list();
        if(daoList.size() > 0){
            return daoList;
        }else{
            QueryBuilder<ShortMessageDao> queryBuilder = dao.queryBuilder().where(ShortMessageDaoDao.Properties.NumberPhone.
                    like("%"+value+"%")).where(new WhereCondition.StringCondition(
                    " TIME in " + "(select max(TIME) from SHORT_MESSAGE_DAO group by NUMBER_PHONE)"));
            List<ShortMessageDao> list = queryBuilder.list();
            return list;
        }
    }


    public void deleteShortMessageOfPrimaryKey(Long id){
       ShortMessageDaoDao daoDao = daoSession.getShortMessageDaoDao();
       daoDao.deleteByKey(id);
    }

    public void deleteShortMessageOfPhone(List<ShortMessageDao> list){
        ShortMessageDaoDao daoDao = daoSession.getShortMessageDaoDao();
        daoDao.deleteInTx(list);
    }
}
