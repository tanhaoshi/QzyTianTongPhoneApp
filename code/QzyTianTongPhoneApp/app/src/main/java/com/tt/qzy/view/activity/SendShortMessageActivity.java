package com.tt.qzy.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.MsgAdapter;
import com.tt.qzy.view.bean.MsgModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendShortMessageActivity extends AppCompatActivity {

    @BindView(R.id.base_tv_toolbar_right)
    ImageView mImageView;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_quantity)
    TextView main_quantity;
    @BindView(R.id.custom_input)
    EditText custom_input;

    private List<MsgModel> msgList = new ArrayList<>();
    private MsgAdapter adapter;
    private static final int REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_short_message);
        ButterKnife.bind(this);
        initView();
        initMsgs();
        initAdapter();
    }

    private void initView() {
        main_quantity.setText(getResources().getString(R.string.TMT_short_message));
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
    }

    private void initMsgs() {
        MsgModel msg1 = new MsgModel("Hello guy",MsgModel.TYPE_RECEIVED);
        MsgModel msg2 = new MsgModel("Hello.Who is that?",MsgModel.TYPE_SENT);
        MsgModel msg3 = new MsgModel("I am Jack.Nice talking to you.",MsgModel.TYPE_RECEIVED);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MsgAdapter(msgList);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.main_quantity,R.id.send,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_quantity:
                finish();
                break;
            case R.id.send:
                sendMessage(MsgModel.TYPE_SENT);
                break;
            case R.id.base_tv_toolbar_right:
                Intent intent = new Intent(SendShortMessageActivity.this,SelectContactsActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }

    private void sendMessage(int model){
        String content = custom_input.getText().toString();
        if (!"".equals(content)){
            MsgModel msg = new MsgModel(content,model);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size()-1);
            mRecyclerView.scrollToPosition(msgList.size() - 1);
            custom_input.setText("");
            model=model==MsgModel.TYPE_RECEIVED?MsgModel.TYPE_SENT:MsgModel.TYPE_RECEIVED;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == 1){
            String s=data.getStringExtra("back");
            main_quantity.setText(s);
        }
    }
}
