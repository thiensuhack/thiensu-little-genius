package com.zuzu.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zuzu.corelib.R;

public class BaseDialogNoTitleBar extends Dialog implements
		android.view.View.OnClickListener {

	public interface OnDialogListener {
		public void onPositiveButtonClicked();

		public void onNegativeButtonClicked();

		public void onDefaultButtonClicked();
	}

	private OnDialogListener mListener = null;

	public static final int TYPE_NO_BUTTON = 0;
	public static final int TYPE_1_BUTTON = 1;
	public static final int TYPE_2_BUTTON = 2;

	private ImageView mExitBtn = null;
	private TextView mDialogTitle = null;
	private View mTitleView=null;
	public BaseDialogNoTitleBar(Context context, String title, int type, int resId) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base_dialog_no_title_layout);

		mDialogTitle = (TextView) findViewById(R.id.dialogTitle);
		mDialogTitle.setText(title);
		mExitBtn = (ImageView) findViewById(R.id.exitDialogBtn);
		mExitBtn.setOnClickListener(this);

		LinearLayout lay2Button = (LinearLayout) findViewById(R.id.up_dialog_base_2_buttons);
		LinearLayout lay1Button = (LinearLayout) findViewById(R.id.up_dialog_base_1_button);
		mTitleView=(RelativeLayout)findViewById(R.id.dialog_base_title);
		mTitleView.setVisibility(View.GONE);
		if (type == TYPE_NO_BUTTON) {
			lay1Button.setVisibility(View.GONE);
			lay2Button.setVisibility(View.GONE);
		} else if (type == TYPE_1_BUTTON) {
			lay1Button.setVisibility(View.VISIBLE);
			lay2Button.setVisibility(View.GONE);

			findViewById(R.id.up_dialog_base_default).setOnClickListener(this);
		} else {
			lay1Button.setVisibility(View.GONE);
			lay2Button.setVisibility(View.VISIBLE);

			findViewById(R.id.up_dialog_base_positive).setOnClickListener(this);
			findViewById(R.id.up_dialog_base_negative).setOnClickListener(this);
		}

		LinearLayout layBody = (LinearLayout) findViewById(R.id.up_dialog_base_body);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(resId, layBody);
		resize();
	}
	protected void showHideTitleBar(boolean isShow){
		mTitleView.setVisibility(isShow?View.VISIBLE:View.GONE);
	}
	protected void setPositiveButtonTitle(String title) {
		((TextView) findViewById(R.id.up_dialog_base_positive)).setText(title);
	}

	protected void setPositiveButtonTitle(int titleId) {
		((TextView) findViewById(R.id.up_dialog_base_positive))
				.setText(titleId);
	}

	protected void setNegativeButtonTitle(String title) {
		((TextView) findViewById(R.id.up_dialog_base_negative)).setText(title);
	}

	protected void setNegativeButtonTitle(int titleId) {
		((TextView) findViewById(R.id.up_dialog_base_negative))
				.setText(titleId);
	}

	protected void setDefaultButtonTitle(String title) {
		((TextView) findViewById(R.id.up_dialog_base_default)).setText(title);
	}

	protected void setDefaultButtonTitle(int titleId) {
		((TextView) findViewById(R.id.up_dialog_base_default)).setText(titleId);
	}

	public void setOnDialogListener(OnDialogListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();

		if (id == R.id.exitDialogBtn) {
			this.dismiss();
		} else if (id == R.id.up_dialog_base_positive) {
			if (mListener != null)
				mListener.onPositiveButtonClicked();
		} else if (id == R.id.up_dialog_base_negative) {
			if (mListener != null)
				mListener.onNegativeButtonClicked();
		} else if (id == R.id.up_dialog_base_default) {
			if (mListener != null)
				mListener.onDefaultButtonClicked();
		}
	}

	public void resize() {
		Window window = getWindow();
		DisplayMetrics displayMatrics = new DisplayMetrics();
		window.getWindowManager().getDefaultDisplay()
				.getMetrics(displayMatrics);
		window.setBackgroundDrawable(new ColorDrawable(
				android.graphics.Color.TRANSPARENT));
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.copyFrom(getWindow().getAttributes());

		if (displayMatrics.widthPixels > displayMatrics.heightPixels) {
			params.width = (int) (displayMatrics.widthPixels * 0.7);
		} else {
			params.width = (int) (displayMatrics.widthPixels * 0.9);
		}

		params.height = LayoutParams.WRAP_CONTENT;

		window.setAttributes(params);
	}
}
