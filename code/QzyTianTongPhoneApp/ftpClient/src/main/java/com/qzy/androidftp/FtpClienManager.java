package com.qzy.androidftp;

import android.os.Environment;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class FtpClienManager {

    private FTPClient client;

    private Thread mThread;

    public void ftpConnet(final IConnectListener listener) {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new FTPClient();
                    String host = "192.168.43.1";
                    client.connect(host, 9997);
                    client.login("admin", "123456");
                    if(listener != null){
                        listener.onConnected(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(listener != null){
                        listener.onConnected(false);
                    }
                }
            }
        });
        mThread.start();
    }

    public void ftpClose() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client != null)
                        client.disconnect(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /***下载文件***/
    public void download(final String url,FTPDataTransferListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client != null) {
                        // String url = "/11.jpg";//服务器上的文件
                        LogUtils.d("-----------------url=" + url);
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ftp");
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        File file1 = new File(file, "11.jpg");
                        client.download(url, file1, new FTPDataTransferListener() {
                            @Override
                            public void started() {
                                LogUtils.d("-----------------started");
                            }

                            @Override
                            public void transferred(int i) {
                                //                   LogUtils.d("-----------------transferred");
                            }

                            @Override
                            public void completed() {
                                LogUtils.d("-----------------completed");
                            }

                            @Override
                            public void aborted() {
                                LogUtils.d("-----------------aborted");
                            }

                            @Override
                            public void failed() {
                                LogUtils.d("-----------------failed");
                            }
                        });
                    } else {
                        LogUtils.d("-----------------client=null");
//                ftpConnet();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /***上傳文件***/
    public void upload(final String pathName, final FTPDataTransferListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client != null) {
                        File file = new File(pathName);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        LogUtils.e("-----------------dsdsfs");
                        try {
                            client.createDirectory("tiantong_update");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        client.changeDirectory("tiantong_update");
                        client.upload(file, listener);
                        client.changeDirectory("/");
                    } else {
                        LogUtils.e("-----------------client=null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public interface IConnectListener{
        void onConnected(boolean isConnect);
    }


}
