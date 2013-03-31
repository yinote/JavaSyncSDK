package yinote.ydep.remote.dto;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONFormat extends RemoteFormat {
	public JSONObject toJSON() throws JSONException;
}
