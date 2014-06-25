package com.orange.studio.littlegenius.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orange.studio.littlegenius.R;

public class KMSFragment extends BaseFragment {
	private View mCameraBtn;
	private View mVideoBtn;
	private View mCourseBtn;
	private View mInfoBtn;
	
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
		mCameraBtn=(LinearLayout)mView.findViewById(R.id.cameraBtn);
		mVideoBtn=(LinearLayout)mView.findViewById(R.id.videoBtn);
		mCourseBtn=(LinearLayout)mView.findViewById(R.id.courseBtn);
		mInfoBtn=(LinearLayout)mView.findViewById(R.id.infoBtn);
		
		selectItem(0);
	}

	@Override
	public void initListener() {
		mCameraBtn.setOnClickListener(this);
		mVideoBtn.setOnClickListener(this);
		mCourseBtn.setOnClickListener(this);
		mInfoBtn.setOnClickListener(this);
	}
	public void selectItem(int position) {
		Fragment fragment = null;
		FragmentManager fragmentManager = getChildFragmentManager();
		switch (position) {
		case 0:
			fragment = CameraKMSFragment.instantiate(getActivity().getApplicationContext(),
					CameraKMSFragment.class.getName());
			break;
		case 1:
			fragment = VideoKMSFragment.instantiate(getActivity().getApplicationContext(),
					VideoKMSFragment.class.getName());
			break;
		case 2:
			fragment = CourseKMSFragment.instantiate(getActivity().getApplicationContext(),
					CourseKMSFragment.class.getName());
			break;
		case 3:
			fragment = InfoKMSFragment.instantiate(getActivity().getApplicationContext(),
					InfoKMSFragment.class.getName());
			break;
		default:
			break;
		}
		
		if (fragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.kmsMainFrameLayout, fragment).commit();
		}
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.cameraBtn:
			selectItem(0);
			break;
		case R.id.videoBtn:
			selectItem(1);
			break;
		case R.id.courseBtn:
			selectItem(2);
			break;
		case R.id.infoBtn:
			selectItem(3);
			break;
		default:
			break;
		}
		super.onClick(v);
	}
}
