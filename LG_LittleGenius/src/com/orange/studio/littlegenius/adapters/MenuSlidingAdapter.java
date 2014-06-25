package com.orange.studio.littlegenius.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.SlidingMenuItem;

public class MenuSlidingAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private Activity mActivity;
	private List<SlidingMenuItem> mData = null;

	public MenuSlidingAdapter(Activity a) {
		mActivity = a;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = new ArrayList<SlidingMenuItem>();
	}

	public void updateFriendList(List<SlidingMenuItem> _listUser) {
		if (mData != null) {
			mData.clear();
			mData.addAll(_listUser);
			notifyDataSetChanged();
		}
	}

	public void insertFriendList(List<SlidingMenuItem> _listUser) {
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
	public SlidingMenuItem getItem(int arg0) {
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
					R.layout.sliding_menu_item, parent, false);
			viewHolder = new MenuViewHolder();
			viewHolder.mCover = (ImageView) convertView
					.findViewById(R.id.menuIcon);
			viewHolder.mName = (TextView) convertView
					.findViewById(R.id.menuName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MenuViewHolder) convertView.getTag();
		}
		SlidingMenuItem item=mData.get(position);
		viewHolder.mName.setText(item.title);
		return convertView;
	}

	public class MenuViewHolder {
		public ImageView mCover;
		public TextView mName;
	}
}
