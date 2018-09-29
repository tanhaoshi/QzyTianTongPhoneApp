package com.tt.qzy.view.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tt.qzy.view.db.dao.ShortMessageDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SHORT_MESSAGE_DAO".
*/
public class ShortMessageDaoDao extends AbstractDao<ShortMessageDao, Long> {

    public static final String TABLENAME = "SHORT_MESSAGE_DAO";

    /**
     * Properties of entity ShortMessageDao.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property NumberPhone = new Property(1, String.class, "numberPhone", false, "NUMBER_PHONE");
        public final static Property Message = new Property(2, String.class, "message", false, "MESSAGE");
        public final static Property Time = new Property(3, String.class, "time", false, "TIME");
        public final static Property IsTitle = new Property(4, int.class, "isTitle", false, "IS_TITLE");
        public final static Property TitleName = new Property(5, String.class, "titleName", false, "TITLE_NAME");
        public final static Property IsCheck = new Property(6, boolean.class, "isCheck", false, "IS_CHECK");
    }


    public ShortMessageDaoDao(DaoConfig config) {
        super(config);
    }
    
    public ShortMessageDaoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHORT_MESSAGE_DAO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NUMBER_PHONE\" TEXT," + // 1: numberPhone
                "\"MESSAGE\" TEXT," + // 2: message
                "\"TIME\" TEXT," + // 3: time
                "\"IS_TITLE\" INTEGER NOT NULL ," + // 4: isTitle
                "\"TITLE_NAME\" TEXT," + // 5: titleName
                "\"IS_CHECK\" INTEGER NOT NULL );"); // 6: isCheck
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHORT_MESSAGE_DAO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShortMessageDao entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String numberPhone = entity.getNumberPhone();
        if (numberPhone != null) {
            stmt.bindString(2, numberPhone);
        }
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(3, message);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
        stmt.bindLong(5, entity.getIsTitle());
 
        String titleName = entity.getTitleName();
        if (titleName != null) {
            stmt.bindString(6, titleName);
        }
        stmt.bindLong(7, entity.getIsCheck() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShortMessageDao entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String numberPhone = entity.getNumberPhone();
        if (numberPhone != null) {
            stmt.bindString(2, numberPhone);
        }
 
        String message = entity.getMessage();
        if (message != null) {
            stmt.bindString(3, message);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
        stmt.bindLong(5, entity.getIsTitle());
 
        String titleName = entity.getTitleName();
        if (titleName != null) {
            stmt.bindString(6, titleName);
        }
        stmt.bindLong(7, entity.getIsCheck() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ShortMessageDao readEntity(Cursor cursor, int offset) {
        ShortMessageDao entity = new ShortMessageDao( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // numberPhone
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // message
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // time
            cursor.getInt(offset + 4), // isTitle
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // titleName
            cursor.getShort(offset + 6) != 0 // isCheck
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ShortMessageDao entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNumberPhone(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMessage(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsTitle(cursor.getInt(offset + 4));
        entity.setTitleName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsCheck(cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ShortMessageDao entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ShortMessageDao entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShortMessageDao entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
