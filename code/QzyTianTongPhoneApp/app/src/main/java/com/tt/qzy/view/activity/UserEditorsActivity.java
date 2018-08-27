package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tt.qzy.view.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserEditorsActivity extends AppCompatActivity {

    @BindView(R.id.base_tv_toolbar_title)
    TextView tab_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editors);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tab_title.setText(getResources().getString(R.string.TMT_userEditors));
    }

    @OnClick({R.id.base_iv_back,R.id.btn_exit,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                finish();
                break;
        }
    }
}
