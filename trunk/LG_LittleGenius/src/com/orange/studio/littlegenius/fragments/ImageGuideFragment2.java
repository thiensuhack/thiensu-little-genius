package com.orange.studio.littlegenius.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.fragments.HomeFragment.DoAction;

public class ImageGuideFragment2 extends Fragment implements OnClickListener {
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	private int imageResource;
	private ImageView mImage = null;
	private static DoAction doAction = null;
	
	public static final ImageGuideFragment2 newInstance(String message,
			int _imageResource, DoAction _doAction) {
		ImageGuideFragment2 f = new ImageGuideFragment2();
		f.imageResource = _imageResource;
		doAction = _doAction;
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View mView = inflater.inflate(R.layout.main_slider_layout,
				container, false);
		mImage = (ImageView) mView.findViewById(R.id.imageSlider);		
		mImage.setOnClickListener(this);
		return mView;
	}
	@Override
	public void onResume() {
		super.onResume();
		if(mImage!=null){
			mImage.setBackgroundResource(imageResource);
		}
	}
	@Override
	public void onClick(View v) {
		try {
			doAction.DissmissDialog();
		} catch (Exception e) {
		}
	}

}
