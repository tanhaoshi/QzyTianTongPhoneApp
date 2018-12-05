package com.hwacreate.at;

import android.app.Activity;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class AtDemo extends Activity{

    ArrayList<Phone> mPhoneList = new ArrayList<Phone>();
    Phone mPhone = null;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_demo);
        btn = (Button)findViewById(R.id.at_button);
        btn.setOnClickListener(btnClick);
        mPhone = PhoneFactory.getPhone(1);
        if(mPhone==null){
            Log.e("AtDemo", "failed to get mPhone\n");
            mPhone = PhoneFactory.getPhone(0);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        String result = msg.getData().getString("result");
                        if (result != null && result.trim().contains("OK")) {
                            Toast.makeText(AtDemo.this, getString(R.string.send_success), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Thread t = new Thread(new Runnable(){
                public void run(){
                    AtThread atThread = new AtThread("AtCommandSendSync", "AT^BEIDOU=1", true, 12*1000, handler, 1);
                    atThread.start();
                    String result = atThread.sendAt(mPhone);
                    atThread.exit();
                }
            });
            t.start();
        }
    };

    
}
