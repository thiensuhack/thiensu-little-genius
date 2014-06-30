package com.orange.studio.littlegenius.models;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orange.studio.littlegenius.LG_ApplicationContext;
import com.orange.studio.littlegenius.listeners.CommonIF;
import com.orange.studio.littlegenius.objects.CourseKMSDTO;
import com.orange.studio.littlegenius.objects.HomeSliderDTO;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.UserDTO;
import com.orange.studio.littlegenius.objects.VideoKMSDTO;
import com.orange.studio.littlegenius.utils.AppConfig.Cache;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.AppConfig;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;
import com.orange.studio.littlegenius.utils.LittleGeniusUtils;
import com.zuzu.db.store.SimpleStoreIF;


public class CommonModel implements CommonIF {
	private static CommonIF _instance;
	private static final Lock createLock = new ReentrantLock();
	private static final int STORE_EXPIRE = -1;
	
	public static CommonIF getInstance() {
		if (_instance == null) {
			createLock.lock();
			if (_instance == null) {
				_instance = new CommonModel();
			}
			createLock.unlock();
		}
		return _instance;
	}
	public CommonModel() {
		getStoreAdapter();
	}
	private SimpleStoreIF getStoreAdapter() {
		return LittleGeniusUtils.getStoreAdapter(Cache.COMMON_NAME,
				LG_ApplicationContext.getContext(), Cache.COMMON_NUMBER);
	}
	@Override
	public void setStore(String key, String value) {
		try {
			this.getStoreAdapter().put(key, value, STORE_EXPIRE);
		} catch (Exception e) {
		}		
	}
	@Override
	public void setStore(String key, String value,int expiredTime) {
		try {
			this.getStoreAdapter().put(key, value, expiredTime);
		} catch (Exception e) {
		}		
	}
	public void setStore(String key, Object item) {
		if (item == null) {
			return;
		}
		Gson gson = new Gson();
		String temp = gson.toJson(item);
		this.getStoreAdapter().put(key, temp, STORE_EXPIRE);
	}
	public void setStore(String key, Object item,int expiredTime) {
		if (item == null) {
			return;
		}
		Gson gson = new Gson();
		String temp = gson.toJson(item);
		this.getStoreAdapter().put(key, temp, expiredTime);
	}
	@Override
	public String getStore(String key){
		String result=null;
		try {
			result = getStoreAdapter().get(key);
			if(result!=null && result.trim().length()<1){
				result=null;
			}
		} catch (Exception e) {
			result=null;
		}
		return result;
	}
	@Override
	public List<HomeSliderDTO> getHomeSlider() {
		return null;
	}
	public UserDTO getUserInfo(){
		String strData=getStore(Cache.USER_INFO_KEY);
		if(strData!=null && strData.trim().length()>0){
			try {
				Gson gs=new Gson();				
				UserDTO result = gs.fromJson(strData, UserDTO.class);
				return result;
			} catch (Exception e) {
				Log.e("GET USER INFO ERROR", e.getMessage());
			}
			return null;
		}
		return null;
	}
	public List<CourseKMSDTO> getListCourseFromServer(){
		List<CourseKMSDTO> mList=null;
		LG_CommonUtils.checkUserInfo();
		if(AppConfig.mUser==null){
			return null;
		}
		String strData = getUserJSONDataForPOST();
		ResultData result=LG_CommonUtils.postDataServer(URLRequest.COURSE_KMS_URL,strData);
		try {
			if(result!=null && result.result==1){
				JSONArray jArr=new JSONArray(result.data);					
				if(jArr!=null && jArr.length()>0){
					mList=new ArrayList<CourseKMSDTO>();
					for (int i = 0; i < jArr.length(); i++) {
						JSONObject jb=jArr.getJSONObject(i);
						CourseKMSDTO course=new CourseKMSDTO();
						course.courseName=jb.optString("course_name");
						course.date=jb.optString("course_date");
						course.id=jb.optString("course_id");
						course.notice=jb.optString("");
						course.status=jb.optString("course_status");
						course.term=jb.optString("course_term");
						course.user_id=jb.optString("user_id");
						mList.add(course);
					}
				}
			}			
		} catch (Exception e) {
		}	
		return mList;
	}
	public List<VideoKMSDTO> getListVideoFromServer(String url){
		try {
		  	List<VideoKMSDTO> mList=null;	
		  	LG_CommonUtils.checkUserInfo();
			if(AppConfig.mUser==null){
				return null;
			}
		  	String strData = getUserJSONDataForPOST();
		  	
			ResultData data=LG_CommonUtils.postDataServer(url,strData);
			if(data!=null && data.result==1){
				JSONArray jArr=new JSONArray(data.data);					
				if(jArr!=null && jArr.length()>0){
					mList=new ArrayList<VideoKMSDTO>();
					for (int i = 0; i < jArr.length(); i++) {
						JSONObject jb=jArr.getJSONObject(i);
						VideoKMSDTO video=new VideoKMSDTO();
						video.id=jb.optString("video_id");
						video.name=jb.optString("video_name");
						video.date=jb.optString("video_date");
						video.status=jb.optString("video_status");
						video.type=jb.optString("video_type");
						video.videoURL=jb.optString("video_url");
						video.youtubeId=jb.optString("youtube_id");
						video.user_id=jb.optString("user_id");
						if(video.youtubeId!=null && video.youtubeId.length()>0){
							video.cover="http://img.youtube.com/vi/"+video.youtubeId+"/1.jpg";
						}
						mList.add(video);
					}
				}
			}
			return mList;
	} catch (Exception e) {
		return null;
	}
	}
	private String getUserJSONDataForPOST() {		
		UserDTO temp=new UserDTO();
		temp.token_id=AppConfig.mUser.token_id;
		temp.user_id=AppConfig.mUser.user_id;
		temp.user_email=null;
		temp.user_login=null;
		temp.user_nicename=null;
		Gson gs=new Gson();
		String strData=gs.toJson(temp);
		return strData;
	}
	@SuppressWarnings("unchecked")
	private Object deserializeStoreData(String json) {
		Object result = null;
		if (json == null || json.equals(""))
			return result;
		try {
			result = new Object();
			Gson gson = new Gson();
			Type listType = new TypeToken<Object>() {
			}.getType();
			result = (Object) gson.fromJson(json, listType);
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	@Override
	public UserDTO userLogin(String url, String data) {
		ResultData result = LG_CommonUtils.postDataServer(URLRequest.LOGIN_URL, data);
		if(result!=null && result.result == 1){
			try {
				JSONObject jb=new JSONObject(result.data);
				UserDTO mUser=new UserDTO();
				mUser.user_email=jb.optString("user_email");
				mUser.user_login=jb.optString("user_login");
				mUser.user_nicename=jb.optString("user_nicename");
				mUser.user_id=jb.optString("user_id");
				mUser.token_id=jb.optString("token_id");
				//AppConfig.mUser=mUser;
				setStore(Cache.USER_INFO_KEY, mUser, Cache.USER_INFO_TIME);
				return mUser;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			return null;
		}
	}

}
