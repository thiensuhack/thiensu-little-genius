package com.pn.littlegenius;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class KmsActivity extends BaseSlidingActivity implements OnClickListener{

	private TextView txt_content1;
	private TextView txt_content;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMetaContentView(R.layout.kms_activity);

		txt_content1 = (TextView)findViewById(R.id.txt_content1);
		txt_content1.setTextColor(Color.BLACK);
		txt_content1.setText(Html.fromHtml("<b>KMS<b>"));
		
		txt_content = (TextView)findViewById(R.id.txt_content);
		txt_content.setTextColor(Color.WHITE);		
	}

	@Override
	public void onClick(View v) {
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

