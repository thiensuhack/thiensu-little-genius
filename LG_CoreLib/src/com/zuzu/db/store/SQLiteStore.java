package com.zuzu.db.store;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.zuzu.common.ApplicationContext;

public class SQLiteStore implements SimpleStoreIF {

	private static Map<String, SimpleStoreIF> instances = new ConcurrentHashMap<String, SimpleStoreIF>();

	private static final Lock createLock_ = new ReentrantLock();

	

	static final String DB_NAME = ApplicationContext.getApplicationName();
	static final int MAX_KEY = 255;
	static final int MAX_VAL = 1024 * 1024; // 1M text

	static final String COL_KEY = "key"; // key => INDEX
	static final String COL_VAL = "val"; // value
	static final String COL_LAST_USED = "lastused"; // timetime lastupdate =>
													// INDEX
	static final String COL_EXPIRED = "expired";

	static final int COL_KEY_INDEX = 0;
	static final int COL_VAL_INDEX = 1;
	static final int COL_LAST_USED_INDEX = 2;
	static final int COL_EXPIRED_INDEX = 3;

	String[] columns = new String[] { COL_KEY, COL_VAL, COL_LAST_USED,
			COL_EXPIRED };

	private int _max_key;
	private int _max_val;

	private String _tableName = "";
	private DatabaseHelper _dbHelper;
	private int _capacity = -1;

	public static SimpleStoreIF getInstance(String name, Context ctx,
			int dbVersion, int capacity) {
		return getInstance(name, ctx, dbVersion, capacity, MAX_KEY, MAX_VAL);
	}

	public static SimpleStoreIF getInstance(String name, Context ctx,
			int dbVersion, int capacity, int max_key_length, int max_val_length) {
		String key = name;

		if (!instances.containsKey(key)) {
			createLock_.lock();
			try {
				if (!instances.containsKey(key)) {
					instances.put(key, new SQLiteStore(name, ctx, dbVersion,
							capacity, max_key_length, max_val_length));
				}
			} finally {
				createLock_.unlock();
			}
		}
		return instances.get(key);
	}

	public SQLiteStore(String name, Context ctx, int dbVersion, int capacity,
			int max_key_length, int max_val_length) {
		// init DBSQLite

		this._tableName = name;
		this._capacity = capacity;
		this._max_key = max_key_length;
		this._max_val = max_val_length;
		_dbHelper = new DatabaseHelper(this._tableName, ctx, dbVersion);
	}

	/**
	 * 
	 * @param capacity
	 *            : max key for this store, if more than key, the key with less
	 *            use will be removed (like LRU concept). If set -1 mean
	 *            unlimited
	 */
	public void init(int capacity) {
		this._capacity = capacity;
		this._max_key = MAX_KEY;
		this._max_val = MAX_VAL;
	}

	public void init(int capacity, int max_key_length, int max_val_length) {
		this._capacity = capacity;
		this._max_key = max_key_length;
		this._max_val = max_val_length;
	}

	@Override
	public void put(String key, String value) {
		
		// check LRU first
		this.put(key, value, -1);
	}

	@Override
	public void put(String key, String value, int expiresInSecs) {
		
		this._insertKey(key, value, expiresInSecs);
	}

	@Override
	public String get(String key) {
		
		return this._get(key);
	}

	@Override
	public void remove(String key) {
		
		this._remove(key);
	}

	@Override
	public Map<String, String> getMultiKeys(List<String> keys) {
		
		return getMultiKeys(keys, null);
	}

	@Override
	public Map<String, String> getMultiKeys(List<String> keys,
			List<String> keys_miss) {
		
		return this._getMulti(keys, keys_miss);
	}

	@Override
	public Map<String, String> getAllKey() {
		return this._getAllKeys();
	}

	@Override
	public void removeAll() {
		
		SQLiteDatabase db = null;
		try {
			db = this._dbHelper.getWritableDatabase();
			db.delete(this._tableName, "", null);
		} catch (Exception ex) {
			//logger.error("Exception in remove", ex);
		}
	}

	// /////////////////////////////////////////////////// private functions
	// /////////////////////////////////////
	private boolean validateKey(String key) {
		if (key == null || key == "")
			return false;
		return key.length() < this._max_key ? true : false;
	}

	private boolean validateVal(String val) {
		if (val == null)
			return false;
		return val.length() < this._max_val ? true : false;
	}

	private synchronized void _remove(String key) {
		if (!this.validateKey(key)) {
//			logger.debug("put: key exceed max length of key (" + this._max_key
//					+ ")");
			return;
		}

		SQLiteDatabase db = null;
		try {
			db = this._dbHelper.getWritableDatabase();
			db.delete(this._tableName, COL_KEY + "=?", new String[] { key });
		} catch (Exception ex) {
			//logger.error("Exception in remove", ex);
		}
	}

	private Map<String, String> _getMulti(List<String> keys,
			List<String> keys_miss) {
		if (keys == null || keys.isEmpty())
			return null;

		Map<String, String> ret = new LinkedHashMap<String, String>();

		SQLiteDatabase db = null;

		try {
			String keys_get = "'"
					+ TextUtils.join("','", keys.toArray(new String[] {}))
					+ "'";
			db = this._dbHelper.getWritableDatabase();

			Cursor c = db.query(this._tableName, columns, COL_KEY + " IN ("
					+ keys_get + ")", null, null, null, null);

			if (c != null && c.getCount() > 0) {
				List<String> key_need_remove = new LinkedList<String>();
				List<String> key_need_update_lastused = new LinkedList<String>();
				c.moveToFirst();
				do {
					String key = c.getString(COL_KEY_INDEX);
					String val = c.getString(COL_VAL_INDEX);
					int expired = c.getInt(COL_EXPIRED_INDEX);
					int now = (int) (System.currentTimeMillis() / 1000);
					if (expired > 0 && now > expired) {
						key_need_remove.add(key);
						if (keys_miss != null)
							keys_miss.add(key);
					} else {
						key_need_update_lastused.add(key);
						ret.put(key, val);
					}

				} while (c.moveToNext());
				if (!c.isClosed())
					c.close();
				if (key_need_remove.size() > 0) {
					// remove
					String keys_remove = "'"
							+ TextUtils.join("','",
									key_need_remove.toArray(new String[] {}))
							+ "'";
					db.delete(this._tableName, COL_KEY + " IN (" + keys_remove
							+ ")", null);
				}

				if (key_need_update_lastused.size() > 0) {
					String keys_update = "'"
							+ TextUtils.join("','", key_need_update_lastused
									.toArray(new String[] {})) + "'";
					ContentValues data = new ContentValues();
					int lastUsed = (int) (System.currentTimeMillis() / 1000);
					data.put(COL_LAST_USED, lastUsed);
//					logger.debug("update last used:" + COL_KEY + " IN ("
//							+ keys_update + ")");
					db.update(this._tableName, data, COL_KEY + " IN ("
							+ keys_update + ")", null);
				}

				// check which key miss
				if (keys_miss != null && keys.size() > ret.size()) {
					Iterator<String> ii = keys.iterator();
					while (ii.hasNext()) {
						String key = ii.next();
						if (!ret.containsKey(key)) {
							keys_miss.add(key);
						}
					}
				}
			} else { // miss all
				if (keys_miss != null)
					keys_miss.addAll(keys);
			}
		} catch (Exception ex) {
			//logger.error("Exception", ex);
		}
		return ret;
	}

	private String _get(String key) {
		if (!this.validateKey(key)) {
//			logger.debug("put: key exceed max length of key (" + this._max_key
//					+ ")");
			return null;
		}

		String ret = null;

		SQLiteDatabase db = null;

		try {
			db = this._dbHelper.getWritableDatabase();
			Cursor c = db.query(this._tableName, columns, COL_KEY + "=?",
					new String[] { key }, null, null, null);
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				String val = c.getString(COL_VAL_INDEX);
				int expired = c.getInt(COL_EXPIRED_INDEX);
				int now = (int) (System.currentTimeMillis() / 1000);
				if (expired > 0 && now > expired) {
					// key expired => remove
					db.delete(this._tableName, COL_KEY + "=?",
							new String[] { key });
				} else {
					// update last used
					ContentValues data = new ContentValues();
					int lastUsed = (int) (System.currentTimeMillis() / 1000);
					data.put(COL_LAST_USED, lastUsed);
					db.update(this._tableName, data, COL_KEY + "=?",
							new String[] { key });
					ret = val;
				}
				if (!c.isClosed())
					c.close();
			}
		} catch (Exception ex) {
			//logger.error("Exception get", ex);
		}
		return ret;
	}

	private synchronized boolean _insertKey(String key, String val,
			int expiresInSecs) {
		if (!this.validateKey(key)) {
			//logger.debug("key exceed max length of key (" + MAX_KEY + ")");
			return false;
		}

		if (!this.validateVal(val)) {
			//logger.debug("key exceed max length of val (" + MAX_VAL + ")");
			return false;
		}

		if (expiresInSecs >= 0) {
			expiresInSecs = (int) (System.currentTimeMillis() / 1000)
					+ expiresInSecs;
		}

		boolean ret = false;
		SQLiteDatabase db = null;
		try {
			// replace
			int lastUsed = (int) (System.currentTimeMillis() / 1000);

			ContentValues content = this.prepareFullContentValues(key, val,
					lastUsed, expiresInSecs);

			db = this._dbHelper.getWritableDatabase();

			db.replace(this._tableName, null, content);

			// strip
			this.stripTable(db);

			ret = true;
		} catch (Exception ex) {
			//logger.error("Exception", ex);
		}
		return ret;
	}

	private Map<String, String> _getAllKeys() {
		SQLiteDatabase db = null;
		Map<String, String> ret = new LinkedHashMap<String, String>();

		try {
			db = this._dbHelper.getReadableDatabase();
			Cursor c = db.query(this._tableName, columns, null, null, null,
					null, COL_LAST_USED + " DESC");

			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					String key = c.getString(COL_KEY_INDEX);
					String val = c.getString(COL_VAL_INDEX);
					ret.put(key, val);

				} while (c.moveToNext());
				if (!c.isClosed())
					c.close();
			}
		} catch (Exception ex) {

		}

		return ret;
	}

	public void dumpAllKey() {
		SQLiteDatabase db = this._dbHelper.getReadableDatabase();
		Cursor c = db.query(this._tableName, columns, null, null, null, null,
				COL_LAST_USED + " ASC");

		if (c != null) {
			if (c.getCount() > 0) {
				c.moveToFirst();
				int total = c.getCount();
				String ret = "";
				for (int j = 0; j < columns.length; j++) {
					String col = columns[j];
					ret += col + "\t";
				}
				ret += "\n";
				for (int i = 0; i < total; i++) {
					for (int j = 0; j < columns.length; j++) {
						if (j == 0 || j == 1) {
							ret += c.getString(j) + "\t";
						} else {
							ret += c.getInt(j);
						}
					}

					c.moveToNext();
					ret += "\n";
				}
				//logger.debug(ret);
			} else {
				//logger.debug("dumpAllKey null");
			}
			if (!c.isClosed())
				c.close();
		}
	}

	private ContentValues prepareFullContentValues(String key, String val,
			int lastUsed, int expired) {
		ContentValues data = new ContentValues();
		data.put(COL_KEY, key);
		data.put(COL_VAL, val);
		data.put(COL_LAST_USED, lastUsed);
		data.put(COL_EXPIRED, expired);
		return data;
	}

	private void stripTable(SQLiteDatabase db) {
		if (this._capacity <= 0)
			return;
		// get current count
		int total = this.getCount(db);

		if (total > (this._capacity * 1.3)) {
			int top = total - this._capacity;
//			logger.debug("strip for name '" + this._tableName
//					+ "' total items in table: " + total + " items -> strip "
//					+ top + " items");
			try {
				Cursor c = db.query(this._tableName, columns, null, null, null,
						null, COL_LAST_USED + " ASC", "0," + top);
//				logger.debug("strip for name '" + this._tableName + "' : "
//						+ top + " items");
				if (c != null && c.getCount() > 0) {

					c.moveToFirst();
					int n = c.getCount();
					String keys_delete = "";
					for (int i = 0; i < n; i++) {
						String key = c.getString(COL_KEY_INDEX);
						if (key != null && !"".equals(key)) {
							//logger.debug("delete key=" + key);
							c.moveToNext();
							keys_delete += "'" + key + "',";
						}
					}
					if (keys_delete != "")
						keys_delete += "''";
					String whereClause = COL_KEY + " IN(" + keys_delete + ")";
					db.delete(this._tableName, whereClause, null);
					if (!c.isClosed())
						c.close();
				}
			} catch (Exception ex) {
				// String a= "";
			}
		}
	}

	private int getCount(SQLiteDatabase db) {
		int ret = -1;
		if (db != null) {
			try {
				String sql = "SELECT count(*) as SUM from `" + this._tableName
						+ "`";
				Cursor cur = db.rawQuery(sql, null);
				if (cur != null && cur.getCount() > 0) {
					cur.moveToFirst();
					ret = cur.getInt(0);
				}
			} catch (Exception ex) {
				// String a= "";
			}
		}
		return ret;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private String _table;

		public DatabaseHelper(String tableName, final Context context,
				int dbVersion) {
			super(context, DB_NAME + "-" + tableName, null, dbVersion);
			this._table = tableName;

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			String sql = "CREATE TABLE `" + this._table + "` (" + COL_KEY
					+ " VARCHAR(255), " + COL_VAL + " TEXT, " + COL_LAST_USED
					+ " INTEGER, " + COL_EXPIRED + " INTEGER);";
			db.execSQL(sql);

			// create index key
			sql = "CREATE UNIQUE INDEX `" + this._table + "_" + COL_KEY
					+ "` ON `" + this._table + "` (" + COL_KEY + " ASC)";
			db.execSQL(sql);

			// create index last_used
			sql = "CREATE INDEX `" + this._table + "_" + COL_LAST_USED
					+ "` ON `" + this._table + "` (" + COL_LAST_USED + " ASC)";
			db.execSQL(sql);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS `" + this._table + "`");

			db.execSQL("DROP INDEX IF EXISTS `" + this._table + "_" + COL_KEY
					+ "`");

			db.execSQL("DROP INDEX IF EXISTS `" + this._table + "_"
					+ COL_LAST_USED + "`");

			onCreate(db);
		}
	}

	@Override
	public synchronized void close() {
		
		try {
			SQLiteDatabase db = this._dbHelper.getReadableDatabase();
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception ex) {
			//logger.error("Exception close()", ex);
		}
	}

}
