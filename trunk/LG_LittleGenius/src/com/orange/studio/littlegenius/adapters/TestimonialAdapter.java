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
import com.orange.studio.littlegenius.objects.TestimonialDTO;
import com.orange.studio.littlegenius.objects.TestimonialDTO;

public class TestimonialAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private Activity mActivity;
	private List<TestimonialDTO> mData = null;

	public TestimonialAdapter(Activity a) {
		mActivity = a;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = new ArrayList<TestimonialDTO>();
	}

	public void updateData(List<TestimonialDTO> _listUser) {
		if (mData != null) {
			mData.clear();
			mData.addAll(_listUser);
			notifyDataSetChanged();
		}
	}

	public void insertFriendList(List<TestimonialDTO> _listUser) {
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
	public TestimonialDTO getItem(int arg0) {
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
					R.layout.testimonial_item, parent, false);
			viewHolder = new MenuViewHolder();
			viewHolder.mCover = (ImageView) convertView
					.findViewById(R.id.menuIcon);
			viewHolder.mContent = (TextView) convertView
					.findViewById(R.id.content);
			viewHolder.mName = (TextView) convertView
					.findViewById(R.id.author);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MenuViewHolder) convertView.getTag();
		}
		
		TestimonialDTO item=mData.get(position);
		viewHolder.mName.setText("("+Html.fromHtml(item.author)+")");
		viewHolder.mContent.setText(Html.fromHtml(item.content));
		ImageLoader.getInstance(mActivity).DisplayImage(item.thumbnail, viewHolder.mCover);
		if(position%2==0){
			convertView.setBackgroundResource(R.color.bright_orange);
		}else{
			convertView.setBackgroundResource(R.color.home_background);
		}
		return convertView;
	}

	public class MenuViewHolder {
		public ImageView mCover;
		public TextView mName;
		public TextView mContent;
	}
}
