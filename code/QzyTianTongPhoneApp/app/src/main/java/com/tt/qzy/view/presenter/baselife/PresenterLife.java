package com.tt.qzy.view.presenter.baselife;

import android.support.annotation.NonNull;
import android.view.View;

import com.tt.qzy.view.view.base.BaseView;

public interface PresenterLife {
    void onCreate();
    void onBindView(@NonNull BaseView view);
    void onDestroy();
}
