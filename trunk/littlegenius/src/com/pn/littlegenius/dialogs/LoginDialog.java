package com.pn.littlegenius.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.pn.littlegenius.R;

public class LoginDialog extends BaseDialog {

	private View mExitBtn=null;
	
	public LoginDialog(Context context) {
	    super(context);
	    initView();
	    initListener();
	}
	public LoginDialog(Context context, int theme) {
	    super(context, theme);
	    initView();
	    initListener();
	}
	private void initView(){
		setContentView(R.layout.dialog_login_layout);
		mExitBtn=(ImageView)findViewById(R.id.exitDialog);
	}
	private void initListener(){
		mExitBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		this.dismiss();
	}
}
