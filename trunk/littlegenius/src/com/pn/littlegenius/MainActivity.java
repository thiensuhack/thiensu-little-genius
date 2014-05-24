package com.pn.littlegenius;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pn.littlegenius.utils.CommonUtils;
import com.pn.littlegenius.utils.CustomData;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private LinearLayout Bg_Home;
	private Button btn_about;
	private Button btn_Program;
	private Button btn_Media;
	private Button btn_Testimonial;
	private Button btn_Kms;
	private Button btn_Contact;
	private Button btn_Preview;
	private TextView Schedule_text;

	private ViewPager mViewPager;
	
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
	
	private CustomData[] mCustomData = new CustomData[] {
            new CustomData(R.drawable.easing_slider_4, ""),
            new CustomData(R.drawable.easing_slider_3, ""),
            new CustomData(R.drawable.easing_slider_21, ""),
            new CustomData(R.drawable.easing_slider_dimensions_1, "")
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

		btn_Program.setOnClickListener(this);
		btn_Testimonial.setOnClickListener(this);
		btn_Media.setOnClickListener(this);
		btn_Kms.setOnClickListener(this);
		btn_Contact.setOnClickListener(this);
		btn_about.setOnClickListener(this);
		btn_Preview.setOnClickListener(this);

		String url_select = CommonUtils.URL_SCHEDULE;
		HTTPRequest request = new HTTPRequest();
		request.execute(url_select);
		
		Schedule_text.setVerticalScrollBarEnabled(true);
		
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
		//		add linner layout hold text and button
				Bg_Home = (LinearLayout)findViewById(R.id.bg_home);
				Bg_Home.setBackgroundColor(Color.parseColor("#1fc0e9"));
				Bg_Home.invalidate();
		//	   	add textview
				Schedule_text = (TextView)findViewById(R.id.home_text);
				Schedule_text.setTextColor(Color.WHITE);
		
		//		add linner layout image viewer
				//btn_Image_Viewer = (LinearLayout)findViewById(R.id.bt_image_view);
		//		add button about us
				btn_about = (Button)findViewById(R.id.btn_about);
				
		// 		add program button		
				btn_Program = (Button)findViewById(R.id.btn_program);
		//		add media button
				btn_Media = (Button)findViewById(R.id.btn_media);
		//		add testimonial button
				btn_Testimonial = (Button)findViewById(R.id.btn_testimonial);
				btn_Kms = (Button)findViewById(R.id.btn_kms);
				btn_Contact = (Button)findViewById(R.id.btn_contact);
				btn_Preview = (Button)findViewById(R.id.btn_preview);
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
				String str_content ="<b>Class Schedule : </b><br><ul>";
				for (int i = 0; i < content.size(); i++) {
					str_content +=  "<li>" + content.get(i) + "</li><br>";
				}
				str_content += "</ul>Classes are available from Thurdays to Sundays.";
				Schedule_text.setText(Html.fromHtml(str_content));
			}
		}
	}

	@Override
	public void onClick(View gId) {		
		switch (gId.getId()) {
		case R.id.btn_about:
		{
			Intent intent = new Intent(this,AboutActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.btn_program:
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
		case R.id.btn_testimonial:
		{	
			Intent intent = new Intent(this,TestimonialActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.btn_kms:
		{	
			Intent intent = new Intent(this,KmsActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.btn_contact:
		{	
			Intent intent = new Intent(this,ContactActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.btn_preview:
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
