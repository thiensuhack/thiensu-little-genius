package com.orange.studio.littlegenius.fragments;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class AboutFragment extends BaseFragment implements OnClickListener{

	//private TextView txt_content1;
	private WebView webView;
	private LoadAboutTask mLoadProgrammeTask=null;
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
//		/txt_content1 = (TextView)mView.findViewById(R.id.txt_content1);
		mBaseActivity=getBaseActivity();
		webView = (WebView)mView.findViewById(R.id.webViewMainContent);
		webView.setBackgroundColor(Color.parseColor(getActivity().getString(R.color.home_background)));
	}
	@Override
	public void initListener() {
		
	}
	private void loadProgramme(){
		if(mLoadProgrammeTask==null || mLoadProgrammeTask.getStatus()==Status.FINISHED){
			mLoadProgrammeTask=new LoadAboutTask();
			mLoadProgrammeTask.execute();
		}
	}
	class LoadAboutTask extends AsyncTask<Void, Void, ResultData> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mBaseActivity.switchView(false,true,false);
		}
		@Override
		protected ResultData doInBackground(Void... arg0) {			
			return LG_CommonUtils.getDataFromServer(URLRequest.ABOUT_URL);
		}

		@Override
		protected void onPostExecute(ResultData result) {
			try {
					if(result!=null && result.result==1){
						JSONObject jb=new JSONObject(result.data);
						//String title=jb.optString("name");
						String content=jb.optString("content");
						//txt_content1.setText(Html.fromHtml(title));
						//txt_content1.setTextColor(Color.WHITE);
						webView.loadData(
								"<body style=\'background-color:transparent;padding: 5px ;color:#EF5535;text-align: justify;'>"
										+ content + "</body>", "text/html; charset=UTF-8",
								null);
						mBaseActivity.switchView(true,false,false);
					}else{
						mBaseActivity.switchView(false,false,true);
					}
				}catch(Exception ex){
					return;
				}			
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		loadProgramme();
	}
//	class HTTPRequest extends AsyncTask<String, Void, String> {
//		@Override
//		protected String doInBackground(String... arg0) {
//			LG_CommonUtils.loadData(arg0[0]);
//			return null;
//		}
//		   
//		@Override
//	    protected void onPostExecute(String valus) {             
//			String content = LG_CommonUtils.getContent();
//			String title = LG_CommonUtils.getTitle();
//			txt_content1.setText(Html.fromHtml(title));		
//			//txt_content1.setTextColor(Color.WHITE);
//			webView.loadData("<div style=\'background-color:transparent;padding: 5px ;color:#EF5535'>"+content+"</div>","text/html; charset=UTF-8", null);
//		}
//	}	
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
