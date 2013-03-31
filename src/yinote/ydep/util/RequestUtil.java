package yinote.ydep.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import yinote.ydep.exception.RequestError;

public class RequestUtil {
	public static JSONObject readResponse(HttpURLConnection conn) throws JSONException, RequestError {
		JSONObject result = null;
		InputStream is = null;
		try {
			if (conn.getResponseCode() == 200) {
	        	is = conn.getInputStream();
	        } else {
	        	is = conn.getErrorStream();
	        }
	        
	        String response = readContent(is);
	        if (conn.getResponseCode() != 200) {
	        	throw new RequestError(response);
	        }
	        result = new JSONObject(response);
		} catch (IOException e) {
			Log.e(RequestUtil.class.getName(),  e.getMessage());
		} finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                	Log.e(RequestUtil.class.getName(),  e.getMessage());
                    e.printStackTrace();
                }
                is = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
//    	Log.d("JsonRequestClient",  "response " + response);
		return result;
	}
	
	private static String readContent(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String result = br.readLine();

		while (result != null) {
		    sb.append(result);
		    result = br.readLine();
		}
		return sb.toString();
	}
	public static String encodeMapToParam(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(encodeByUtf8(entry.getValue())).append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	private static String encodeByUtf8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(RequestUtil.class.getName(), e.getMessage());
		}
		return result;
	}
}
