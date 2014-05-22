package com.meta.gui.dialog;

import com.meta.gui.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MetaDialog extends Dialog implements
		android.view.View.OnClickListener {
	public static final int TYPE_NO_BUTTON = 0;
	public static final int TYPE_1_BUTTON = 1;
	public static final int TYPE_2_BUTTON = 2;

	private OnDialogListener mListener = null;

	protected MetaDialog(Context context, String title, int type, int resId) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.meta__dialog_base_content);

		((TextView) findViewById(R.id.up_dialog_base_title)).setText(title);

		LinearLayout lay2Button = (LinearLayout) findViewById(R.id.up_dialog_base_2_buttons);
		LinearLayout lay1Button = (LinearLayout) findViewById(R.id.up_dialog_base_1_button);

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

	public void setOnDialogListener(OnDialogListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();

		if (id == R.id.up_dialog_base_positive) {
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

}
