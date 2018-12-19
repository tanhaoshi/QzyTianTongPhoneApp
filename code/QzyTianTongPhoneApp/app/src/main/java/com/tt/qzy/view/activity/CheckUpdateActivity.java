package com.tt.qzy.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.VersionCodeModel;
import com.tt.qzy.view.layout.NiftyExpandDialog;
import com.tt.qzy.view.network.OkHttpUtil;
import com.tt.qzy.view.presenter.activity.CheckUpdatePresenter;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.view.CheckUpdateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckUpdateActivity extends AppCompatActivity implements CheckUpdateView{

    @BindView(R.id.main_quantity)
    TextView main_quantity;
    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.circle_loading_view)
    AnimatedCircleLoadingView mCircleLoadingView;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.about_version)
    TextView about_version;

    private CheckUpdatePresenter mPresenter;
    private KProgressHUD mHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update);
        ButterKnife.bind(this);
        mPresenter = new CheckUpdatePresenter(this);
        mPresenter.onBindView(this);
        initView();
    }

    private void initView(){
        main_quantity.setText("检查更新");
        about_version.setText("当前版本号:"+AppUtils.getVersionName(CheckUpdateActivity.this));
        initProgress();
    }

    @OnClick({R.id.main_quantity,R.id.checkUpdate})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_quantity:
                finish();
                break;
            case R.id.checkUpdate:
                mHUD.show();
                if(!OkHttpUtil.isNetWorkAvailable()){
                    mPresenter.getAppversionRequest();
                }else{
                    NToast.shortToast(CheckUpdateActivity.this,"当前无网络!");
                    mHUD.dismiss();
                }
                break;
        }
    }

    @Override
    public void getCheckUpdate(VersionCodeModel versionCodeModel) {
        if(mPresenter.checkUpdate(versionCodeModel)){
            niftyDialog(versionCodeModel);
        }else{
            NToast.shortToast(CheckUpdateActivity.this,"当前无最新版本!");
        }
    }

    private void niftyDialog(final VersionCodeModel versionCodeModel){
        final NiftyExpandDialog dialogBuilder = NiftyExpandDialog.getInstance(CheckUpdateActivity.this).initDialogBuilder();
        dialogBuilder.nonCanceDismiss(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDownLoader(versionCodeModel);
                        dialogBuilder.niftyDismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.niftyDismiss();
                    }
                })
                .show();
        TextView content = (TextView)dialogBuilder.getNiftyDialogBuilder().findViewById(R.id.content);
        content.setText(versionCodeModel.getData().getChangeContent());
    }

    public void startDownLoader(VersionCodeModel versionCodeModel){
        mPresenter.startUploader(versionCodeModel.getData().getLoadUrl());
    }

    public void viewTransition(boolean isChange){
        if(isChange){
            mLinearLayout.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
        }else{
            mLinearLayout.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStartDownloader() {
        viewTransition(true);
        mCircleLoadingView.startDeterminate();
    }

    @Override
    public void onProgressPercent(int progressPercent) {
        mCircleLoadingView.setPercent(progressPercent);
    }

    @Override
    public void onCompelete(String installPath) {
        AppUtils.installApk(CheckUpdateActivity.this,installPath);
    }

    @Override
    public void onError(String errorMessage) {
        NToast.shortToast(CheckUpdateActivity.this,errorMessage);
    }

    @Override
    public void showProgress(boolean isTrue) {
        mHUD.show();
    }

    @Override
    public void hideProgress() {
        mHUD.dismiss();
    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {
        mHUD.dismiss();
        NToast.shortToast(CheckUpdateActivity.this,msg);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
    }

    private void initProgress(){
        mHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel(getString(R.string.TMT_loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NiftyExpandDialog.getInstance(CheckUpdateActivity.this).release();
    }
}
