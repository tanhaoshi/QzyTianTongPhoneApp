package com.tt.qzy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.socks.library.KLog;
import com.tt.qzy.view.MainActivity;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.SendShortMessageActivity;
import com.tt.qzy.view.adapter.ShortMessageAdapter;
import com.tt.qzy.view.bean.MsgModel;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.ShortMessageDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.ShortMessageManager;
import com.tt.qzy.view.layout.ClearEditText;
import com.tt.qzy.view.layout.PopShortMessageWindow;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.presenter.fragment.ShortMessagePresenter;
import com.tt.qzy.view.presenter.manager.SyncManager;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinUtils;
import com.tt.qzy.view.view.ShortMessageView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShortMessageFragment extends Fragment implements PopWindow.OnDismissListener, ShortMessageAdapter.OnItemClickListener
        , ShortMessageView, PopShortMessageWindow.ClearSignalListener {

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
    @BindView(R.id.custom_input)
    ClearEditText mClearEditText;

    private PopShortMessageWindow mPopShortMessageWindow;
    private List<ShortMessageDao> models = new ArrayList<>();
    private ShortMessageAdapter shortMessageAdapter;
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
        setRetainInstance(true);
        mPresenter = new ShortMessagePresenter(getActivity());

        setShortMsgSyncListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_message, container, false);
        ButterKnife.bind(this, view);
        mPresenter.onBindView(this);
        initView();
        //  EventBus.getDefault().register(this);
        return view;
    }

    private void initView() {
        initProgress();
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_short_message));
        base_tv_toolbar_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.more));

        todayRecyclerView.setNestedScrollingEnabled(false);
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shortMessageAdapter = new ShortMessageAdapter(getActivity(), models);
        shortMessageAdapter.setOnItemClickListener(this);
        todayRecyclerView.setAdapter(shortMessageAdapter);
        initListener();

        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                List<ShortMessageDao> list = ShortMessageManager.getInstance(getActivity()).fuzzySearch(s.toString());
                shortMessageAdapter.setData(list);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initListener() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                // load more
                int count = daoListSize - offset;
                if (count > 0) {
                    if (count < 20) {
                        mPresenter.getLoadShortMessageMore(0, offset + count);
                        refreshlayout.finishLoadmore(200);
                    } else {
                        mPresenter.getLoadShortMessageMore(0, offset + 20);
                        refreshlayout.finishLoadmore(200);
                    }
                } else if (count == 0) {
                    NToast.shortToast(getActivity(), getString(R.string.TMT_dataisnull));
                    refreshlayout.finishLoadmore(200);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //  refresh
                refreshlayout.finishRefresh(200);
                mPresenter.getLoadShortMessageFresh(0, offset);
            }
        });
    }

    @OnClick({R.id.base_tv_toolbar_right, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_tv_toolbar_right:
                if (mPopShortMessageWindow == null) {
                    mPopShortMessageWindow = new PopShortMessageWindow(getActivity());
                    mPopShortMessageWindow.setOnDismissListener(this);
                    mPopShortMessageWindow.setOpenPictureListener(this);
                    setWindowAttibus(0.5f);
                    mPopShortMessageWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    setWindowAttibus(0.5f);
                    mPopShortMessageWindow.showAtLocation(getActivity().findViewById(R.id.todayRecyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.fab:
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity.tt_isSignal) {
                    Intent intent = new Intent(getActivity(), SendShortMessageActivity.class);
                    startActivity(intent);
                } else {
                    NToast.shortToast(getActivity(), "设备未入网,请先入网!");
                }
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
        Intent intent = new Intent(getActivity(), SendShortMessageActivity.class);
        intent.putExtra(Constans.SHORT_MESSAGE_NAME, models.get(position).getName());
        intent.putExtra(Constans.SHORT_MESSAGE_PHONE, models.get(position).getNumberPhone());
        intent.putExtra(Constans.SHORT_MESSAGE_CONTENT, models.get(position).getMessage());
        intent.putExtra(Constans.SHORT_MESSAGE_TYPE, models.get(position).getState());
        intent.putExtra(Constans.SHORT_MESSAGE_ID, models.get(position).getServerId());
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
        mFloatingActionButton.setVisibility(View.GONE);
        PinyinUtils.Vibrate(getActivity(), 100);
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
        NToast.shortToast(getActivity(), msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData(true);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) mainActivity.showRecordRead();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showProgress(true);
        mPresenter.getShortMessageDataList(0, 20);
    }

    private void initProgress() {
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

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SHORT_MESSAGE:
                parseSmsreciver(event.getObject());
                break;
        }
    }*/

    /**
     * 设置短信同步接口回调
     */
    private void setShortMsgSyncListener() {
        TtPhoneDataManager.getInstance().setISyncMsgDataListener("ShortMessageFragment", new SyncManager.ISyncMsgDataListener() {
            @Override
            public void onShorMsgSignalSyncFinish(PhoneCmd phoneCmd) {
                parseSmsreciver(phoneCmd);
            }
        });
    }

    /**
     * 解析收到短息
     *
     * @param object
     */
    private void parseSmsreciver(Object object) {
        PhoneCmd cmd = (PhoneCmd) object;
        TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage =
                (TtShortMessageProtos.TtShortMessage.ShortMessage) cmd.getMessage();
        if (mPresenter != null) {
            mPresenter.getShortMessageDataList(0, 20);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // EventBus.getDefault().unregister(this);
    }
}
