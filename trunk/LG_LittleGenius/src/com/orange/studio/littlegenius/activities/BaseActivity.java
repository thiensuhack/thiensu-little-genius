package com.orange.studio.littlegenius.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.MenuSlidingAdapter;
import com.orange.studio.littlegenius.fragments.HomeFragment;
import com.orange.studio.littlegenius.objects.SlidingMenuItem;
import com.slidingmenu.lib.SlidingMenu;

public class BaseActivity extends MetaSlidingFragmentActivity {

	private SlidingMenu sm = null;
	private ListView mListViewMenu = null;
	private MenuSlidingAdapter mMenuAdapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.sliding_menu_layout);
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		enableDrawerButton(true);
		
		createMenuLeft();
		setMetaContentView(R.layout.activity_base_layout);
		selectItem(0);
	}
	private void createMenuLeft(){
		mListViewMenu=(ListView)sm.findViewById(R.id.menuList);
		List<SlidingMenuItem> mListItem=new ArrayList<SlidingMenuItem>();
		SlidingMenuItem menuHome=new SlidingMenuItem(111,"Home");
		SlidingMenuItem menuProgram=new SlidingMenuItem(112,"Program");
		SlidingMenuItem menuTestimonials=new SlidingMenuItem(113,"Testimonials");
		SlidingMenuItem menuPreview=new SlidingMenuItem(114,"preview");
		SlidingMenuItem menuKms=new SlidingMenuItem(115,"Kms");
		SlidingMenuItem menuContact=new SlidingMenuItem(116,"Contact");
		SlidingMenuItem menuAbout=new SlidingMenuItem(117,"About");
		
		mListItem.add(menuHome);
		mListItem.add(menuProgram);
		mListItem.add(menuTestimonials);
		mListItem.add(menuPreview);
		mListItem.add(menuKms);
		mListItem.add(menuContact);
		mListItem.add(menuAbout);	
		
		mMenuAdapter=new MenuSlidingAdapter(this);
		mListViewMenu.setAdapter(mMenuAdapter);
		mMenuAdapter.updateFriendList(mListItem);		
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
