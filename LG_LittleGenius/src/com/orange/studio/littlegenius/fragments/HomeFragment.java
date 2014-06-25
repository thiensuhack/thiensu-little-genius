package com.orange.studio.littlegenius.fragments;

import java.util.ArrayList;
import java.util.List;

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
import com.orange.studio.littlegenius.objects.SlideItemData;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;


public class HomeFragment extends BaseFragment {
	private View mAboutBtn;
	private View mProgramBtn;
	private View mMediaBtn;
	private View mTestimonialBtn;
	private View mKmsBtn;
	private View mContactBtn;
	private View mPreviewBtn;
	private TextView mScheduleTxt;
	
	private  ViewPager mViewPager=null;
	private MyFragmentAdapter mSlideAdapter=null;
	private List<SlideItemData> mData=null;
	private View mPreviousBtn=null;
	private View mNextBtn=null;
	
	private LoadDataTask mLoadDataTask=null;
	
	public interface DoAction{
		public void DissmissDialog();
	}
	private DoAction mDoAction = null;
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
				String str_content ="<b><font color=\"#0181D8\" size=\"35px\">Class Schedule : </font></b><br><ul>";
				for (int i = 0; i < content.size(); i++) {
					str_content +=  "<li><font color=\"#0181D8\" size=\"30px\">" + content.get(i) + "</font></li><br>";
				}
				str_content += "</ul><font color=\"#0181D8\" size=\"30px\">Classes are available from Thurdays to Sundays.</font>";
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
		case R.id.aboutBtn:
			mActivity.selectItem(6);
			break;
		case R.id.programBtn:
			mActivity.selectItem(1);
			break;			
		case R.id.testimonialsBtn:
			mActivity.selectItem(2);
			break;			
		case R.id.kmsBtn:
			showLoginDialog();
			break;			
		case R.id.contactBtn:
			mActivity.selectItem(5);
			break;			
		case R.id.previewBtn:
			mActivity.selectItem(3);
			break;			
		default:
			break;
		}
	}

	@Override
	public void initView() {
		mData=new ArrayList<SlideItemData>();
		mData.add(new SlideItemData(R.drawable.easing_slider_4, ""));
		mData.add(new SlideItemData(R.drawable.easing_slider_3, ""));
		mData.add(new SlideItemData(R.drawable.easing_slider_4, ""));
		mData.add(new SlideItemData(R.drawable.easing_slider_21, ""));
		
		mDoAction=new DoAction() {
			@Override
			public void DissmissDialog() {
				showRegisterDialog();
			}
		};
		
		mViewPager=(ViewPager)mView.findViewById(R.id.slideViewPager);
		mSlideAdapter=new MyFragmentAdapter(getChildFragmentManager(), mData, mDoAction);
		mViewPager.setAdapter(mSlideAdapter);
		
		mPreviousBtn=(ImageView)mView.findViewById(R.id.previousBtn);
		mNextBtn=(ImageView)mView.findViewById(R.id.nextBtn);

		mScheduleTxt = (TextView)mView.findViewById(R.id.home_text);
		mScheduleTxt.setTextColor(Color.WHITE);

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
	private void showLoginDialog() {
		LoginDialog mLoginDialog=new LoginDialog(getActivity(),mDoAction);
		mLoginDialog.show();
	}
	private void showRegisterDialog(){
		RegisterDialog mRegisterDialog=new RegisterDialog(getActivity());
		mRegisterDialog.show();
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
	}
}
