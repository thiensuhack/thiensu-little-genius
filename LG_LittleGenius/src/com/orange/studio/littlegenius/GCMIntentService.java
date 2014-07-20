package com.orange.studio.littlegenius;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.orange.studio.littlegenius.activities.BaseActivity;
import com.orange.studio.littlegenius.objects.NotificationDTO;
import com.orange.studio.littlegenius.pushnotification.CommonUtilities;
import com.orange.studio.littlegenius.pushnotification.ServerUtilities;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String TAG = GCMIntentService.class.getSimpleName();

	public GCMIntentService() {
		super(CommonUtilities.SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "onRegistered: regId = " + registrationId);
		ServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "onUnregistered: regId = " + registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String message = intent.getExtras().getString("message");
		Log.i(TAG, "onMessage: message = " + message);
		generateNotification(context, message);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		String message = getString(R.string.gcm_deleted, total);
		Log.i(TAG, "onDeletedMessages: message = " + message);
		generateNotification(context, message);
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "onError: errorId = " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.i(TAG, "onRecoverableError: errorId = " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	private static void generateNotification(Context context, String message) {
		try { 
			int icon = R.drawable.logo;
			String title = context.getString(R.string.app_name);
			// long when = System.currentTimeMillis();
			Bitmap largeIcon = null;
			NotificationDTO notify = new NotificationDTO();
			
//			{
//		    aps =     {
//		            alert = "My first push notification!";
//		            sound = default;
//		        };
//		        "property_id" = 2;
//		    }
			JSONObject jb=new JSONObject(message);
			notify.type=jb.optInt("property_id");
			notify.message=jb.getJSONObject("aps").getString("alert");
			
			Intent notificationIntent = null;
			int pushID = 1111;
			notificationIntent = new Intent(context,
					BaseActivity.class);
			title = context.getString(R.string.app_name);
			message = notify.message;
			pushID = 1112;			

			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					context)
					// Set small icon
					.setSmallIcon(icon)
					// set large icon
					.setLargeIcon(largeIcon)
					// Set Title
					.setContentTitle(title)
					// Set Text
					.setContentText(message)
					// Set PendingIntent into Notification
					.setContentIntent(intent)
					// Dismiss Notification
					.setAutoCancel(true)
					
					.setDefaults(Notification.DEFAULT_LIGHTS);
			
			
			AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

		    /* Even if the mode is set to "Sound & Vibration" in the phone, 
		     * the status code that getRingerMode() returns is RINGER_MODE_NORMAL.
		     */
		    switch (am.getRingerMode()) 
		    {
		        case AudioManager.RINGER_MODE_VIBRATE:
		        	builder.setDefaults(Notification.DEFAULT_VIBRATE);
		            break;
		        case AudioManager.RINGER_MODE_NORMAL:
		        	builder.setDefaults(Notification.DEFAULT_SOUND);
		            break;
		        default:
		        	builder.setDefaults(Notification.DEFAULT_SOUND);
		     }


		  //  builder.setContentIntent(intent);   
			notificationManager.notify(pushID, builder.build());
		} catch (Exception e) {
			return;
		}
	}

}
