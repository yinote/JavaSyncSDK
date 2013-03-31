package yinote.ydep.remote.dto;

import org.json.JSONException;
import org.json.JSONObject;


public class TodoUpdateDto implements JSONFormat {
	private String id = null;
	private String title = null;
	private long startAt = 0;
	private long completeAt = 0;
	private int usn = 0;
	
	private boolean titleSet = false;
	private boolean startAtSet = false;
	private boolean completeAtSet = false;
	
	public TodoUpdateDto(String id, int usn) {
		super();
		this.id = id;
		this.usn = usn;
	}
	
	public TodoUpdateDto setTitle(String title) {
		this.title = title;
		titleSet = true;
		return this;
	}
	public TodoUpdateDto setStartAt(long startAt) {
		this.startAt = startAt;
		startAtSet = true;
		return this;
	}
	public TodoUpdateDto setCompleteAt(long completeAt) {
		this.completeAt = completeAt;
		completeAtSet = true;
		return this;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("usn", usn);
		if (titleSet) {
			json.put("title", title);
		}
		if (startAtSet) {
			json.put("startAt", startAt);
		}
		if (completeAtSet) {
			json.put("completeAt", completeAt);
		}
		return json;
	}
	
}
