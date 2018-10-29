package com.tt.qzy.view.db.manager;

import android.content.Context;

import com.tt.qzy.view.db.DaoMaster;
import com.tt.qzy.view.db.DaoSession;
import com.tt.qzy.view.db.MailListDaoDao;
import com.tt.qzy.view.db.dao.MailListDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MailListManager {

    public static MailListManager sMailListManager;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private MailListManager(Context context){
        daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static MailListManager getInstance(Context context){

        if(sMailListManager == null){

            sMailListManager = new MailListManager(context);
        }

        return sMailListManager;
    }

    public List<MailListDao> queryMailList() {
        MailListDaoDao dao = daoSession.getMailListDaoDao();
        QueryBuilder<MailListDao> qb = dao.queryBuilder();
        List<MailListDao> list = qb.list();
        return list;
    }

    public void insertMailListList(List<MailListDao> mailListDaos,Context context) {
        if (mailListDaos == null || mailListDaos.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao userDao = daoSession.getMailListDaoDao();
        userDao.insertInTx(mailListDaos);
    }

    public void insertMailListSignal(MailListDao mailListDao,Context context){
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao mailListDaoDao = daoSession.getMailListDaoDao();
        mailListDaoDao.insert(mailListDao);
    }

    public void deleteMailContacts(MailListDao dao , Context context){
        DaoMaster daoMaster = new DaoMaster(DBManager.getInstance(context).getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MailListDaoDao mailListDaoDao = daoSession.getMailListDaoDao();
        mailListDaoDao.delete(dao);
    }
}
