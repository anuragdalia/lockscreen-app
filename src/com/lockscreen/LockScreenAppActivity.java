package com.lockscreen;
import android.R.color;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class LockScreenAppActivity extends Activity 
{

	KeyguardManager.KeyguardLock k1;
	boolean inDragMode;
	int selectedImageViewX;
	int selectedImageViewY;
	int windowwidth;
	int windowheight;
	int home_x,home_y;
	int[] droidpos;

	private LayoutParams layoutParams;

	@Override
	public void onAttachedToWindow() 
	{
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG|WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onAttachedToWindow();
	}
	TextView tv;
	public void onCreate(Bundle savedInstanceState) {   	

		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		if(getIntent()!=null&&getIntent().hasExtra("kill")&&getIntent().getExtras().getInt("kill")==1)
		{
			finish();
		}

		try{


			startService(new Intent(this,MyService.class));




			KeyguardManager km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
			k1 = km.newKeyguardLock("IN");
			k1.disableKeyguard();
			StateListener phoneStateListener = new StateListener();
			TelephonyManager telephonyManager =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

			windowwidth=getWindowManager().getDefaultDisplay().getWidth();
			windowheight=getWindowManager().getDefaultDisplay().getHeight();


			LinearLayout homelinear = (LinearLayout)findViewById(R.id.homelinearlayout);
			homelinear.setPadding(0,0,0,(windowheight/32)*3);

			tv= (TextView)findViewById(R.id.textView3);
			tv.setBackgroundColor(Color.WHITE);
			tv.setTextColor(Color.BLACK);
			SeekBar sb= (SeekBar)findViewById(R.id.seekBar1);
			sb.setMax(100);

			sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {


				public void onStopTrackingTouch(SeekBar seekBar) {
					if(seekBar.getProgress()>50 && seekBar.getProgress()<60)
					{

						finish();
					}

				}

				public void onStartTrackingTouch(SeekBar seekBar) {


				}

				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					if(seekBar.getProgress()>95)
					{
						tv.setText("S|HER");	
					}
					else if(seekBar.getProgress()>75)
					{
						tv.setText("SHE");	
					}
					else if(seekBar.getProgress()>55)
					{
						tv.setText("SH");	
					}
					else if(seekBar.getProgress()>25)
					{
						tv.setText("S");	
					}
					else 
					{
						tv.setText("");	
					}

				}
			});



		}catch (Exception e) {}

	}
	class StateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);
			switch(state){
			case TelephonyManager.CALL_STATE_RINGING:
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("call Activity off hook");
				finish();



				break;
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			}
		}
	};


	public void onSlideTouch( View view, MotionEvent event )
	{
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			int x_cord = (int)event.getRawX();
			int y_cord = (int)event.getRawY();

			if(x_cord>windowwidth){x_cord=windowwidth;}
			if(y_cord>windowheight){y_cord=windowheight;}

			layoutParams.leftMargin = x_cord -25;
			layoutParams.topMargin = y_cord - 75;

			view.setLayoutParams(layoutParams);
			break;
		default:
			break;




		}


	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}


	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event)
	{

		if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)||(keyCode == KeyEvent.KEYCODE_POWER)||(keyCode == KeyEvent.KEYCODE_VOLUME_UP)||(keyCode == KeyEvent.KEYCODE_CAMERA))
		{
			return true; 
		}
		if((keyCode == KeyEvent.KEYCODE_HOME))
		{

			return true;
		}

		return super.onKeyDown(keyCode, event);

	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_POWER ||(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)||(event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
			return false;
		}
		if((event.getKeyCode() == KeyEvent.KEYCODE_HOME)){

			System.out.println("alokkkkkkkkkkkkkkkkk");
			return false;
		}
		return false;
	}

	public void onDestroy(){
		k1.reenableKeyguard();

		super.onDestroy();
	}

}