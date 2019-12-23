package tk.yallandev.reportbot.steamapi.bot;

import java.util.List;

import lombok.AllArgsConstructor;
import tk.yallandev.reportbot.steamapi.SteamProfile;
import tk.yallandev.reportbot.steamapi.SteamUser;

@AllArgsConstructor
public class Bot {
	
	private SteamUser fetcher;
	
	private String serverId;
	private String matchId;
	
	private List<SteamUser> altList;
	private SteamProfile target;
	
	private BotType botType;
	
	public Bot(SteamUser fetcher, String matchId, List<SteamUser> altList, SteamProfile target, BotType botType) {
		this(fetcher, "AUTO", matchId, altList, target, botType);
	}
	
	public void sendRequest() {
		//TODO send
	}
	
}
