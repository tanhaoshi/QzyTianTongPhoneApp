package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.utils.NToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserEditorsActivity extends AppCompatActivity {

    @BindView(R.id.base_tv_toolbar_title)
    TextView tab_title;
    @BindView(R.id.consigneeName)
    EditText consigneeName;

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
                if(TextUtils.isEmpty(consigneeName.getText().toString().trim())){
                    NToast.shortToast(this,"用户名不能为空");
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("code",consigneeName.getText().toString());
                    setResult(1,intent);
                    finish();
                }
                break;
        }
    }
}
