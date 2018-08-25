package com.tt.qzy.view.layout.dialpad;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneActivity;
import com.tt.qzy.view.layout.DigitsEditText;


public class InputPwdView extends LinearLayout implements View.OnTouchListener,View.OnClickListener{
    private Context context;
    private InputPwdView_Pwd inputPwdView_Pwd;
    private InputPwdListener inputPwdListener;
    private DigitsEditText mDigitsEditText;
    private LinearLayout one;
    private LinearLayout two;
    private LinearLayout three;
    private LinearLayout four;
    private LinearLayout five;
    private LinearLayout six;
    private LinearLayout seven;
    private LinearLayout eight;
    private LinearLayout nine;
    private ImageView software;
    private ImageView tellphone;
    private ImageView clear;
    private TextView start;
    private TextView zero;
    private TextView jinghao;
    private StringBuffer sb;
    private Vibrator vb;

    public InputPwdView(Context context) {
        super(context);
        init();
    }

    public InputPwdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputPwdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.inputpwd_layout, this);
        mDigitsEditText = (DigitsEditText)view.findViewById(R.id.inputContent);
        one = (LinearLayout)view.findViewById(R.id.one);
        two = (LinearLayout)view.findViewById(R.id.two);
        three = (LinearLayout)view.findViewById(R.id.three);
        four = (LinearLayout)view.findViewById(R.id.four);
        five = (LinearLayout)view.findViewById(R.id.five);
        six = (LinearLayout)view.findViewById(R.id.six);
        seven = (LinearLayout)view.findViewById(R.id.seven);
        eight = (LinearLayout)view.findViewById(R.id.eight);
        nine = (LinearLayout)view.findViewById(R.id.nine);
        software = (ImageView)view.findViewById(R.id.software);
        tellphone = (ImageView) view.findViewById(R.id.tellphone);
        clear = (ImageView) view.findViewById(R.id.clear);
        start = (TextView) view.findViewById(R.id.start);
        zero = (TextView) view.findViewById(R.id.zero);
        jinghao = (TextView) view.findViewById(R.id.jinghao);
        sb = new StringBuffer();
        initListener();
    }

    public void initListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        software.setOnClickListener(this);
        tellphone.setOnClickListener(this);
        clear.setOnClickListener(this);
        start.setOnClickListener(this);
        zero.setOnClickListener(this);
        jinghao.setOnClickListener(this);
        one.setOnTouchListener(this);
        two.setOnTouchListener(this);
        three.setOnTouchListener(this);
        four.setOnTouchListener(this);
        five.setOnTouchListener(this);
        six.setOnTouchListener(this);
        seven.setOnTouchListener(this);
        eight.setOnTouchListener(this);
        nine.setOnTouchListener(this);
        software.setOnTouchListener(this);
        tellphone.setOnTouchListener(this);
        clear.setOnTouchListener(this);
        start.setOnTouchListener(this);
        zero.setOnTouchListener(this);
        jinghao.setOnTouchListener(this);
    }

    public void reSetView(){
        if(null!=inputPwdView_Pwd){
            inputPwdView_Pwd.reSetView();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        EventBus.getDefault().unregister(this);
    }

//    @Subscribe
//    public void onEventMainThread(InputEvent even){
//        if("close".equals(even.getMessage())){
//            setVisibility(View.GONE);
//        }
//    }

    public void setListener(InputPwdListener inputPwdListener) {
        this.inputPwdListener = inputPwdListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                sb.append("1");
                mDigitsEditText.setText(sb);
                break;
            case R.id.two:
                sb.append("2");
                mDigitsEditText.setText(sb);
                break;
            case R.id.three:
                sb.append("3");
                mDigitsEditText.setText(sb);
                break;
            case R.id.four:
                sb.append("4");
                mDigitsEditText.setText(sb);
                break;
            case R.id.five:
                sb.append("5");
                mDigitsEditText.setText(sb);
                break;
            case R.id.six:
                sb.append("6");
                mDigitsEditText.setText(sb);
                break;
            case R.id.seven:
                sb.append("7");
                mDigitsEditText.setText(sb);
                break;
            case R.id.eight:
                sb.append("8");
                mDigitsEditText.setText(sb);
                break;
            case R.id.nine:
                sb.append("9");
                mDigitsEditText.setText(sb);
                break;
            case R.id.software:
                break;
            case R.id.tellphone:
                if(inputPwdListener != null){
                    inputPwdListener.inputString(sb.toString());
                }
                break;
            case R.id.clear:
                if (sb.length() == 0) return;
                sb.delete(sb.length()-1,sb.length());
                mDigitsEditText.setText(sb);
                break;
            case R.id.start:
                sb.append("*");
                mDigitsEditText.setText(sb);
                break;
            case R.id.zero:
                sb.append("0");
                mDigitsEditText.setText(sb);
                break;
            case R.id.jinghao:
                sb.append("#");
                mDigitsEditText.setText(sb);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.one:
                shock(event);
                break;
            case R.id.two:
                shock(event);
                break;
            case R.id.three:
                shock(event);
                break;
            case R.id.four:
                shock(event);
                break;
            case R.id.five:
                shock(event);
                break;
            case R.id.six:
                shock(event);
                break;
            case R.id.seven:
                shock(event);
                break;
            case R.id.eight:
                shock(event);
                break;
            case R.id.nine:
                shock(event);
                break;
            case R.id.software:
                shock(event);
                break;
            case R.id.tellphone:
                shock(event);
                break;
            case R.id.clear:
                shock(event);
                break;
            case R.id.start:
                shock(event);
                break;
            case R.id.zero:
                shock(event);
                break;
            case R.id.jinghao:
                shock(event);
                break;
        }
        return false;
    }

    private void shock(MotionEvent event){
       /* if(vb != null){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                vb.vibrate(5000);
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                vb.cancel();
            }
        }else{
            Vibrator vb = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                vb.vibrate(5000);
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                vb.cancel();
            }
        }*/
    }

    public interface InputPwdListener {
        void inputString(String diapadNumber);
    }

}
