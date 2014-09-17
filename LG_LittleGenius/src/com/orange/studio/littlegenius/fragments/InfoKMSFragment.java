package com.orange.studio.littlegenius.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.models.CommonModel;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.UpdateInfoDTO;
import com.orange.studio.littlegenius.utils.AppConfig;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class InfoKMSFragment extends BaseFragment implements OnClickListener{

	private TextView mName;
	private TextView mEmail;
	private TextView mPassword;
	private TextView mAddress;
	private Button mUpdateInfoBtn;
	private Button mChangePasswordBtn;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_info_layout, container,
					false);
			initView();
			initListener();
		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
    }

	@Override
	public void initView() {
		mBaseActivity=getBaseActivity();
		mName=(TextView)mView.findViewById(R.id.userName);
		mEmail=(TextView)mView.findViewById(R.id.userEmail);
		mPassword=(TextView)mView.findViewById(R.id.userPassword);
		mAddress=(TextView)mView.findViewById(R.id.userAddress);
		mUpdateInfoBtn=(Button)mView.findViewById(R.id.updateInfoBtn);
		mChangePasswordBtn=(Button)mView.findViewById(R.id.changePasswordBtn);
		
		LG_CommonUtils.checkUserInfo();
		if(AppConfig.mUser!=null){
			mName.setText(AppConfig.mUser.user_nicename);
			mEmail.setText(AppConfig.mUser.user_email);			
		}
	}

	@Override
	public void initListener() {
		mUpdateInfoBtn.setOnClickListener(this);
		mChangePasswordBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.updateInfoBtn:
			updateUserInfo();
			break;
		case R.id.changePasswordBtn:
			break;
		default:
			super.onClick(v);
			break;
		}
	}

	private void updateUserInfo() {
		//String name=mName.getText().toString();
		String email=mEmail.getText().toString();
		String password=mPassword.getText().toString();
		if(email==null || email.trim().length()<1 || password==null || password.trim().length()<1){
			Toast.makeText(getActivity(), getActivity().getString(R.string.empty_warning), Toast.LENGTH_LONG).show();
			return;
		}
		if(!LG_CommonUtils.validateEmail(email)){
			Toast.makeText(getActivity(), getActivity().getString(R.string.email_warning), Toast.LENGTH_LONG).show();
			return;
		}
//		if(!LG_CommonUtils.validatePhoneNumber(password)){
//			Toast.makeText(getActivity(), getActivity().getString(R.string.phone_warning), Toast.LENGTH_LONG).show();
//			return;
//		}
		///name, email, telephone, dob, preferred_timing, preferred_date
		UpdateInfoDTO update=new UpdateInfoDTO();
		update.token_id=AppConfig.mUser.token_id;
		update.user_id=AppConfig.mUser.user_id;
		update.user_email=email;
		update.user_password=password;
		
		SendContactTask mSendContactTask=new SendContactTask(update);
		mSendContactTask.execute();
	}
	private class SendContactTask extends AsyncTask<Void, Void, ResultData>{
		private UpdateInfoDTO data=null;
		public SendContactTask(UpdateInfoDTO _data){
			//name, email, telephone, dob, preferred_timing, preferred_date
			data=_data;						
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			try {
				Gson gs=new Gson();
				String jsData=gs.toJson(data);
//				Bundle param=new Bundle();
//				param.putString("dob", data.dob);
//				param.putString("email", data.email);
//				param.putString("fullname", data.fullname);
//				param.putString("telephone", data.telephone);
				return LG_CommonUtils.postDataServer(URLRequest.INFO_KMS_UPDATE_URL, jsData);
			} catch (Exception e) {
			}
			return null;
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			if(result!=null){
				if(result.result==1){
					LG_CommonUtils.showToast(getActivity().getString(R.string.update_success));
					CommonModel.getInstance().clearUserInfo();
					mBaseActivity.selectItem(0, false);
				}
				else{
					LG_CommonUtils.showToast(getActivity().getString(R.string.update_failed));
				}
				//Toast.makeText(getActivity(), result.msg , Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getActivity(), getActivity().getString(R.string.update_failed), Toast.LENGTH_LONG).show();
			}
		}
	}
}

