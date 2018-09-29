package com.tt.qzy.view.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;

import com.tt.qzy.view.db.CallRecordDaoDao;
import com.tt.qzy.view.db.MailListDaoDao;
import com.tt.qzy.view.db.ShortMessageDaoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig callRecordDaoDaoConfig;
    private final DaoConfig mailListDaoDaoConfig;
    private final DaoConfig shortMessageDaoDaoConfig;

    private final CallRecordDaoDao callRecordDaoDao;
    private final MailListDaoDao mailListDaoDao;
    private final ShortMessageDaoDao shortMessageDaoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        callRecordDaoDaoConfig = daoConfigMap.get(CallRecordDaoDao.class).clone();
        callRecordDaoDaoConfig.initIdentityScope(type);

        mailListDaoDaoConfig = daoConfigMap.get(MailListDaoDao.class).clone();
        mailListDaoDaoConfig.initIdentityScope(type);

        shortMessageDaoDaoConfig = daoConfigMap.get(ShortMessageDaoDao.class).clone();
        shortMessageDaoDaoConfig.initIdentityScope(type);

        callRecordDaoDao = new CallRecordDaoDao(callRecordDaoDaoConfig, this);
        mailListDaoDao = new MailListDaoDao(mailListDaoDaoConfig, this);
        shortMessageDaoDao = new ShortMessageDaoDao(shortMessageDaoDaoConfig, this);

        registerDao(CallRecordDao.class, callRecordDaoDao);
        registerDao(MailListDao.class, mailListDaoDao);
        registerDao(ShortMessageDao.class, shortMessageDaoDao);
    }
    
    public void clear() {
        callRecordDaoDaoConfig.clearIdentityScope();
        mailListDaoDaoConfig.clearIdentityScope();
        shortMessageDaoDaoConfig.clearIdentityScope();
    }

    public CallRecordDaoDao getCallRecordDaoDao() {
        return callRecordDaoDao;
    }

    public MailListDaoDao getMailListDaoDao() {
        return mailListDaoDao;
    }

    public ShortMessageDaoDao getShortMessageDaoDao() {
        return shortMessageDaoDao;
    }

}