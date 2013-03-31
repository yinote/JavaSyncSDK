package yinote.ydep.remote.dto;

public class UserInfo {
	private String accessToken;

	
	
	public UserInfo(String accessToken) {
		super();
		this.accessToken = accessToken;
	}



	public String getAccessToken() {
		return accessToken;
	}
	
}
