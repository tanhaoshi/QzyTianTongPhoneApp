package com.qzy.tt.phone;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.intercom.util.IPUtil;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.common.IFragmentChange;
import com.qzy.tt.phone.eventbus.CallingModel;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.qzy.tt.phone.fragment.CallingFragment;
import com.qzy.tt.phone.fragment.DialpadFragment;
import com.qzy.tt.phone.fragment.StateFragment;
import com.qzy.tt.phone.service.TtPhoneService;
import com.qzy.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DialpadFragment.OnDialpadQueryChangedListener,IFragmentChange {


    @BindView(R.id.button_dialpad)
    ImageButton mDialpadButton;
    @BindView(R.id.button_dial)
    ImageButton mDialButton;
    @BindView(R.id.button_send_message)
    ImageButton mSmsButton;

    private Context mContext;

    //拨号键盘
    private DialpadFragment mDialpadFragment;
    private static final String TAG_DIALPAD_FRAGMENT = "dialpad";

    private static final String TAG_STATE_FRAGMENT = "state";

    private CallingFragment mCallingFragment;
    private static final String TAG_CALLING_FRAGMENT = "calling";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.layout_dial, new StateFragment(), TAG_STATE_FRAGMENT)
                    .add(R.id.layout_dialer_panel, new DialpadFragment(), TAG_DIALPAD_FRAGMENT)
                    .add(R.id.layout_calling, new CallingFragment(), TAG_CALLING_FRAGMENT)
                    .commit();
        }
        CommonData.localWifiIp = IPUtil.getLocalIPAddress(this);
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof DialpadFragment) {
            mDialpadFragment = (DialpadFragment) fragment;
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.hide(mDialpadFragment);
            transaction.commit();
        } else if (fragment instanceof CallingFragment) {
            mCallingFragment = (CallingFragment) fragment;
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.hide(mCallingFragment);
            transaction.commit();
        }


       /* else if (fragment instanceof RecentContactsFragment) {
            mFrequentDialFragment = (RecentContactsFragment) fragment;
        }*/
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startServcie();

    }

    /**
     * 开启服务
     */
    private void startServcie() {
        startService(new Intent(this, TtPhoneService.class));

    }

    /**
     * 停止服务
     */
    private void stopServcie() {
        stopService(new Intent(this, TtPhoneService.class));

    }


    /**
     * show 拨号键盘
     *
     * @param animate
     */
    public void showDialpad(boolean animate) {
        // TODO 有一个bug，就是在hideDialpad动画没有完成之前就执行showDialpad的话，会导致永久hide
        // 这个bug在系统的拨号App中同样存在
        mDialpadFragment.setAdjustTranslationForAnimation(animate);
        mDialpadButton.setVisibility(View.GONE);
        mDialButton.setVisibility(View.VISIBLE);
        // mSmsButton.setVisibility(View.VISIBLE);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.slide_in, 0);
        } else {
            mDialpadFragment.setYFraction(0);
        }
        ft.show(mDialpadFragment);
        ft.commit();
    }


    long waitTime = 2000;
    long touchTime = 0;

    @Override
    public void onBackPressed() {

        if(isDCallingShowing()){

           /* long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(this, "再按一次挂断电话", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
               hideCallingView();
            }*/

            return;
        }

        if (isDialpadShowing()) {
            hideDialpad(true, false);
            return;
        }
       /* if (currentStatus == Status_Show_Search) {
            mDialpadFragment.clearDialpad();
            currentStatus = Status_Show_Frequent;
            return;
        }*/

        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }

        //moveTaskToBack(false);
    }

    /**
     * hide 拨号键盘
     *
     * @param animate
     * @param clearDialpad
     */
    public void hideDialpad(boolean animate, boolean clearDialpad) {
        if (mDialpadFragment == null)
            return;
        mDialButton.setVisibility(View.GONE);
        //mSmsButton.setVisibility(View.GONE);
        mDialpadButton.setVisibility(View.VISIBLE);
        if (clearDialpad) {
            mDialpadFragment.clearDialpad();
        }
        if (!mDialpadFragment.isVisible())
            return;
        mDialpadFragment.setAdjustTranslationForAnimation(animate);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (animate) {
            ft.setCustomAnimations(0, R.anim.slide_out);
        }
        ft.hide(mDialpadFragment);
        ft.commit();
    }

    @OnClick({R.id.button_dialpad, R.id.button_dial, R.id.button_send_message, R.id.button_all_contacts})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_dialpad:
                showDialpad(true);
                break;
            case R.id.button_dial:
                if (isDialpadShowing()) {
                    String number = mDialpadFragment.getDiapadNumber();
                    if (!TextUtils.isEmpty(number) && number.length() >= 3) {
                        if (CommonData.isConnected) {
                            //ContactHelper.makePhoneCall(number);
                            callPhone(number);
                        } else {
                            ToastUtils.showToast(mContext, "未连接天通猫");
                        }
                    }
                }
                break;
            case R.id.button_send_message:
                if (isDialpadShowing()) {
                    String number = mDialpadFragment.getDiapadNumber();
                    if (!TextUtils.isEmpty(number) && number.length() >= 3) {
                        // ContactHelper.sendSMS(number);
                    }
                }
                break;
            case R.id.button_all_contacts:
                //  Intent intent = new Intent(this, AllContactActivity.class);
                //  startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 打电话
     *
     * @param phoneNumber
     */
    private void callPhone(String phoneNumber) {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.localWifiIp)
                .setPhoneNumber(phoneNumber)
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.CALL)
                .build();
        EventBus.getDefault().post(new PhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));

        if(showCallingView()){
            setCallingPhoneNumber(phoneNumber);
        }
    }


    private boolean isDCallingShowing() {
        return mCallingFragment != null && mCallingFragment.isVisible();
    }

    public boolean showCallingView() {
        if(!isDCallingShowing()){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.show(mCallingFragment).commit();
            return true;
        }
        return false;
    }

    public void setCallingPhoneNumber(String phoneNumber){
        CallingModel callingModel = new CallingModel();
        callingModel.setPhone_number(phoneNumber);
        EventBus.getDefault().post(callingModel);
    }

    @Override
    public void hideCallingView() {
        if(isDCallingShowing()){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.hide(mCallingFragment).commit();
        }
    }

    private boolean isDialpadShowing() {
        return mDialpadFragment != null && mDialpadFragment.isVisible();
    }

    @Override
    public void onDialpadQueryChanged(String query) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonData.relase();
        stopServcie();
        System.exit(0);
    }


}
