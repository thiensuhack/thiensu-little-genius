package com.orange.studio.littlegenius;

import android.app.Application;

public class LG_Appplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		LG_ApplicationContext.setContext(getApplicationContext());
	}	
}
