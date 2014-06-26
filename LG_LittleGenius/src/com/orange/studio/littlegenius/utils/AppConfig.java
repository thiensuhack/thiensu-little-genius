package com.orange.studio.littlegenius.utils;

public class AppConfig {
	public static int DBVERSION=1;
	
	public class Cache{		
		public static final String COMMON_NAME="Common";
		public static final int COMMON_NUMBER=50;		
	}
	public class URLRequest{
		public static final String DOMAIN_URL="http://mylittlegenius.com.vn";
		public static final String CONTACT_US_URL=DOMAIN_URL+"/contact/contact-us/?api";
		public static final String LOGIN_URL=DOMAIN_URL+"/login/?api";
		public static final String LOGOUT_URL=DOMAIN_URL+"/login/?api&act=logout";
		public static final String UPDATE_USER_INFO_URL=DOMAIN_URL+"/kms/user-info/?api";
		
	}
}
