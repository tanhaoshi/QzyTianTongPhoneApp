package com.qzy.androidftp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class MainActivity extends AppCompatActivity {

    private FtpClienManager mFtpClienManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFtpClienManager = new FtpClienManager();
        mFtpClienManager.ftpConnet(new FtpClienManager.IConnectListener() {
            @Override
            public void onConnected(boolean isConnect) {
                if (isConnect) {
                    upload();
                }
            }
        });

    }

    private void upload() {
        mFtpClienManager.upload("/mnt/sdcard/tiantong_update.zip", new FTPDataTransferListener() {
            @Override
            public void started() {
                LogUtils.e("-----------------started");
            }

            @Override
            public void transferred(int i) {
                //                   LogUtils.d("-----------------transferred");
            }

            @Override
            public void completed() {
                LogUtils.e("-----------------completed");
            }

            @Override
            public void aborted() {
                LogUtils.e("-----------------aborted");
            }

            @Override
            public void failed() {
                LogUtils.e("-----------------failed");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mFtpClienManager.ftpClose();
    }

}
