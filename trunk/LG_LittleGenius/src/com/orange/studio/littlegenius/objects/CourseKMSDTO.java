package com.orange.studio.littlegenius.objects;

public class CourseKMSDTO {
	public String id; 
	public String courseName;
	public String status;
	public String date;
	public String term;
	public String notice;
	public String user_id;
	public CourseKMSDTO(){
		
	}
	public CourseKMSDTO(String _id,String _courseName,String _status){
		id=_id;
		courseName=_courseName;
		status=_status;
	}
//	"course_id":"2",
//	"course_name":"Genius Immersion",
//	"course_status":"Attending",
//	"course_date":"25\/7\/2013",
//	"course_term":"",
//	"course_notice":"",
//	"user_id":"2"
}
