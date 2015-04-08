package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lockscreen.LockScreenAppActivity;

public class lockScreenReeiver extends BroadcastReceiver 
{
	public static boolean wasScreenOn = true;

	@Override
	public void onReceive(Context context, Intent intent1) 
	{

		Intent intent = new Intent(context,LockScreenAppActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) 
		{
			wasScreenOn=false;
			
			context.startActivity(intent);

		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
		{
			wasScreenOn=true;
			}
		else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			context.startActivity(intent);

		}

	}


}
