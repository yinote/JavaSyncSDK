package yinote.ydep.remote.dto;

import org.json.JSONException;
import org.json.JSONObject;


public class TodoCreateDto implements JSONFormat {
	private String id = null;
	private String title = null;
	private long startAt = 0;
	

	public TodoCreateDto(String id, String title, long startAt) {
		super();
		this.id = id;
		this.title = title;
		this.startAt = startAt;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject jObject = new JSONObject();
		jObject.put("id", id);
		jObject.put("title", title);
		jObject.put("startAt", startAt);
		return jObject;
	}
	
}
