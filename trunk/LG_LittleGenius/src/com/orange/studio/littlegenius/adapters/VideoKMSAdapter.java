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

import com.fedorvlasov.lazylist.ImageLoader;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.VideoKMSDTO;


public class VideoKMSAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private Activity mActivity;
	private List<VideoKMSDTO> mData = null;

	public VideoKMSAdapter(Activity a) {
		mActivity = a;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = new ArrayList<VideoKMSDTO>();
	}

	public void updateData(List<VideoKMSDTO> _listUser) {
		if (mData != null) {
			mData.clear();
			mData.addAll(_listUser);
			notifyDataSetChanged();
		}
	}

	public void insertFriendList(List<VideoKMSDTO> _listUser) {
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
	public VideoKMSDTO getItem(int arg0) {
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
					R.layout.video_kms_item, parent, false);
			viewHolder = new MenuViewHolder();
			viewHolder.mCover = (ImageView) convertView
					.findViewById(R.id.menuIcon);
			viewHolder.mVideoName = (TextView) convertView
					.findViewById(R.id.videoName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MenuViewHolder) convertView.getTag();
		}
		
		VideoKMSDTO item=mData.get(position);
		viewHolder.mVideoName.setText(Html.fromHtml(item.name));
		ImageLoader.getInstance(mActivity).DisplayImage(item.cover, viewHolder.mCover);
		if(position%2==0){
			convertView.setBackgroundResource(R.color.bright_orange_2);
		}else{
			convertView.setBackgroundResource(R.color.home_background);
		}
		return convertView;
	}

	public class MenuViewHolder {
		public ImageView mCover;
		public TextView mVideoName;
	}
}
