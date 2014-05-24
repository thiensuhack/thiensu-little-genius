package com.zuzu.corelib;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONException;
import org.json.JSONObject;

import com.zuzu.queue.QueueCommand;

import android.os.Bundle;

public abstract class ZZHttpRequest implements QueueCommand {
	protected int _requestType; // type of request : quyet dinh request nay danh
								// cho action gi
	private RequestMethodEnum _method; // method la GET or POST
	private ZZHttpRequestResult _resultType;
	private String _endPoint;
	private Bundle _params;
	private boolean _debug;

	public ZZHttpRequest(int requestType, String endpoint, Bundle params,
			ZZHttpRequestResult resultType, boolean debug) {
		this._requestType = requestType;
		this._method = RequestMethodEnum.GET;
		this._resultType = resultType;
		this._endPoint = endpoint;
		this._params = params;
		this._debug = debug;
	}

	public ZZHttpRequest(int requestType, String endpoint, Bundle params,
			RequestMethodEnum method, ZZHttpRequestResult resultType,
			boolean debug) {
		this._requestType = requestType;
		this._method = method;
		this._resultType = resultType;
		this._endPoint = endpoint;
		this._params = params;
		this._debug = debug;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

		if (this._resultType == ZZHttpRequestResult.JSONResult) {
			this.sendRequestResponseJSON();
		} else if (this._resultType == ZZHttpRequestResult.StringResult) {
			this.sendRequestResponseString();
		} else if (this._resultType == ZZHttpRequestResult.RawResult) {
			this.sendRequestResponseRaw();
		} else if (this._resultType == ZZHttpRequestResult.ByteRequest) {
			this.sendRequestResponseByte();
		} else {
			this.sendRequestResponseString();
		}

	}

	abstract public void onGetJsonDataCompleted(int typeRequest, JSONObject data);

	abstract public void onGetStringDataCompleted(int typeRequest, String data);

	abstract public void onGetBinaryDataCompleted(int typeRequest, byte[] data);

	abstract public void onGetRawDataCompleted(int typeRequest,
			HttpResponse data);

	abstract public void onGetDataError(int typeRequest, HttpErrors errorCode);

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		onGetDataError(this._requestType, new HttpErrors(
				HttpErrors.REQUEST_CANCELLED));
	}

	private void sendRequestResponseJSON() {
		try {
			Request request = new Request();
			String result = request.sendRequest(this._endPoint, this._method,
					this._params, this._debug);
			if (result == null) {
				onGetDataError(this._requestType, new HttpErrors(
						HttpErrors.NULL_DATA));
			} else {
				try {
					JSONObject data = new JSONObject(result);
					onGetJsonDataCompleted(this._requestType, data);
				} catch (JSONException ex) {
					onGetDataError(this._requestType, new HttpErrors(
							HttpErrors.JSON_PARSE_ERROR, ex.getMessage()));
				}
			}
		} catch (SocketTimeoutException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_TIMEOUT, ex.getMessage()));
		} catch (ClientProtocolException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (HttpHostConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (ConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (Exception ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.NETWORK_UNKNOWN, ex.getMessage()));
		}

	}

	private void sendRequestResponseString() {
		try {
			Request request = new Request();
			String result = request.sendRequest(this._endPoint, this._method,
					this._params, this._debug);
			if (result == null) {
				onGetDataError(this._requestType, new HttpErrors(
						HttpErrors.NULL_DATA));
			} else {
				onGetStringDataCompleted(this._requestType, result);
			}
		} catch (SocketTimeoutException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_TIMEOUT, ex.getMessage()));
		} catch (ClientProtocolException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (HttpHostConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (ConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (Exception ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.NETWORK_UNKNOWN, ex.getMessage()));
		}
	}

	private void sendRequestResponseRaw() {
		try {
			Request request = new Request();
			HttpResponse result = request.sendRequestResponseRaw(
					this._endPoint, this._method, this._params, this._debug);
			if (result == null) {
				onGetDataError(this._requestType, new HttpErrors(
						HttpErrors.NULL_DATA));
			} else {
				onGetRawDataCompleted(this._requestType, result);
			}
		} catch (SocketTimeoutException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_TIMEOUT, ex.getMessage()));
		} catch (ClientProtocolException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (HttpHostConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (ConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (Exception ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.NETWORK_UNKNOWN, ex.getMessage()));
		}
	}

	private void sendRequestResponseByte() {
		try {
			Request request = new Request();
			byte[] result = request.sendRequestResponseByte(this._endPoint,
					this._method, this._params, this._debug);
			if (result == null) {
				onGetDataError(this._requestType, new HttpErrors(
						HttpErrors.NULL_DATA));
			} else {
				onGetBinaryDataCompleted(this._requestType, result);
			}
		} catch (SocketTimeoutException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_TIMEOUT, ex.getMessage()));
		} catch (ClientProtocolException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (HttpHostConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (ConnectException ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.CONNECTION_REFUSED, ex.getMessage()));
		} catch (Exception ex) {
			onGetDataError(this._requestType, new HttpErrors(
					HttpErrors.NETWORK_UNKNOWN, ex.getMessage()));
		}
	}
}
