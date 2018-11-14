package com.tt.qzy.view.bean;

public class VersionCodeModel {

    /**
     * code : 200
     * data : {"id":"3","loadUrl":"http://qzytest.oss-cn-shenzhen.aliyuncs.com/0.apk","versionCode":"3838438","versionName":"美国纽约","titleName":"独立日","changeContent":"外星人超大宇宙飞船","createDate":"2018-11-07 15:15:56"}
     * message : 查询成功!
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * id : 3
         * loadUrl : http://qzytest.oss-cn-shenzhen.aliyuncs.com/0.apk
         * versionCode : 3838438
         * versionName : 美国纽约
         * titleName : 独立日
         * changeContent : 外星人超大宇宙飞船
         * createDate : 2018-11-07 15:15:56
         */

        private String id;
        private String loadUrl;
        private String versionCode;
        private String versionName;
        private String titleName;
        private String changeContent;
        private String createDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
