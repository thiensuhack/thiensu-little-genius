package com.orange.studio.littlegenius.fragments;


import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.VideoKMSAdapter;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.VideoKMSDTO;

public class VideoKMSFragment extends BaseFragment implements OnClickListener{

	private ListView mListView;
	private VideoKMSAdapter mAdapter;
	private LoadVideoKMSTask mLoadVideoKMSTask=null;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_video_layout, container,
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
		mListView=(ListView)mView.findViewById(R.id.listViewVideo);
		mAdapter=new VideoKMSAdapter(getActivity());
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
	private void loadVideoKMS(){
		if(mAdapter!=null && mAdapter.getCount()>0){
			return;
		}
		if(mLoadVideoKMSTask==null || mLoadVideoKMSTask.getStatus()==Status.FINISHED){
			mLoadVideoKMSTask=new LoadVideoKMSTask();
			mLoadVideoKMSTask.execute();
		}
	}
	private class LoadVideoKMSTask extends AsyncTask<Void, Void, ResultData>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			getBaseActivity().switchView(true);
		}
		@Override
		protected ResultData doInBackground(Void... params) {
			///return LG_CommonUtils.getDataFromServer(URLRequest.TESTIMONIAL);
			return null;
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			try {
//				if(result!=null && result.result==1){
//					JSONArray jArr=new JSONArray(result.data);					
//					if(jArr!=null && jArr.length()>0){
//						List<VideoKMSDTO> mList=new ArrayList<VideoKMSDTO>();
//						for (int i = 0; i < jArr.length(); i++) {
//							JSONObject jb=jArr.getJSONObject(i);
//							String content=jb.optString("content");
//							if(content!=null && content.length()>0){
//								content=content.replace("<p>", "").replace("</p>", "").replace("\n", "");
//							}
//							mList.add(new VideoKMSDTO(jb.optString("thumbnail"),jb.optString("author"),content,content));
//						}
//						mAdapter.updateData(mList);
//					}
//				}
				List<VideoKMSDTO> mList=new ArrayList<VideoKMSDTO>();
				for (int i = 0; i < 12; i++) {
					String id=String.valueOf(i);
					mList.add(new VideoKMSDTO(id, "Video " + id, "", "http://l.f31.img.vnecdn.net/2014/06/29/1_1404011636_1404015283_490x294.jpg"));
				}
				mAdapter.updateData(mList);
			} catch (Exception e) {
			}	
			getBaseActivity().switchView(false);
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		loadVideoKMS();
	}
}

