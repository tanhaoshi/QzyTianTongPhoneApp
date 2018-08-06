package com.qzy.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class ToastUtils {

    public static final void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
