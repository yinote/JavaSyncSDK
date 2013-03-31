package yinote.ydep.remote.store.impl;

import org.json.JSONObject;

import yinote.ydep.remote.dto.Notebook;
import yinote.ydep.remote.dto.Tag;

public class TagImpl implements Tag {
	private String id;
	private String name;
	private int usn;
	
	public TagImpl(JSONObject json) {
		this.id = json.optString("id");
		this.name = json.optString("name");
		this.usn = json.optInt("usn");
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Tag#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Tag#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see yinote.ydep.remote.store.impl.Tag#getUsn()
	 */
	@Override
	public int getUsn() {
		return usn;
	}
	
	
}
