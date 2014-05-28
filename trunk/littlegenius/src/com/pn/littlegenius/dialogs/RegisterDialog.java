package com.pn.littlegenius.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pn.littlegenius.R;

public class RegisterDialog extends BaseDialog {

	private View mExitBtn=null;
	private View mRegisterBtn=null;
	
	private EditText mName=null;
	private EditText mUserName=null;
	private EditText mPassword=null;
	private EditText mEmail=null;
	private EditText mPhone=null;
	private EditText mAddress=null;
	
	public RegisterDialog(Context context) {
	    super(context);
	    initView();
	    initListener();
	}
	public RegisterDialog(Context context, int theme) {
	    super(context, theme);
	    initView();
	    initListener();
	}
	private void initView(){
		setContentView(R.layout.dialog_register_layout);
		mExitBtn=(ImageView)findViewById(R.id.exitDialog);		
		mRegisterBtn=(LinearLayout)findViewById(R.id.registerBtn);
		
		mName=(EditText)findViewById(R.id.nameEdittext);
		mUserName=(EditText)findViewById(R.id.userNameEdittext);
		mPassword=(EditText)findViewById(R.id.passEdittext);
		mEmail=(EditText)findViewById(R.id.emailEdittext);
		mPhone=(EditText)findViewById(R.id.phoneEdittext);
		mAddress=(EditText)findViewById(R.id.addressEdittext);		
	}
	private void initListener(){
		mRegisterBtn.setOnClickListener(this);
		mExitBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.exitDialog:
			this.dismiss();
			break;
		case R.id.registerBtn:
			
			break;
		default:
			super.onClick(v);	
			break;
		}		
	}
}
