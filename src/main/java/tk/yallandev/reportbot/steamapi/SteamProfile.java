package tk.yallandev.reportbot.steamapi;

import lombok.Getter;

@Getter
public class SteamProfile {
	
	private String profileId;
	private String profileCustomUrl;
	
	public String getProfileUrl() {
		return "";
	}
	
	public static void getProfileById(String profileId) {
		
	}
	
	public static void getProfileByUrl(String profileUrl) {
		
	}
	
}
