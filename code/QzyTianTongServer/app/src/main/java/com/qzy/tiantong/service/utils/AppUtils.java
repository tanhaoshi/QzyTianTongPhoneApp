package com.qzy.tiantong.service.utils;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class AppUtils {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String decimalDouble(double latItude){
        DecimalFormat df = new DecimalFormat("##0.0000");
        String handleValue = df.format(latItude);
        return handleValue;
    }
}
