package com.orange.studio.littlegenius.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.RadioButtonItem;

public class ListRadioButtonAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private Activity mActivity;
	private List<RadioButtonItem> mData = null;

	public ListRadioButtonAdapter(Activity a) {
		mActivity = a;
		mInflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = new ArrayList<RadioButtonItem>();
	}

	public void updateFriendList(List<RadioButtonItem> _listData) {
		if (mData != null) {
			mData.clear();
			mData.addAll(_listData);
			notifyDataSetChanged();
		}
	}

	public void insertFriendList(List<RadioButtonItem> _listData) {
		if (mData != null) {
			mData.addAll(_listData);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public RadioButtonItem getItem(int arg0) {
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
			viewHolder.mName = (RadioButton) convertView
					.findViewById(R.id.menuName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MenuViewHolder) convertView.getTag();
		}
		RadioButtonItem item=mData.get(position);
		viewHolder.mName.setText(item.name);
		viewHolder.mName.setTag(item.id);
		return convertView;
	}

	public class MenuViewHolder {
		public RadioButton mName;
	}
}
