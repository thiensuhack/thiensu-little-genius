package com.orange.studio.littlegenius.dialogs;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class LG_DatePickerNoLimitMaxDateFragment extends DialogFragment {
	OnDateSetListener ondateSet;
	private LG_DatePickerDialog mDatePickerDialog = null;

	public LG_DatePickerNoLimitMaxDateFragment() {
	}

	public void setCallBack(OnDateSetListener ondate) {
		ondateSet = ondate;
	}

	private int year, month, day;

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		year = args.getInt("year");
		month = args.getInt("month");
		day = args.getInt("day");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDatePickerDialog = new LG_DatePickerDialog(getActivity(), ondateSet,
				year, month, day,2025,11,31,true);
		return mDatePickerDialog;
	}
}