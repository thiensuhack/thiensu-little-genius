package com.orange.studio.littlegenius.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.pushnotification.CommonUtilities;
import com.orange.studio.littlegenius.pushnotification.ConnectionDetector;
import com.orange.studio.littlegenius.pushnotification.ServerUtilities;
import com.orange.studio.littlegenius.utils.AppConfig.PushNotificationKey;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_TIME = 2000;
	private TextView mVersionName = null;
	private AsyncTask<Void, Void, Void> mRegisterTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash_layout);
		mVersionName = (TextView) findViewById(R.id.txtViewVersion);

		String verName = LG_CommonUtils.getVersionName(getApplicationContext());
		if (verName != null) {
			mVersionName.setText(verName);
		}

		registerGCM();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				checkConnection();
			}
		}, SPLASH_DISPLAY_TIME);
	}

	private void checkConnection() {
		go2HomePage();
		// if (LG_CommonUtils.isOnline(getApplicationContext())) {
		//
		// }
		// else {
		// //go2NetworkError();
		// }
	}

	private void go2HomePage() {
		Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
		intent.putExtra(PushNotificationKey.PROGRAM_KEY, true);
		startActivity(intent);
		finish();
	}

	private void registerGCM() {
		try {
			ConnectionDetector cd = new ConnectionDetector(
					getApplicationContext());

			if (cd.isConnectingToInternet()) {
				// Make sure the device has the proper dependencies.
				GCMRegistrar.checkDevice(this);

				// Make sure the manifest was properly set - comment out this
				// line
				// while developing the app, then uncomment it when it's ready.
				GCMRegistrar.checkManifest(this);

				// Get GCM registration id
				final String regId = GCMRegistrar.getRegistrationId(this);

				// Check if regid already presents
				if (regId.equals("")) {
					// Registration is not present, register now with GCM
					GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
				} else {
					// Device is already registered on GCM
					if (GCMRegistrar.isRegisteredOnServer(this)) {
						// Skips registration.
						/*
						 * Toast.makeText(getApplicationContext(),
						 * "Already registered with GCM", Toast.LENGTH_LONG)
						 * .show();
						 */
					} else {
						// Try to register again, but not in the UI thread.
						// It's also necessary to cancel the thread onDestroy(),
						// hence the use of AsyncTask instead of a raw thread.
						final Context context = this;
						mRegisterTask = new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								// Register on our server
								// On server creates a new user
								ServerUtilities.register(context, regId);
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								mRegisterTask = null;
							}

						};
						mRegisterTask.execute(null, null, null);
					}
				}
			}
		} catch (Exception e) {
			return;
		}
	}
}
