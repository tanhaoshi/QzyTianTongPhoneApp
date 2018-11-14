package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.utils.APKVersionCodeUtils;
import com.tt.qzy.view.utils.Constans;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAboutActivity extends AppCompatActivity {

    @BindView(R.id.about_version)
    TextView about_version;
    @BindView(R.id.about_soft_version)
    TextView about_soft_version;
    @BindView(R.id.about_fixed_version)
    TextView about_fixed_version;
    @BindView(R.id.about_number)
    TextView about_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        about_version.setText(String.valueOf(APKVersionCodeUtils.getVerName(this)));
        about_soft_version.setText(Constans.SERVER_APP_VERSION_NAME);
    }

    @OnClick({R.id.main_quantity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_quantity:
                finish();
                break;
        }
    }
}
