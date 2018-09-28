package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.adapter.DeleteContactsAdapter;
import com.tt.qzy.view.bean.SortModel;
import com.tt.qzy.view.layout.ClearEditText;
import com.tt.qzy.view.utils.PingyinContacts;
import com.tt.qzy.view.utils.PinyinComparator;
import com.tt.qzy.view.layout.PopDeleteContactsWindow;
import com.tt.qzy.view.layout.PopWindow;
import com.tt.qzy.view.layout.SideBar;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteContactsActivity extends AppCompatActivity implements DeleteContactsAdapter.OnItemClickListener,PopDeleteContactsWindow.DeleteEntryListener
      ,PopWindow.OnDismissListener{

    @BindView(R.id.base_tv_toolbar_title)
    TextView base_tv_toolbar_title;
    @BindView(R.id.base_iv_back)
    TextView base_iv_back;
    @BindView(R.id.base_tv_toolbar_right)
    TextView base_tv_toolbar_right;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.dialog)
    TextView mDialog;
    @BindView(R.id.sideBar)
    SideBar mSideBar;
    @BindView(R.id.custom_input)
    ClearEditText mClearEditText;

    private List<SortModel> sourceDateList;
    private PingyinContacts pinyinComparator;
    private DeleteContactsAdapter adapter;

    private PopDeleteContactsWindow popContactsWindow;

    private boolean isDelete;
    //用来判断未选择用户就进行删除用户 给一个限定最大值
    private int position = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contacts);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        base_iv_back.setVisibility(View.VISIBLE);
        base_tv_toolbar_right.setVisibility(View.VISIBLE);
        base_iv_back.setText(getResources().getString(R.string.TMT_cannel));
        base_tv_toolbar_title.setText(getResources().getString(R.string.TMT_select_contacts));
        base_tv_toolbar_right.setText(getResources().getString(R.string.TMT_delete));

        mSideBar.setTextView(mDialog);
        pinyinComparator = new PingyinContacts();

        sourceDateList = filledData(getResources().getStringArray(R.array.date));
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new DeleteContactsAdapter(this, sourceDateList);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : sourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }

    @OnClick({R.id.base_iv_back,R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                if(popContactsWindow == null){
                    popContactsWindow = new PopDeleteContactsWindow(this);
                    popContactsWindow.setOnDismissListener(this);
                    popContactsWindow.setDeleteEneryListener(this);
                    setWindowAttibus(0.5f);
                    popContactsWindow.showAtLocation(this.findViewById(R.id.recyclerView),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else{
                    setWindowAttibus(0.5f);
                    popContactsWindow.showAtLocation(this.findViewById(R.id.recyclerView),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position, boolean isFlag) {
        if(isFlag){
            isDelete = isFlag;
            this.position = position;
        }else{
            isDelete = isFlag;
        }
    }

    @Override
    public void onDismiss() {
        setWindowAttibus(1f);
    }

    @Override
    public void deleteEntry() {
        Log.i(getClass().getSimpleName().toString(),"deleEntry");
        if(position == Integer.MAX_VALUE)

            NToast.shortToast(this,"请先选择删除用户");

        else

        if(isDelete){
            sourceDateList.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    private void setWindowAttibus(float color){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = color;
        getWindow().setAttributes(lp);
    }
}
