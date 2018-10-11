// IKernelManager.aidl
package com.qzy;

import com.qzy.IDataCallback;

interface IPcmManager {
    void start();
    void setDataCallback(IDataCallback callBack);
    byte[] getPcmData();
    void removePcmData();
    void writePcmData(in byte[] data);
    void stop();
}
