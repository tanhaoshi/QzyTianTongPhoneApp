package com.qzy.tiantong.service.phone;

import com.qzy.tiantong.service.phone.data.ClientInfoBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class PhoneClientManager {

    private static PhoneClientManager manager;


    public static PhoneClientManager getInstance() {
        if (manager == null) {
            manager = new PhoneClientManager();
        }
        return manager;
    }


    private ConcurrentHashMap<String, ClientInfoBean> mHaspMapPhoneClient;


    public PhoneClientManager() {
        mHaspMapPhoneClient = new ConcurrentHashMap<>();
    }


    /**
     * 新加入客户端
     *
     * @param ip
     * @param ctx
     */
    public boolean addPhoneClient(String ip, ChannelHandlerContext ctx) {
        ClientInfoBean infoBean = mHaspMapPhoneClient.get(ip);
        if (infoBean == null) {
            mHaspMapPhoneClient.put(ip, new ClientInfoBean(ip, ctx, false));
            return true;
        }
        infoBean.setCtx(ctx);
        infoBean.setIp(ip);
        mHaspMapPhoneClient.put(ip, infoBean);
        return true;
    }

    /**
     * 客户端退出
     *
     * @param ip
     */
    public boolean removePhoneClient(String ip) {
        ClientInfoBean infoBean = mHaspMapPhoneClient.get(ip);
        if (infoBean != null) {
            mHaspMapPhoneClient.remove(ip);
            return true;
        }
        return false;
    }


    /**
     * 获取发送句柄
     *
     * @param ip
     * @return
     */
    public ChannelHandlerContext getChannelHandlerContext(String ip) {
        ClientInfoBean infoBean = mHaspMapPhoneClient.get(ip);
        if (infoBean != null) {
            return infoBean.getCtx();
        }
        return null;
    }

    public ConcurrentHashMap<String, ClientInfoBean> getmHaspMapPhoneClient() {
        return mHaspMapPhoneClient;
    }

    /**
     * 设置当前通话用户
     *
     * @param ip
     */
    public void setCurrentCallingUser(String ip) {
        ClientInfoBean infoBean = mHaspMapPhoneClient.get(ip);
        if (infoBean != null) {
            infoBean.setCalling(true);
            mHaspMapPhoneClient.put(ip, infoBean);
        }
    }

    /**
     * 设置用户通话结束
     *
     * @param ip
     */
    public void setEndCallUser(String ip) {
        ClientInfoBean infoBean = mHaspMapPhoneClient.get(ip);
        if (infoBean != null) {
            infoBean.setCalling(false);
            mHaspMapPhoneClient.put(ip, infoBean);
        }
    }

    /**
     * 挂断状态设置通话状态为挂断
     */
    public void setEndCallUser() {
        ConcurrentHashMap<String, ClientInfoBean> hashMap = PhoneClientManager.getInstance().getmHaspMapPhoneClient();
        for (Map.Entry<String, ClientInfoBean> entry : hashMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().isCalling()) {
                entry.getValue().setCalling(false);
            }
        }
    }

    /**
     * 当前通话的ip
     *
     * @return
     */
    public String isCallingIp() {

        ConcurrentHashMap<String, ClientInfoBean> hashMap = PhoneClientManager.getInstance().getmHaspMapPhoneClient();

        if (hashMap == null) {
            return null;
        }

        for (Map.Entry<String, ClientInfoBean> entry : hashMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().isCalling()) {

                return entry.getValue().getIp();
            }
        }
        return null;
    }


    public void free() {
        mHaspMapPhoneClient = null;
        manager = null;
    }
}
