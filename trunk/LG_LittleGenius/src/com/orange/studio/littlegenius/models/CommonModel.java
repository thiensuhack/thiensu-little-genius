package com.orange.studio.littlegenius.models;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.orange.studio.littlegenius.LG_ApplicationContext;
import com.orange.studio.littlegenius.listeners.CommonIF;
import com.orange.studio.littlegenius.objects.HomeSliderDTO;
import com.orange.studio.littlegenius.utils.AppConfig.Cache;
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

}
