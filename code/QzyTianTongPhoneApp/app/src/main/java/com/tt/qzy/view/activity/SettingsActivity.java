package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.utils.NToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @BindView(R.id.setting_map)
    TextView setting_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.settings_sos,R.id.setting_map,R.id.setting_about,R.id.main_quantity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.settings_sos:
                ininDialog();
                break;
            case R.id.setting_map:
                Intent intent = new Intent(SettingsActivity.this,OffLineMapActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_about:
                Intent about_intent = new Intent(SettingsActivity.this,MainAboutActivity.class);
                startActivity(about_intent);
                break;
            case R.id.main_quantity:
                finish();
                break;
        }
    }

    public void ininDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SettingsActivity.this);
        View v = inflater.inflate(R.layout.customied_dialog_style, null);
        final EditText custom_input = (EditText)v.findViewById(R.id.custom_input);
        final TextView custom_cannel = (TextView)v.findViewById(R.id.custom_cannel);
        final TextView custom_yes = (TextView)v.findViewById(R.id.custom_yes);
        dialog = builder.create();
        dialog.setView(inflater.inflate(R.layout.customied_dialog_style, null));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        custom_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSoftInputFromWindow(custom_input);
            }
        });

        custom_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dialog.dismiss();
            }
        });

        custom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NToast.shortToast(SettingsActivity.this,getResources().getString(R.string.TMT_share));
            }
        });
    }

    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
