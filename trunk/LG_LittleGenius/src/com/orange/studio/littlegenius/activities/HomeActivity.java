package com.orange.studio.littlegenius.activities;

import android.os.Bundle;

import com.meta.gui.activity.MetaFragmentActivity;
import com.orange.studio.littlegenius.R;

public class HomeActivity extends MetaFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMetaContentView(R.layout.activity_home_layout);
	}

}
