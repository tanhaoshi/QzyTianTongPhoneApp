package com.tt.qzy.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.fragment.CityListFragment;
import com.tt.qzy.view.fragment.LoadManagerFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffLineMapActivity extends AppCompatActivity {

    private CityListFragment cityListFragment;
    private LoadManagerFragment loadManagerFragment;

    @BindView(R.id.loadManager)
    TextView loadManager;
    @BindView(R.id.cityList)
    TextView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_line_map);
        ButterKnife.bind(this);
        showCityListFragment();
    }

    @OnClick({R.id.cityList,R.id.loadManager,R.id.main_quantity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.cityList:
                showCityListFragment();
                loadManager.setTextColor(getResources().getColor(R.color.blue));
                loadManager.setBackground(getResources().getDrawable(R.drawable.offline_load_style));
                cityList.setTextColor(getResources().getColor(R.color.white));
                cityList.setBackground(getResources().getDrawable(R.drawable.offline_city_style));
                break;
            case R.id.loadManager:
                loadManager.setTextColor(getResources().getColor(R.color.white));
                loadManager.setBackground(getResources().getDrawable(R.drawable.offline_load_layout));
                cityList.setTextColor(getResources().getColor(R.color.blue));
                cityList.setBackground(getResources().getDrawable(R.drawable.offline_city_stander));
                showLoadManagerFragment();
                break;
            case R.id.main_quantity:
                finish();
                break;
        }
    }

    public void showCityListFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(cityListFragment == null){
            cityListFragment = cityListFragment.newInstance();
            fragmentTransaction.add(R.id.content, cityListFragment);
        }
        commitShowFragment(fragmentTransaction,cityListFragment);
    }

    public void showLoadManagerFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if(loadManagerFragment == null){
            loadManagerFragment = loadManagerFragment.newInstance();
            fragmentTransaction.add(R.id.content, loadManagerFragment);
        }
        commitShowFragment(fragmentTransaction,loadManagerFragment);
    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment){
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    public void hideAllFragment(FragmentTransaction fragmentTransaction){
        hideFragment(fragmentTransaction,cityListFragment);
        hideFragment(fragmentTransaction,loadManagerFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction,Fragment fragment){
        if(fragment!=null){
            fragmentTransaction.hide(fragment);
        }
    }

}
