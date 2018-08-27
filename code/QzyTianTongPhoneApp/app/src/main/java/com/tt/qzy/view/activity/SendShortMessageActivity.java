package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tt.qzy.view.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendShortMessageActivity extends AppCompatActivity {

    @BindView(R.id.base_tv_toolbar_right)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_short_message);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
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
