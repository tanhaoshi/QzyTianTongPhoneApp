package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.qzy.data.PhoneCmd;

import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.data.TtPhoneState;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.utils.LogUtils;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;

import java.util.List;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class TellPhoneActivityPresenter {
    private Context mContext;


    public TellPhoneActivityPresenter(Context context) {
        mContext = context;
    }

    /**
     * 挂断
     */
    public void endCall() {
        // EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_HUNGUP));
        endCallToServer();
    }

    /**
     * 底层挂断接口
     */
    private void endCallToServer() {
        TtPhoneDataManager.getInstance().hangupTtPhone();
    }

    /**
     * 接听
     */
    public void acceptCall() {
        //EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_ACCEPTCALL));
        acceptCallToServer();
    }

    /**
     * 底层接听电话接口
     */
    private void acceptCallToServer() {
        TtPhoneDataManager.getInstance().answerTtPhone();
    }

    public String getPhoneKeyForName(String phone) {
        List<MailListDao> listModels = MailListManager.getInstance(mContext).getByPhoneList(phone);
        String name;
        if (listModels.size() > 0) {
            name = listModels.get(0).getName();
        } else {
            name = "";
        }
        return name;
    }
}
