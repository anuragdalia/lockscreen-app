package com.lockscreen;

import receiver.lockScreenReeiver;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MyService extends Service{
	BroadcastReceiver mReceiver;
	KeyguardManager.KeyguardLock k1;
	KeyguardManager km;

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		k1= km.newKeyguardLock("IN");
		k1.disableKeyguard();



		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);

		mReceiver = new lockScreenReeiver();
		registerReceiver(mReceiver, filter);

		super.onCreate();


	}
	@Override
	public void onStart(Intent intent, int startId) {

		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() 
	{
		//k1.reenableKeyguard();
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
}
