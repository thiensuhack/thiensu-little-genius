package com.pn.littlegenius;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pn.littlegenius.utils.CommonUtils;

public class PreviewActivity extends BaseSlidingActivity implements OnClickListener{

	private TextView txt_content1;
	private TextView txt_content;
	private WebView webView;
	private EditText strUser;
	private EditText strEmail;
	private EditText strPhone;
	private EditText strBirthday;
	private View mSendDataBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainContentView(R.layout.activity_preview_layout);

		txt_content1 = (TextView)findViewById(R.id.txt_content1);
		txt_content1.setTextColor(Color.BLACK);
		webView = (WebView)findViewById(R.id.wb_webview);
		webView.setBackgroundColor(Color.parseColor("#1fc0e9"));
		
		strUser = (EditText)findViewById(R.id.str_user);
		strEmail = (EditText)findViewById(R.id.str_email);
		strPhone = (EditText)findViewById(R.id.str_phone);
		strBirthday   =(EditText)findViewById(R.id.str_birthday);

		mSendDataBtn = (LinearLayout)findViewById(R.id.sendDataBtn);
		mSendDataBtn.setOnClickListener(this);
		
		String url_select = CommonUtils.URL_PREVIEW;

		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
	}

	class HTTPRequest extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			CommonUtils.loadData(arg0[0]);
			return null;
		}
		   
		@Override
	    protected void onPostExecute(String valus) {             
			String content = CommonUtils.getContent();
			String title = CommonUtils.getTitle();
			txt_content1.setText(Html.fromHtml(title));		
			txt_content1.setTextColor(Color.WHITE);
			webView.loadData("<div style=\'background-color:transparent;padding: 5px ;color:#ffffff'>"+content+"</div>","text/html; charset=UTF-8", null);
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

