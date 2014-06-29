package com.orange.studio.littlegenius.objects;

public class CourseKMSDTO {
	public String id; 
	public String courseName;
	public String status;
	public CourseKMSDTO(String _id,String _courseName,String _status){
		id=_id;
		courseName=_courseName;
		status=_status;
	}
}
