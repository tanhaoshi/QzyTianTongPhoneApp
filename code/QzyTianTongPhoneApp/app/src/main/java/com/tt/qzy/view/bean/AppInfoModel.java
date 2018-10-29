package com.tt.qzy.view.bean;

public class AppInfoModel {
    private String ip;
    private String phoneAppVersion;
    private String serverAppVersion;
    private String tiantongUpdateMd;

    public AppInfoModel(String ip, String phoneAppVersion, String serverAppVersion, String tiantongUpdateMd) {
        this.ip = ip;
        this.phoneAppVersion = phoneAppVersion;
        this.serverAppVersion = serverAppVersion;
        this.tiantongUpdateMd = tiantongUpdateMd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPhoneAppVersion() {
        return phoneAppVersion;
    }

    public void setPhoneAppVersion(String phoneAppVersion) {
        this.phoneAppVersion = phoneAppVersion;
    }

    public String getServerAppVersion() {
        return serverAppVersion;
    }

    public void setServerAppVersion(String serverAppVersion) {
        this.serverAppVersion = serverAppVersion;
    }

    public String getTiantongUpdateMd() {
        return tiantongUpdateMd;
    }

    public void setTiantongUpdateMd(String tiantongUpdateMd) {
        this.tiantongUpdateMd = tiantongUpdateMd;
    }
}
