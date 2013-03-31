package yinote.ydep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import yinote.ydep.exception.RequestError;
import android.util.Log;

public class MultipartFormDataClient {
	private final static String CRLF = "\r\n"; // Line separator required by multipart/form-data.
	private final static String charset = "UTF-8";
	
	public JSONObject send(String url, Map<String, String> params, File binaryFile, HttpMethod method) throws JSONException, RequestError {
		
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		
		PrintWriter writer = null;
		HttpURLConnection connection = null;
		JSONObject result = null;
		try {
			connection = (HttpURLConnection)(new URL(url)).openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		    OutputStream output = connection.getOutputStream();
		    writer = new PrintWriter(new OutputStreamWriter(output, charset), true); // true = autoFlush, important!

		    if (params != null && !params.isEmpty()) {
		    	// Send normal param.
		    	writeNormalParams(writer, params, boundary);
		    }
		    if (binaryFile != null && binaryFile.exists()) {
			    // Send binary file.
			    writeBinaryFile(writer, binaryFile, boundary, output);
		    }

		    // End of multipart/form-data.
		    writer.append("--" + boundary + "--").append(CRLF);
		    
		    result = RequestUtil.readResponse(connection);
		} catch (Exception e) {
			Log.e(getClass().getName(), e.getMessage());
		} finally {
		    if (writer != null) writer.close();
		    if (connection != null) {
		    	connection.disconnect();
		    	connection = null;
            }
		}
		return result;
	}
	
	private void writeNormalParams(PrintWriter writer, Map<String, String> params, String boundary) {
		writer.append("--" + boundary).append(CRLF);
	    writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
	    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
	    writer.append(CRLF);
	    writer.append(RequestUtil.encodeMapToParam(params)).append(CRLF).flush();
	}
	
	private void writeBinaryFile(PrintWriter writer, File binaryFile, String boundary, OutputStream output) {
	    writer.append("--" + boundary).append(CRLF);
	    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
	    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
	    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
	    writer.append(CRLF).flush();
	    writeToConnOutputStream(binaryFile, output);
	    writer.append(CRLF).flush(); // CRLF is important! It indicates end of binary boundary.
	}
	private void writeToConnOutputStream(File binaryFile, OutputStream output) {
		InputStream input = null;
	    try {
	        input = new FileInputStream(binaryFile);
	        byte[] buffer = new byte[1024];
	        for (int length = 0; (length = input.read(buffer)) > 0;) {
	            output.write(buffer, 0, length);
	        }
	        output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	Log.e(getClass().getName(), e.getMessage());
		} finally {
	        if (input != null) try { input.close(); } catch (IOException e) {Log.e(getClass().getName(), e.getMessage());}
	    }
	}
}
