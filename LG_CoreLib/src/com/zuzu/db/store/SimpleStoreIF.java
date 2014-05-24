package com.zuzu.db.store;

import java.util.List;
import java.util.Map;

public interface SimpleStoreIF {
	public void put(String key, String value);

	public void put(String key, String value, int expiresInSecs);

	public String get(String key);

	public Map<String, String> getMultiKeys(List<String> keys);

	public Map<String, String> getMultiKeys(List<String> keys,
			List<String> keys_miss);

	public void remove(String key);

	public void removeAll();

	public Map<String, String> getAllKey();

	public void close();
}
