package com.tt.qzy.view.view;

import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface DeleteContactsView extends BaseView{
    void getContactsList(List<MallListModel> listModels);
    void getContactsDao(List<MailListDao> mailListDaos);
}
