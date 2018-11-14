package com.tt.qzy.view.view;

import com.tt.qzy.view.bean.VersionCodeModel;
import com.tt.qzy.view.view.base.BaseView;

public interface MainActivityView extends BaseView{

    void getAppVersionCode(VersionCodeModel versionCodeModel);

    void onStartDownloader();

    void onProgressPercent(int progressPercent);

    void onCompelete(String installPath);

    void onError(String errorMessage);
}
