package com.orange.studio.littlegenius.objects;

public class VideoKMSDTO {
	public String id;
	public String name;
	public String videoURL;
	public String cover;
	public String youtubeId;
	public String type;
	public String date;
	public String status;
	public String user_id;
	public VideoKMSDTO(String _id,String _name,String _videoURL,String _cover){
		id=_id;
		name=_name;
		videoURL=_videoURL;
		cover=_cover;
	}
	public VideoKMSDTO(){
		
	}
//	"video_id":"8",
//	"video_name":"Video Test 1",
//	"video_url":"",
//	"youtube_id":"ApGa4LsOS1w",
//	"video_type":"1",
//	"video_date":"3\/4\/2013",
//	"video_status":"1",
//	"user_id":"2"
}
