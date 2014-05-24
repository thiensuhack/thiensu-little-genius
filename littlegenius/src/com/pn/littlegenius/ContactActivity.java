package com.pn.littlegenius;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends Activity implements OnClickListener{

	private TextView txt_content1;
	private TextView txt_content;
	private Button btn_Home;
	private Button btn_Setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);

		txt_content1 = (TextView)findViewById(R.id.txt_content1);
		txt_content1.setTextColor(Color.BLACK);
		txt_content1.setText(Html.fromHtml("<b>Liên hệ<b>"));
		
		txt_content = (TextView)findViewById(R.id.txt_content);
		txt_content.setTextColor(Color.WHITE);

		btn_Home = (Button)findViewById(R.id.btn_home);
		btn_Home.setOnClickListener(this);
		
		btn_Setting = (Button)findViewById(R.id.btn_setting);
		btn_Setting.setOnClickListener(this);
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
		case R.id.btn_setting:
		{
			Intent intent = new Intent(this,SettingActivity.class);
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

