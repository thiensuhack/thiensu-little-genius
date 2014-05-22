package com.meta.gui.quickaction;

import android.content.Context;
import android.view.WindowManager;

public class SearchAction extends QuickAction {
	private int mWidth = WindowManager.LayoutParams.WRAP_CONTENT;
	private int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;

	public SearchAction(Context context) {
		super(context);
	}

	public SearchAction(Context context, int width, int height) {
		super(context);
		mWidth = width;
		mHeight = height;
	}

	@Override
	protected void preShow() {
		super.preShow();
		mWindow.setWidth(mWidth);
		mWindow.setHeight(mHeight);
	}
}
