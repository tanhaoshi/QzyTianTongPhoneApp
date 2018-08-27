package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.ContactsAdapter;
import com.tt.qzy.view.bean.ContactsModel;
import com.tt.qzy.view.layout.PopContactsWindow;
import com.tt.qzy.view.layout.PopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsActivity extends AppCompatActivity implements PopWindow.OnDismissListener{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.loding_more)
    TextView loding_more;
    @BindView(R.id.take_up)
    TextView take_up;

    private List<ContactsModel> mModelList = new ArrayList<>();
    private List<ContactsModel> openList = new ArrayList<>();
    private ContactsAdapter contactsAdapter;

    private PopContactsWindow mPopContactsWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initView();
        initAdapter();
    }

    private void initView() {
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_contacts));
        base_iv_back.setImageDrawable(getResources().getDrawable(R.drawable.iv_back));
        base_tv_toolbar_right.setImageDrawable(getResources().getDrawable(R.drawable.more));
    }

    private void initAdapter(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        contactsAdapter = new ContactsAdapter(this);
        mRecyclerView.setAdapter(contactsAdapter);
        mModelList.add(new ContactsModel("2018年8月20日 11时:36分","13786686688","未接通"));
        mModelList.add(new ContactsModel("2018年8月20日 11时:36分","13786686688","呼出42秒"));
        mModelList.add(new ContactsModel("2018年8月20日 11时:36分","13786686688","未接通"));
        mModelList.add(new ContactsModel("2018年8月20日 11时:36分","13786686688","呼出42秒"));
        mModelList.add(new ContactsModel("2018年8月20日 11时:36分","13786686688","未接通"));
        setAdapter(mModelList);
    }

    @OnClick({R.id.loding_more,R.id.take_up,R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.loding_more:
                contactsAdapter.setMsgs(mModelList);
                loding_more.setVisibility(View.GONE);
                take_up.setVisibility(View.VISIBLE);
                break;
            case R.id.take_up:
                contactsAdapter.setMsgs(openList);
                loding_more.setVisibility(View.VISIBLE);
                take_up.setVisibility(View.GONE);
                break;
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                if(mPopContactsWindow == null){
                    mPopContactsWindow = new PopContactsWindow(ContactsActivity.this);
                    mPopContactsWindow.setOnDismissListener(this);
                    setWindowAttibus(0.5f);
                    mPopContactsWindow.showAtLocation(this.findViewById(R.id.recyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    mPopContactsWindow.showAtLocation(this.findViewById(R.id.recyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
        }
    }

    private void setAdapter(List<ContactsModel> list){
        if(list.size() > 3){
            loding_more.setVisibility(View.VISIBLE);
            openList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                openList.add(list.get(i));
            }
            contactsAdapter.setMsgs(openList);
        }else {
            loding_more.setVisibility(View.GONE);
            contactsAdapter.setMsgs(mModelList);
        }
    }

    private void setWindowAttibus(float color){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = color;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setWindowAttibus(1f);
    }
}
