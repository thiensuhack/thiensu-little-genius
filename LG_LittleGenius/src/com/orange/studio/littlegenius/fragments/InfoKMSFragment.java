package com.orange.studio.littlegenius.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;

public class InfoKMSFragment extends BaseFragment implements OnClickListener{

	private TextView mName;
	private TextView mEmail;
	private TextView mPhone;
	private TextView mAddress;
	private Button mUpdateInfoBtn;
	private Button mChangePasswordBtn;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_info_layout, container,
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
		mName=(TextView)mView.findViewById(R.id.userName);
		mEmail=(TextView)mView.findViewById(R.id.userEmail);
		mPhone=(TextView)mView.findViewById(R.id.userPhone);
		mAddress=(TextView)mView.findViewById(R.id.userAddress);
		mUpdateInfoBtn=(Button)mView.findViewById(R.id.updateInfoBtn);
		mChangePasswordBtn=(Button)mView.findViewById(R.id.changePasswordBtn);
	}

	@Override
	public void initListener() {
		mUpdateInfoBtn.setOnClickListener(this);
		mChangePasswordBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.updateInfoBtn:
			break;
		case R.id.changePasswordBtn:
			break;
		default:
			super.onClick(v);
			break;
		}
	}


}

