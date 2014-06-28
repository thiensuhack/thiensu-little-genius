package com.orange.studio.littlegenius.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.activities.BaseActivity.DoAction;

public class ExitDialog extends BaseDialog {

	private View mExitBtn=null;
	private View mCancelBtn=null;
	private View mOKBtn=null;
	
	private DoAction mDoAction=null;
	private Context mContext;
	
	public ExitDialog(Context context,DoAction _mDoAction) {
	    super(context);
	    mContext=context;
	    mDoAction=_mDoAction;
	    initView();
	    initListener();
	}
	public ExitDialog(Context context, int theme) {
	    super(context, theme);
	    initView();
	    initListener();
	}
	private void initView(){
		setContentView(R.layout.dialog_exit_layout);
		mCancelBtn=(LinearLayout)findViewById(R.id.cancelBtn);
		mOKBtn=(LinearLayout)findViewById(R.id.OKBtn);
		mExitBtn=(ImageView)findViewById(R.id.exitDialog);
	}
	private void initListener(){
		mCancelBtn.setOnClickListener(this);
		mOKBtn.setOnClickListener(this);
		mExitBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {		
		case R.id.exitDialog:
			dismiss();
			break;
		case R.id.cancelBtn:
			dismiss();
			 break;
		case R.id.OKBtn:
			dismiss();
			mDoAction.ExitApplication();			
			break;
		default:
			super.onClick(v);
			break;
		}					
	}
}
