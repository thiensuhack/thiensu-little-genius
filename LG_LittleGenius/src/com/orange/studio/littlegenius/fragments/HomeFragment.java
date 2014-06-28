package com.orange.studio.littlegenius.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.activities.BaseActivity;
import com.orange.studio.littlegenius.adapters.MyFragmentAdapter;
import com.orange.studio.littlegenius.dialogs.LoginDialog;
import com.orange.studio.littlegenius.dialogs.RegisterDialog;
import com.orange.studio.littlegenius.objects.HomeSliderDTO;
import com.orange.studio.littlegenius.objects.RadioButtonItem;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.SlideItemData;
import com.orange.studio.littlegenius.utils.AppConfig;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;
import com.orange.studio.littlegenius.utils.AppConfig.URLRequest;


public class HomeFragment extends BaseFragment {
	private View mAboutBtn;
	private View mProgramBtn;
	private View mMediaBtn;
	private View mTestimonialBtn;
	private View mKmsBtn;
	private View mContactBtn;
	private View mPreviewBtn;
	private TextView mScheduleTxt;
	private TextView mScheduleTxt1;
	private TextView mScheduleTxt2;
	
	private  ViewPager mViewPager=null;
	private MyFragmentAdapter mSlideAdapter=null;
	private List<HomeSliderDTO> mData=null;
	private View mPreviousBtn=null;
	private View mNextBtn=null;
	
	private LoadDataTask mLoadDataTask=null;
	private LoadSlideBannerTask mLoadSlideBannerTask=null;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		
    	if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_home_layout, container,
					false);
			initView();
			initListener();
		} else {
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		return mView;
    }
	class LoadDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
			LG_CommonUtils.loadDataContent(arg0[0]);
			return null;
		}
		   
		@Override
	    protected void onPostExecute(String valus) {             
			ArrayList<String> content = LG_CommonUtils.getListData();

			if ( content != null )
			{
//				String str_content ="<b><font color=\"#0181D8\" size=\"35px\">Class Schedule : </font></b><br><ul>";
//				for (int i = 0; i < content.size(); i++) {
//					str_content +=  "<li><font color=\"#0181D8\" size=\"30px\">" + content.get(i) + "</font></li><br>";
//				}
//				str_content += "</ul><font color=\"#0181D8\" size=\"30px\">Classes are available from Thurdays to Sundays.</font>";
				
				String str_content1 ="<b><font color=\"#0181D8\" size=\"35px\">Class Schedule : </font></b><br>";
				for (int i = 0; i < (content.size()/2); i++) {
					str_content1 +=  "<font color=\"#0181D8\" size=\"30px\">" + content.get(i) + "</font>";
				}
				str_content1 += "";
				
				mScheduleTxt1.setText(Html.fromHtml(str_content1));
				String str_content2 ="";
				for (int i = (content.size()/2); i < content.size(); i++) {
					str_content2 +=  "<font color=\"#0181D8\" size=\"30px\">" + content.get(i) + "</font>";
				}
				str_content2 += "";
				mScheduleTxt2.setText(Html.fromHtml(str_content2));
				
				String str_content="<font color=\"#0181D8\" size=\"30px\">Classes are available from Thurdays to Sundays.</font>";
				mScheduleTxt.setText(Html.fromHtml(str_content));
			}
		}
	}

	@Override
	public void onClick(View gId) {	
		BaseActivity mActivity=(BaseActivity)getActivity();
		switch (gId.getId()) {
		case R.id.previousBtn:
			int prev=mViewPager.getCurrentItem()-1;
			prev=(prev<0?mViewPager.getAdapter().getCount()-1:prev);
			mViewPager.setCurrentItem(prev, true);
			break;
		case R.id.nextBtn:
			int next=mViewPager.getCurrentItem()+1;
			next=(next>mViewPager.getAdapter().getCount()-1?0: next);
			mViewPager.setCurrentItem(next, true);
			break;
		
		case R.id.programBtn:
			getActivity().getIntent().putExtra(LG_CommonUtils.PROGRAM_PARAM, LG_CommonUtils.URL_PROGRAM);
			mActivity.selectItem(1,false);
			break;			
		case R.id.testimonialsBtn:
			mActivity.selectItem(2,false);
			break;		
		case R.id.previewBtn:
			mActivity.selectItem(3,false);
			break;
		case R.id.kmsBtn:
			if(AppConfig.mUser==null){
				getBaseActivity().showLoginDialog();
			}else{
				getBaseActivity().mDoAction.Go2KMS();
			}
			break;			
		case R.id.contactBtn:
			mActivity.selectItem(5,false);
			break;			
		case R.id.aboutBtn:
			mActivity.selectItem(6,false);
			break;
		default:
			break;
		}
	}
//	private BaseActivity getBaseActivity(){
//		return (BaseActivity)getActivity();
//	}
	@Override
	public void initView() {
		mData=new ArrayList<HomeSliderDTO>();
//		mData.add(new SlideItemData(R.drawable.easing_slider_4, ""));
//		mData.add(new SlideItemData(R.drawable.easing_slider_3, ""));
//		mData.add(new SlideItemData(R.drawable.easing_slider_4, ""));
//		mData.add(new SlideItemData(R.drawable.easing_slider_21, ""));
		
		mViewPager=(ViewPager)mView.findViewById(R.id.slideViewPager);
		mSlideAdapter=new MyFragmentAdapter(getChildFragmentManager(), mData, getBaseActivity().mDoAction);
		mViewPager.setAdapter(mSlideAdapter);
		
		mPreviousBtn=(ImageView)mView.findViewById(R.id.previousBtn);
		mNextBtn=(ImageView)mView.findViewById(R.id.nextBtn);

		mScheduleTxt = (TextView)mView.findViewById(R.id.home_text);
		mScheduleTxt.setTextColor(Color.WHITE);

		mScheduleTxt1 = (TextView)mView.findViewById(R.id.homeTxtCol1);
		mScheduleTxt1.setTextColor(Color.WHITE);
		mScheduleTxt2 = (TextView)mView.findViewById(R.id.homeTxtCol2);
		mScheduleTxt2.setTextColor(Color.WHITE);
		
		mAboutBtn = (LinearLayout)mView.findViewById(R.id.aboutBtn);	
		mProgramBtn = (LinearLayout)mView.findViewById(R.id.programBtn);
		mTestimonialBtn = (LinearLayout)mView.findViewById(R.id.testimonialsBtn);
		mKmsBtn = (LinearLayout)mView.findViewById(R.id.kmsBtn);
		mContactBtn = (LinearLayout)mView.findViewById(R.id.contactBtn);
		mPreviewBtn = (LinearLayout)mView.findViewById(R.id.previewBtn);
	}
	@Override
	public void initListener() {
		mProgramBtn.setOnClickListener(this);
		mTestimonialBtn.setOnClickListener(this);
		//mMediaBtn.setOnClickListener(this);
		mKmsBtn.setOnClickListener(this);
		mContactBtn.setOnClickListener(this);
		mAboutBtn.setOnClickListener(this);
		mPreviewBtn.setOnClickListener(this);
		
		mPreviousBtn.setOnClickListener(this);
		mNextBtn.setOnClickListener(this);
	}
	private class LoadSlideBannerTask extends AsyncTask<Void, Void, ResultData>{
		@Override
		protected ResultData doInBackground(Void... params) {
			return LG_CommonUtils.getDataFromServer(URLRequest.HOME_SLIDE);
		}
		@Override
		protected void onPostExecute(ResultData result) {
			super.onPostExecute(result);
			try {
				if(result!=null && result.result==1){
					JSONArray jArr=new JSONArray(result.data);					
					if(jArr!=null && jArr.length()>0){
						List<HomeSliderDTO> mList=new ArrayList<HomeSliderDTO>();
						for (int i = 0; i < jArr.length(); i++) {
							mList.add(new HomeSliderDTO(String.valueOf(i),jArr.getString(i)));
						}
						mSlideAdapter.updateData(mList);
					}
				}
			} catch (Exception e) {
			}			
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		loadData();
	}

	private void loadData() {		
		if(mLoadDataTask==null || mLoadDataTask.getStatus()==Status.FINISHED){
			String url_select = LG_CommonUtils.URL_SCHEDULE;
			mLoadDataTask=new LoadDataTask();
			mLoadDataTask.execute(url_select);
		}
		if(mLoadSlideBannerTask==null || mLoadSlideBannerTask.getStatus()==Status.FINISHED){
			mLoadSlideBannerTask=new LoadSlideBannerTask();
			mLoadSlideBannerTask.execute();
		}
	}
}
