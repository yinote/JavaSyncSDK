package yinote.ydep.remote.store.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yinote.ydep.remote.dto.NoteAttributes;
import yinote.ydep.remote.dto.NoteMetadata;
import yinote.ydep.util.JsonUtil;


public class NoteMetadataImpl implements NoteMetadata {
	private String id = null;
	private String title = null;
	private String summary = null;
	private String contentHash = null;
	private long created = 0;
	private long updated = 0;
	private long deleted = 0;
	private boolean active = false;
	private int usn = 0;	
	private String notebookId = null;
	private List<String> tagIds = new ArrayList<String>();
	
	private NoteAttributes attributes = null;
	
	private String largestResourceMime = null;
	
	private int largestResourceSize = 0;
	
	private String largestResourceId = null;
	//-----  optional end-------
	

	

	public NoteMetadataImpl(JSONObject json) {
		this.id = json.optString("id");
		this.title = json.optString("title");
		this.summary = json.optString("summary");
		this.contentHash = json.optString("contentHash");
		
		this.created = json.optLong("created");
		this.updated = json.optLong("updated");
		this.deleted = json.optLong("deleted");
		this.active = json.optBoolean("active");
		this.usn = json.optInt("usn");
		this.notebookId = json.optString("notebookId");
		this.tagIds = JsonUtil.stringList(json.optJSONArray("tagIds"));
		this.attributes = new NoteAttributesImpl(json.optJSONObject("attributes"));
		this.largestResourceMime = json.optString("largestResourceMime");
		this.largestResourceSize = json.optInt("largestResourceSize");
		this.largestResourceId = json.optString("largestResourceId");
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getId()
	 */
	@Override
	public String getId() {
		return id;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getSummary()
	 */
	@Override
	public String getSummary() {
		return summary;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getContentHash()
	 */
	@Override
	public String getContentHash() {
		return contentHash;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getCreated()
	 */
	@Override
	public long getCreated() {
		return created;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getUpdated()
	 */
	@Override
	public long getUpdated() {
		return updated;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getDeleted()
	 */
	@Override
	public long getDeleted() {
		return deleted;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#isActive()
	 */
	@Override
	public boolean isActive() {
		return active;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getUsn()
	 */
	@Override
	public int getUsn() {
		return usn;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getNotebookId()
	 */
	@Override
	public String getNotebookId() {
		return notebookId;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getTagIds()
	 */
	@Override
	public List<String> getTagIds() {
		return tagIds;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getAttributes()
	 */
	@Override
	public NoteAttributes getAttributes() {
		return attributes;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getLargestResourceMime()
	 */
	@Override
	public String getLargestResourceMime() {
		return largestResourceMime;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getLargestResourceSize()
	 */
	@Override
	public int getLargestResourceSize() {
		return largestResourceSize;
	}




	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.NoteMetadata#getLargestResourceId()
	 */
	@Override
	public String getLargestResourceId() {
		return largestResourceId;
	}
	
	
}
