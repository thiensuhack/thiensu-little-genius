package com.meta.gui.activity;

import com.meta.gui.R;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivityHelper;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class MetaSlidingActivity extends MetaActivity {
	private View mDrawer = null;
	private boolean mDrawerPressEnable = false;
	private SlidingActivityHelper mSlidingHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSlidingHelper = new SlidingActivityHelper(this);
		mSlidingHelper.onCreate(savedInstanceState);
		mDrawer = findViewById(R.id.meta_activity_base_home_drawer);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mSlidingHelper.onPostCreate(savedInstanceState);
	}

	public void enableDrawerButton(boolean enable) {
		mDrawerPressEnable = enable;
		if (enable) {
			mDrawer.setVisibility(View.VISIBLE);
		} else
			mDrawer.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v.getId() == R.id.meta_activity_base_home) {
			if (mDrawerPressEnable) {
				mSlidingHelper.showMenu();
			}
		}
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mSlidingHelper.findViewById(id);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mSlidingHelper.onSaveInstanceState(outState);
	}

	public SlidingMenu getSlidingMenu() {
		return mSlidingHelper.getSlidingMenu();
	}

	public void toggle() {
		mSlidingHelper.toggle();
	}

	public void showContent() {
		mSlidingHelper.showContent();
	}

	public void showMenu() {
		mSlidingHelper.showMenu();
	}

	public void showSecondaryMenu() {
		mSlidingHelper.showSecondaryMenu();
	}

	public void setSlidingActionBarEnabled(boolean b) {
		mSlidingHelper.setSlidingActionBarEnabled(b);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean result = false;

		do {
			if (keyCode == KeyEvent.KEYCODE_MENU) {
				mSlidingHelper.toggle();
				result = true;
				break;
			}

			if (mSlidingHelper.onKeyUp(keyCode, event)) {
				result = true;
				break;
			}

			result = super.onKeyUp(keyCode, event);
		} while (false);

		return result;
	}
}
