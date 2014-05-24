package com.zuzu.common;

import android.content.Context;

public class ApplicationContext {
	public String applicationName = "ZuZuCORE";
	public Context context = null;

	private static ApplicationContext _instance;

	public static ApplicationContext getInstance() {
		if (_instance == null) {
			_instance = new ApplicationContext();
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
