package com.zuzu.corelib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.os.Bundle;

public class Request {

	//ULogger logger = ULogger.getLogger(Request.class);

	public static int CONNECTION_TIMEOUT = 30000;
	public static int SOCKET_TIMEOUT = 30000;
	public static int NOTIFICATION_SOCKET_TIMEOUT = 20000;
	private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
	private static HttpParams httpParameters;

	private ClientConnectionManager ccm = null;
	private HttpClient client = null;

	private static final String START_REQUEST = "start_request";
	private static final String END_REQUEST = "end_request";
	private static final String RESPONE_REQUEST = "respone_request";
	private static final String EXCEPTION_REQUEST = "exception_request";

	public Request() {
		httpParameters = new BasicHttpParams();

		httpParameters.setBooleanParameter("http.protocol.expect-continue",
				false);

		HttpConnectionParams.setConnectionTimeout(httpParameters,
				CONNECTION_TIMEOUT);
		HttpConnectionParams.setTcpNoDelay(httpParameters, true);
		HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIMEOUT);
		HttpConnectionParams.setSocketBufferSize(httpParameters,
				DEFAULT_SOCKET_BUFFER_SIZE);

		HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParameters, "utf-8");

		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);

		KeyStore trustStore = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			trustStore.load(null, null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SSLSocketFactory sf = null;
		try {
			sf = new CustomSSLSocketFactory(trustStore);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			//logger.error("Exception", e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		// Registering schemes for both HTTP and HTTPS
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", sf, 443));

		// Creating thread safe client connection manager
		this.ccm = new ThreadSafeClientConnManager(httpParameters, registry);

	}

	// response String
	public String sendRequest(String endpoint, Bundle params, boolean debug)
			throws HttpException, ClientProtocolException, IOException {
		return sendRequest(endpoint, RequestMethodEnum.GET, params, debug);
	}

	public String sendRequest(String endpoint, RequestMethodEnum method,
			Bundle params, boolean debug) throws HttpException,
			ClientProtocolException, IOException {

		HttpResponse httpResponse = null;
		HttpEntity entity = null;
		InputStream instream = null;
		String response = "";
		GZIPInputStream decodedStream = null;
		long execTime = System.currentTimeMillis();
		try {
			HttpClient httpClient = getHttpClient();

			if (method == RequestMethodEnum.GET) {
				HttpGet httpGet = getPostEntityByGetMethod(endpoint, params,
						debug);
				if (httpGet == null) {
					if (debug) {
						//logger.debug("httpGet null");
					}
					return "";
				}
				httpResponse = httpClient.execute(httpGet);
				execTime = System.currentTimeMillis() - execTime;

			} else {
				HttpPost httpPost = getPostEntityByPostMethod(endpoint, params);
				if (httpPost == null) {
					if (debug) {
						//logger.debug("httpPost null");
					}
					return "";
				}
				httpResponse = httpClient.execute(httpPost);
				execTime = System.currentTimeMillis() - execTime;
			}

			entity = httpResponse.getEntity();
			instream = entity.getContent();

			Header content_encoding = entity.getContentEncoding();
			if (debug) {
				//logger.debug("Do get url='" + endpoint + "' take " + execTime
					//	+ " msec");
			}

			if (content_encoding != null
					&& content_encoding.getValue().equals("gzip")) {
				decodedStream = new GZIPInputStream(instream);
				response = convertStreamToString(decodedStream);
			} else {
				response = convertStreamToString(instream);
			}

		} catch (SocketTimeoutException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			throw e;
		} catch (ClientProtocolException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			throw e;
		} catch (HttpHostConnectException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			throw e;
		} catch (ConnectException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} catch (IOException e) {
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			throw e;
		} finally {
			try {
				if (decodedStream != null) {
					decodedStream.close();
					decodedStream = null;
				}
				if (instream != null) {
					instream.close();
					instream = null;
				}
				this.client = null;
				System.gc();
			} catch (Exception e) {

			}
		}
		return response;
	}

	// response rawResponse
	public HttpResponse sendRequestResponseRaw(String endpoint, Bundle params,
			boolean debug) throws HttpException, ClientProtocolException,
			IOException {
		return sendRequestResponseRaw(endpoint, RequestMethodEnum.GET, params,
				debug);
	}

	public HttpResponse sendRequestResponseRaw(String endpoint,
			RequestMethodEnum method, Bundle params, boolean debug)
			throws HttpException, ClientProtocolException, IOException {

		HttpResponse httpResponse = null;

		try {
			HttpClient httpClient = getHttpClient();

			if (method == RequestMethodEnum.GET) {
				HttpGet httpGet = getPostEntityByGetMethod(endpoint, params,
						debug);
				if (httpGet == null) {
//					if (debug) {
//						logger.debug("httpGet null");
//					}
					return null;
				}
				httpResponse = httpClient.execute(httpGet);
			} else {
				HttpPost httpPost = getPostEntityByPostMethod(endpoint, params);
				if (httpPost != null) {
//					if (debug) {
//						logger.debug("httpPost null");
//					}
//					return null;
				}
				httpResponse = httpClient.execute(httpPost);
			}

		} catch (SocketTimeoutException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
		} catch (ClientProtocolException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} catch (ConnectException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} catch (IOException e) {
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} finally {
			// this.client.getConnectionManager().shutdown();

		}
		return httpResponse;
	}

	// response byte[]
	public byte[] sendRequestResponseByte(String endpoint, Bundle params,
			boolean debug) throws HttpException, ClientProtocolException,
			IOException {
		return sendRequestResponseByte(endpoint, RequestMethodEnum.GET, params,
				debug);
	}

	public byte[] sendRequestResponseByte(String endpoint,
			RequestMethodEnum method, Bundle params, boolean debug)
			throws HttpException, ClientProtocolException, IOException {

		HttpResponse httpResponse = null;
		HttpEntity entity = null;
		InputStream instream = null;
		byte[] response = null;
		GZIPInputStream decodedStream = null;

		try {
			HttpClient httpClient = getHttpClient();

			if (method == RequestMethodEnum.GET) {
				HttpGet httpGet = getPostEntityByGetMethod(endpoint, params,
						debug);
				if (httpGet == null) {
					if (debug) {
						//logger.debug("httpGet null");
					}
					return null;
				}
				httpResponse = httpClient.execute(httpGet);
			} else {
				HttpPost httpPost = getPostEntityByPostMethod(endpoint, params);
				if (httpPost != null) {
					if (debug) {
						//logger.debug("httpPost null");
					}
					return null;
				}
				httpResponse = httpClient.execute(httpPost);
			}

			entity = httpResponse.getEntity();
			instream = entity.getContent();

			Header content_encoding = entity.getContentEncoding();

			if (content_encoding != null
					&& content_encoding.getValue().equals("gzip")) {
				decodedStream = new GZIPInputStream(instream);
				response = convertStreamToByteArray(decodedStream);
			} else {
				response = convertStreamToByteArray(instream);
			}

		} catch (SocketTimeoutException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
		} catch (ClientProtocolException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			this.client.getConnectionManager().shutdown();
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
		} catch (ConnectException e) {
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();

		} catch (IOException e) {
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} catch (Exception e) {
			//logger.error("\n" + EXCEPTION_REQUEST + e.toString(), e);
			e.printStackTrace();
		} finally {
			// this.client.getConnectionManager().shutdown();
			try {
				if (decodedStream != null) {
					decodedStream.close();
					decodedStream = null;
				}
				if (instream != null) {
					instream.close();
					instream = null;
				}
				this.client = null;
				System.gc();
			} catch (Exception e) {

			}
		}
		return response;
	}

	// /////////////////// private functions ///////////////////////

	private HttpGet getPostEntityByGetMethod(String endpoint, Bundle params,
			boolean debug) {
		endpoint += "?" + buildGetData(params);
//		if (debug) {
//			logger.debug("url=" + endpoint);
//		}
		HttpGet httpGet = new HttpGet(endpoint);
		httpGet.setHeader("Accept-Encoding", "gzip");
		httpGet.setHeader("User-Agent", "ZuZu-Android");
		return httpGet;
	}

	private HttpPost getPostEntityByPostMethod(String endpoint, Bundle params)
			throws HttpException {
		HttpPost post = new HttpPost(endpoint);

		if (params == null)
			return post;

		try {
			post.setEntity(new UrlEncodedFormEntity(buildPostData(params)));
		} catch (UnsupportedEncodingException e) {
			//logger.debug("Got exception in getPostEntity(). Cannot initialize parameter for post request!");
			e.printStackTrace();
			throw new HttpException("Unable to initialize POST request");
		}

		post.setHeader("Accept-Encoding", "gzip");
		post.setHeader("User-Agent", "ZuZu-Android");

		return post;
	}

	private HttpClient getHttpClient() {
		if (this.client == null) {
			this.client = new DefaultHttpClient(this.ccm, httpParameters);
		}
		return this.client;
	}

	private static String buildGetData(Bundle params) {
		if (params == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : params.keySet()) {
			if (first)
				first = false;
			else
				sb.append("&");
			sb.append(URLEncoder.encode(key) + "="
					+ URLEncoder.encode(params.getString(key)));
		}

		return sb.toString();
	}

	private static List<NameValuePair> buildPostData(Bundle params) {
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			data.add(new BasicNameValuePair(key, params.getString(key)));
		}
		return data;
	}

	private byte[] convertStreamToByteArray(InputStream is) {
		byte[] s = null;
		try {
			int len = is.available();
			if (len == 0)
				return new byte[0];
			s = new byte[len];
			is.read(s, 0, len);
		} catch (Exception e) {
			System.gc();
		}
		return s;
	}

	private String convertStreamToString(InputStream is) {
		String s = "";
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.gc();
			} finally {
				try {
					is.close();
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.gc();
				}
			}
			s = sb.toString();
			return s;
		} catch (Exception e) {
			System.gc();
		}
		return s;
	}
}
