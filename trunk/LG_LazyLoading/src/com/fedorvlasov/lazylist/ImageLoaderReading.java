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

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.ulabs.cache.ImageViewCache;

public class ImageLoaderReading {

	MemoryCache memoryCache = MemoryCache.getInstance();
	private FileCache fileCache;
	// private Map<ImageView, String> imageViews=Collections.synchronizedMap(new
	// WeakHashMap<ImageView, String>());
	private Map<ImageView, String> imageViews = ImageViewCache.getInstanCache()
			.getImageViews();

	ExecutorService executorService;
	// Handler handler=new Handler();//handler to display images in UI thread
	// //thiensuhack comment
	private static ImageLoaderReading instance;

	private ImageLoaderReading(Context context) {
		setFileCache(new FileCache(context, ".ComicWorldReading"));
		executorService = Executors.newFixedThreadPool(11);
	}

	public static ImageLoaderReading getInstance(Context _context) {
		if (instance == null) {
			instance = new ImageLoaderReading(_context);
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
			memoryCache.clear();
			return;
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	public Bitmap getBitmap(String url) {
		File f = getFileCache().getFile(url);

		// from SD cache
		Bitmap b = decodeFileForReader(f);
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
			bitmap = decodeFileForReader(f);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return decodeFileForReader2(f);
		}
	}

	public void getBitmap2(String url) {
		File f = getFileCache().getFile(url);
		if (f.exists()) {
			return;
		}
		// from SD cache
		/*
		 * Bitmap b = decodeFile(f); if(b!=null) return;
		 */
		try {
			// Bitmap bitmap=null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			conn.disconnect();
			// bitmap = decodeFile(f);
			// return bitmap;
			return;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return;
		}
	}

	public File downloadImageURL(String url) {
		File f = getFileCache().getFile(url);
		if (f.exists()) {
			return f;
		}
		// from SD cache
		/*
		 * Bitmap b = decodeFile(f); if(b!=null) return;
		 */
		try {
			// Bitmap bitmap=null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			conn.disconnect();
			return f;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	// private Bitmap decodeFile(File f){
	// try {
	// //decode image size
	// BitmapFactory.Options o = new BitmapFactory.Options();
	// o.inJustDecodeBounds = true;
	// FileInputStream stream1=new FileInputStream(f);
	// BitmapFactory.decodeStream(stream1,null,o);
	// stream1.close();
	//
	// //Find the correct scale value. It should be the power of 2.
	// final int REQUIRED_SIZE=70;
	// int width_tmp=o.outWidth, height_tmp=o.outHeight;
	// int scale=1;
	// while(true){
	// if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
	// break;
	// width_tmp/=2;
	// height_tmp/=2;
	// scale*=2;
	// }
	// //decode with inSampleSize
	// BitmapFactory.Options o2 = new BitmapFactory.Options();
	// o2.inSampleSize=scale;
	// FileInputStream stream2=new FileInputStream(f);
	// Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
	// stream2.close();
	// return bitmap;
	// } catch (FileNotFoundException e) {
	// clearMemCache();
	// return null;
	// }
	// catch (IOException e) {
	// clearMemCache();
	// e.printStackTrace();
	// return null;
	// }
	// }
	public Bitmap getBitmapFromSdcard(String url) {
		File f = getFileCache().getFile(url);
		// from SD cache
		Bitmap b = decodeFileForReader(f);
		return b;
	}

	public void saveBitmapToSdcard(String url, Bitmap bm) {
		try {
			File f = getFileCache().getFile(url);
			OutputStream os = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, os);
			os.flush();
			os.close();
		} catch (IOException ex) {
			clearMemCache();
			return;
		}

	}

	private Bitmap decodeFileForReader(File f) {
		try {
			// decode image size
			// Log.d("decodeFileForReader", "f = " + f.getAbsolutePath());
			// BitmapFactory.Options o = new BitmapFactory.Options();
			// o.inJustDecodeBounds = true;
			// FileInputStream stream1=new FileInputStream(f);
			// BitmapFactory.decodeStream(stream1,null,o);
			// stream1.close();
			//
			// DisplayMetrics displayMetrics =
			// Resources.getSystem().getDisplayMetrics();
			// int dpHeight = (int)(displayMetrics.heightPixels /
			// displayMetrics.density + 0.5);
			// int dpWidth = (int)(displayMetrics.widthPixels /
			// displayMetrics.density + 0.5);
			//
			// //Find the correct scale value. It should be the power of 2.
			// final int REQUIRED_SIZE=dpWidth;
			// int width_tmp=o.outWidth, height_tmp=o.outHeight;
			// int scale=1;
			// while(true){
			// if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
			// break;
			// width_tmp/=2;
			// height_tmp/=2;
			// scale*=2;
			// }
			// //decode with inSampleSize
			// BitmapFactory.Options o2 = new BitmapFactory.Options();
			// o2.inSampleSize=scale;
			// FileInputStream stream2=new FileInputStream(f);
			// Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
			// stream2.close();

			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2);
			if (bitmap != null) {
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				FileInputStream stream1 = new FileInputStream(f);
				BitmapFactory.decodeStream(stream1, null, o);
				stream1.close();

				int width_tmp = o.outWidth;
				int height_tmp = o.outHeight;

				int width = 0;
				int height = 0;
				int SIZE = getMaximumTextureSize();
				if (SIZE < 1024 || SIZE > 2048) {
					SIZE = 2048;
				}
				float scaleRatio = 1.0f;
				while (true) {
					if (width_tmp <= SIZE && height_tmp <= SIZE) {
						width = width_tmp;
						height = height_tmp;
						break;
					}

					scaleRatio += 0.1f;
					if (width_tmp / scaleRatio <= SIZE
							&& height_tmp / scaleRatio <= SIZE) {
						width = (int) (width_tmp / scaleRatio);
						height = (int) (height_tmp / scaleRatio);
						break;
					}
				}
				bitmap = Bitmap
						.createScaledBitmap(bitmap, width, height, false);
			}
			stream2.close();
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError) {
				memoryCache.clear();
				System.gc();
			}
			return decodeFileForReader2(f);
		}
	}

	public int getMaximumTextureSize() {
		EGL10 egl = (EGL10) EGLContext.getEGL();
		EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
		// Initialise
		int[] version = new int[2];
		egl.eglInitialize(display, version);
		// Query total number of configurations
		int[] totalConfigurations = new int[1];
		egl.eglGetConfigs(display, null, 0, totalConfigurations);

		// Query actual list configurations
		EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
		egl.eglGetConfigs(display, configurationsList, totalConfigurations[0],
				totalConfigurations);

		int[] textureSize = new int[1];
		int maximumTextureSize = 0;
		// Iterate through all the configurations to located the maximum texture
		// size
		for (int i = 0; i < totalConfigurations[0]; i++) {
			// Only need to check for width since opengl textures are always
			// squared
			egl.eglGetConfigAttrib(display, configurationsList[i],
					EGL10.EGL_MAX_PBUFFER_WIDTH, textureSize);

			// Keep track of the maximum texture size
			if (maximumTextureSize < textureSize[0]) {
				maximumTextureSize = textureSize[0];
			}

			// Log.i("GLHelper", Integer.toString(textureSize[0]));
		}
		// Release
		egl.eglTerminate(display);
		Log.i("GLHelper",
				"Maximum GL texture size: "
						+ Integer.toString(maximumTextureSize));
		return maximumTextureSize;

	}

	private Bitmap decodeFileForReader2(File f) {
		try {
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = 2;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			/*
			 * FileInputStream stream2=new FileInputStream(f); Bitmap
			 * bitmap=BitmapFactory.decodeStream(stream2); stream2.close();
			 */
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError) {
				memoryCache.clear();
				System.gc();
			}
			return decodeFileForReader3(f);
		}
	}

	private Bitmap decodeFileForReader3(File f) {
		try {
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = 4;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError) {
				memoryCache.clear();
				System.gc();
			}
			return null;
		}
	}

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
			final int REQUIRED_SIZE = 70;
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
			clearMemCache();
			return null;
		} catch (IOException e) {
			clearMemCache();
			e.printStackTrace();
			return null;
		}
	}

	private Bitmap decodeFile2(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			int width_tmp = o.outWidth;
			int height_tmp = o.outHeight;

			Log.d("TAG", String.format("W = %d, H = %d", width_tmp, height_tmp));

			int width = 0;
			int height = 0;
			final int SIZE = 2048;
			float scaleRatio = 1.0f;
			while (true) {
				if (width_tmp <= SIZE && height_tmp <= SIZE) {
					width = width_tmp;
					height = height_tmp;
					break;
				}

				scaleRatio += 0.1f;
				if (width_tmp / scaleRatio <= SIZE
						&& height_tmp / scaleRatio <= SIZE) {
					width = (int) (width_tmp / scaleRatio);
					height = (int) (height_tmp / scaleRatio);
					break;
				}
			}

			Log.d("TAG", String.format("W = %d, H = %d", width, height));
			// //Find the correct scale value. It should be the power of 2.
			// final int REQUIRED_SIZE=70;
			// int width_tmp=o.outWidth, height_tmp=o.outHeight;
			// int scale=1;
			// while(true){
			// if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
			// break;
			// width_tmp/=2;
			// height_tmp/=2;
			// scale*=2;
			// }
			// //decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			// o2.inSampleSize=scale;
			o2.outWidth = width;
			o2.outHeight = height;
			FileInputStream stream2 = new FileInputStream(f);
			// Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
			Bitmap bitmap = Bitmap.createScaledBitmap(
					BitmapFactory.decodeStream(stream2, null, null), width,
					height, false);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {
			clearMemCache();
			return null;
		} catch (IOException e) {
			clearMemCache();
			e.printStackTrace();
			return null;
		}
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
				// Bitmap bmp=getBitmap(photoToLoad.url);
				// memoryCache.put(photoToLoad.url, bmp);
				if (imageViewReused(photoToLoad))
					return;
				// BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
				// handler.post(bd); thiensuhack comment
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

	public boolean clearCache() {
		clearMemCache();
		return clearFileCache();
	}

	public boolean clearCache(List<String> listURL) {
		clearMemCache();
		return clearFileCache(listURL);
	}

	public boolean clearFileCache(List<String> listURL) {
		if (getFileCache() != null) {
			return getFileCache().clear(listURL);
		}
		return false;
	}

	public boolean clearFileCache() {
		if (getFileCache() != null) {
			return getFileCache().clear();
		}
		return false;
	}

	public void clearMemCache() {
		if (memoryCache != null) {
			memoryCache.clear();
		}
	}

	public FileCache getFileCache() {
		return fileCache;
	}

	private void setFileCache(FileCache fileCache) {
		this.fileCache = fileCache;
	}
}
