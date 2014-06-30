package com.orange.studio.littlegenius.dialogs;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.activities.BaseActivity.DoAction;
import com.orange.studio.littlegenius.models.CommonModel;
import com.orange.studio.littlegenius.objects.LoginDTO;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.UserDTO;
import com.orange.studio.littlegenius.utils.AppConfig;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class LoginDialog extends BaseDialog {

	private View mExitBtn=null;
	private View mLoginBtn=null;
	private View mRegisterBtn=null;
	private EditText mUserName=null;
	private EditText mPassword=null;
	
	private DoAction mDoAction=null;
	private Context mContext;
	private LoginTask mLoginTask=null;
	
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
			String password=mPassword.getText().toString();
			if(userName==null || userName.trim().length()<1 || password==null || password.trim().length()<1){
				Toast.makeText(mContext, mContext.getString(R.string.empty_warning), Toast.LENGTH_LONG).show();
				return;
			}
			DoLogin();
			break;
		case R.id.registerBtn:
			this.dismiss();
			mDoAction.DissmissDialog();
			break;
		case R.id.exitDialog:
			dismiss();
			break;
		default:
			super.onClick(v);
			break;
		}					
	}
	private void DoLogin(){
		if(mLoginTask==null || mLoginTask.getStatus()==Status.FINISHED){
			mLoginTask=new LoginTask();
			mLoginTask.execute();
		}
	}
	private class LoginTask extends AsyncTask<Void, Void, UserDTO>{

		@Override
		protected UserDTO doInBackground(Void... params) {
			try {
				String userName=mUserName.getText().toString();
				String password=mPassword.getText().toString();
				LoginDTO mData=new LoginDTO();
				mData.user_login=userName;
				mData.user_password=password;
				Gson gs=new Gson();
				String data=gs.toJson(mData);
				return CommonModel.getInstance().userLogin(URLRequest.LOGIN_URL, data);
				
			} catch (Exception e) {
			}
			return null;
		}
		@Override
		protected void onPostExecute(UserDTO result) {
			super.onPostExecute(result);
			if(result!=null){
				AppConfig.mUser=result;
				mDoAction.Go2KMS();
				LoginDialog.this.dismiss();
//				if(result.result == 1){
//					try {
//						JSONObject jb=new JSONObject(result.data);
//						UserDTO mUser=new UserDTO();
//						mUser.user_email=jb.optString("user_email");
//						mUser.user_login=jb.optString("user_login");
//						mUser.user_nicename=jb.optString("user_nicename");
//						mUser.user_id=jb.optString("user_id");
//						mUser.token_id=jb.optString("token_id");
//						AppConfig.mUser=mUser;
//						LoginDialog.this.dismiss();
//						mDoAction.Go2KMS();
//						Toast.makeText(mContext, mContext.getString(R.string.login_success_message), Toast.LENGTH_LONG).show();
//					} catch (JSONException e) {
//						e.printStackTrace();
//						Toast.makeText(mContext, mContext.getString(R.string.login_failed_message), Toast.LENGTH_LONG).show();
//						return;
//					}
//				}else{
//					Toast.makeText(mContext, (result.msg!=null && result.msg.trim().length()>0)?result.msg:mContext.getString(R.string.login_failed_message), Toast.LENGTH_LONG).show();
//					return;
//				}
			}else{
				Toast.makeText(mContext, mContext.getString(R.string.login_failed_message), Toast.LENGTH_LONG).show();
				return;
			}
		}
	}
}
