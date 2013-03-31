package yinote.ydep.remote.dto;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class NoteAttributesDto {
	private double latitude = 0;
	private boolean latitudeSet = false;
	private double longitude = 0;
	private boolean longitudeSet = false;
	private double altitude = 0;
	private boolean altitudeSet = false;
	private String author = null;
	private boolean authorSet = false;
	private String source = null;
	private boolean sourceSet = false;
	private String sourceURL = null;
	private boolean sourceURLSet = false;
	private String sourceApplication = null;
	private boolean sourceApplicationSet = false;
	private long shareDate = 0;
	private boolean shareDateSet = false;
	private String placeName = null;
	private boolean placeNameSet = false;
	private String contentClass = null;
	private boolean contentClassSet = false;
	private Map<String, String> applicationData = null;
	private boolean applicationDataSet = false;
	private String lastEditedBy = null;
	private boolean lastEditedBySet = false;
	private Map<String, String> classifications = null;
	private boolean classificationsSet = false;
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jObject = new JSONObject();
		if (latitudeSet)
			jObject.put("latitude", latitude);
		if (longitudeSet)
			jObject.put("longitude", longitude);
		if (altitudeSet)
			jObject.put("altitude", altitude);
		if (authorSet)
			jObject.put("author", author);
		if (sourceSet)
			jObject.put("source", source);
		if (sourceURLSet)
			jObject.put("sourceURL", sourceURL);
		if (sourceApplicationSet)
			jObject.put("sourceApplication", sourceApplication);
		if (shareDateSet)
			jObject.put("shareDate", shareDate);
		if (placeNameSet)
			jObject.put("placeName", placeName);
		if (contentClassSet)
			jObject.put("contentClass", contentClass);
		if (applicationDataSet)
			jObject.put("applicationData", applicationData);
		if (lastEditedBySet)
			jObject.put("lastEditedBy", lastEditedBy);
		if (classificationsSet)
			jObject.put("classifications", classifications);
		return jObject;
	}
}
