package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tt.qzy.view.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
}
