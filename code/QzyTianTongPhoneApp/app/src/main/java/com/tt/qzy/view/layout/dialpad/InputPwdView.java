package com.tt.qzy.view.layout.dialpad;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.provider.Settings;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tt.qzy.view.R;
import com.tt.qzy.view.layout.DialpadKeyButton;
import com.tt.qzy.view.layout.DigitsEditText;

import java.util.HashSet;


public class InputPwdView extends LinearLayout implements View.OnTouchListener,View.OnClickListener,
    DialpadKeyButton.OnPressedListener,View.OnLongClickListener{

    private Context context;
    private InputPwdView_Pwd inputPwdView_Pwd;
    private InputPwdListener inputPwdListener;
    private DigitsEditText mDigits;
    private DialpadKeyButton one;
    private DialpadKeyButton two;
    private DialpadKeyButton three;
    private DialpadKeyButton four;
    private DialpadKeyButton five;
    private DialpadKeyButton six;
    private DialpadKeyButton seven;
    private DialpadKeyButton eight;
    private DialpadKeyButton nine;
    private ImageView software;
    private ImageView tellphone;
    private ImageView clear;
    private DialpadKeyButton start;
    private DialpadKeyButton zero;
    private DialpadKeyButton jinghao;
    private StringBuffer sb;

    private final Object mToneGeneratorLock = new Object();
    private final HashSet<View> mPressedDialpadKeys = new HashSet<View>(12);
    private ToneGenerator mToneGenerator;
    /**
     * 系统设置中的拨号键盘触摸音效开关
     */
    private boolean mDTMFToneEnabled;
    private boolean mAdjustTranslationForAnimation = true;
    private static final int TONE_LENGTH_INFINITE = -1;
    private static final int TONE_RELATIVE_VOLUME = 80;
    private static final int DIAL_TONE_STREAM_TYPE = AudioManager.STREAM_DTMF;
    private static final float DIALPAD_SLIDE_FRACTION = 0.67f;

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
        mDigits = (DigitsEditText)view.findViewById(R.id.inputContent);
        one = (DialpadKeyButton)view.findViewById(R.id.one);
        two = (DialpadKeyButton)view.findViewById(R.id.two);
        three = (DialpadKeyButton)view.findViewById(R.id.three);
        four = (DialpadKeyButton)view.findViewById(R.id.four);
        five = (DialpadKeyButton)view.findViewById(R.id.five);
        six = (DialpadKeyButton)view.findViewById(R.id.six);
        seven = (DialpadKeyButton)view.findViewById(R.id.seven);
        eight = (DialpadKeyButton)view.findViewById(R.id.eight);
        nine = (DialpadKeyButton)view.findViewById(R.id.nine);
        software = (ImageView)view.findViewById(R.id.software);
        tellphone = (ImageView) view.findViewById(R.id.tellphone);
        clear = (ImageView) view.findViewById(R.id.clear);
        start = (DialpadKeyButton) view.findViewById(R.id.start);
        zero = (DialpadKeyButton) view.findViewById(R.id.zero);
        jinghao = (DialpadKeyButton) view.findViewById(R.id.jinghao);
        sb = new StringBuffer();
        initListener();
    }

    public void initListener() {
        final ContentResolver contentResolver = context
                .getContentResolver();
        mDTMFToneEnabled = Settings.System.getInt(contentResolver,
                Settings.System.DTMF_TONE_WHEN_DIALING, 1) == 1;
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                try {
                    mToneGenerator = new ToneGenerator(DIAL_TONE_STREAM_TYPE,
                            TONE_RELATIVE_VOLUME);
                } catch (RuntimeException e) {
                    mToneGenerator = null;
                }
            }
        }
        one.setOnPressedListener(this);
        two.setOnPressedListener(this);
        three.setOnPressedListener(this);
        four.setOnPressedListener(this);
        five.setOnPressedListener(this);
        six.setOnPressedListener(this);
        seven.setOnPressedListener(this);
        eight.setOnPressedListener(this);
        nine.setOnPressedListener(this);
        start.setOnPressedListener(this);
        zero.setOnPressedListener(this);
        jinghao.setOnPressedListener(this);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        start.setOnClickListener(this);
        zero.setOnClickListener(this);
        jinghao.setOnClickListener(this);
        software.setOnClickListener(this);
        tellphone.setOnClickListener(this);

        clear.setOnClickListener(this);
        clear.setOnLongClickListener(this);

        one.setOnTouchListener(this);
        two.setOnTouchListener(this);
        three.setOnTouchListener(this);
        four.setOnTouchListener(this);
        five.setOnTouchListener(this);
        six.setOnTouchListener(this);
        seven.setOnTouchListener(this);
        eight.setOnTouchListener(this);
        nine.setOnTouchListener(this);
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
    }

    public void setListener(InputPwdListener inputPwdListener) {
        this.inputPwdListener = inputPwdListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear:
                keyPressed(KeyEvent.KEYCODE_DEL);
                break;
            case R.id.tellphone:
                inputPwdListener.inputString(mDigits.getText().toString());
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPressed(View view, boolean pressed) {
        if (pressed) {
            switch (view.getId()) {
                case R.id.one: {
                    keyPressed(KeyEvent.KEYCODE_1);
                    break;
                }
                case R.id.two: {
                    keyPressed(KeyEvent.KEYCODE_2);
                    break;
                }
                case R.id.three: {
                    keyPressed(KeyEvent.KEYCODE_3);
                    break;
                }
                case R.id.four: {
                    keyPressed(KeyEvent.KEYCODE_4);
                    break;
                }
                case R.id.five: {
                    keyPressed(KeyEvent.KEYCODE_5);
                    break;
                }
                case R.id.six: {
                    keyPressed(KeyEvent.KEYCODE_6);
                    break;
                }
                case R.id.seven: {
                    keyPressed(KeyEvent.KEYCODE_7);
                    break;
                }
                case R.id.eight: {
                    keyPressed(KeyEvent.KEYCODE_8);
                    break;
                }
                case R.id.nine: {
                    keyPressed(KeyEvent.KEYCODE_9);
                    break;
                }
                case R.id.zero: {
                    keyPressed(KeyEvent.KEYCODE_0);
                    break;
                }
                case R.id.jinghao: {
                    keyPressed(KeyEvent.KEYCODE_POUND);
                    break;
                }
                case R.id.start: {
                    keyPressed(KeyEvent.KEYCODE_STAR);
                    break;
                }
                default: {
                    break;
                }
            }
            mPressedDialpadKeys.add(view);
        } else {
            view.jumpDrawablesToCurrentState();
            mPressedDialpadKeys.remove(view);
            if (mPressedDialpadKeys.isEmpty()) {
                stopTone();
            }
        }
    }

    private void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                playTone(ToneGenerator.TONE_DTMF_1, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_2:
                playTone(ToneGenerator.TONE_DTMF_2, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_3:
                playTone(ToneGenerator.TONE_DTMF_3, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_4:
                playTone(ToneGenerator.TONE_DTMF_4, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_5:
                playTone(ToneGenerator.TONE_DTMF_5, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_6:
                playTone(ToneGenerator.TONE_DTMF_6, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_7:
                playTone(ToneGenerator.TONE_DTMF_7, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_8:
                playTone(ToneGenerator.TONE_DTMF_8, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_9:
                playTone(ToneGenerator.TONE_DTMF_9, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_0:
                playTone(ToneGenerator.TONE_DTMF_0, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_POUND:
                playTone(ToneGenerator.TONE_DTMF_P, TONE_LENGTH_INFINITE);
                break;
            case KeyEvent.KEYCODE_STAR:
                playTone(ToneGenerator.TONE_DTMF_S, TONE_LENGTH_INFINITE);
                break;
            default:
                break;
        }
        if (mAdjustTranslationForAnimation) {
            mDigits.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        mDigits.onKeyDown(keyCode, event);

        // If the cursor is at the end of the text we hide it.
        final int length = mDigits.length();
        if (length == mDigits.getSelectionStart()
                && length == mDigits.getSelectionEnd()) {
            mDigits.setCursorVisible(false);
        }
    }

    private void playTone(int tone, int durationMs) {
        if (!mDTMFToneEnabled) {
            return;
        }
        // TODO to resume
        AudioManager audioManager = (AudioManager)context
                .getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();
        if ((ringerMode == AudioManager.RINGER_MODE_SILENT)
                || (ringerMode == AudioManager.RINGER_MODE_VIBRATE)) {
            return;
        }

        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                return;
            }
            mToneGenerator.startTone(tone, durationMs);
        }
    }

    private void stopTone() {
        if (!mDTMFToneEnabled) {
            return;
        }
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                return;
            }
            mToneGenerator.stopTone();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        final Editable digits = mDigits.getText();
        final int id = v.getId();
        switch (id) {
            case R.id.clear: {
                digits.clear();
                // TODO: The framework forgets to clear the pressed
                // status of disabled button. Until this is fixed,
                // clear manually the pressed status. b/2133127
                clear.setPressed(false);
                return true;
            }
            case R.id.inputContent: {
                // Right now EditText does not show the "paste" option when
                // cursor
                // is not visible.
                // To show that, make the cursor visible, and return false,
                // letting
                // the EditText
                // show the option by itself.
                mDigits.setCursorVisible(true);
                return false;
            }
        }
        return false;
    }

    public interface InputPwdListener {
        void inputString(String diapadNumber);
    }

}
