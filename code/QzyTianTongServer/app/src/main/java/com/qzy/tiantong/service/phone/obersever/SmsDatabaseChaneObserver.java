package com.qzy.tiantong.service.phone.obersever;

import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.qzy.tiantong.service.phone.data.SmsInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsDatabaseChaneObserver extends ContentObserver {

    // 只检查收件箱
    public static final Uri MMSSMS_ALL_MESSAGE_URI = Uri.parse("content://sms/inbox");
    public static final String SORT_FIELD_STRING = "_id asc";  // 排序
    public static final String DB_FIELD_ID = "_id";
    public static final String DB_FIELD_ADDRESS = "address";
    public static final String DB_FIELD_PERSON = "person";
    public static final String DB_FIELD_BODY = "body";
    public static final String DB_FIELD_DATE = "date";
    public static final String DB_FIELD_TYPE = "type";
    public static final String DB_FIELD_THREAD_ID = "thread_id";
    public static final String[] ALL_DB_FIELD_NAME = {
            DB_FIELD_ID, DB_FIELD_ADDRESS, DB_FIELD_PERSON, DB_FIELD_BODY,
            DB_FIELD_DATE, DB_FIELD_TYPE, DB_FIELD_THREAD_ID};
    public static int mMessageCount = -1;

    private static final long DELTA_TIME = 60 * 1000;
    private ContentResolver mResolver;

    private IReceiverMsgListener listener;

    public SmsDatabaseChaneObserver(ContentResolver resolver, Handler handler, IReceiverMsgListener listener) {
        super(handler);
        mResolver = resolver;
        this.listener = listener;
    }

    @Override
    public void onChange(boolean selfChange) {
        onReceiveSms();
    }

    private void onReceiveSms() {
        Cursor cursor = null;
        // 添加异常捕捉
        try {
            cursor = mResolver.query(MMSSMS_ALL_MESSAGE_URI, ALL_DB_FIELD_NAME, null, null, SORT_FIELD_STRING);
            final int count = cursor.getCount();
            if (count <= mMessageCount) {
                mMessageCount = count;
                return;
            }
            // 发现收件箱的短信总数目比之前大就认为是刚接收到新短信---如果出现意外，请神保佑
            // 同时认为id最大的那条记录为刚刚新加入的短信的id---这个大多数是这样的，发现不一样的情况的时候可能也要求神保佑了
            mMessageCount = count;
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToLast();
                final long smsdate = Long.parseLong(cursor.getString(cursor.getColumnIndex(DB_FIELD_DATE)));
                final long nowdate = System.currentTimeMillis();
                // 如果当前时间和短信时间间隔超过60秒,认为这条短信无效
                if (nowdate - smsdate > DELTA_TIME) {
                    return;
                }
                final long id = cursor.getInt(cursor.getColumnIndex(DB_FIELD_ID));
                final int type = cursor.getInt(cursor.getColumnIndex(DB_FIELD_TYPE));
                final String strAddress = cursor.getString(cursor.getColumnIndex(DB_FIELD_ADDRESS));    // 短信号码
                final String strbody = cursor.getString(cursor.getColumnIndex(DB_FIELD_BODY));          // 在这里获取短信信息
                final int smsid = cursor.getInt(cursor.getColumnIndex(DB_FIELD_ID));

                String smsDate = cursor.getString(cursor.getColumnIndex("date"));

                final int isRead;
                if(cursor.getColumnIndex("read") == -1){
                     isRead = 1;
                }else{
                     isRead = cursor.getInt(cursor.getColumnIndex("read"));
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(smsDate));
                smsDate = dateFormat.format(d);
                if (TextUtils.isEmpty(strAddress) || TextUtils.isEmpty(strbody)) {
                    return;
                }
                String smsName = cursor.getString(cursor.getColumnIndex("person"));
                if (smsName == null) {
                    smsName = "未知号码";
                }
                final SmsInfo smsInfo = new SmsInfo(id, type, smsName, strAddress, strbody, smsDate, isRead);
                // 得到短信号码和内容之后进行相关处理
                if (listener != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                listener.onReceiveMsg(smsInfo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                try {  // 有可能cursor都没有创建成功
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface IReceiverMsgListener {
        void onReceiveMsg(SmsInfo smsInfo);
    }

}
