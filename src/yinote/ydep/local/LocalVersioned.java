package yinote.ydep.local;

import yinote.ydep.remote.dto.Versioned;

public interface LocalVersioned extends Versioned {
	public boolean isDirty();
	public boolean isExpunged();
}
