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

import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.SendShortMessageActivity;
import com.tt.qzy.view.adapter.ShortMessageAdapter;
import com.tt.qzy.view.bean.ShortMessageModel;
import com.tt.qzy.view.layout.PopShortMessageWindow;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShortMessageFragment extends Fragment implements PopWindow.OnDismissListener,ShortMessageAdapter.OnItemClickListener{

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

    private PopShortMessageWindow mPopShortMessageWindow;
    private List<ShortMessageModel> models;
    private ShortMessageAdapter shortMessageAdapter;
    private OnKeyDownListener mOnKeyDownListener;

    // "昨天" 标题进行添加一次的控制
    private boolean isYesterday = true;

    // "更早" 标题进行添加一次的控制
    private boolean isEarlier = true;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short_message, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        base_iv_back.setVisibility(View.GONE);
        base_tv_toolbar_title.setText(getActivity().getResources().getString(R.string.TMT_short_message));
        base_tv_toolbar_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.more));

        todayRecyclerView.setNestedScrollingEnabled(false);
        todayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        List<ShortMessageModel> list = new ArrayList<>();
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-28 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-25 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-20 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-30 9:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-29 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-29 18:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-27 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-23 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-24 14:33:24",0,""));
        list.add(new ShortMessageModel("106575020131875",getResources().getString(R.string.TMT_text),"2018-8-20 14:33:24",0,""));
        models = new ArrayList<>();
        //判断短信列表中 是否有数据 有数据下面的操作才有含义，没有数据的话就不用处理。
        if(list.size()>=1){
            //对时间进行排序
            sortData(list);
            //添加今天的栏目
            models.add(new ShortMessageModel("","","",1,"今天"));
            for(ShortMessageModel shortMessageModel : list){
                if(DateUtil.isToday(shortMessageModel.getTime())){
                    models.add(shortMessageModel);
                }else if(DateUtil.isYesterday(shortMessageModel.getTime())){
                    if(isYesterday){
                        models.add(new ShortMessageModel("","","",1,"昨天"));
                        isYesterday = false;
                    }
                    models.add(shortMessageModel);
                }else{
                    if(isYesterday){
                        models.add(new ShortMessageModel("","","",1,"昨天"));
                        isYesterday = false;
                    }
                    if(isEarlier){
                        models.add(new ShortMessageModel("","","",1,"更早"));
                        isEarlier = false;
                    }
                    models.add(shortMessageModel);
                }
            }
        }
        shortMessageAdapter = new ShortMessageAdapter(getActivity(),models);
        shortMessageAdapter.setOnItemClickListener(this);
        todayRecyclerView.setAdapter(shortMessageAdapter);
    }

    @OnClick({R.id.base_tv_toolbar_right,R.id.fab})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_tv_toolbar_right:
                if(mPopShortMessageWindow == null){
                    mPopShortMessageWindow = new PopShortMessageWindow(getActivity());
                    mPopShortMessageWindow.setOnDismissListener(this);
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

    private List<ShortMessageModel> sortData(List<ShortMessageModel> mList) {
        Collections.sort(mList, new Comparator<ShortMessageModel>() {
            @Override
            public int compare(ShortMessageModel lhs, ShortMessageModel rhs) {
                Date date1 = DateUtil.stringToDate(lhs.getTime());
                Date date2 = DateUtil.stringToDate(rhs.getTime());
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }

    @Override
    public void onClick(int position) {
        //Log.i(getClass().getSimpleName().toString(),""+position);
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
                for(ShortMessageModel shortMessageModel : models){
                    shortMessageModel.setCheck(false);
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

    public interface OnKeyDownListener{
        void setOnkeyDown(boolean isKeyDown);
    }
}
