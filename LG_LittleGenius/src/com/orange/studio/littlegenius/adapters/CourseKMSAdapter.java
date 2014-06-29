package com.orange.studio.littlegenius.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.CourseKMSDTO;


public class CourseKMSAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private Activity mActivity;
	private List<CourseKMSDTO> mData = null;

	public CourseKMSAdapter(Activity a) {
		mActivity = a;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = new ArrayList<CourseKMSDTO>();
	}

	public void updateData(List<CourseKMSDTO> _listUser) {
		if (mData != null) {
			mData.clear();
			mData.addAll(_listUser);
			notifyDataSetChanged();
		}
	}

	public void insertFriendList(List<CourseKMSDTO> _listUser) {
		if (mData != null) {
			mData.addAll(_listUser);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public CourseKMSDTO getItem(int arg0) {
		if (mData != null) {
			return mData.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.course_kms_item, parent, false);
			viewHolder = new MenuViewHolder();
			viewHolder.mName = (TextView) convertView
					.findViewById(R.id.courseName);
			viewHolder.mStatus = (TextView) convertView
					.findViewById(R.id.courseStatus);
			viewHolder.mIconStatus = (ImageView) convertView
					.findViewById(R.id.statusIcon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MenuViewHolder) convertView.getTag();
		}
		
		CourseKMSDTO item=mData.get(position);
		viewHolder.mName.setText(Html.fromHtml(item.courseName));
		viewHolder.mStatus.setText(Html.fromHtml(item.status));
		if(item.status.equals("Done")){
			viewHolder.mIconStatus.setVisibility(View.VISIBLE);
		}else{
			viewHolder.mIconStatus.setVisibility(View.GONE);
		}
		if(position%2==0){
			convertView.setBackgroundResource(R.color.bright_orange_2);
		}else{
			convertView.setBackgroundResource(R.color.home_background);
		}
		return convertView;
	}

	public class MenuViewHolder {
		public TextView mName;
		public TextView mStatus;
		public ImageView mIconStatus;
	}
}
