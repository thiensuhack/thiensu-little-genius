package com.orange.studio.littlegenius.pushnotification;

import com.orange.studio.littlegenius.LG_ApplicationContext;
import com.orange.studio.littlegenius.utils.DeviceUtils;

public final class CommonUtilities {
	public static final String SERVER_BASE_URL = "http://mylittlegenius.com.vn/?api&act=register";
	public static final String SERVER_REGISTER_METHOD = "acceptNotificationAndroid";
	public static final String SENDER_ID = "464357390434";

	public static final String APP_NAME = "littlegenius";
	public static String DEVICE_ID = "";
	public static final String USER_ID = "";

	// public static final String DISPLAY_MESSAGE_ACTION =
	// "com.androidhive.pushnotifications.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "message";

	static {
		DEVICE_ID = DeviceUtils.getDeviceId(LG_ApplicationContext.getContext());
	}
	
	
}
