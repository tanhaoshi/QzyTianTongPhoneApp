package com.qzy.tiantong.service.update;

public interface IUpdateLocalTool {
    void startLocalBackup();
    void startLocalUpdte();
    void startLocalRecover();
    void startLocalReboot();
    void free();
}
