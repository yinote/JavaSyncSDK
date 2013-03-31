package yinote.ydep.remote.store.impl;

import org.json.JSONException;
import org.json.JSONObject;

import yinote.ydep.remote.dto.Todo;

public class TodoImpl implements Todo {
	private String id;
    private long startAt;
    private String title;
    private long completeAt;
    private int usn;
    
    public TodoImpl(JSONObject jsonData) {
    	if (jsonData == null) {
            return;
        }
        try {
            id = jsonData.getString("id");
            title = jsonData.getString("title");
            startAt = jsonData.getLong("startAt");
            usn = jsonData.getInt("usn");
            completeAt = jsonData.optLong("completeAt");
        }catch (JSONException e){
            e.printStackTrace();
        }     
    }
    
    
	public String getId() {
		return id;
	}
	public long getStartAt() {
		return startAt;
	}
	public String getTitle() {
		return title;
	}
	public long getCompleteAt() {
		return completeAt;
	}
	public int getUsn() {
		return usn;
	}


	@Override
	public String toString() {
		return "Todo{ id:" + id 
				+ ", startAt: " + startAt 
				+ ", title: " + title
				+ ", completeAt: " + completeAt
				+ ", usn: " + usn + "}";
	}

	
    
}
