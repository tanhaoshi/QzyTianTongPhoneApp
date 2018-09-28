package com.tt.qzy.view.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tt.qzy.view.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity{

    protected ImageView img1;
    protected ImageView img2;
    protected ImageView img3;
    protected ImageView img4;
    protected ImageView img5;
    protected RelativeLayout statusLayout;

    private Unbinder m;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initStatusBar();
        initView();
        initData();
    }

    private void initStatusBar(){
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        statusLayout = (RelativeLayout) findViewById(R.id.statusLayout);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        m = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m.unbind();
    }

    public abstract int getContentView();

    public abstract void initView();

    public abstract void initData();

}
