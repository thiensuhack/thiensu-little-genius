package com.orange.studio.littlegenius.fragments;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class AboutFragment extends BaseFragment implements OnClickListener{

	private TextView txt_content;
	private TextView txt_content1;
	private InputStream inputStream;
	private WebView webView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_about_layout, container,
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
		//webView.setBackgroundResource(R.color.home_background);
		
		String url_select = LG_CommonUtils.URL_ABOUT;
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
	public boolean isNetworkOnline() {
	    boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        }else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                status= true;
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return false;
	    }
	    return status;

	    }
	  
}
