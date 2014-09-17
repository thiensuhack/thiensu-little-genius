package com.orange.studio.littlegenius.fragments;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class ProgramFragment extends BaseFragment implements OnClickListener,
		OnTouchListener {
	//private TextView txt_content1;
	private WebView webView;
	private WebViewClient client;
	private LoadProgrammeTask mLoadProgrammeTask=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_program_layout,
					container, false);
			initView();
			initListener();
		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
	}

	public void initView() {
		mBaseActivity=getBaseActivity();
//		txt_content1 = (TextView) mView.findViewById(R.id.txt_content1);

		webView = (WebView) mView.findViewById(R.id.webViewMainContent);
		webView.setBackgroundColor(Color.parseColor(getActivity().getString(R.color.home_background)));
	}

	public void initListener() {

		webView.setOnTouchListener(this);
//		client = new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				String mUrl = url;
//				LoadProgrammeTask request = new LoadProgrammeTask();
//				request.execute();
//				return false;
//			}
//		};
		webView.setWebViewClient(client);
	}
	private void loadProgramme(){
		if(mLoadProgrammeTask==null || mLoadProgrammeTask.getStatus()==Status.FINISHED){
			mLoadProgrammeTask=new LoadProgrammeTask();
			mLoadProgrammeTask.execute();
		}
	}
	class LoadProgrammeTask extends AsyncTask<Void, Void, ResultData> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mBaseActivity.switchView(false,true,false);
		}
		@Override
		protected ResultData doInBackground(Void... arg0) {			
			return LG_CommonUtils.getDataFromServer(URLRequest.PROGRAMME);
		}

		@Override
		protected void onPostExecute(ResultData result) {
			try {
					if(result!=null && result.result==1){
						JSONObject jb=new JSONObject(result.data);
//						String title=jb.optString("name");
						String content=jb.optString("content");
//						txt_content1.setText(Html.fromHtml(title));
						webView.loadData(
								"<div style=\'background-color:transparent;padding: 5px ;color:#EF5535'>"
										+ content + "</div>", "text/html; charset=UTF-8",
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
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			super.onClick(v);
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	@Override
	public void onResume() {
		super.onResume();
		loadProgramme();
	}
}
