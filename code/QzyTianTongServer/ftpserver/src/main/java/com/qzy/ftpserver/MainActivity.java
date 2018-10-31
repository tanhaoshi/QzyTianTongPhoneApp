package com.qzy.ftpserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FtpServerManager mFtpServerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFtpServerManager = new FtpServerManager();
        mFtpServerManager.onStartServer();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mFtpServerManager.stopFtpServer();
    }
}
