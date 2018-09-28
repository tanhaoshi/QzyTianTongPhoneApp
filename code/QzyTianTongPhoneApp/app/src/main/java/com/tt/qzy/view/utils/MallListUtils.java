package com.tt.qzy.view.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.socks.library.KLog;
import com.tt.qzy.view.bean.MallListModel;

import java.util.ArrayList;
import java.util.List;

public class MallListUtils {

    public static List<MallListModel> readContacts(Context context) {
        try {
            List<MallListModel> mallListModels = null;
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if(cursor != null){
                mallListModels = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    String contact = cursor.getString(nameFieldColumnIndex);
                    String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                    String PhoneNumber = null;
                    while (phone.moveToNext()) {
                        PhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        PhoneNumber = PhoneNumber.replace("-", "");
                        PhoneNumber = PhoneNumber.replace(" ", "");
                    }
                    mallListModels.add(new MallListModel(PhoneNumber,contact));
                }
                return mallListModels;
            }else{
                KLog.i("custor is null !");
            }
        }catch (Exception e){
            e.printStackTrace();
            KLog.i("error : " + e.getMessage().toString());
        }
        return null;
    }
}
