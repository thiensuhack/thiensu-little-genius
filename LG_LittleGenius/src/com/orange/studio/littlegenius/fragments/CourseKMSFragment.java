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
import com.orange.studio.littlegenius.adapters.CourseKMSAdapter;
import com.orange.studio.littlegenius.models.CommonModel;
import com.orange.studio.littlegenius.objects.CourseKMSDTO;

public class CourseKMSFragment extends BaseFragment implements OnClickListener{

	private ListView mListView;
	private CourseKMSAdapter mAdapter;
	private LoadVideoKMSTask mLoadVideoKMSTask=null;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_course_layout, container,
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
		mAdapter=new CourseKMSAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		mParentFragment=(KMSFragment)getParentFragment();
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
	private class LoadVideoKMSTask extends AsyncTask<Void, Void, List<CourseKMSDTO>>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			switchView(false,true,false);
		}
		@Override
		protected List<CourseKMSDTO> doInBackground(Void... params) {
			return CommonModel.getInstance().getListCourseFromServer();
			//return null;
		}
		@Override
		protected void onPostExecute(List<CourseKMSDTO> result) {
			super.onPostExecute(result);
			try {
//				result=new ArrayList<CourseKMSDTO>();
//				CourseKMSDTO item=new CourseKMSDTO();
//				item.courseName="hello";
//				item.id="1";
//				item.date="20/12/2014";
//				item.notice="123";
//				item.status="Done";
//				item.term="";
//				result.add(item);
//				result.add(item);
//				result.add(item);
//				result.add(item);
//				result.add(item);
				if(result!=null){
					mAdapter.updateData(result);
					switchView(true,false,false);
				}else{
					switchView(false,false,true);
				}
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
}

