package com.fedorvlasov.lazylist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.ulabs.cache.ImageViewCache;

public class ImageLoader {

	MemoryCache memoryCache = MemoryCache.getInstance();
	FileCache fileCache;
	// private Map<ImageView, String> imageViews=Collections.synchronizedMap(new
	// WeakHashMap<ImageView, String>());
	private Map<ImageView, String> imageViews = ImageViewCache.getInstanCache()
			.getImageViews();

	ExecutorService executorService;
	Handler handler = new Handler();// handler to display images in UI thread
	private static ImageLoader instance;

	private ImageLoader(Context context) {
		fileCache = new FileCache(context, ".tempImages");
		executorService = Executors.newFixedThreadPool(5);
	}

	public static ImageLoader getInstance(Context _context) {
		if (instance == null) {
			instance = new ImageLoader(_context);
		}
		return instance;
	}

	final int stub_id = R.drawable.not_fount_icon;

	public void DisplayImage(String url, ImageView imageView) {
		try {
			imageViews.put(imageView, url);
			Bitmap bitmap = memoryCache.get(url);
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
			else {
				queuePhoto(url, imageView);
				imageView.setImageResource(stub_id);
			}
		} catch (Exception ex) {
			return;
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	public Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			conn.disconnect();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	/*
	 * public Bitmap getBitmapFromSdcard(String url){ File
	 * f=fileCache.getFile(url); //from SD cache Bitmap b =
	 * decodeFileForReader(f); return b; }
	 */
	public void saveBitmapToSdcard(String url, Bitmap bm) {
		try {
			File f = fileCache.getFile(url);
			OutputStream os = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 30, os);
			os.flush();
			os.close();
		} catch (IOException ex) {
			return;
		}

	}

	/*
	 * private Bitmap decodeFileForReader(File f){ try { //decode image size
	 * BitmapFactory.Options o = new BitmapFactory.Options();
	 * o.inJustDecodeBounds = true; FileInputStream stream1=new
	 * FileInputStream(f); BitmapFactory.decodeStream(stream1,null,o);
	 * stream1.close();
	 * 
	 * //Find the correct scale value. It should be the power of 2. final int
	 * REQUIRED_SIZE=300; int width_tmp=o.outWidth, height_tmp=o.outHeight; int
	 * scale=1; while(true){ if(width_tmp/2<REQUIRED_SIZE ||
	 * height_tmp/2<REQUIRED_SIZE) break; width_tmp/=2; height_tmp/=2; scale*=2;
	 * } int scale=1; //decode with inSampleSize BitmapFactory.Options o2 = new
	 * BitmapFactory.Options(); o2.inSampleSize=scale; FileInputStream
	 * stream2=new FileInputStream(f); Bitmap
	 * bitmap=BitmapFactory.decodeStream(stream2, null, o2); stream2.close();
	 * return bitmap; } catch (FileNotFoundException e) { } catch (IOException
	 * e) { e.printStackTrace(); } return null; }
	 */
	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 100;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			try {
				if (imageViewReused(photoToLoad))
					return;
				Bitmap bmp = getBitmap(photoToLoad.url);
				memoryCache.put(photoToLoad.url, bmp);
				if (imageViewReused(photoToLoad))
					return;
				BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
				handler.post(bd);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public void clearCache(List<String> listURL) {
		memoryCache.clear();
		fileCache.clear(listURL);
	}
}
