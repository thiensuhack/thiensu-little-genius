package com.orange.studio.littlegenius.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.studio.littlegenius.R;

public class HomeFragment extends BaseFragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.activity_home_layout, container,
					false);
			initView();
			initListener();
		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
    }
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.aboutBtn:
			break;
		default:
			super.onClick(v);
			break;
		}				
	}
	@Override
	public void initView() {
		
	}
	@Override
	public void initListener() {
		
	}
}
