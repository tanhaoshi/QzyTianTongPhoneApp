package com.tt.qzy.view.view;

import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface ImportMailView extends BaseView{

    void getContactsMallList(List<MallListModel> listModels);
}
