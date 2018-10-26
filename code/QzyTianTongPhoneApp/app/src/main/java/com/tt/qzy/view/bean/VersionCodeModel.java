package com.tt.qzy.view.bean;

import java.sql.Date;

public class VersionCodeModel {
    //下载路径
    private String loadUrl;
    //版本号
    private String versionCode;
    //版本名称
    private String versionName;
    //更新标题
    private String titleName;
    //更新内容
    private String changeContent;
    //时间
    private Date createDate;

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private VersionCodeModel(Builder builder){

    }

    public static class Builder{
        //下载路径
        private String loadUrl;
        //版本号
        private String versionCode;
        //版本名称
        private String versionName;
        //更新标题
        private String titleName;
        //更新内容
        private String changeContent;
        //时间
        private Date createDate;

        public Builder setLoadUrl(String loadUrl){
            this.loadUrl = loadUrl;
            return this;
        }

        public Builder setVersionCode(String versionCode){
            this.versionCode = versionCode;
            return this;
        }

        public Builder setVersionName(String versionName){
            this.versionName = versionName;
            return this;
        }

        public Builder setTitleName(String titleName){
            this.titleName = titleName;
            return this;
        }

        public Builder setChangeContent(String changeContent){
            this.changeContent = changeContent;
            return this;
        }

        public Builder setCreateDate(Date date){
            this.createDate = date;
            return this;
        }

        public VersionCodeModel build(){
            return new VersionCodeModel(this);
        }
    }
}
