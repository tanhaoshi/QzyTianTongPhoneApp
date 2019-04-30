package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.tt.qzy.view.MainActivity;
import com.tt.qzy.view.R;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;

public class DBMActivity extends AppCompatActivity {

    SwitchCompat mSwitchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbm);
        initView();
        initListener();
    }

    private void initView() {
        mSwitchCompat = (SwitchCompat) findViewById(R.id.sc_settin_testxinlv);
        mSwitchCompat.setChecked((Boolean)SPUtils.getShare(DBMActivity.this,Constans.CHECK_DBM_OPEN,false));
    }

    private void initListener(){
        mSwitchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSwitchValue(mSwitchCompat.isChecked());
            }
        });
    }

    private void setSwitchValue(boolean switchValue){
        SPUtils.putShare(DBMActivity.this, Constans.CHECK_DBM_OPEN,switchValue);
        Intent intent = new Intent(DBMActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
