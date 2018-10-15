package com.tt.qzy.view.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;


public final class ActivityUtils {

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity The activity.
     * @param clz      The activity class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(), null);
    }

    private static void startActivity(Activity activity, Bundle extras, String packageName, String name, Object o) {

    }
}
