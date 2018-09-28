package com.tt.qzy.view.view.base;

public interface BaseView {

    void showProgress(boolean isTrue);

    void hideProgress();

    void showError(String msg, boolean pullToRefresh);

    void loadData(boolean pullToRefresh);
}
