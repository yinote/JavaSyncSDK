package yinote.ydep.remote.store;

import org.json.JSONException;

import yinote.ydep.exception.RequestError;
import yinote.ydep.remote.dto.JSONFormat;
import yinote.ydep.remote.dto.RemoteFormat;
import yinote.ydep.remote.dto.VersionedResult;

public interface RemoteStore {
	VersionedResult create(RemoteFormat obj) throws JSONException, RequestError;
	VersionedResult update(RemoteFormat obj) throws JSONException, RequestError;
	VersionedResult expunge(JSONFormat obj) throws JSONException, RequestError;
}
