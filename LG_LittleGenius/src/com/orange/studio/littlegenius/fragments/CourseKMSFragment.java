package com.orange.studio.littlegenius.fragments;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.orange.studio.littlegenius.adapters.CourseKMSAdapter;
import com.orange.studio.littlegenius.objects.CourseKMSDTO;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;

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
		mListView=(ListView)mView.findViewById(R.id.listViewVideo);
		mAdapter=new CourseKMSAdapter(getActivity());
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
			//return LG_CommonUtils.getDataFromServer(URLRequest.COURSE_KMS_URL);
			return null;
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			try {
				if(result!=null && result.result==1){
					JSONArray jArr=new JSONArray(result.data);					
					if(jArr!=null && jArr.length()>0){
						List<CourseKMSDTO> mList=new ArrayList<CourseKMSDTO>();
						for (int i = 0; i < jArr.length(); i++) {
							JSONObject jb=jArr.getJSONObject(i);
//							"course_id":"2",
//							"course_name":"Genius Immersion",
//							"course_status":"Attending",
//							"course_date":"25\/7\/2013",
//							"course_term":"",
//							"course_notice":"",
//							"user_id":"2"
							CourseKMSDTO course=new CourseKMSDTO();
							course.courseName=jb.optString("course_name");
							course.date=jb.optString("course_date");
							course.id=jb.optString("course_id");
							course.notice=jb.optString("");
							course.status=jb.optString("course_status");
							course.term=jb.optString("course_term");
							course.user_id=jb.optString("user_id");
						}
						mAdapter.updateData(mList);
					}
				}else{
					List<CourseKMSDTO> mList=new ArrayList<CourseKMSDTO>();
					for (int i = 0; i < 12; i++) {
						String id=String.valueOf(i);
						Random rand=new Random();
						int temp=rand.nextInt();
						String status="Done";
						if(temp%2==0){
							status="Registering";
						}
						mList.add(new CourseKMSDTO(id, "Content demo for Course KMS , Content demo for Course KMS " + id,status));
					}
					mAdapter.updateData(mList);
				}
				
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

