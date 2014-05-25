package com.pn.littlegenius.dialogs;

import com.pn.littlegenius.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class BaseDialog extends Dialog implements OnClickListener{

	public BaseDialog(Context context) {
		super(context,R.style.Theme_Transparent);
	    requestWindowFeature(Window.FEATURE_NO_TITLE); //This line
	}
	public BaseDialog(Context context, int theme) {
	    super(context, theme);
	    requestWindowFeature(Window.FEATURE_NO_TITLE); //This line 
	}
	@Override
	public void onClick(View v) {
		
	}
}
