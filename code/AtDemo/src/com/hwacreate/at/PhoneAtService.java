package com.hwacreate.at;

import com.android.internal.telephony.PhoneFactory;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.telephony.Phone;

@SuppressLint("NewApi")
public class PhoneAtService extends Service {
	private static final String TAG = "PhoneAtService";

	private static final String action_gps_open = "com.qzy.tt.ACTION_GPS_OPEN";
	private static final String action_gps_close = "com.qzy.tt.ACTION_GPS_CLOSE";
	
	private static final String action_gps_result = "com.qzy.tt.ACTION_GPS_RESULT";
	private static final String extra_option = "extra_option";
	private static final String extra_value = "extra_value";

	private Phone mPhone;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.e(TAG, "onCreate");

		mPhone = PhoneFactory.getPhone(1);
		if (mPhone == null) {
			Log.e("AtDemo", "failed to get mPhone\n");
			mPhone = PhoneFactory.getPhone(0);
		}

		regiseter();
	}

	private void regiseter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(action_gps_open);
		intentFilter.addAction(action_gps_close);
		registerReceiver(mReceiver, intentFilter);

	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Log.e(TAG, " action = " + action);
			if (action.equals(action_gps_open)) {
				controlGps(true);
			} else if (action.equals(action_gps_close)) {
				controlGps(false);
			}
		}

	};

	private void controlGps(boolean flag) {
		String command = "AT^BEIDOU=0";
		int msgwhat = 1;

		if (flag) {
			command = "AT^BEIDOU=1";
			msgwhat = 2;
		}

		final String cmd = command;
		final int mwhat = msgwhat;
		Thread t = new Thread(new Runnable() {
			public void run() {
				AtThread atThread = new AtThread("AtCommandSendSync", cmd,
						true, 12 * 1000, handler, mwhat);
				atThread.start();
				String result = atThread.sendAt(mPhone);
				atThread.exit();
			}
		});
		t.start();
	}

	@SuppressLint("NewApi")
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				try {
					String result = msg.getData().getString("result");
					if (result != null && result.trim().contains("OK")) {
						// Toast.makeText(getApplicationContext(),
						// getString(R.string.send_success),
						// Toast.LENGTH_SHORT).show();
						
						sendResult("close",result);
						
					}else{
						sendResult("close","failed");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					String result = msg.getData().getString("result");
					if (result != null && result.trim().contains("OK")) {
						// Toast.makeText(getApplicationContext(),
						// getString(R.string.send_success),
						// Toast.LENGTH_SHORT).show();
						
						sendResult("open",result);
						
					}else{
						sendResult("open","failed");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
			return false;
		}
	});
	
	
	private void sendResult(String actionType,String result){
		Intent intent = new Intent(action_gps_result);
		intent.putExtra(extra_value, result);
		intent.putExtra(extra_option, actionType);
		sendBroadcast(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG, "onDestroy");

		unregisterReceiver(mReceiver);
	}

}
