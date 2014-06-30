package com.orange.studio.littlegenius.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class SplashActivity extends Activity {
	private  final int SPLASH_DISPLAY_TIME = 2000;
	private TextView mVersionName=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_layout);
		mVersionName=(TextView)findViewById(R.id.txtViewVersion);
		String verName=LG_CommonUtils.getVersionName(getApplicationContext());
		if(verName!=null){
			mVersionName.setText(verName);
		}
				
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				checkConnection();
			}
		}, SPLASH_DISPLAY_TIME);
	}
	private void checkConnection() {
		go2HomePage();
//		if (LG_CommonUtils.isOnline(getApplicationContext())) {
//			
//		} 
//		else {
//			//go2NetworkError();
//		}
	}
	private void go2HomePage(){
		Intent intent=new Intent(getApplicationContext(),BaseActivity.class);
		startActivity(intent);
		finish();
	}
}
