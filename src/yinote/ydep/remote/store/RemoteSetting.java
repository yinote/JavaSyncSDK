package yinote.ydep.remote.store;

import yinote.ydep.remote.dto.UserInfo;

public class RemoteSetting {
	
	private String host = null;
	private int port = 80;
	private UserInfo user = null;
	public RemoteSetting(String host, int port, UserInfo user) {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public UserInfo getUser() {
		return user;
	}
	
	
}
