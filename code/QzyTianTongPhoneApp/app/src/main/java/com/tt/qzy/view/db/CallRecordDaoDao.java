package com.tt.qzy.view.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tt.qzy.view.db.dao.CallRecordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CALL_RECORD_DAO".
*/
public class CallRecordDaoDao extends AbstractDao<CallRecordDao, Long> {

    public static final String TABLENAME = "CALL_RECORD_DAO";

    /**
     * Properties of entity CallRecordDao.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PhoneNumber = new Property(1, String.class, "phoneNumber", false, "PHONE_NUMBER");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Address = new Property(3, String.class, "address", false, "ADDRESS");
        public final static Property State = new Property(4, String.class, "state", false, "STATE");
        public final static Property Date = new Property(5, String.class, "date", false, "DATE");
        public final static Property Duration = new Property(6, long.class, "duration", false, "DURATION");
        public final static Property IsTitle = new Property(7, int.class, "isTitle", false, "IS_TITLE");
        public final static Property TitleName = new Property(8, String.class, "titleName", false, "TITLE_NAME");
    }


    public CallRecordDaoDao(DaoConfig config) {
        super(config);
    }
    
    public CallRecordDaoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CALL_RECORD_DAO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PHONE_NUMBER\" TEXT," + // 1: phoneNumber
                "\"NAME\" TEXT," + // 2: name
                "\"ADDRESS\" TEXT," + // 3: address
                "\"STATE\" TEXT," + // 4: state
                "\"DATE\" TEXT," + // 5: date
                "\"DURATION\" INTEGER NOT NULL ," + // 6: duration
                "\"IS_TITLE\" INTEGER NOT NULL ," + // 7: isTitle
                "\"TITLE_NAME\" TEXT);"); // 8: titleName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CALL_RECORD_DAO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CallRecordDao entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(5, state);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(6, date);
        }
        stmt.bindLong(7, entity.getDuration());
        stmt.bindLong(8, entity.getIsTitle());
 
        String titleName = entity.getTitleName();
        if (titleName != null) {
            stmt.bindString(9, titleName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CallRecordDao entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(5, state);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(6, date);
        }
        stmt.bindLong(7, entity.getDuration());
        stmt.bindLong(8, entity.getIsTitle());
 
        String titleName = entity.getTitleName();
        if (titleName != null) {
            stmt.bindString(9, titleName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CallRecordDao readEntity(Cursor cursor, int offset) {
        CallRecordDao entity = new CallRecordDao( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // phoneNumber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // address
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // state
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // date
            cursor.getLong(offset + 6), // duration
            cursor.getInt(offset + 7), // isTitle
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // titleName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CallRecordDao entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhoneNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAddress(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setState(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDate(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDuration(cursor.getLong(offset + 6));
        entity.setIsTitle(cursor.getInt(offset + 7));
        entity.setTitleName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CallRecordDao entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CallRecordDao entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CallRecordDao entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
