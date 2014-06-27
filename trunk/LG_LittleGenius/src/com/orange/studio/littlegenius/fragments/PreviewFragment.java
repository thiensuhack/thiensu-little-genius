package com.orange.studio.littlegenius.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.ListRadioButtonAdapter;
import com.orange.studio.littlegenius.dialogs.LG_DatePickerFragment;
import com.orange.studio.littlegenius.objects.ContactDTO;
import com.orange.studio.littlegenius.objects.RadioButtonItem;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class PreviewFragment extends BaseFragment implements OnClickListener, OnFocusChangeListener{

	private TextView txt_content1;
	private TextView txt_content;
	private WebView mMainContent;
	private EditText strUser;
	private EditText strEmail;
	private EditText strPhone;
	private EditText strBirthday;
	private RadioButton mHougang;
	private RadioGroup mOptionGroup;
	
	private View mSendDataBtn;
	private ListView mListTimer;
	private ListRadioButtonAdapter mAdapter;
	private LoadListTimerTask mLoadListTimerTask=null;
	private boolean isHougangChecked=false;
	
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
		mListTimer=(ListView)mView.findViewById(R.id.listViewRadio);
		mAdapter=new ListRadioButtonAdapter(getActivity());
		mListTimer.setAdapter(mAdapter);
		
		txt_content1 = (TextView)mView.findViewById(R.id.txt_content1);
		//txt_content1.setTextColor(Color.BLACK);
		mMainContent = (WebView)mView.findViewById(R.id.webViewMainContent);
		mMainContent.setBackgroundColor(Color.parseColor(getActivity().getString(R.color.home_background)));
		
		strUser = (EditText)mView.findViewById(R.id.str_user);
		strEmail = (EditText)mView.findViewById(R.id.str_email);
		strPhone = (EditText)mView.findViewById(R.id.str_phone);
		strBirthday   =(EditText)mView.findViewById(R.id.str_birthday);
		mHougang=(RadioButton)mView.findViewById(R.id.radioHougang);
		
		mSendDataBtn = (LinearLayout)mView.findViewById(R.id.sendDataBtn);
		
		String url_select = LG_CommonUtils.URL_PREVIEW;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
	}

	@Override
	public void initListener() {
		mSendDataBtn.setOnClickListener(this);
		strBirthday.setOnClickListener(this);
		strBirthday.setKeyListener(null);
		strBirthday.setOnFocusChangeListener(this);
	}
	public void onRadioButtonClicked(View view) {
	    boolean checked = ((RadioButton) view).isChecked();
	    if(checked){
		    	switch(view.getId()) {
		        
	            default:
	            	break;
		    }
	    }
	}
	@Override
	public void onResume() {
		super.onResume();
		loadListTimer();
	}
	private void loadListTimer(){
		if(mLoadListTimerTask==null || mLoadListTimerTask.getStatus()==Status.FINISHED){
			mLoadListTimerTask=new LoadListTimerTask();
			mLoadListTimerTask.execute();
		}
	}
	private class LoadListTimerTask extends AsyncTask<Void, Void, ResultData>{

		@Override
		protected ResultData doInBackground(Void... params) {
			return null;
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			List<RadioButtonItem> mList=new ArrayList<RadioButtonItem>();
			mList.add(new RadioButtonItem(1, "8:30 am"));
			mList.add(new RadioButtonItem(2, "10:00 am"));
			mList.add(new RadioButtonItem(3, "11:30 am"));
			mList.add(new RadioButtonItem(4, "13:00 pm"));
			mAdapter.updateFriendList(mList);			
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
			String title = LG_CommonUtils.getTitle();
			txt_content1.setText(Html.fromHtml(title));		
			mMainContent.loadData("<div style=\'background-color:transparent;padding: 5px ;color:#EF5535'>"+content+"</div>","text/html; charset=UTF-8", null);
			//mMainContent.setText(Html.fromHtml(content));
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
			boolean isHougang=mHougang.isSelected();
			
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
			ContactDTO mContact=new ContactDTO();
			mContact.first_name=name;
			mContact.last_name=name;
			mContact.email=email;
			mContact.telephone=phone;
			
			break;
		}
		case R.id.str_birthday:
			selectBirthday();
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
			case R.id.str_birthday: {
				hideSoftKeyBoard();
				selectBirthday();
				break;
			}
				default:
					break;
			}
		}
	}
	private class SendContactTask extends AsyncTask<Void, Void, ResultData>{
		private ContactDTO data=null;
		public SendContactTask(ContactDTO _data){
			data=_data;
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			
			return null;
		}
		
	}
}

