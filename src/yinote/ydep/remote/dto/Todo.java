package yinote.ydep.remote.dto;


public interface Todo extends Versioned {
	String getTitle();
	long getStartAt();
	long getCompleteAt();
}
