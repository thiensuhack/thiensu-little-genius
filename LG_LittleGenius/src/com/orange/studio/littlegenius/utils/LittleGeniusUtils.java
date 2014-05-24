package com.orange.studio.littlegenius.utils;

import android.content.Context;

import com.zuzu.db.store.SQLiteStore;
import com.zuzu.db.store.SimpleStoreIF;

public class LittleGeniusUtils {
	public static SimpleStoreIF getStoreAdapter(String name, Context mContext,
			int items) {
		return SQLiteStore.getInstance(name, mContext, AppConfig.DBVERSION,
				items);
	}
}
