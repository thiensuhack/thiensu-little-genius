package com.meta.gui.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meta.gui.R;
import com.meta.gui.quickaction.QuickAction;

public class MetaFragmentActivity extends FragmentActivity implements
		OnClickListener {
	private View mHomeActionMenu = null;
	private View mBackActionIcon = null;
	private boolean mEnableBackPress = false;
	private ImageView mHomeActionIcon = null;

	private View mQuickActionMenu = null;
	private ImageView mQuickActionIcon = null;
	private QuickAction mQuickActionController = null;

	private View mSecondActionMenu = null;
	private ImageView mSecondActionIcon = null;
	private QuickAction mSecondActionController = null;

	private View mThirdActionMenu = null;
	private ImageView mThirdActionIcon = null;
	private QuickAction mThirdActionController = null;

	private LinearLayout mContentLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.meta__activity_base_content);

		mHomeActionMenu = findViewById(R.id.meta_activity_base_home);
		mHomeActionMenu.setOnClickListener(this);
		mHomeActionIcon = (ImageView) findViewById(R.id.meta_activity_base_home_icon);
		mBackActionIcon = findViewById(R.id.meta_activity_base_home_back);

		mQuickActionMenu = findViewById(R.id.meta_activity_base_quick_action);
		mQuickActionMenu.setVisibility(View.GONE);
		mQuickActionMenu.setOnClickListener(this);
		mQuickActionIcon = (ImageView) findViewById(R.id.meta__activity_base_quick_action_icon);

		mSecondActionMenu = findViewById(R.id.meta_activity_base_second_action);
		mSecondActionMenu.setVisibility(View.GONE);
		mSecondActionMenu.setOnClickListener(this);
		mSecondActionIcon = (ImageView) findViewById(R.id.meta__activity_base_second_action_icon);

		mThirdActionMenu = findViewById(R.id.meta_activity_base_third_action);
		mThirdActionIcon = (ImageView) findViewById(R.id.meta__activity_base_third_action_icon);
		mThirdActionMenu.setOnClickListener(this);

		mContentLayout = (LinearLayout) findViewById(R.id.meta__activity_base_content);
		setHomeIcon(getApplicationInfo().icon);
		setTitle(getTitle());
	}

	protected void setMetaContentView(int resId) {
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(resId, mContentLayout);
	}

	protected void setMetaContentView(View view) {
		setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	public void setMetaContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
	}

	public void enableBackButton(boolean enable) {
		mEnableBackPress = enable;
		if (enable) {
			mBackActionIcon.setVisibility(View.VISIBLE);
		} else
			mBackActionIcon.setVisibility(View.INVISIBLE);
	}

	@Override
	public void setTitle(CharSequence title) {
		((TextView) findViewById(R.id.meta_activity_base_title)).setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		((TextView) findViewById(R.id.meta_activity_base_title))
				.setText(getString(titleId));
	}

	protected void setHomeIcon(int resId) {
		mHomeActionIcon.setImageResource(resId);
	}

	protected void setHomeIcon(Bitmap bm) {
		mHomeActionIcon.setImageBitmap(bm);
	}

	protected void setSearchActionVisibility(int visibility) {
		mSecondActionMenu.setVisibility(visibility);
	}

	protected void setShareActionVisibility(int visibility) {
		mThirdActionMenu.setVisibility(visibility);
	}

	/**************************************************************************
	 * Quick action menu
	 *************************************************************************/
	protected final QuickAction getQuickAction() {
		return mQuickActionController;
	}

	protected final void setQuickAction(QuickAction action, int resId) {
		do {
			if (action == null)
				break;

			mQuickActionMenu.setVisibility(View.VISIBLE);
			mQuickActionMenu.setClickable(true);
			mQuickActionIcon.setImageResource(resId);
			mQuickActionController = action;
		} while (false);
	}

	protected final void setQuickAction(QuickAction action, Bitmap bit) {
		do {
			if (action == null)
				break;

			mQuickActionMenu.setVisibility(View.VISIBLE);
			mQuickActionMenu.setClickable(true);
			mQuickActionIcon.setImageBitmap(bit);
			mQuickActionController = action;
		} while (false);
	}

	protected final void setQuicktActionIcon(int resId) {
		mQuickActionIcon.setImageResource(resId);
	}

	protected final void setQuickActionIcon(Bitmap bit) {
		mQuickActionIcon.setImageBitmap(bit);
	}

	protected final void removeQuickAction() {
		mQuickActionMenu.setVisibility(View.GONE);
		mQuickActionMenu.setClickable(false);
		mQuickActionIcon.setImageBitmap(null);
		mQuickActionController = null;
	}

	/**************************************************************************
	 * Second action menu
	 *************************************************************************/
	protected final QuickAction getSecondAction() {
		return mSecondActionController;
	}

	protected final void setSecondAction(QuickAction action, int resId) {
		do {
			if (action == null)
				break;

			mSecondActionMenu.setVisibility(View.VISIBLE);
			mSecondActionMenu.setClickable(true);
			mSecondActionIcon.setImageResource(resId);
			mSecondActionController = action;
		} while (false);
	}

	protected final void setSecondAction(QuickAction action, Bitmap bit) {
		do {
			if (action == null)
				break;

			mSecondActionMenu.setVisibility(View.VISIBLE);
			mSecondActionMenu.setClickable(true);
			mSecondActionIcon.setImageBitmap(bit);
			mSecondActionController = action;
		} while (false);
	}

	protected final void setSecondtActionIcon(int resId) {
		mSecondActionIcon.setImageResource(resId);
	}

	protected final void setSecondActionIcon(Bitmap bit) {
		mSecondActionIcon.setImageBitmap(bit);
	}

	protected final void removeSecondAction() {
		mSecondActionMenu.setVisibility(View.GONE);
		mSecondActionMenu.setClickable(false);
		mSecondActionIcon.setImageBitmap(null);
		mSecondActionController = null;
	}

	/**************************************************************************
	 * Third action menu
	 *************************************************************************/
	protected final QuickAction getThirdAction() {
		return mThirdActionController;
	}

	protected final void setThirdAction(QuickAction action, int resId) {
		do {
			if (action == null)
				break;

			mThirdActionMenu.setVisibility(View.VISIBLE);
			mThirdActionMenu.setClickable(true);
			mThirdActionIcon.setImageResource(resId);
			mThirdActionController = action;
		} while (false);
	}

	protected final void setThirdAction(QuickAction action, Bitmap bit) {
		do {
			if (action == null)
				break;

			mThirdActionMenu.setVisibility(View.VISIBLE);
			mThirdActionMenu.setClickable(true);
			mThirdActionIcon.setImageBitmap(bit);
			mThirdActionController = action;
		} while (false);
	}

	protected final void setThirdtActionIcon(int resId) {
		mThirdActionIcon.setImageResource(resId);
	}

	protected final void setThirdActionIcon(Bitmap bit) {
		mThirdActionIcon.setImageBitmap(bit);
	}

	protected final void removeThirdAction() {
		mThirdActionMenu.setVisibility(View.GONE);
		mThirdActionMenu.setClickable(false);
		mThirdActionIcon.setImageBitmap(null);
		mThirdActionController = null;
	}

	protected void onHomeButtonClicked() {
		if (mEnableBackPress)
			finish();
	}

	protected void onQuickActionClicked() {
		if (mQuickActionController != null)
			mQuickActionController
					.show(findViewById(R.id.meta_activity_base_quick_action));
	}

	protected void onSecondActionClicked() {
		if (mSecondActionController != null)
			mSecondActionController
					.show(findViewById(R.id.meta_activity_base_second_action));
	}

	protected void onThirdActionClicked() {
		if (mThirdActionController != null)
			mThirdActionController
					.show(findViewById(R.id.meta_activity_base_third_action));
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.meta_activity_base_home) {
			onHomeButtonClicked();
		} else if (id == R.id.meta_activity_base_quick_action) {
			onQuickActionClicked();
		} else if (id == R.id.meta_activity_base_second_action) {
			onSecondActionClicked();
		} else if (id == R.id.meta_activity_base_third_action) {
			onThirdActionClicked();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (mQuickActionController != null)
			mQuickActionController.dismiss();
		if (mSecondActionController != null)
			mSecondActionController.dismiss();
		if (mThirdActionController != null)
			mThirdActionController.dismiss();
	}
}
