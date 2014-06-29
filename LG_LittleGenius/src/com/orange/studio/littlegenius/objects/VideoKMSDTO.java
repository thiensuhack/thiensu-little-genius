package com.orange.studio.littlegenius.objects;

public class VideoKMSDTO {
	public String id;
	public String name;
	public String videoURL;
	public String cover;
	public VideoKMSDTO(String _id,String _name,String _videoURL,String _cover){
		id=_id;
		name=_name;
		videoURL=_videoURL;
		cover=_cover;
	}
}
