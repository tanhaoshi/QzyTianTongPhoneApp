package com.tt.qzy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.SendShortMessageActivity;
import com.tt.qzy.view.adapter.ShortMessageAdapter;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.layout.PopShortMessageWindow;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.presenter.fragment.ShortMessagePresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinUtils;
import com.tt.qzy.view.view.ShortMessageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShortMessageFragment extends Fragment implements PopWindow.OnDismissListener,ShortMessageAdapter.OnItemClickListener
,ShortMessageView,PopShortMessageWindow.ClearSignalListener{

    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.todayRecyclerView)
    RecyclerView todayRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private PopShortMessageWindow mPopShortMessageWindow;
    private List<ShortMessageDao> models = new ArrayList<>();
    private ShortMessageAdapter shortMessageAdapter;
    private OnKeyDownListener mOnKeyDownListener;
    private KProgressHUD mHUD;
    private ShortMessagePresenter mPresenter;

    private int offset = 0;
    private int daoListSize = 0;
    private int dateSize = 0;

    public ShortMessageFragment() {
    }

    public static ShortMessageFragment newInstance() {
        ShortMessageFragment fragment = new ShortMessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mPresenter = new ShortMessagePresenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_message, container, false);
        ButterKnife.bind(this, view);
        mPresenter.onBindView(this);
        initView();
        return view;
    }

    private void initView() {
        initProgress();
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_short_message));
        base_tv_toolbar_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.more));

        todayRecyclerView.setNestedScrollingEnabled(false);
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        shortMessageAdapter = new ShortMessageAdapter(getActivity(),models);
        shortMessageAdapter.setOnItemClickListener(this);
        todayRecyclerView.setAdapter(shortMessageAdapter);
        initListener();
    }

    private void initListener(){
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                // load more
                int count = daoListSize - offset;
                if(count > 0){
                    if(count < 20){
                        mPresenter.getLoadShortMessageMore(0,offset+count);
                        refreshlayout.finishLoadmore(200);
                    }else{
                        mPresenter.getLoadShortMessageMore(0,offset+20);
                        refreshlayout.finishLoadmore(200);
                    }
                }else if(count == 0){
                    NToast.shortToast(getActivity(),getString(R.string.TMT_dataisnull));
                    refreshlayout.finishLoadmore(200);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //  refresh
                refreshlayout.finishRefresh(200);
                mPresenter.getLoadShortMessageFresh(0,offset);
            }
        });
    }

    @OnClick({R.id.base_tv_toolbar_right,R.id.fab})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_tv_toolbar_right:
                if(mPopShortMessageWindow == null){
                    mPopShortMessageWindow = new PopShortMessageWindow(getActivity());
                    mPopShortMessageWindow.setOnDismissListener(this);
                    mPopShortMessageWindow.setOpenPictureListener(this);
                    setWindowAttibus(0.5f);
                    mPopShortMessageWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    mPopShortMessageWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.fab:
                Intent intent = new Intent(getActivity(), SendShortMessageActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDismiss() {
        setWindowAttibus(1f);
    }

    private void setWindowAttibus(float color) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = color;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(),SendShortMessageActivity.class);
        intent.putExtra(Constans.SHORT_MESSAGE_NAME,models.get(position).getName());
        intent.putExtra(Constans.SHORT_MESSAGE_PHONE,models.get(position).getNumberPhone());
        intent.putExtra(Constans.SHORT_MESSAGE_CONTENT,models.get(position).getMessage());
        intent.putExtra(Constans.SHORT_MESSAGE_TYPE,models.get(position).getState());
        intent.putExtra(Constans.SHORT_MESSAGE_ID,models.get(position).getServerId());
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
        mFloatingActionButton.setVisibility(View.GONE);
        PinyinUtils.Vibrate(getActivity(),100);
        mOnKeyDownListener.setOnkeyDown(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(models != null){
                for(ShortMessageDao shortMessageModel : models){
                    shortMessageModel.setIsCheck(false);
                }
                shortMessageAdapter.notifyDataSetChanged();
            }
            return true;
        }
        return false;
    }

    public void setOnKeyDownListener(OnKeyDownListener onKeyDownListener){
        this.mOnKeyDownListener = onKeyDownListener;
    }

    @Override
    public void getShortMessageData(List<ShortMessageDao> list) {
        this.models = list;
        shortMessageAdapter.setData(models);
    }

    @Override
    public void getLoadRefresh(List<ShortMessageDao> list) {
        models.clear();
        this.models = list;
        shortMessageAdapter.setData(models);
    }

    @Override
    public void getListSize(int listSize) {
        offset = listSize;
    }

    @Override
    public void getDaoListSize(int daoListSize) {
        this.daoListSize = daoListSize;
    }

    @Override
    public void getDateSize(int dateSize) {
        this.dateSize = dateSize;
    }

    @Override
    public void getLoadMore(List<ShortMessageDao> list) {
        models.clear();
        this.models = list;
        shortMessageAdapter.setData(models);
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
        NToast.shortToast(getActivity(),msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData(true);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showProgress(true);
        mPresenter.getShortMessageDataList(0,20);
    }

    private void initProgress(){
        mHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel(getString(R.string.loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void deleteAllList() {
        mHUD.show();
        mPresenter.clearMessage();
        mHUD.dismiss();
        shortMessageAdapter.setData(null);
    }

    public interface OnKeyDownListener{
        void setOnkeyDown(boolean isKeyDown);
    }

}
