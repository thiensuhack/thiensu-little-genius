package com.orange.studio.littlegenius.listeners;

import java.util.List;

import com.orange.studio.littlegenius.objects.CourseKMSDTO;
import com.orange.studio.littlegenius.objects.HomeSliderDTO;
import com.orange.studio.littlegenius.objects.UserDTO;
import com.orange.studio.littlegenius.objects.VideoKMSDTO;

public interface CommonIF {
	public void setStore(String key, String value);
	public void setStore(String key, String value,int expiredTime);
	public String getStore(String key);
	public List<HomeSliderDTO> getHomeSlider();
	public UserDTO userLogin(String url,String _data);
	public UserDTO getUserInfo();
	public List<VideoKMSDTO> getListVideoFromServer(String url);
	public List<CourseKMSDTO> getListCourseFromServer();
}
