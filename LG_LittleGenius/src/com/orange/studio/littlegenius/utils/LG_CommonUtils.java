package com.orange.studio.littlegenius.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class LG_CommonUtils {
	public static boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected())
			return true;
		else
			return false;
	}

	public static String getVersionName(Context c) {
		try {
			return c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName;
		} catch (Exception e) {
			return null;
		}
	}

	public static void go2WebAction(Activity activity,String url) {
		try {
			Intent action = new Intent(Intent.ACTION_VIEW,
					Uri.parse(url));
			action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(action);
		} catch (Exception e) {
			return;
		}
	}
}
