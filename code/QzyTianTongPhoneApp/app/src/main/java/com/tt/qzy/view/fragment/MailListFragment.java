package com.tt.qzy.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.AddContactsActivity;
import com.tt.qzy.view.adapter.SortAdapter;
import com.tt.qzy.view.bean.MallListModel;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.layout.ClearEditText;
import com.tt.qzy.view.utils.MallListUtils;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.layout.PopMallListWindow;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.layout.SideBar;
import com.tt.qzy.view.presenter.fragment.MailListFragmentPresenter;
import com.tt.qzy.view.view.MailListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MailListFragment extends Fragment implements PopWindow.OnDismissListener,MailListView,PopMallListWindow.OpenWindowListener
,SortAdapter.OnItemClickListener{

    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_iv_back)
    ImageView base_iv_back;
    @BindView(R.id.base_tv_toolbar_right)
    ImageView base_tv_toolbar_right;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.dialog)
    TextView mDialog;
    @BindView(R.id.sideBar)
    SideBar mSideBar;
    @BindView(R.id.custom_input)
    ClearEditText mClearEditText;
    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private List<MallListModel> listModels = new ArrayList<>();
    private PinyinComparator pinyinComparator;
    private SortAdapter adapter;

    private PopMallListWindow mPopMallListWindow;
    private MailListFragmentPresenter mPresenter;
    private KProgressHUD mHUD;

    public MailListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MailListFragment newInstance() {
        MailListFragment fragment = new MailListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mPresenter = new MailListFragmentPresenter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail_list, container, false);
        mPresenter.onBindView(this);
        ButterKnife.bind(this, view);
        initView();
        initAdapter();
        loadData(true);
        return view;
    }

    private void initView() {
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_mall_list));
        base_tv_toolbar_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.more));

        mSideBar.setTextView(mDialog);
        pinyinComparator = new PinyinComparator();

        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.filterData(listModels,s.toString(),pinyinComparator,adapter);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getMallList(getActivity());
                mRefreshLayout.finishRefresh(200);
            }
        });

        initProgress();
    }

    private void initAdapter(){
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new SortAdapter(getActivity(), listModels);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
    }

    @OnClick({R.id.base_tv_toolbar_right,R.id.fab})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_tv_toolbar_right:
                if(mPopMallListWindow == null){
                    mPopMallListWindow = new PopMallListWindow(getActivity());
                    mPopMallListWindow.setOnDismissListener(this);
                    mPopMallListWindow.setOpenWindowListener(this);
                    setWindowAttibus(0.5f);
                    mPopMallListWindow.showAtLocation(getActivity().findViewById(R.id.recyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    mPopMallListWindow.showAtLocation(getActivity().findViewById(R.id.recyclerView),
                            Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.fab:
                Intent intent = new Intent(getActivity(), AddContactsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDismiss() {
        setWindowAttibus(1f);
    }

    private void setWindowAttibus(float color){
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = color;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void loadData(List<MallListModel> listModels) {
        this.listModels = listModels;
        Collections.sort(listModels);
        adapter.updateList(listModels);
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
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showProgress(true);
        mPresenter.getMallList(getActivity());
    }

    private void initProgress() {
        mHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setDetailsLabel(getString(R.string.loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void importPhoneMailList() {
        mHUD.show();
        mPresenter.getContactsMallList(getActivity());
    }

    @Override
    public void onItemClick(View view, int position , List<MallListModel> mData) {
       mPresenter.startTargetActivity(getActivity(),mData.get(position).getPhone());
    }

}
