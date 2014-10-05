package com.orange.studio.littlegenius.dialogs;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class LG_DatePickerDialog extends DatePickerDialog {	
	int maxYear = 2025;
	int maxMonth = 11;
	int maxDay = 31;

	int minYear = 1920;
	int minMonth = 1;
	int minDay = 1;
	public LG_DatePickerDialog(Context context, OnDateSetListener callBack,
			int curYear, int curMonth, int curDay) {
		super(context, callBack, curYear, curMonth, curDay);
		try {
			Calendar currentDate = Calendar.getInstance();
			maxDay = currentDate.get(Calendar.DAY_OF_MONTH);
			maxMonth = currentDate.get(Calendar.MONTH);
			maxYear = currentDate.get(Calendar.YEAR);
		} catch (Exception e) {
			
		}
	}
	public LG_DatePickerDialog(Context context, OnDateSetListener callBack,
			int minYear, int minMonth, int minDay, int maxYear, int maxMonth,
			int maxDay) {
		super(context, callBack, minYear, minMonth, minDay);
		this.minDay = 1;
		this.minMonth = 1;
		this.minYear = 1920;
		this.maxDay = maxDay;
		this.maxMonth = maxMonth;
		this.maxYear = maxYear;
	}
	public LG_DatePickerDialog(Context context, OnDateSetListener callBack,
			int minYear, int minMonth, int minDay, int maxYear, int maxMonth,
			int maxDay,boolean isMinDate) {
		super(context, callBack, minYear, minMonth, minDay);
		this.minDay = minDay;
		this.minMonth = minMonth;
		this.minYear = minYear;
		this.maxDay = maxDay;
		this.maxMonth = maxMonth;
		this.maxYear = maxYear;
	}
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		super.onDateChanged(view, year, monthOfYear, dayOfMonth);

		if (year > maxYear || monthOfYear > maxMonth && year == maxYear
				|| dayOfMonth > maxDay && year == maxYear
				&& monthOfYear == maxMonth) {
			view.updateDate(maxYear, maxMonth, maxDay);
		} else if (year < minYear || monthOfYear < minMonth && year == minYear
				|| dayOfMonth < minDay && year == minYear
				&& monthOfYear == minMonth) {
			view.updateDate(minYear, minMonth, minDay);
		}
	}
}