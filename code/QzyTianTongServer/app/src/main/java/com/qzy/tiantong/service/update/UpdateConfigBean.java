package com.qzy.tiantong.service.update;

public class UpdateConfigBean {
    private String app_version;
    private String server_version;
    private String zip_md;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getServer_version() {
        return server_version;
    }

    public void setServer_version(String server_version) {
        this.server_version = server_version;
    }

    public String getZip_md() {
        return zip_md;
    }

    public void setZip_md(String zip_md) {
        this.zip_md = zip_md;
    }
}
