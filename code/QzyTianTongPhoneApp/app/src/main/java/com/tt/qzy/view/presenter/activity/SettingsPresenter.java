package com.tt.qzy.view.presenter.activity;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtBeiDouStatuss;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.presenter.baselife.BasePresenter;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.view.SettingsView;



public class SettingsPresenter extends BasePresenter<SettingsView>{

    private Context mContext;

    public SettingsPresenter(Context context){
        this.mContext = context;
       // EventBus.getDefault().register(this);
    }

    /**
     * 打开usb开关
     */
    public void openUsbSwtich(boolean isSwitch){
        if(TtPhoneDataManager.getInstance() != null){
            TtBeidouOpenBean ttBeidouOpenBean = new TtBeidouOpenBean(isSwitch);
            if(isSwitch) {
                TtPhoneDataManager.getInstance().openUsbMode(ttBeidouOpenBean);
            }else{
                TtPhoneDataManager.getInstance().openUsbMode(ttBeidouOpenBean);
            }
        }
    }

    /**
     * 设备是否连接上北斗卫星
     */
    public boolean getTianTongConnectBeiDou(Object obj){
        PhoneCmd cmd = (PhoneCmd)obj;
        TtBeiDouStatuss.TtBeiDouStatus ttBeiDouStatus = (TtBeiDouStatuss.TtBeiDouStatus)cmd.getMessage();
        boolean isConnect = ttBeiDouStatus.getIsBeiDouStatus();
        return isConnect;
    }

    /**
     * 恢复出厂设置
     */
    public void recoverSystem(Context context){
        CallRecordManager.getInstance(context).deleteRecordList();
        ShortMessageManager.getInstance(context).deleteShortMessageList();
        MailListManager.getInstance(context).deleteAllMail(context);
        SPUtils.clearShare(context);

    }

    public void release(){
       // EventBus.getDefault().unregister(this);
    }
}
