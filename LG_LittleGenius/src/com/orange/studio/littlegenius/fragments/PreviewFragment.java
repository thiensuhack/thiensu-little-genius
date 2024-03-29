package com.orange.studio.littlegenius.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.ListRadioButtonAdapter;
import com.orange.studio.littlegenius.dialogs.LG_DatePickerFragment;
import com.orange.studio.littlegenius.dialogs.LG_DatePickerNoLimitMaxDateFragment;
import com.orange.studio.littlegenius.objects.ContactDTO;
import com.orange.studio.littlegenius.objects.RadioButtonItem;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class PreviewFragment extends BaseFragment implements OnClickListener, OnFocusChangeListener{

	//private TextView txt_content1;
	private ScrollView mScrollView=null;	
	private WebView mMainContent;
	private EditText strUser;
	private EditText strEmail;
	private EditText strPhone;
	private EditText strBirthday;
	private EditText strPreferredDate;
	
	private View mSendDataBtn;
	private ListView mListTimer;
	private ListRadioButtonAdapter mAdapter;
	private LoadListTimerTask mLoadListTimerTask=null;
	private ProgressDialog mProgress=null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_preview_layout, container,
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
		mProgress=new ProgressDialog(getActivity());
		mProgress.setMessage(getActivity().getString(R.string.please_waitting));
		mScrollView=(ScrollView)mView.findViewById(R.id.scrollView);
		mBaseActivity=getBaseActivity();
		mListTimer=(ListView)mView.findViewById(R.id.listViewRadio);
		mAdapter=new ListRadioButtonAdapter(getActivity());
		mListTimer.setAdapter(mAdapter);
		
		//txt_content1 = (TextView)mView.findViewById(R.id.txt_content1);
		mMainContent = (WebView)mView.findViewById(R.id.webViewMainContent);
		mMainContent.setBackgroundColor(Color.parseColor(getActivity().getString(R.color.home_background)));
		
		strUser = (EditText)mView.findViewById(R.id.str_user);
		strEmail = (EditText)mView.findViewById(R.id.str_email);
		strPhone = (EditText)mView.findViewById(R.id.str_phone);
		strBirthday   =(EditText)mView.findViewById(R.id.str_birthday);
		strPreferredDate   =(EditText)mView.findViewById(R.id.str_preferredDate);
		
		mSendDataBtn = (Button)mView.findViewById(R.id.sendDataBtn);		
	}

	@Override
	public void initListener() {
		mSendDataBtn.setOnClickListener(this);
		strBirthday.setOnClickListener(this);
		strBirthday.setKeyListener(null);		
		strBirthday.setOnFocusChangeListener(this);
		strPreferredDate.setKeyListener(null);
		strPreferredDate.setOnClickListener(this);
		strPreferredDate.setOnFocusChangeListener(this);
	}
	@Override
	public void onResume() {
		super.onResume();
		loadListTimer();
	}
	private void loadListTimer(){
		
		String url_select = LG_CommonUtils.URL_PREVIEW;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
		
		if(mLoadListTimerTask==null || mLoadListTimerTask.getStatus()==Status.FINISHED){
			mLoadListTimerTask=new LoadListTimerTask();
			mLoadListTimerTask.execute();
		}
	}
	private class LoadListTimerTask extends AsyncTask<Void, Void, ResultData>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mBaseActivity.switchView(false,true,false);
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			return LG_CommonUtils.getDataFromServer(URLRequest.PREVIEW_TIMING);
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			try {
				if(result!=null && result.result==1){
					JSONArray jArr=new JSONArray(result.data);					
					if(jArr!=null && jArr.length()>0){
						List<RadioButtonItem> mList=new ArrayList<RadioButtonItem>();
						for (int i = 0; i < jArr.length(); i++) {
							mList.add(new RadioButtonItem(i,jArr.getString(i)));
						}
						mAdapter.updateFriendList(mList);
					}
					mBaseActivity.switchView(true,false,false);
				}else{
					mBaseActivity.switchView(false,false,true);
				}
			} catch (Exception e) {
			}
			finally{
				mScrollView.pageScroll(View.FOCUS_UP);
			}
		}
	}
	class HTTPRequest extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			LG_CommonUtils.loadData(arg0[0]);
			return null;
		}
		   
		@Override
	    protected void onPostExecute(String valus) {             
			String content = LG_CommonUtils.getContent();
//			String title = LG_CommonUtils.getTitle();
//			txt_content1.setText(Html.fromHtml(title));		
			mMainContent.loadData("<body style=\'background-color:transparent;padding: 5px ;color:#EF5535;text-align: justify;'>"+content+"</body>","text/html; charset=UTF-8", null);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {		
		case R.id.sendDataBtn:
		{
			String name=strUser.getText().toString();
			String email=strEmail.getText().toString();
			String phone=strPhone.getText().toString();
			String birthday=strBirthday.getText().toString();
			
			if(name==null || name.trim().length()<1){
				Toast.makeText(getActivity(), getActivity().getString(R.string.empty_warning), Toast.LENGTH_LONG).show();
				return;
			}
			if(!LG_CommonUtils.validateEmail(email)){
				Toast.makeText(getActivity(), getActivity().getString(R.string.email_warning), Toast.LENGTH_LONG).show();
				return;
			}
			if(!LG_CommonUtils.validatePhoneNumber(phone)){
				Toast.makeText(getActivity(), getActivity().getString(R.string.phone_warning), Toast.LENGTH_LONG).show();
				return;
			}
			if(birthday.trim().length()<1){
				Toast.makeText(getActivity(), getActivity().getString(R.string.empty_warning), Toast.LENGTH_LONG).show();
				return;
			}
			///name, email, telephone, dob, preferred_timing, preferred_date
			ContactDTO mContact=new ContactDTO();
			mContact.fullname=name;
			mContact.email=email;
			mContact.telephone=phone;
			mContact.dob=birthday;
			mContact.preferred_date=strPreferredDate.getText().toString();
			
			RadioButtonItem item=mAdapter.getCurrentItem();
			if(item!=null){
				mContact.preferred_timing=item.name;
			}else{
				mContact.preferred_timing="";
			}
			mContact.preferred_date="29/06/2014";
			SendContactTask mSendContactTask=new SendContactTask(mContact);
			mSendContactTask.execute();
			break;
		}
		case R.id.str_birthday:
			selectBirthday();
			break;
		case R.id.str_preferredDate:
			selectPreferredDate();
			break;
		default:
			super.onClick(v);
			break;
		}
	}	
	private Calendar getBirthDay(String strBirthday) {
		Calendar birthday = Calendar.getInstance();
		try {
			if (strBirthday != null && strBirthday.length() > 0) {
				String[] myBirthDay = strBirthday.split("/");
				if (myBirthDay.length > 2) {
					birthday.set(Integer.parseInt(myBirthDay[2]),
							(Integer.parseInt(myBirthDay[1]) - 1),
							Integer.parseInt(myBirthDay[0]));
				}
			}
			return birthday;
		} catch (Exception e) {
			return Calendar.getInstance();
		}
	}
	public void selectBirthday() {

		String birthday = strBirthday.getText().toString().trim();

		Calendar calender = getBirthDay(birthday);

		int mYear = calender.get(Calendar.YEAR);
		int mMonth = calender.get(Calendar.MONTH);
		int mDay = calender.get(Calendar.DAY_OF_MONTH);

		LG_DatePickerFragment date = new LG_DatePickerFragment();
		Bundle args = new Bundle();
		args.putInt("year", mYear);
		args.putInt("month", mMonth);
		args.putInt("day", mDay);
		date.setArguments(args);
		date.setCallBack(onDateBirthday);
		date.show(getChildFragmentManager(), getActivity()
				.getString(R.string.dialogTitleSelectDate));
	}

	OnDateSetListener onDateBirthday = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String strDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
			strBirthday.setText(strDate);
		}
	};
	public void selectPreferredDate() {

		String preferredDate = strPreferredDate.getText().toString().trim();

		Calendar calender = getBirthDay(preferredDate);

		int mYear = calender.get(Calendar.YEAR);
		int mMonth = calender.get(Calendar.MONTH);
		int mDay = calender.get(Calendar.DAY_OF_MONTH);

		LG_DatePickerNoLimitMaxDateFragment date = new LG_DatePickerNoLimitMaxDateFragment();
		Bundle args = new Bundle();
		args.putInt("year", mYear);
		args.putInt("month", mMonth);
		args.putInt("day", mDay+1);
		date.setArguments(args);
		date.setCallBack(onPreferredDate);
		date.show(getChildFragmentManager(), getActivity()
				.getString(R.string.dialogTitleSelectDate));
	}
	OnDateSetListener onPreferredDate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String strDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
			strPreferredDate.setText(strDate);
		}
	};
	protected void hideSoftKeyBoard(EditText myEditText) {
		try {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
		} catch (Exception e) {
		}
	}
	private void hideSoftKeyBoard() {
		hideSoftKeyBoard(strUser);
		hideSoftKeyBoard(strEmail);
		hideSoftKeyBoard(strPhone);
	}
	@Override
	public void onFocusChange(View v, boolean isFocus) {
		if (isFocus) {
			int viewID = v.getId();
			switch (viewID) {
			case R.id.str_birthday: 
				hideSoftKeyBoard();
				selectBirthday();
				break;
			case R.id.str_preferredDate: 
				hideSoftKeyBoard();
				selectPreferredDate();
				break;
			default:
					break;
			}
		}
	}
	private class SendContactTask extends AsyncTask<Void, Void, ResultData>{
		private ContactDTO data=null;
		public SendContactTask(ContactDTO _data){
			//name, email, telephone, dob, preferred_timing, preferred_date
			data=_data;						
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(!mProgress.isShowing()){
				mProgress.show();
			}
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			try {
//				Gson gs=new Gson();
//				String jsData=gs.toJson(data);
				Bundle param=new Bundle();
				param.putString("dob", data.dob);
				param.putString("email", data.email);
				param.putString("fullname", data.fullname);
				param.putString("preferred_date", data.preferred_date);
				param.putString("preferred_timing", data.preferred_timing);
				param.putString("telephone", data.telephone);
				return LG_CommonUtils.postDataServer(URLRequest.PREVIEW_CONTACT_US_POST_URL, param);
			} catch (Exception e) {
			}
			return null;
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			if(result!=null){
				if(result.result==1){
					ClearAllView();
					Toast.makeText(getActivity(), getActivity().getString(R.string.sending_success), Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getActivity(), getActivity().getString(R.string.sending_failed), Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(getActivity(), getActivity().getString(R.string.sending_failed), Toast.LENGTH_LONG).show();
			}
			if(mProgress.isShowing()){
				mProgress.dismiss();
			}
		}
	}
	private void ClearAllView(){
		strUser.setText("");
		strEmail.setText("");
		strPhone.setText("");
		strBirthday.setText("");
		strPreferredDate.setText("");
	}
}

