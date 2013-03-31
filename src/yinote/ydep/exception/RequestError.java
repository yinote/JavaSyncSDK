package yinote.ydep.exception;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestError extends Exception {

	private String message = null;
	private String code = null;
	private String uri = null;
	private String requestId = null;
	
	public RequestError(String jsonStr) {
		try {
			JSONObject json = new JSONObject(jsonStr);
			this.code = json.getString("code");
			this.message = json.getString("message");
			this.uri = json.getString("uri");
			this.requestId = json.getString("requestId");
		} catch (JSONException e) {
			this.message = jsonStr;
		}
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

	public String getUri() {
		return uri;
	}

	public String getRequestId() {
		return requestId;
	}
	
}
