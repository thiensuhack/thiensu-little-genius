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
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class TestimonialFragment extends BaseFragment implements OnClickListener{

	private TextView txt_content1;
	private TextView txt_content;
	private WebView webView;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_testimonial, container,
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
		txt_content1.setTextColor(Color.BLACK);
		webView = (WebView)mView.findViewById(R.id.wb_webview);
		//webView.setBackgroundColor(Color.parseColor("#1fc0e9"));
		
		String url_select = LG_CommonUtils.URL_TESTIMO;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
	}
	@Override
	public void initListener() {
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
			txt_content1.setTextColor(Color.WHITE);
			webView.loadData("<div style=\'background-color:transparent;padding: 5px ;color:#ffffff'>"+content+"</div>","text/html; charset=UTF-8", null);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			super.onClick(v);
			break;
		}
	}
}
