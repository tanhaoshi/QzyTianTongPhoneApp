package com.tt.qzy.view.view;

import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.view.base.BaseView;

import java.util.List;

public interface ShortMessageView extends BaseView{

    void getShortMessageData(List<ShortMessageDao> list);

    void getLoadRefresh(List<ShortMessageDao> list);

    void getListSize(int listSize);

    void getDaoListSize(int daoListSize);

    void getDateSize(int dateSize);

    void getLoadMore(List<ShortMessageDao> list);

}
