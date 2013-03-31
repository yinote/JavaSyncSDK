package yinote.ydep.remote.store.impl;

import org.json.JSONObject;

import yinote.ydep.remote.dto.Notebook;

public class NotebookImpl implements Notebook {
	private String id;
	private String name;
	private int usn;
	private boolean defaultNotebook;
	private long serviceCreated;
	private long serviceUpdated;
	
	public NotebookImpl(JSONObject json) {
		this.id = json.optString("id");
		this.name = json.optString("name");
		this.usn = json.optInt("usn");
		this.defaultNotebook = json.optBoolean("defaultNotebook");
		this.serviceCreated = json.optLong("serviceCreated");
		this.serviceUpdated = json.optLong("serviceUpdated");
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Notebook#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Notebook#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Notebook#getUsn()
	 */
	@Override
	public int getUsn() {
		return usn;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Notebook#isDefaultNotebook()
	 */
	@Override
	public boolean isDefaultNotebook() {
		return defaultNotebook;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Notebook#getServiceCreated()
	 */
	@Override
	public long getServiceCreated() {
		return serviceCreated;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Notebook#getServiceUpdated()
	 */
	@Override
	public long getServiceUpdated() {
		return serviceUpdated;
	}
	
	
	
}
