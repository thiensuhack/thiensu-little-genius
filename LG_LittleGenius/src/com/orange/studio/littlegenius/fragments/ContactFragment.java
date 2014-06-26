package com.orange.studio.littlegenius.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;

public class ContactFragment extends BaseFragment implements OnClickListener{

	private TextView txt_content1;
	private TextView txt_content;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_contact_layout, container,
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
		txt_content1 = (TextView)mView.findViewById(R.id.txt_content1);
		//txt_content1.setTextColor(Color.BLACK);
		txt_content1.setText(Html.fromHtml("<b>Liên hệ<b>"));
		
		txt_content = (TextView)mView.findViewById(R.id.txt_content);
		txt_content.setTextColor(Color.WHITE);
	}

	@Override
	public void initListener() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			super.onClick(v);
			break;
		}
	}


}

