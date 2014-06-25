package com.orange.studio.littlegenius.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.meta.gui.activity.MetaFragmentActivity;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.fragments.KMSFragment;
import com.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends MetaFragmentActivity {
	protected SlidingMenu sm = null;
	private View mAboutBtn=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setBehindContentView(R.layout.menu_home_footer_layout);
//		sm = getSlidingMenu();		
//		sm.setShadowWidthRes(R.dimen.shadow_width);
//		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		sm.setFadeDegree(0.35f);
//		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//		enableDrawerButton(true);
		setMetaContentView(R.layout.fragment_home_layout);
		mAboutBtn=(LinearLayout)findViewById(R.id.aboutBtn);
		mAboutBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.aboutBtn:
			Intent intent=new Intent(getApplicationContext(), KMSFragment.class);
			startActivity(intent);
			break;
		default:
			super.onClick(v);
			break;
		}				
	}
}
