package yinote.ydep.remote.store.impl;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import yinote.ydep.remote.dto.NoteAttributes;
import yinote.ydep.util.JsonUtil;

public class NoteAttributesImpl implements NoteAttributes {
	private double latitude = 0;
	private double longitude = 0;
	private double altitude = 0;
	private String author = null;
	private String source = null;
	private String sourceURL = null;
	private String sourceApplication = null;
	private long shareDate = 0;
	private String placeName = null;
	private String contentClass = null;
	private Map<String, String> applicationData = null;
	private String lastEditedBy = null;
	private Map<String, String> classifications = null;
	
	public NoteAttributesImpl(JSONObject json) {
		if (json == null) {
			return;
		}
		this.latitude = json.optDouble("latitude");
		this.longitude = json.optDouble("longitude");
		this.altitude = json.optDouble("altitude");
		this.author = json.optString("author");
		this.source = json.optString("source");
		this.sourceURL = json.optString("sourceURL");
		this.sourceApplication = json.optString("sourceApplication");
		this.shareDate = json.optLong("shareDate");
		this.placeName = json.optString("placeName");
		this.contentClass = json.optString("contentClass");
		this.applicationData = JsonUtil.stringMap(json.optJSONObject("applicationData"));
		this.lastEditedBy = json.optString("lastEditedBy");
		this.classifications = JsonUtil.stringMap(json.optJSONObject("classifications"));
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getLatitude()
	 */
	@Override
	public double getLatitude() {
		return latitude;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getLongitude()
	 */
	@Override
	public double getLongitude() {
		return longitude;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getAltitude()
	 */
	@Override
	public double getAltitude() {
		return altitude;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getAuthor()
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getSource()
	 */
	@Override
	public String getSource() {
		return source;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getSourceURL()
	 */
	@Override
	public String getSourceURL() {
		return sourceURL;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getSourceApplication()
	 */
	@Override
	public String getSourceApplication() {
		return sourceApplication;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getShareDate()
	 */
	@Override
	public long getShareDate() {
		return shareDate;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getPlaceName()
	 */
	@Override
	public String getPlaceName() {
		return placeName;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getContentClass()
	 */
	@Override
	public String getContentClass() {
		return contentClass;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getApplicationData()
	 */
	@Override
	public Map<String, String> getApplicationData() {
		return applicationData;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getLastEditedBy()
	 */
	@Override
	public String getLastEditedBy() {
		return lastEditedBy;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteAttributes#getClassifications()
	 */
	@Override
	public Map<String, String> getClassifications() {
		return classifications;
	}
}
