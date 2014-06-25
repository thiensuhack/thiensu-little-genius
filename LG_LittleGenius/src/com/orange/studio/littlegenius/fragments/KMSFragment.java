package com.orange.studio.littlegenius.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.studio.littlegenius.R;

public class KMSFragment extends BaseFragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_kms_layout, container,
					false);
			initView();
			initListener();
		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
    }
	@Override
	public void initView() {
		
	}

	@Override
	public void initListener() {
		
	}
}
