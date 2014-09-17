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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.VideoKMSAdapter;
import com.orange.studio.littlegenius.models.CommonModel;
import com.orange.studio.littlegenius.objects.VideoKMSDTO;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

public class VideoKMSFragment extends BaseFragment implements OnClickListener, OnItemClickListener{

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
		mBaseActivity=getBaseActivity();
		mListView=(ListView)mView.findViewById(R.id.listViewVideo);
		mAdapter=new VideoKMSAdapter(getActivity());
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void initListener() {
		mListView.setOnItemClickListener(this);
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
	private class LoadVideoKMSTask extends AsyncTask<Void, Void, List<VideoKMSDTO>>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mBaseActivity.switchView(false,true,false);
		}
		@Override
		protected List<VideoKMSDTO> doInBackground(Void... params) {
			return CommonModel.getInstance().getListVideoFromServer(URLRequest.VIDEO_KMS_URL);
			//return null;
		}
		@Override
		protected void onPostExecute(List<VideoKMSDTO> result) {
			super.onPostExecute(result);
			try {
				if(result!=null && result.size()>0){
					mAdapter.updateData(result);
					mBaseActivity.switchView(true,false,false);
				}else{
					mBaseActivity.switchView(false,false,true);
				}
//				else{
//					List<VideoKMSDTO> mList=new ArrayList<VideoKMSDTO>();
//					for (int i = 0; i < 12; i++) {
//						String id=String.valueOf(i);
//						mList.add(new VideoKMSDTO(id, "Video " + id, "http://www.youtube.com/watch?v=tjnA7thSKPY", "http://l.f31.img.vnecdn.net/2014/06/29/1_1404011636_1404015283_490x294.jpg"));
//					}
//					mAdapter.updateData(mList);
//				}
			} catch (Exception e) {
				return;
			}	
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		loadVideoKMS();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		try {
			VideoKMSDTO item=mAdapter.getItem(position);
			//Toast.makeText(getActivity(), item.name, Toast.LENGTH_SHORT).show();
			String videoURL="http://www.youtube.com/watch?v="+item.youtubeId;
			LG_CommonUtils.go2WebAction(getActivity(), videoURL);
		} catch (Exception e) {
		}
	}
}

