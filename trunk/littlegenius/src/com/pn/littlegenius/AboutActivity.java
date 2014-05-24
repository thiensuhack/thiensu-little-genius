package com.pn.littlegenius;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.pn.littlegenius.utils.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity implements OnClickListener{

	private TextView txt_content;
	private TextView txt_content1;
	private Button btn_Home;
	private Button btn_Setting;
	private InputStream inputStream;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutactivity);

//		add buton home
		
		btn_Home = (Button)findViewById(R.id.btn_home);
		btn_Home.setOnClickListener(this);
		
		btn_Setting = (Button)findViewById(R.id.btn_setting);
		btn_Setting.setOnClickListener(this);
		
		txt_content1 = (TextView)findViewById(R.id.txt_content1);
		txt_content1.setTextColor(Color.BLACK);
		
		webView = (WebView)findViewById(R.id.wb_webview);
		webView.setBackgroundColor(Color.parseColor("#1fc0e9"));
		
		String url_select = CommonUtils.URL_ABOUT;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
	}

	class HTTPRequest extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
			CommonUtils.loadData(arg0[0]);
			return null;
		}
		   
		@Override
	    protected void onPostExecute(String valus) {             
	     
		        // TODO: check this.exception 
		        // retrieve your 'code' here
			String content = CommonUtils.getContent();
			String title = CommonUtils.getTitle();
			txt_content1.setText(Html.fromHtml(title));		
			txt_content1.setTextColor(Color.WHITE);
			
			webView.loadData("<div style=\'background-color:transparent;padding: 5px ;color:#ffffff'>"+content+"</div>","text/html; charset=UTF-8", null);
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
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
	public boolean isNetworkOnline() {
	    boolean status=false;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
