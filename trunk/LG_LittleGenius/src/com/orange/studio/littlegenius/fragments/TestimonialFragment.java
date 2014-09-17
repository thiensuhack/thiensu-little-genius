package com.orange.studio.littlegenius.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.TestimonialAdapter;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.TestimonialDTO;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class TestimonialFragment extends BaseFragment implements OnClickListener{

	private ListView mListView;
	private TestimonialAdapter mAdapter;
	private LoadTestimonialTask mLoadTestimonialTask=null;
	
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
		mBaseActivity=getBaseActivity();
		mAdapter=new TestimonialAdapter(getActivity());
		mListView=(ListView)mView.findViewById(R.id.listViewTestimonial);	
		mListView.setAdapter(mAdapter);
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
	private void loadTestimonial(){
		if(mAdapter!=null && mAdapter.getCount()>0){
			return;
		}
		if(mLoadTestimonialTask==null || mLoadTestimonialTask.getStatus()==Status.FINISHED){
			mLoadTestimonialTask=new LoadTestimonialTask();
			mLoadTestimonialTask.execute();
		}
	}
	private class LoadTestimonialTask extends AsyncTask<Void, Void, ResultData>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mBaseActivity.switchView(false,true,false);
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			return LG_CommonUtils.getDataFromServer(URLRequest.TESTIMONIAL);
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			try {
				if(result!=null && result.result==1){
					JSONArray jArr=new JSONArray(result.data);					
					if(jArr!=null && jArr.length()>0){
						List<TestimonialDTO> mList=new ArrayList<TestimonialDTO>();
						for (int i = 0; i < jArr.length(); i++) {
							JSONObject jb=jArr.getJSONObject(i);
							String content=jb.optString("content");
							if(content!=null && content.length()>0){
								content=content.replace("<p>", "").replace("</p>", "").replace("\n", "");
							}
							mList.add(new TestimonialDTO(jb.optString("thumbnail"),jb.optString("author"),content));
						}
						mAdapter.updateData(mList);
					}
					mBaseActivity.switchView(true,false,false);
				}else{
					mBaseActivity.switchView(false,false,true);
				}
			} catch (Exception e) {
				return;
			}	
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		loadTestimonial();
	}
}
