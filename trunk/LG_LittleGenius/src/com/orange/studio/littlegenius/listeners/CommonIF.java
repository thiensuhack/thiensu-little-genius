package com.orange.studio.littlegenius.listeners;

import java.util.List;

import com.orange.studio.littlegenius.objects.HomeSliderDTO;

public interface CommonIF {
	public void setStore(String key, String value);
	public void setStore(String key, String value,int expiredTime);
	public String getStore(String key);
	public List<HomeSliderDTO> getHomeSlider();
}
