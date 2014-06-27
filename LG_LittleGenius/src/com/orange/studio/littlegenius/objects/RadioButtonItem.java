package com.orange.studio.littlegenius.objects;

public class RadioButtonItem {
	public int id;
	public String name;
	public boolean isChecked;
	public RadioButtonItem(int _id,String _name){
		id=_id;
		name=_name;
		isChecked=false;
	}
}
