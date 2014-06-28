package com.orange.studio.littlegenius.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.RadioButtonItem;

public class ListRadioButtonAdapter extends BaseAdapter {

	private static LayoutInflater mInflater = null;
	private Activity mActivity;
	private List<RadioButtonItem> mData = null;
	private int currentId=-1;
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
	public void updateCheckedList(int id){
		for (int i = 0; i < mData.size(); i++) {
			if(id == mData.get(i).id){
				mData.get(i).isChecked=true;
			}else{
				mData.get(i).isChecked=false;
			}
		}
		notifyDataSetChanged();
	}
	public RadioButtonItem getCurrentItem(){
		return getItem(currentId);
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public RadioButtonItem getItem(int position) {
		if (mData != null && position>-1 && position < mData.size()) {
			return mData.get(position);
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
					R.layout.radio_button_item_row, parent, false);
			viewHolder = new MenuViewHolder();
			viewHolder.mRadioBtn = (RadioButton) convertView
					.findViewById(R.id.radioButton);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MenuViewHolder) convertView.getTag();
		}
		RadioButtonItem item=mData.get(position);
		
		viewHolder.mRadioBtn.setChecked(item.isChecked);
		viewHolder.mRadioBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id=(int)v.getTag();
				currentId=id;
				updateCheckedList(id);
			}
		});
		viewHolder.mRadioBtn.setText(item.name);
		viewHolder.mRadioBtn.setTag(item.id);
		return convertView;
	}

	public class MenuViewHolder {
		public RadioButton mRadioBtn;
	}
}
