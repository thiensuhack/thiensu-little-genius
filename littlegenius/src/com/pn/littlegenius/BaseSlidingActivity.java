package com.pn.littlegenius;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;

public class BaseSlidingActivity extends MetaSlidingFragmentActivity {
	private SlidingMenu sm = null;
	private View mHomeBtn;
	private View mSettingBtn;
	private LinearLayout mContentLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.menu_home_footer_layout);
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		enableDrawerButton(true);
		setMetaContentView(R.layout.activity_base_layout);
		
		initBaseView();
		initBaseListener();
	}

	private void initBaseView() {
		mContentLayout = (LinearLayout) findViewById(R.id.activity_base_content);
		mHomeBtn = (LinearLayout) findViewById(R.id.homeBtn);
		mSettingBtn = (ImageView) findViewById(R.id.homeSlidingBtn);
	}
	private void initBaseListener(){
		mSettingBtn.setOnClickListener(this);
		mHomeBtn.setOnClickListener(this);
	}
	protected void setMainContentView(int resId) {
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(resId, mContentLayout);
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homeBtn:
//			Intent intent = new Intent(this,MainActivity.class);
//			startActivity(intent);
			finish();
			break;
		case R.id.btn_setting:
//			Intent intent = new Intent(this,SettingActivity.class);
//			startActivity(intent);
//			finish();
			break;
		default:
			break;
		}
	}
}
