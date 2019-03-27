package com.qzy.tt.phone.data;

import com.qzy.tt.phone.data.impl.IMainFragment;
import com.qzy.tt.phone.data.impl.ITtPhoneDataListener;
import com.qzy.tt.phone.data.impl.ITtPhoneHandlerManager;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.socks.library.KLog;

/**
 * 数据管理类
 */
public class TtPhoneDataManager implements ITtPhoneHandlerManager {

    private static TtPhoneDataManager instance;

    private PhoneNettyManager phoneNettyManager;

    public static TtPhoneDataManager getInstance() {
        return instance;
    }

    /**
     * 初始化
     */
    public static void init(PhoneNettyManager manager) {
        KLog.i("是否初始化!");
        instance = new TtPhoneDataManager(manager);
    }

    private TtPhoneDataManager(PhoneNettyManager manager) {
        phoneNettyManager = manager;
    }


    /**
     * 注册数据回调
     *
     * @param iTtPhoneDataListener
     */
    public void setTtPhoneDataListener(ITtPhoneDataListener iTtPhoneDataListener) {
        phoneNettyManager.getmCmdHandler().setmDataListener(iTtPhoneDataListener);
    }

    /** 注册MainFragment数据回调接口就*/
    public void setMainFragmentListener(IMainFragment iMainFragment){
        phoneNettyManager.getmCmdHandler().setIMainFragment(iMainFragment);
    }




    public void free() {
        instance = null;
    }


    @Override
    public void connectTtPhoneServer(String ip, int port) {
        phoneNettyManager.connect(port, ip);
    }

}
