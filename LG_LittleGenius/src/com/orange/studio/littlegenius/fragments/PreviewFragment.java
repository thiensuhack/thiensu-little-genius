package com.orange.studio.littlegenius.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class PreviewFragment extends BaseFragment implements OnClickListener{

	private TextView txt_content1;
	private TextView txt_content;
	private WebView mMainContent;
	private EditText strUser;
	private EditText strEmail;
	private EditText strPhone;
	private EditText strBirthday;
	private View mSendDataBtn;

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
		txt_content1 = (TextView)mView.findViewById(R.id.txt_content1);
		//txt_content1.setTextColor(Color.BLACK);
		mMainContent = (WebView)mView.findViewById(R.id.webViewMainContent);
		mMainContent.setBackgroundColor(Color.parseColor(getActivity().getString(R.color.home_background)));
		
		strUser = (EditText)mView.findViewById(R.id.str_user);
		strEmail = (EditText)mView.findViewById(R.id.str_email);
		strPhone = (EditText)mView.findViewById(R.id.str_phone);
		strBirthday   =(EditText)mView.findViewById(R.id.str_birthday);

		mSendDataBtn = (LinearLayout)mView.findViewById(R.id.sendDataBtn);
		
		String url_select = LG_CommonUtils.URL_PREVIEW;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
	}

	@Override
	public void initListener() {
		mSendDataBtn.setOnClickListener(this);
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
			break;
		}
		default:
			super.onClick(v);
			break;
		}
	}	
}

