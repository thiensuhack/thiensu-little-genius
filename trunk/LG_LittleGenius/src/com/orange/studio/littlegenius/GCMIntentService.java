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
			
			//{type:"123",message:"<data here>"}
			JSONObject jb=new JSONObject(message);
			notify.type=jb.getString("type");
			notify.message=jb.getString("msg");
			
			Intent notificationIntent = null;
			int pushID = 1111;
			notificationIntent = new Intent(context,
					BaseActivity.class);
			title = context.getString(R.string.app_name);
			message = notify.message;
			pushID = 1112;
			
//			switch (notify.getDataType()) {
//			case NEWCOMIC: {
//				if (notify.getComic() != null) {
//					ComicDTO comic = notify.getComic();
//					// AppConfig.curComic = notify.getComic();
//					AppConfig.isPushNotification = true;
//					notificationIntent = new Intent(context,
//							DetailActivity.class);
//					notificationIntent.putExtra(AppConfig.COMIC_ID_KEY,
//							comic.getId());
//					title = context.getString(R.string.new_comic_notify);
//					message = comic.getName() + " - "
//							+ comic.getAuthor().getName();
//					pushID = 1112;
//				} else {
//					return;
//				}
//				break;
//			}
//			case NEWCOMICNUMBER: {
//				COMIC_NUMBER_DTO comicNumber = notify.getComicNumber();
//				if (comicNumber == null) {
//					return;
//				}
//				if (TheComicsWorldUltils
//						.parserString2Integer(comicNumber.numberComic) > 0) {
//					notificationIntent = new Intent(context, HomeActivity.class);
//					Bundle params = new Bundle();
//					params.putInt("curTab", 2);
//					notificationIntent.putExtras(params);
//					message = "Vá»«a cáº­p nháº­t " + comicNumber.numberComic
//							+ " cuá»‘n truyá»‡n má»›i. Xem ngay!";
//					pushID = 1113;
//				} else {
//					return;
//				}
//				break;
//			}
//			case EVENT: {
//				if (notify.getEvent() != null) {
//					AppConfig.isPushNotification = true;
//					notificationIntent = new Intent(context,
//							EventDetailActivity.class);
//					notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					Gson gs = new Gson();
//					String strEvent = gs.toJson(notify.getEvent());
//					notificationIntent.putExtra("eventObject", strEvent);
//					title = context.getString(R.string.event_title_label);
//					message = notify.getEvent().getEventName();
//					pushID = 1119;
//				} else {
//					return;
//				}
//				break;
//			}
//
//			
//			case UPDATE: {
//				String version = TheComicsWorldUltils
//						.getCurrentVersionName(context);
//				AppVersion oldVer = TheComicsWorldUltils
//						.parserOldVersion(version);
//				UpdateDTO update = notify.getUpdate();
//				AppVersion newVer = new AppVersion();
//				newVer.setMinVerSion(update.minVerId);
//				newVer.setVersion(update.verId);
//				if (TheComicsWorldUltils.compareVersion(newVer, oldVer)) {
//					message = "Ä�Ã£ cÃ³ phiÃªn báº£n má»›i " + newVer.getVersion()
//							+ ". Cáº­p nháº­t ngay!";
//					if (PackageHelper.getInstance(context).isInstalled(
//							"com.android.vending")) {
//						notificationIntent = new Intent(
//								Intent.ACTION_VIEW,
//								Uri.parse("market://details?id=com.ulabs.comicworld"));
//					} else {
//						notificationIntent = new Intent(
//								Intent.ACTION_VIEW,
//								Uri.parse("http://play.google.com/store/apps/details?id=com.ulabs.comicworld"));
//					}
//					notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				} else {
//					return;
//				}
//				break;
//			}
//			case APP_PROMOTE: {
//				if (notify.getAppPromote() != null) {
//					if (!PackageHelper.getInstance(context).isInstalled(
//							notify.getAppPromote().packageName.trim())) {						
//
//						notificationIntent = new Intent(Intent.ACTION_VIEW,
//								Uri.parse(notify.getAppPromote().actionlink
//										+ "&utm_medium=thegioitruyentranh"));
//						notificationIntent
//								.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						title = notify.getAppPromote().appName;
//						message = context
//								.getString(R.string.promote_app_title_label);
//						largeIcon = ImageLoader.getInstance(context).getBitmap(
//								notify.getAppPromote().logo);
//						// icon = R.drawable.playstoreicon;
//						pushID = 1115;
//					} else {
//						return;
//					}
//				} else {
//					return;
//				}
//				break;
//			}
//			case NORMAL_MESSAGE: {
//				break;
//			}
//			default: {
//				return;
//			}
//			}

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
