package com.orange.studio.littlegenius.fragments;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

public class ContactFragment extends BaseFragment implements OnClickListener {

	// private TextView txt_content1;
	private WebView webView;
	private LoadContactTask mLoadContactTask = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_contact_layout,
					container, false);
			initView();
			initListener();
		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void initView() {
		mBaseActivity=getBaseActivity();
		// txt_content1 = (TextView) mView.findViewById(R.id.txt_content1);

		webView = (WebView) mView.findViewById(R.id.webViewMainContent);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.setBackgroundColor(Color.parseColor(getActivity().getString(
				R.color.home_background)));

	}

	@Override
	public void initListener() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			super.onClick(v);
			break;
		}
	}

	private void loadContact() {
		if (mLoadContactTask == null
				|| mLoadContactTask.getStatus() == Status.FINISHED) {
			mLoadContactTask = new LoadContactTask();
			mLoadContactTask.execute();
		}
	}

	class LoadContactTask extends AsyncTask<Void, Void, ResultData> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mBaseActivity.switchView(true);
		}

		@Override
		protected ResultData doInBackground(Void... arg0) {
			return LG_CommonUtils.getDataFromServer(URLRequest.CONTACT_URL);
		}

		@Override
		protected void onPostExecute(ResultData result) {
			try {
				if (result != null && result.result == 1) {
					JSONObject jb = new JSONObject(result.data);
					// String title = jb.optString("name");
					String content = jb.optString("content");
					// txt_content1.setText(Html.fromHtml(title));
					// txt_content1.setTextColor(Color.WHITE);
					webView.loadData(
							"<div style=\'background-color:transparent;padding: 5px ;color:#EF5535'>"
									+ content + "</div>",
							"text/html; charset=UTF-8", null);
				}
			} catch (Exception ex) {

			}
			mBaseActivity.switchView(false);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		loadContact();
	}
}
