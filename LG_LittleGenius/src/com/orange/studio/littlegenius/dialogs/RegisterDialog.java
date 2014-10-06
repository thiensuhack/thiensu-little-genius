package com.orange.studio.littlegenius.dialogs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.orange.studio.littlegenius.LG_ApplicationContext;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.RegisterDTO;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class RegisterDialog extends BaseDialog {

	private View mExitBtn=null;
	private View mRegisterBtn=null;
	
	private EditText mName=null;
	private EditText mUserName=null;
	private EditText mPassword=null;
	private EditText mEmail=null;
	private EditText mPhone=null;
	private EditText mAddress=null;
	private Context mContext=null;
	
	private SubmitRegisterTask mSubmitRegisterTask=null;
	
	public RegisterDialog(Context context) {
	    super(context);
	    mContext=context;
	    initView();
	    initListener();
	}
	public RegisterDialog(Context context, int theme) {
	    super(context, theme);
	    mContext=context;
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
			String name=mName.getText().toString();
			//String username=mUserName.getText().toString();
			//String password=mPassword.getText().toString();
			String email=mEmail.getText().toString();
			//String phone=mPhone.getText().toString();
			//String address=mAddress.getText().toString();
//			if(name.trim().length()<1 || username.trim().length()<1 
//					|| password.trim().length()<1 || email.trim().length()<1 || phone.trim().length()<1 || address.trim().length()<1){
//				Toast.makeText(mContext, mContext.getString(R.string.empty_warning), Toast.LENGTH_LONG).show();
//				return null;
//			}

//			if(!LG_CommonUtils.validatePhoneNumber(phone)){
//				Toast.makeText(mContext, mContext.getString(R.string.phone_warning), Toast.LENGTH_LONG).show();
//				return null;
//			}
			if(name.trim().length()<1 || email.trim().length()<1){
				LG_CommonUtils.showToast(LG_ApplicationContext.getContext().getString(R.string.empty_warning));
				return;
			}
			if(!LG_CommonUtils.validateEmail(email)){
				LG_CommonUtils.showToast(LG_ApplicationContext.getContext().getString(R.string.email_warning));
				return;
			}
			submitRegister();
			break;
		default:
			super.onClick(v);	
			break;
		}		
	}
	private void setEnableView(boolean isEnable){
		mName.setEnabled(isEnable);
		mEmail.setEnabled(isEnable);
		mRegisterBtn.setEnabled(isEnable);
	}
	private void submitRegister(){
		if(mSubmitRegisterTask==null || mSubmitRegisterTask.getStatus()==Status.FINISHED){
			mSubmitRegisterTask=new SubmitRegisterTask();
			mSubmitRegisterTask.execute();
		}
	}
	private class SubmitRegisterTask extends AsyncTask<Void, Void, ResultData>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setEnableView(false);
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			String name=mName.getText().toString();
			String email=mEmail.getText().toString();

			RegisterDTO userInfo=new RegisterDTO();
			userInfo.user_login=name;
			userInfo.user_email=email;
			Gson gs=new Gson();
			String data=gs.toJson(userInfo);
			return LG_CommonUtils.postDataServer(URLRequest.REGISTER_URL, data);
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			if(result!=null){
				if(result.result==1){
					//Toast.makeText(mContext, mContext.getString(R.string.update_success), Toast.LENGTH_LONG).show();
					RegisterDialog.this.dismiss();
				}
				LG_CommonUtils.showToast(result.msg);
			}else{
				LG_CommonUtils.showToast(mContext.getString(R.string.register_failed));
			}
			setEnableView(true);
		}
	}
}
