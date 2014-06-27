package com.orange.studio.littlegenius.dialogs;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.fragments.HomeFragment.DoAction;
import com.orange.studio.littlegenius.objects.LoginDTO;
import com.orange.studio.littlegenius.objects.ResultData;

public class LoginDialog extends BaseDialog {

	private View mExitBtn=null;
	private View mLoginBtn=null;
	private View mRegisterBtn=null;
	private EditText mUserName=null;
	private EditText mPassword=null;
	
	private DoAction mDoAction=null;
	private Context mContext;
	public LoginDialog(Context context,DoAction _mDoAction) {
	    super(context);
	    mContext=context;
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
		
		mUserName=(EditText)findViewById(R.id.userName);
		mPassword=(EditText)findViewById(R.id.password);
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
			String userName=mUserName.getText().toString();
			String password=mUserName.getText().toString();
			if(userName==null || userName.trim().length()<1 || password==null || password.trim().length()<1){
				Toast.makeText(mContext, mContext.getString(R.string.empty_warning), Toast.LENGTH_LONG).show();
				return;
			}
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
	private class LoginTask extends AsyncTask<Void, Void, ResultData>{

		@Override
		protected ResultData doInBackground(Void... params) {
			try {
				String userName=mUserName.getText().toString();
				String password=mUserName.getText().toString();
				LoginDTO mData=new LoginDTO();
				mData.user_login=userName;
				mData.user_password=password;
				
			} catch (Exception e) {
			}
			return null;
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
		}
	}
}
