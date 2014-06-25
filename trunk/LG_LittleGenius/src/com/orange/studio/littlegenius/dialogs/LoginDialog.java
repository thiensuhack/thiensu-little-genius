package com.orange.studio.littlegenius.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.fragments.HomeFragment.DoAction;

public class LoginDialog extends BaseDialog {

	private View mExitBtn=null;
	private View mLoginBtn=null;
	private View mRegisterBtn=null;
	
	private DoAction mDoAction=null;
	
	public LoginDialog(Context context,DoAction _mDoAction) {
	    super(context);
	    mDoAction=_mDoAction;
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
		mLoginBtn=(LinearLayout)findViewById(R.id.loginBtn);
		mRegisterBtn=(LinearLayout)findViewById(R.id.registerBtn);
		mExitBtn=(ImageView)findViewById(R.id.exitDialog);
	}
	private void initListener(){
		mLoginBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
		mExitBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.loginBtn:
			
			break;
		case R.id.registerBtn:
			this.dismiss();
			mDoAction.DissmissDialog();
			break;
		case R.id.exitDialog:
			this.dismiss();
			break;
		default:
			super.onClick(v);
			break;
		}					
	}
}
