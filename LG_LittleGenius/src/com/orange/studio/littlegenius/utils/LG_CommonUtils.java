package com.orange.studio.littlegenius.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.orange.studio.littlegenius.LG_ApplicationContext;
import com.orange.studio.littlegenius.models.CommonModel;
import com.orange.studio.littlegenius.objects.ResultData;
import com.orange.studio.littlegenius.objects.VideoKMSDTO;
import com.zuzu.db.store.SQLiteStore;
import com.zuzu.db.store.SimpleStoreIF;

public class LG_CommonUtils {
	public static String Title = "";
	public static String Content = "";
	  public static String Data = "";
	  public static ArrayList<String> listData;
	  
	  
	  public static String URL_PREVIEW_ACTION = "http://mylittlegenius.com.vn/whats-new/previews/?api";
	  public static String URL_ABOUT = "http://mylittlegenius.com.vn/home/what-is-my-little-geniustm/?api";
	  public static String URL_MEDIA = "http://mylittlegenius.com.vn/whats-new/media/?api";
	  public static String URL_PREVIEW = "http://mylittlegenius.com.vn/whats-new/previews/?api&action=info";
	  public static String URL_PROGRAM = "http://mylittlegenius.com.vn/programme-overview/?api";
	  public static String URL_TESTIMO = "http://mylittlegenius.com.vn/testimonials/?api";
	  public static String URL_SCHEDULE = "http://mylittlegenius.com.vn/?api&act=schedules";
	  public static String URL_COURSE_PROGRAM = "http://mylittlegenius.com.vn/home/course-background/?api";
	  public static String PROGRAM_PARAM = "program";
	  
	  public static void showToast(String toast){
		  Toast.makeText(LG_ApplicationContext.getContext(), toast, Toast.LENGTH_LONG).show();
	  }
	  public static void checkUserInfo(){
		  if(AppConfig.mUser==null){
			  AppConfig.mUser=CommonModel.getInstance().getUserInfo();
		  }
	  }
	  public static SimpleStoreIF getStoreAdapter(String name, Context mContext,
				int items) {
			return SQLiteStore.getInstance(name, mContext, AppConfig.DBVERSION,
					items);
		}
	  
	  public static boolean validateEmail(String email) {
			Pattern pattern;
			Matcher matcher;
			String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(email);
			return matcher.matches();

		}
	  public static boolean validatePhoneNumber(String phone){
			if (phone == null || phone.trim().length()<10 || phone.trim().length()>11 || !phone.matches("-?\\d+(\\.\\d+)?")) {
				return false;
			}
			return true;
	  }
	  public static void loadData(String url)
		{
			String url_select = url;

		      try {
		            // HttpClient is more then less deprecated. Need to change to URLConnection
		            HttpClient httpClient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(url_select);
//		            httpPost.setEntity(new UrlEncodedFormEntity(param));
		            HttpResponse httpResponse = httpClient.execute(httpPost);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            // Read content & Log
//		            inputStream = httpEntity.getContent();
		            if(httpEntity != null){
		            	String _response=EntityUtils.toString(httpEntity); // content will be consume only once
		            	final JSONObject jObject=new JSONObject(_response);
		                String dataObject = jObject.getString("data");  
		            	final JSONObject jObjectData=new JSONObject(dataObject);
		            	Title = jObjectData.getString("name");   
		            	Content = jObjectData.getString("content");   
		            	Data = dataObject;
		            }
		      } catch (UnsupportedEncodingException e1) {
		            Log.e("UnsupportedEncodingException", e1.toString());
		            e1.printStackTrace();
		        } catch (ClientProtocolException e2) {
		            Log.e("ClientProtocolException", e2.toString());
		            e2.printStackTrace();
		        } catch (IllegalStateException e3) {
		            Log.e("IllegalStateException", e3.toString());
		            e3.printStackTrace();
		        } catch (IOException e4) {
		            Log.e("IOException", e4.toString());
		            e4.printStackTrace();
		        } catch (ParseException e) {
					
					e.printStackTrace();
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
		}
	  public static void loadDataContent(String url)
		{
			String url_select = url;

		      try {
		            HttpClient httpClient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(url_select);
		            HttpResponse httpResponse = httpClient.execute(httpPost);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            // Read content & Log
		            if(httpEntity != null){
		            	String _response=EntityUtils.toString(httpEntity); // content will be consume only once
		            	final JSONObject jObject=new JSONObject(_response);
		                JSONArray movie = jObject.getJSONArray("data");
		                listData = new ArrayList<String>();
		                for (int i = 0; i < movie.length(); i++) {
		                	String actor = movie.getString(i);
		                	listData.add(actor);
						}
		            }
		      } catch (UnsupportedEncodingException e1) {
		            Log.e("UnsupportedEncodingException", e1.toString());
		            e1.printStackTrace();
		        } catch (ClientProtocolException e2) {
		            Log.e("ClientProtocolException", e2.toString());
		            e2.printStackTrace();
		        } catch (IllegalStateException e3) {
		            Log.e("IllegalStateException", e3.toString());
		            e3.printStackTrace();
		        } catch (IOException e4) {
		            Log.e("IOException", e4.toString());
		            e4.printStackTrace();
		        } catch (ParseException e) {
					
					e.printStackTrace();
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
		}
	  
	  public static List<VideoKMSDTO> getListVideoFromServer(String url){
		  try {
			  	List<VideoKMSDTO> mList=null;			  	
				ResultData data=getDataFromServer(url);
				if(data!=null && data.result==1){
					JSONArray jArr=new JSONArray(data.data);					
					if(jArr!=null && jArr.length()>0){
						mList=new ArrayList<VideoKMSDTO>();
						for (int i = 0; i < jArr.length(); i++) {
							JSONObject jb=jArr.getJSONObject(i);
							VideoKMSDTO video=new VideoKMSDTO();
							video.id=jb.optString("video_id");
							video.name=jb.optString("video_name");
							video.date=jb.optString("video_date");
							video.status=jb.optString("video_status");
							video.type=jb.optString("video_type");
							video.videoURL=jb.optString("video_url");
							video.youtubeId=jb.optString("youtube_id");
							video.user_id=jb.optString("user_id");
							if(video.youtubeId!=null && video.youtubeId.length()>0){
								video.cover="http://img.youtube.com/vi/"+video.youtubeId+"/1.jpg";
							}
							mList.add(video);
						}
					}
				}
				return mList;
		} catch (Exception e) {
			return null;
		}
	  }
	  public static ResultData getDataFromServer(String url){
	  		ResultData result=null;
			String data=getStringFromURL(url);
			if(data!=null && data.length()>0){
				try {
					JSONObject jb=new JSONObject(data);
					result=new ResultData();
					result.result=jb.optInt("result");
					result.msg=jb.optString("msg");
					result.data=jb.optString("data");
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
			}
			return result;
		}	
	  public static ResultData postDataServer(String url,String _data){
		  	ResultData result=null;		
			String data=postServer(url,_data);
			if(data!=null && data.length()>0){
				try {
					JSONObject jb=new JSONObject(data);
					result=new ResultData();
					result.result=jb.optInt("result");
					result.msg=jb.optString("msg");
					result.data=jb.optString("data");
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
			}
			return result;
		}	
	  public static ResultData postDataServer(String url,Bundle _data){
		  ResultData result=null;		
			String data=postServer(url,_data);
			if(data!=null && data.length()>0){
				try {
					JSONObject jb=new JSONObject(data);
					result=new ResultData();
					result.result=jb.optInt("result");
					result.msg=jb.optString("msg");
					result.data=jb.optString("data");
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
			}
			return result;
		}	
	  public static String postServer(String url,Bundle params) {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);		
						
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				List<String> listKey = new ArrayList<String>();

				for (String key : params.keySet()) {
					listKey.add(key);
				}
				for (String key : listKey) {
					String value = params.getString(key);
					nameValuePairs.add(new BasicNameValuePair(key, value));
				}						   
				UrlEncodedFormEntity form=new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
	            httpPost.setEntity(form);

				HttpResponse response = client.execute(httpPost);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
			return builder.toString();
		}	
	  public static String postServer(String url,String data) {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);		
						
			try {
//				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//				List<String> listKey = new ArrayList<String>();
//
//				for (String key : params.keySet()) {
//					listKey.add(key);
//				}
//				for (String key : listKey) {
//					String value = params.getString(key);
//					nameValuePairs.add(new BasicNameValuePair(key, value));
//				}						   
//				UrlEncodedFormEntity form=new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
//	            httpPost.setEntity(form);
				httpPost.setEntity(new StringEntity(data));
				HttpResponse response = client.execute(httpPost);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
			return builder.toString();
		}	
	  public static String getStringFromURL(String url) {
			
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);		
			try {			
				
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return builder.toString();
		}
	  public static String getTitle()
	  {
		  return Title;
	  }
	  public static String getContent()
	  {
		  return Content;
	  }
	  public static String getData()
	  {
		  return Data;
	  }
	  public static ArrayList<String> getListData()
	  {
		  return listData;
	  }
	  
	public static boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected())
			return true;
		else
			return false;
	}

	public static String getVersionName(Context c) {
		try {
			return c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName;
		} catch (Exception e) {
			return null;
		}
	}

	public static void go2WebAction(Activity activity,String url) {
		try {
			Intent action = new Intent(Intent.ACTION_VIEW,
					Uri.parse(url));
			action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(action);
		} catch (Exception e) {
			return;
		}
	}
}
