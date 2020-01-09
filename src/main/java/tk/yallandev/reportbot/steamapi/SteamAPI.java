package tk.yallandev.reportbot.steamapi;

public class SteamAPI {
	
	public static boolean isCorrectAccountUrl(String steamUrl) {
		steamUrl = steamUrl.toLowerCase();
		
		//https://steamcommunity.com/id/thenameofdreams
		//https://steamcommunity.com/profiles/76561198078517715
		
		if (steamUrl.startsWith("https://steamcommunity.com/"))
			if (steamUrl.contains("/id/") || steamUrl.contains("/profiles/"))
				return true;
		
		return false;
	}
	
	public static boolean isSteam64(String steamId64) {
		return steamId64.length() == 17 || steamId64.length() == 18;
	}
	
	public static String getUrlFromArgument(String arg) {
		String profileUrl;
		
		if (SteamAPI.isCorrectAccountUrl(arg))
			profileUrl = arg;
		else if (SteamAPI.isSteam64(arg))
			profileUrl = "https://steamcommunity.com/profiles/" + arg + "/";
		else
			profileUrl = "https://steamcommunity.com/id/" + arg + "/";
		
		return profileUrl;
	}

}
