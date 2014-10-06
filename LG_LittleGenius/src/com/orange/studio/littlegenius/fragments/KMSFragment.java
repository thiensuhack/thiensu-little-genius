package com.orange.studio.littlegenius.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.todddavies.components.progressbar.ProgressWheel;

public class KMSFragment extends BaseFragment {
	private View mCameraBtn;
	private View mVideoBtn;
	private View mCourseBtn;
	private View mInfoBtn;
	private TextView mCameraTv;
	private TextView mVideoTv;
	private TextView mCourseTv;
	private TextView mInfoTv;
	
	private View mContainerView = null;
	private View mLoadingView = null;
	private View mNotFoundDataView = null;
	private ProgressWheel mProgressView = null;
	
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
		mContainerView = (FrameLayout) mView.findViewById(R.id.kmsMainFrameLayout);
		mLoadingView = (LinearLayout) mView.findViewById(R.id.loadingViewKMS);
		mNotFoundDataView = (RelativeLayout) mView.findViewById(R.id.notFoundDataViewKMS);
		mProgressView = (ProgressWheel) mView.findViewById(R.id.progressWheel);

		switchView(true, false, false);
		
		mCameraBtn=(LinearLayout)mView.findViewById(R.id.cameraBtn);
		mVideoBtn=(LinearLayout)mView.findViewById(R.id.videoBtn);
		mCourseBtn=(LinearLayout)mView.findViewById(R.id.courseBtn);
		mInfoBtn=(LinearLayout)mView.findViewById(R.id.infoBtn);
		
		mCameraTv=(TextView)mView.findViewById(R.id.cameraTxt);
		mVideoTv=(TextView)mView.findViewById(R.id.videoTxt);
		mCourseTv=(TextView)mView.findViewById(R.id.courseTxt);
		mInfoTv=(TextView)mView.findViewById(R.id.infoTxt);
		selectItem(0);
	}
	public void switchView(boolean isShowMainView, boolean isShowLoadingView,
			boolean isNotFound) {
		try {
			mLoadingView.setVisibility(isShowLoadingView ? View.VISIBLE
					: View.GONE);
			if (isShowLoadingView) {
				mProgressView.spin();
			} else {
				mProgressView.stopSpinning();
			}
			mNotFoundDataView.setVisibility(isNotFound ? View.VISIBLE
					: View.GONE);
			mContainerView.setVisibility(isShowMainView ? View.VISIBLE
					: View.INVISIBLE);
		} catch (Exception e) {

		}

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
			changeBackgroundButtonSelected(true, false, false, false);		
			fragment = CameraKMSFragment.instantiate(getActivity().getApplicationContext(),
					CameraKMSFragment.class.getName());
			break;
		case 1:
			changeBackgroundButtonSelected(false, true, false, false);
			fragment = VideoKMSFragment.instantiate(getActivity().getApplicationContext(),
					VideoKMSFragment.class.getName());
			break;
		case 2:
			changeBackgroundButtonSelected(false, false, true, false);
			fragment = CourseKMSFragment.instantiate(getActivity().getApplicationContext(),
					CourseKMSFragment.class.getName());
			break;
		case 3:
			changeBackgroundButtonSelected(false, false, false, true);
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
			//changeBackgroundButtonSelected(true, false, false, false);		
			selectItem(0);
			break;
		case R.id.videoBtn:
			//changeBackgroundButtonSelected(false, true, false, false);
			selectItem(1);
			break;
		case R.id.courseBtn:
			//changeBackgroundButtonSelected(false, false, true, false);
			selectItem(2);
			break;
		case R.id.infoBtn:
			//changeBackgroundButtonSelected(false, false, false, true);
			selectItem(3);
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	private void changeBackgroundButtonSelected(boolean isCamera,boolean isVideo,boolean isCourse,boolean isInfo){
		mCameraBtn.setBackgroundResource(isCamera?R.drawable.kms_menu_button_1_active:R.drawable.kms_menu_button_1);
		mVideoBtn.setBackgroundResource(isVideo?R.drawable.kms_menu_button_3_active:R.drawable.kms_menu_button_3);
		mCourseBtn.setBackgroundResource(isCourse?R.drawable.kms_menu_button_3_active:R.drawable.kms_menu_button_3);
		mInfoBtn.setBackgroundResource(isInfo?R.drawable.kms_menu_button_2_active:R.drawable.kms_menu_button_2);
		
		
		mCameraTv.setTextColor(isCamera?Color.parseColor(getActivity().getString(R.color.blue)):Color.parseColor(getActivity().getString(R.color.white)));
		mVideoTv.setTextColor(isVideo?Color.parseColor(getActivity().getString(R.color.blue)):Color.parseColor(getActivity().getString(R.color.white)));
		mCourseTv.setTextColor(isCourse?Color.parseColor(getActivity().getString(R.color.blue)):Color.parseColor(getActivity().getString(R.color.white)));
		mInfoTv.setTextColor(isInfo?Color.parseColor(getActivity().getString(R.color.blue)):Color.parseColor(getActivity().getString(R.color.white)));
	}
}
