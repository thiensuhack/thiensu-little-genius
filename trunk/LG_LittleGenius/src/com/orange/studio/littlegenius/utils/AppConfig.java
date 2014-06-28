package com.orange.studio.littlegenius.utils;

import com.orange.studio.littlegenius.objects.UserDTO;

public class AppConfig {
	
	public static UserDTO mUser=null;
	
	public static int DBVERSION=1;
	public class Cache{		
		public static final String COMMON_NAME="Common";
		public static final int COMMON_NUMBER=50;		
	}
	public class URLRequest{
		public static final String DOMAIN_URL="http://www.mylittlegenius.com.vn";
		public static final String CONTACT_US_URL=DOMAIN_URL+"/contact/contact-us/?api";
		public static final String LOGIN_URL=DOMAIN_URL+"/login/?api";
		public static final String LOGOUT_URL=DOMAIN_URL+"/login/?api&act=logout";
		public static final String UPDATE_USER_INFO_URL=DOMAIN_URL+"/kms/user-info/?api";
		public static final String TESTIMONIAL=DOMAIN_URL+"/testimonials/?api";
		public static final String PREVIEW_TIMING=DOMAIN_URL+ "/whats-new/previews/?api=&action=time";
		public static final String HOME_SLIDE=DOMAIN_URL+ "/?act=slideshow&api=&";
	}
}