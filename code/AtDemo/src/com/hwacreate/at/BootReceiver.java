package com.hwacreate.at;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("boot receiver "," action = " + intent.getAction());
		
		context.getApplicationContext().startService(new Intent(context.getApplicationContext(),PhoneAtService.class));
          
	}

}
