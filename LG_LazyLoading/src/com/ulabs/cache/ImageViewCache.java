package com.ulabs.cache;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import android.widget.ImageView;

public class ImageViewCache {
	private static ImageViewCache instance = null;
	private Map<ImageView, String> imageViews = null;

	private ImageViewCache() {
		setImageViews(Collections
				.synchronizedMap(new WeakHashMap<ImageView, String>()));
	}

	public static ImageViewCache getInstanCache() {
		if (instance == null) {
			instance = new ImageViewCache();
		}
		return instance;
	}

	public Map<ImageView, String> getImageViews() {
		return imageViews;
	}

	public void setImageViews(Map<ImageView, String> imageViews) {
		this.imageViews = imageViews;
	}
}
