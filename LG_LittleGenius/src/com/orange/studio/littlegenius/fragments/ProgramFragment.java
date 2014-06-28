package com.orange.studio.littlegenius.fragments;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class ProgramFragment extends BaseFragment implements OnClickListener,
		OnTouchListener {
	private TextView txt_content1;
	//private TextView txt_content;
	private WebView webView;
	private WebViewClient client;
	//private LinearLayout ll_Title;
	//private Handler handler;
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
		txt_content1 = (TextView) mView.findViewById(R.id.txt_content1);
		txt_content1.setTextColor(Color.BLACK);

		webView = (WebView) mView.findViewById(R.id.webViewMainContent);
		webView.setBackgroundColor(Color.parseColor(getActivity().getString(R.color.home_background)));
		//handler = new Handler();
		//ll_Title = (LinearLayout) mView.findViewById(R.id.ll_title);
//		String url_select = getActivity().getIntent().getStringExtra(
//				LG_CommonUtils.PROGRAM_PARAM);

//		LoadProgrammeTask request = new LoadProgrammeTask();
//		request.execute();
//		if (!url_select.equals(LG_CommonUtils.URL_COURSE_PROGRAM)) {
//
//			Button btn_Course = new Button(getActivity());
//			btn_Course.setLayoutParams(new LayoutParams(
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			btn_Course.setText("Course Background");
//			btn_Course.setPadding(10, 0, 10, 0);
//			ll_Title.addView(btn_Course);
//			btn_Course.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					getActivity().getIntent().putExtra(
//							LG_CommonUtils.PROGRAM_PARAM,
//							LG_CommonUtils.URL_COURSE_PROGRAM);
//					((BaseActivity) getActivity()).selectItem(1, false);
//				}
//			});
//		}
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
		protected ResultData doInBackground(Void... arg0) {			
			return LG_CommonUtils.getDataFromServer(URLRequest.PROGRAMME);
		}

		@Override
		protected void onPostExecute(ResultData result) {
			try {
					if(result!=null && result.result==1){
						JSONObject jb=new JSONObject(result.data);
						String title=jb.optString("name");
						String content=jb.optString("content");
						txt_content1.setText(Html.fromHtml(title));
						txt_content1.setTextColor(Color.WHITE);
						webView.loadData(
								"<div style=\'background-color:transparent;padding: 5px ;color:#EF5535'>"
										+ content + "</div>", "text/html; charset=UTF-8",
								null);
					}
				}catch(Exception ex){
					
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
