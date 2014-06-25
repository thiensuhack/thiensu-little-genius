package com.orange.studio.littlegenius.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.fragments.HomeFragment;
import com.slidingmenu.lib.SlidingMenu;

public class BaseActivity extends MetaSlidingFragmentActivity {	
	private SlidingMenu sm=null;
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
		selectItem(0);
	}
	private void selectItem(int position) {
		Fragment fragment = null;
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			fragment = HomeFragment.instantiate(getApplicationContext(),
					HomeFragment.class.getName());
			break;
		case 6:
			break;
		default:
			break;
		}
		
		if (fragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.mainFrameLayout, fragment).commit();
		}
	}
}
