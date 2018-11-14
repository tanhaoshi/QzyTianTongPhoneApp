package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.view.ContactsActivityView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivityPresenter extends BasePresenter<ContactsActivityView>{

    private Context mContext;

    public ContactsActivityPresenter(Context context){
        this.mContext = context;
    }

    public List<MallListModel> getPhoneKeyForList(String phone){
        List<MailListDao> listModels = MailListManager.getInstance(mContext).getByPhoneList(phone);
        return mergeData(listModels);
    }

    private List<MallListModel> mergeData(List<MailListDao> listDaos){
        List<MallListModel> listModels = new ArrayList<>();
        for(MailListDao mailListDao : listDaos){
            listModels.add(new MallListModel(mailListDao.getPhone(),mailListDao.getName(),mailListDao.getId()));
        }
        return listModels;
    }
}
