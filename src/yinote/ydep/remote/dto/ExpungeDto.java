package yinote.ydep.remote.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class ExpungeDto implements JSONFormat {
	private String id;
	private int usn;
	private long expunged;
	
	public String getId() {
		return id;
	}
	public int getUsn() {
		return usn;
	}
	public long getExpunged() {
		return expunged;
	}
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("usn", usn);
		json.put("expunged", expunged);
		return json;
	}
	
	
}
