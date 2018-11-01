package com.tt.qzy.view.view;

import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface DeleteShortMessageView extends BaseView{
     void getShortMessageList(List<ShortMessageDao> list);
}
