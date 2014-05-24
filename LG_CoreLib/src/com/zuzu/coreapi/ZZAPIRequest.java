package com.zuzu.coreapi;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import android.os.Bundle;

import com.zuzu.corelib.HttpErrors;
import com.zuzu.corelib.RequestMethodEnum;
import com.zuzu.corelib.ZZHttpRequest;
import com.zuzu.corelib.ZZHttpRequestResult;

public class ZZAPIRequest extends ZZHttpRequest {

	protected IDelegateAPIRequestGetter mDelegate;
	private APIRequestResult apiRequestResult = null;
	private HttpErrors error = null;
	private boolean doRightNow = false;

	public ZZAPIRequest(int requestType, String endpoint, Bundle params,
			IDelegateAPIRequestGetter delegate, boolean debug) {
		super(requestType, endpoint, params, ZZHttpRequestResult.JSONResult,
				debug);
		this.mDelegate = delegate;
	}

	public ZZAPIRequest(int requestType, String endpoint, Bundle params,
			RequestMethodEnum method, IDelegateAPIRequestGetter delegate,
			boolean debug) {
		super(requestType, endpoint, params, method,
				ZZHttpRequestResult.JSONResult, debug);
		this.mDelegate = delegate;
	}

	public ZZAPIRequest(int requestType, String endpoint, Bundle params,
			RequestMethodEnum method, IDelegateAPIRequestGetter delegate,
			boolean debug, ZZHttpRequestResult resultType) {
		super(requestType, endpoint, params, method, resultType, debug);
		this.mDelegate = delegate;
	}

	public ZZAPIRequest(int requestType, String endpoint, Bundle params,
			RequestMethodEnum method, boolean debug,
			ZZHttpRequestResult resultType) {
		super(requestType, endpoint, params, method, resultType, debug);
	}

	public ZZAPIRequest(int requestType, String endpoint, Bundle params,
			RequestMethodEnum method, boolean debug) {
		super(requestType, endpoint, params, method,
				ZZHttpRequestResult.JSONResult, debug);
	}

	public void doRightNow() {
		this.doRightNow = true;
		this.execute();
	}

	public HttpErrors getError() {
		return error;
	}

	public APIRequestResult getAPIRequestResult() {
		return this.apiRequestResult;
	}

	@Override
	public void onGetJsonDataCompleted(final int requestType,
			final JSONObject data) {
		// TODO Auto-generated method stub
		if (data == null) {
			onGetDataError(requestType, new HttpErrors(HttpErrors.NULL_DATA,
					"data null"));
			return;
		}

		try {
			int errorCode = -999999;
			if (data.has("error_code")) {
				errorCode = data.getInt("error_code");
			}

			String errorMsg = "";
			if (data.has("error_msg")) {
				errorMsg = data.getString("error_msg");
			}

			String errorType = "";

			if (data.has("error_type")) {
				errorType = data.getString("error_type");
			}

			Object _data = null;
			if (data.has("data") && !data.isNull("data")) {
				_data = data.get("data");
			}

			final APIRequestResult ret = new APIRequestResult();
			ret.error_code = errorCode;
			ret.error_msg = errorMsg;
			ret.error_type = errorType;
			ret.data = _data;

			if (this.doRightNow) {
				this.apiRequestResult = ret;
			} else {
				if (this.mDelegate != null) {
//					MyHandler.getInstance().post(new Runnable() {
//						@Override
//						public void run() {
//							// mDelegate.onGetDataError(Errors.NETWORK_UNKNOWN);
//							mDelegate.onGetAPIRequestDataCompleted(requestType,
//									ret);
//						}
//					});
				}
			}
		} catch (final Exception ex) {
			onGetDataError(requestType, new HttpErrors(
					HttpErrors.JSON_PARSE_ERROR, ex.getMessage()));
		}

	}

	@Override
	public void onGetStringDataCompleted(final int requestType,
			final String data) {
		// TODO Auto-generated method stub
//		if (this.mDelegate != null) {
//			MyHandler.getInstance().post(new Runnable() {
//				@Override
//				public void run() {
//					// mDelegate.onGetDataError(Errors.NETWORK_UNKNOWN);
//					mDelegate.onGetStringDataCompleted(requestType, data);
//				}
//			});
//		}
	}

	@Override
	public void onGetBinaryDataCompleted(final int requestType,
			final byte[] data) {
		// TODO Auto-generated method stub
//		if (this.mDelegate != null) {
//			MyHandler.getInstance().post(new Runnable() {
//				@Override
//				public void run() {
//					// mDelegate.onGetDataError(Errors.NETWORK_UNKNOWN);
//					mDelegate.onGetBinaryDataCompleted(requestType, data);
//				}
//			});
//		}
	}

	@Override
	public void onGetRawDataCompleted(final int requestType,
			final HttpResponse data) {
		// TODO Auto-generated method stub
//		if (this.mDelegate != null) {
//			MyHandler.getInstance().post(new Runnable() {
//				@Override
//				public void run() {
//					// mDelegate.onGetDataError(Errors.NETWORK_UNKNOWN);
//					mDelegate.onGetRawDataCompleted(requestType, data);
//				}
//			});
//		}
	}

	@Override
	public void onGetDataError(final int requestType, final HttpErrors error) {
		// TODO Auto-generated method stub
		if (this.doRightNow) {
			this.error = error;
		} 
//		else if (this.mDelegate != null) {
//			MyHandler.getInstance().post(new Runnable() {
//				@Override
//				public void run() {
//					// mDelegate.onGetDataError(Errors.NETWORK_UNKNOWN);
//					mDelegate.onGetDataError(requestType, error);
//				}
//			});
//		}
	}

}
