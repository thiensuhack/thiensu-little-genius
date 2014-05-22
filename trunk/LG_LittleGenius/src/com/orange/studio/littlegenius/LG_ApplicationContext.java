package com.orange.studio.littlegenius;

import android.content.Context;

public class LG_ApplicationContext {
	public String applicationName = "LG ApplicationContext";
	public Context context = null;

	private static LG_ApplicationContext _instance;

	public static LG_ApplicationContext getInstance() {
		if (_instance == null) {
			_instance = new LG_ApplicationContext();
		}
		return _instance;
	}

	public static void setApplicationName(String appName) {
		getInstance().applicationName = appName;
	}

	public static String getApplicationName() {
		return getInstance().applicationName;
	}

	public static void setContext(Context context) {
		getInstance().context = context;
	}

	public static Context getContext() {
		return getInstance().context;
	}
}
