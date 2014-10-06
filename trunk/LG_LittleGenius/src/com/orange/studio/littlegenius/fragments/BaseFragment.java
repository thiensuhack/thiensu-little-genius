package com.orange.studio.littlegenius.fragments;

import com.orange.studio.littlegenius.activities.BaseActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class BaseFragment extends Fragment implements OnClickListener{
	protected View mView=null;
	protected BaseActivity mBaseActivity=null;
	protected KMSFragment mParentFragment=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public void initView(){
		
	}
	public void initListener(){
		
	}
	@Override
	public void onClick(View v) {
		
	}
	protected BaseActivity getBaseActivity(){
		return (BaseActivity)getActivity();
	}
	protected void switchView(boolean isShowMainView, boolean isShowLoadingView,
			boolean isNotFound){
		if(mParentFragment!=null){
			mParentFragment.switchView(isShowMainView, isShowLoadingView, isNotFound);
		}
	}
}
