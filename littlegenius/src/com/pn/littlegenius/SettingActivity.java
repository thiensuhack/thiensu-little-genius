package com.pn.littlegenius;

import com.pn.littlegenius.utils.CommonUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity  extends Activity implements OnClickListener {
	private Button btn_Home;
	private Button btn_KMS;
	private Button btn_Media;
	private Button btn_Testi;
	private Button btn_Program;
	private Button btn_Contact;
	private Button btn_Prev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		btn_Home = (Button)findViewById(R.id.btn_home);
		btn_Home.setOnClickListener(this);
		
		btn_KMS = (Button)findViewById(R.id.btn_kms);
		btn_KMS.setOnClickListener(this);
		
		btn_Media = (Button)findViewById(R.id.btn_media);
		btn_Media.setOnClickListener(this);
		
		btn_Testi = (Button)findViewById(R.id.btn_testi);
		btn_Testi.setOnClickListener(this);
		
		btn_Program = (Button)findViewById(R.id.btn_program);
		btn_Program.setOnClickListener(this);

		btn_Contact = (Button)findViewById(R.id.btn_contact);
		btn_Contact.setOnClickListener(this);
		
		btn_Prev = (Button)findViewById(R.id.btn_preview);
		btn_Prev.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_home:
		{
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.btn_kms:
		{	
			Intent intent =  new Intent(this,KmsActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.btn_media:
		{
			Intent intent =  new Intent(this,MediaActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.btn_testi:
		{
			Intent intent =  new Intent(this,TestimonialActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.btn_program:
		{
			Intent intent =  new Intent(this,ProgramActivity.class);
			intent.putExtra(CommonUtils.PROGRAM_PARAM, CommonUtils.URL_PROGRAM);
			startActivity(intent);
			finish();
			break;
		}
		case R.id.btn_contact:
		{
			Intent intent =  new Intent(this,ContactActivity.class);
			startActivity(intent);
			finish();
			break;			
		}
		case R.id.btn_preview:
		{
			Intent intent =  new Intent(this,PreviewActivity.class);
			startActivity(intent);
			finish();
			break;
		}
		default:
		break;
		}	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
