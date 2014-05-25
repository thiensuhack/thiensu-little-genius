package com.pn.littlegenius;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pn.littlegenius.adapters.MyFragmentAdapter;
import com.pn.littlegenius.dialogs.LoginDialog;
import com.pn.littlegenius.utils.CommonUtils;
import com.pn.littlegenius.utils.SlideItemData;

public class MainActivity extends FragmentActivity implements OnClickListener {

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
	
	public interface DoAction{
		public void DissmissDialog();
	}
	private DoAction doAction = null;
	
	Timer _timer = null;
	int timeDelay = 2000;
	void startTimer()
	{
		_timer = new Timer();
        _timer.schedule(new TimerTask() {            
            @Override
            public void run() {
            	callRunnableMethod();
            }
        },timeDelay, timeDelay);
	}

	void stopTimer()
	{
		if ( _timer != null )
		{
			_timer.cancel();
			_timer = null;
		}
	}
	
	private void callRunnableMethod() {
    	this.runOnUiThread( new Runnable() {
			
			@Override
			public void run() {
				//scrollToNext();
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
		String url_select = CommonUtils.URL_SCHEDULE;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
		mScheduleTxt.setVerticalScrollBarEnabled(true);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
					}
				});
			}
		}, 0 , 2000);
		
	}

	private void initView() {
		mData=new ArrayList<SlideItemData>();
		mData.add(new SlideItemData(R.drawable.easing_slider_4, ""));
		mData.add(new SlideItemData(R.drawable.easing_slider_3, ""));
		mData.add(new SlideItemData(R.drawable.easing_slider_4, ""));
		mData.add(new SlideItemData(R.drawable.easing_slider_21, ""));
		
		doAction=new DoAction() {
			@Override
			public void DissmissDialog() {
			}
		};
		
		mViewPager=(ViewPager)findViewById(R.id.slideViewPager);
		mSlideAdapter=new MyFragmentAdapter(getSupportFragmentManager(), mData, doAction);
		mViewPager.setAdapter(mSlideAdapter);
		
		mPreviousBtn=(ImageView)findViewById(R.id.previousBtn);
		mNextBtn=(ImageView)findViewById(R.id.nextBtn);

		//	   	add textview
		mScheduleTxt = (TextView)findViewById(R.id.home_text);
		mScheduleTxt.setTextColor(Color.WHITE);

		//		add linner layout image viewer
				//btn_Image_Viewer = (LinearLayout)findViewById(R.id.bt_image_view);
		//		add button about us
		mAboutBtn = (LinearLayout)findViewById(R.id.aboutBtn);
				
		// 		add program button		
		mProgramBtn = (LinearLayout)findViewById(R.id.programBtn);
		//		add media button
		//mMediaBtn = (LinearLayout)findViewById(R.id.menu_settings);
		//		add testimonial button
		mTestimonialBtn = (LinearLayout)findViewById(R.id.testimonialsBtn);
		mKmsBtn = (LinearLayout)findViewById(R.id.kmsBtn);
		mContactBtn = (LinearLayout)findViewById(R.id.contactBtn);
		mPreviewBtn = (LinearLayout)findViewById(R.id.previewBtn);
	}
	private void initListener() {
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
	class HTTPRequest extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... arg0) {
				
			CommonUtils.loadDataContent(arg0[0]);
			return null;
		}
		   
		@Override
	    protected void onPostExecute(String valus) {             
			ArrayList<String> content = CommonUtils.getListData();

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
		{			
			Intent intent = new Intent(this,AboutActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.programBtn:
		{	
			Intent intent = new Intent(this,ProgramActivity.class);
			intent.putExtra(CommonUtils.PROGRAM_PARAM, CommonUtils.URL_PROGRAM);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.btn_media:
		{	
			Intent intent = new Intent(this,MediaActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.testimonialsBtn:
		{	
			Intent intent = new Intent(this,TestimonialActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.kmsBtn:
		{	
			LoginDialog mLoginDialog=new LoginDialog(MainActivity.this);
			mLoginDialog.show();
//			Intent intent = new Intent(this,KmsActivity.class);
//			startActivity(intent);
//			finish();
			break;			
		}
		case R.id.contactBtn:
		{	
			Intent intent = new Intent(this,ContactActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.previewBtn:
		{	
			Intent intent = new Intent(this,PreviewActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		default:
			break;
		}
	}
}
