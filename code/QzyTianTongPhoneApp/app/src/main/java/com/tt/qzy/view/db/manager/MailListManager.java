package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.MailListDaoDao;
import com.tt.qzy.view.db.dao.MailListDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MailListManager {

    public static volatile MailListManager sMailListManager;

    private DaoSession daoSession;

    private MailListManager(Context context){
        daoSession = TtPhoneApplication.getInstance().getDaoSession();
    }

    public static MailListManager getInstance(Context context){
        if(sMailListManager == null){
            synchronized (MailListManager.class){
                if (sMailListManager == null){
                    sMailListManager = new MailListManager(context);
                }
            }
        }

        return sMailListManager;
    }

    public List<MailListDao> queryMailList() {
        MailListDaoDao dao = daoSession.getMailListDaoDao();
        QueryBuilder<MailListDao> qb = dao.queryBuilder();
        List<MailListDao> list = qb.list();
        return list;
    }

    public MailListDao queryIdMail(Long id){
        MailListDaoDao daoDao = daoSession.getMailListDaoDao();
        MailListDao mailListDao = daoDao.load(id);
        return mailListDao;
    }

    public void insertMailListList(List<MailListDao> mailListDaos, Context context) {
        if (mailListDaos == null || mailListDaos.isEmpty()) {
            return;
        }
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao userDao = daoSession.getMailListDaoDao();
        userDao.insertInTx(mailListDaos);
    }

    public void insertMailListSignal(MailListDao mailListDao, Context context){
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao mailListDaoDao = daoSession.getMailListDaoDao();
        mailListDaoDao.insert(mailListDao);
    }

    public void deleteMailContacts(MailListDao dao , Context context){
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao mailListDaoDao = daoSession.getMailListDaoDao();
        mailListDaoDao.delete(dao);
    }

    public void deleteAllMail(Context context){
//        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao mailListDaoDao = daoSession.getMailListDaoDao();
        mailListDaoDao.deleteAll();
    }

    public List<MailListDao> fuzzyMailSearch(String value){
        MailListDaoDao dao = daoSession.getMailListDaoDao();
        QueryBuilder<MailListDao> db = dao.queryBuilder().where(MailListDaoDao.Properties.Name.like("%"+value+"%"));
        List<MailListDao> daoList = db.list();
        if(daoList.size() > 0){
            return daoList;
        }else{
            QueryBuilder<MailListDao> queryBuilder = dao.queryBuilder().where(MailListDaoDao.Properties.Phone.like("%"+value+"%"));
            return queryBuilder.list();
        }
    }

    public void deleteByPrimaryKey(Long id){
        MailListDaoDao daoDao = daoSession.getMailListDaoDao();
        daoDao.deleteByKey(id);
    }

    public List<MailListDao> getByPhoneList(String phone){
        MailListDaoDao dao = daoSession.getMailListDaoDao();
        QueryBuilder<MailListDao> qb = dao.queryBuilder().where(MailListDaoDao.Properties.Phone.eq(phone));
        List<MailListDao> list = qb.list();
        return list;
    }
}
