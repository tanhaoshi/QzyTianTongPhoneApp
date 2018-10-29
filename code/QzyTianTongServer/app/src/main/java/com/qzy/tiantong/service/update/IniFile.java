package com.qzy.tiantong.service.update;

import com.qzy.tiantong.lib.utils.IniFileUtils;

import java.io.File;

public class IniFile {

    private UpdateConfigBean configBean;

    private IniFileUtils mIni;

    private static final String section_name = "tiantong_config";
    private static final String app_vesion = "app_vesion";
    private static final String server_version = "server_version";
    private static final String zip_md = "zip_md";

    public IniFile() {
        initWorkSpace();
        initIniFile();
    }

    private void initWorkSpace() {
        try {
            File file = new File("/mnt/sdcard/" + "tiantong_work");
            boolean isExsit = file.exists();
            if (!isExsit) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initIniFile() {
        try {
            File file = new File("/mnt/sdcard/tiantong_work/" + "config.ini");
            boolean isExsit = file.exists();
            if (!isExsit) {
                file.createNewFile();
            }
            mIni = new IniFileUtils(file);
            mIni.setCharSet("utf-8");
            mIni.setLineSeparator("\r\n");

            if (!isExsit) {
                mIni.set(section_name, app_vesion, "1.0.1");
                mIni.set(section_name, server_version, "1");
                mIni.set(section_name, zip_md, "");
                mIni.save();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取配置
     *
     * @param flag true 强制读取一次
     * @return
     */
    public UpdateConfigBean getUpdateConfigBean(boolean flag) {
        if (configBean == null) {
            configBean = new UpdateConfigBean();
            flag = true;
        }
        if (flag) {
            configBean.setApp_version((String) mIni.get(section_name, app_vesion, ""));
            configBean.setServer_version((String) mIni.get(section_name, server_version, ""));
            configBean.setZip_md((String) mIni.get(section_name, zip_md, ""));
        }
        return configBean;
    }

}
