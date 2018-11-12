package com.qzy.tiantong.service.update;

public class UpdateLocalConfigBean {
    private boolean isUpdateStart = false;
   private String backup = "0";
    private String update= "0";
    private String recover = "0";
    private String reboot = "0";

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getRecover() {
        return recover;
    }

    public void setRecover(String recover) {
        this.recover = recover;
    }

    public String getReboot() {
        return reboot;
    }

    public void setReboot(String reboot) {
        this.reboot = reboot;
    }

    public boolean isUpdateStart() {
        return isUpdateStart;
    }

    public void setUpdateStart(boolean updateStart) {
        isUpdateStart = updateStart;
    }
}
