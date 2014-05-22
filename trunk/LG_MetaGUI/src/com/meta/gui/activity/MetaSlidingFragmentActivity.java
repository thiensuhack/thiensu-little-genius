package com.meta.gui.activity;

import com.meta.gui.R;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivityHelper;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class MetaSlidingFragmentActivity extends MetaFragmentActivity {
	private SlidingActivityHelper mSlidingHelper;

	private View mDrawer = null;
	private boolean mDrawerPressEnable = false;

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

	@Override
	public void setMetaContentView(int id) {
		super.setMetaContentView(id);

		View v = getLayoutInflater().inflate(id, null);
		mSlidingHelper.registerAboveContentView(v, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	@Override
	public void setMetaContentView(View v, LayoutParams params) {
		super.setMetaContentView(v, params);
		mSlidingHelper.registerAboveContentView(v, params);
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

	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	public void setBehindContentView(View v) {
		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	public void setBehindContentView(View v, LayoutParams params) {
		mSlidingHelper.setBehindContentView(v, params);
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
