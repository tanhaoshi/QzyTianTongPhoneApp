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

    /**
     * Require the objects are not null.
     *
     * @param objects The object.
     * @throws NullPointerException if any object is null in objects
     */
    public static void requireNonNull(final Object... objects) {
        if (objects == null) throw new NullPointerException();
        for (Object object : objects) {
            if (object == null) throw new NullPointerException();
        }
    }
}
