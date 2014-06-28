package com.fedorvlasov.lazylist;

import java.io.File;
import java.util.List;

import android.content.Context;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context, String folderName) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					folderName);
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename = hashCodeURL(url);
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public String hashCodeURL(String url) {
		return String.valueOf(url.hashCode() + ".jpg");
	}

	public boolean clear(List<String> listURL) {
		if (listURL == null || listURL.size() < 1) {
			return true;
		}
		try {
			File[] files = cacheDir.listFiles();
			if (files == null)
				return true;
			for (File f : files) {
				for (String s : listURL) {
					String temp = hashCodeURL(s);
					if (temp.equals(f.getName())) {
						f.delete();
					}
				}
			}

		} catch (Exception ex) {
			return false;
		}
		return true;

	}

	public boolean clear() {
		try {
			File[] files = cacheDir.listFiles();
			if (files == null)
				return true;
			for (File f : files)
				f.delete();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

}