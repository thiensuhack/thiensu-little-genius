package com.pn.littlegenius;

import com.pn.littlegenius.AboutActivity.HTTPRequest;
import com.pn.littlegenius.utils.CommonUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProgramActivity extends Activity implements OnClickListener, OnTouchListener {
	private TextView txt_content1;
	private TextView txt_content;
	private Button btn_Home;
	private Button btn_Setting;
	private WebView webView;
	private WebViewClient client;
	private LinearLayout ll_Title;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program);

		txt_content1 = (TextView)findViewById(R.id.txt_content1);
		txt_content1.setTextColor(Color.BLACK);

		btn_Home = (Button)findViewById(R.id.btn_home);
		btn_Home.setOnClickListener(this);

		btn_Setting = (Button)findViewById(R.id.btn_setting);
		btn_Setting.setOnClickListener(this);

		webView = (WebView)findViewById(R.id.wb_webview);
		webView.setBackgroundColor(Color.parseColor("#1fc0e9"));
		handler = new Handler();
		ll_Title = (LinearLayout)findViewById(R.id.ll_title);
		String url_select = getIntent().getStringExtra(CommonUtils.PROGRAM_PARAM);

		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
		if (!url_select.equals(CommonUtils.URL_COURSE_PROGRAM)) {

			Button btn_Course = new Button(getBaseContext());
			btn_Course.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));		btn_Course.setText("Course Background");
			btn_Course.setPadding(10, 0, 10, 0);
			ll_Title.addView(btn_Course);
			btn_Course.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ProgramActivity.this,ProgramActivity.class);
					intent.putExtra(CommonUtils.PROGRAM_PARAM, CommonUtils.URL_COURSE_PROGRAM);
					startActivity(intent);
					finish();
				}
			});
		}
		webView.setOnTouchListener(this);
		client = new WebViewClient(){ 
			@Override public boolean shouldOverrideUrlLoading(WebView view, String url) { 

				String mUrl = url;
				HTTPRequest request = new HTTPRequest();
				request.execute(mUrl);
				return false;
			} 
		}; 
		webView.setWebViewClient(client);
	}

	class HTTPRequest extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			CommonUtils.loadData(arg0[0]);
			return arg0[0];
		}

		@Override
		protected void onPostExecute(String valus) {             

			// TODO: check this.exception 
			// retrieve your 'code' here
			String content = CommonUtils.getContent();
			String title = CommonUtils.getTitle();
			txt_content1.setText(Html.fromHtml(title));
			txt_content1.setTextColor(Color.WHITE);
//			webView.loadData(content,"text/html; charset=UTF-8", null);
			webView.loadData("<div style=\'background-color:transparent;padding: 5px ;color:#ffffff'>"+content+"</div>","text/html; charset=UTF-8", null);

		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_home:
		{
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
			break;

		}
		case R.id.btn_setting:
		{
			Intent intent = new Intent(this,SettingActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		default:
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
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
