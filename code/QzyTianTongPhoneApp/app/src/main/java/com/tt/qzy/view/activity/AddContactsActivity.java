package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.utils.NToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactsActivity extends AppCompatActivity {

    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.qq)
    EditText qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_add_contacts));
    }

    private void handleData(){

        if(TextUtils.isEmpty(name.getText().toString()) ||  name.getText().toString().length() <= 0){
            NToast.shortToast(this,"名字不能为空!");
            return;
        }

        if(TextUtils.isEmpty(phone.getText().toString()) || phone.getText().toString().length() <= 0){
            NToast.shortToast(this,"电话不能为空!");
            return;
        }

        MailListDao mailListDao = new MailListDao();
        mailListDao.setName(name.getText().toString());
        mailListDao.setPhone(phone.getText().toString());
        mailListDao.setMail(email.getText().toString());
        mailListDao.setQq(qq.getText().toString());

        MailListManager.getInstance(this).insertMailListSignal(mailListDao,this);
    }

    @OnClick({R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                handleData();
                NToast.shortToast(AddContactsActivity.this,getString(R.string.TMT_save_success));
                finish();
                break;
        }
    }
}
