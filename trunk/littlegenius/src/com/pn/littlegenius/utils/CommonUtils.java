package com.pn.littlegenius.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.array;
import android.os.AsyncTask;
import android.util.Log;

public class CommonUtils{
	  public static String Title = "";
	  public static String Content = "";
	  public static String Data = "";
	  public static ArrayList<String> listData;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	  
	  public static String URL_PREVIEW_ACTION = "http://mylittlegenius.com.vn/whats-new/previews/?api";
	  public static String URL_ABOUT = "http://mylittlegenius.com.vn/home/what-is-my-little-geniustm/?api";
	  public static String URL_MEDIA = "http://mylittlegenius.com.vn/whats-new/media/?api";
	  public static String URL_PREVIEW = "http://mylittlegenius.com.vn/whats-new/previews/?api&action=info";
	  public static String URL_PROGRAM = "http://mylittlegenius.com.vn/programme-overview/?api";
	  public static String URL_TESTIMO = "http://mylittlegenius.com.vn/testimonials/?api";
	  public static String URL_SCHEDULE = "http://mylittlegenius.com.vn/?api&act=schedules";
	  public static String URL_COURSE_PROGRAM = "http://mylittlegenius.com.vn/home/course-background/?api";
	  public static String PROGRAM_PARAM = "program";
}