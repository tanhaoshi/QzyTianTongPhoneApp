package com.qzy.tiantong.service.utils;

import android.os.UserHandle;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class QzyUserHandler {

    public static UserHandle getUserHandleALL() {
        UserHandle userHandle = null;
        try {
            Field[] fields = UserHandle.class.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (field.getName().equals("ALL")) {
                    //LogUtils.e("get userHandle ..........");
                    userHandle = (UserHandle) field.get(UserHandle.class);
                    // LogUtils.e("get userHandle success ..........");
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userHandle;
    }

}
