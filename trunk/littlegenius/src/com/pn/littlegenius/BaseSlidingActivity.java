package com.pn.littlegenius;

import android.os.Bundle;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;

public class BaseSlidingActivity extends MetaSlidingFragmentActivity{
	private SlidingMenu sm=null;
	
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
	}
}
