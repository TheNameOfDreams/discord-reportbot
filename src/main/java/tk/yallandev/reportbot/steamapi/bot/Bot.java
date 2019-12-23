package tk.yallandev.reportbot.steamapi.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.CommonGeneral;
import tk.yallandev.reportbot.steamapi.SteamProfile;
import tk.yallandev.reportbot.steamapi.SteamUser;
import tk.yallandev.reportbot.steamapi.bot.settings.Setting;

@AllArgsConstructor
public class Bot {

	private SteamUser fetcher;

	private String serverId;
	private String matchId;

	private List<SteamUser> altList;
	private SteamProfile target;

	private Setting setting;

	private BotType botType;

	public Bot(SteamUser fetcher, String matchId, List<SteamUser> altList, SteamProfile target, Setting setting,
			BotType botType) {
		this(fetcher, "AUTO", matchId, altList, target, setting, botType);
	}

	public Bot(SteamUser fetcher, String matchId, List<SteamUser> altList, SteamProfile target, BotType botType) {
		this(fetcher, "AUTO", matchId, altList, target, new Setting() {

			@Override
			public Map<String, Integer> toMap() {
				Map<String, Integer> map = new HashMap<>();

				map.put("aimbot", 15);
				map.put("wallhack", 15);
				map.put("speedhack", 15);

				return map;
			}

		}, botType);
	}

	public JsonElement sendRequest() {
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("type", botType.name());
		
		if (fetcher != null)
			jsonObject.addProperty("fetcher", CommonConst.GSON.toJson(fetcher));
		
		jsonObject.addProperty("method", serverId == null ? "AUTO" : "SERVER");
		jsonObject.addProperty("target", target.getProfileUrl());
		jsonObject.addProperty("serverID", serverId);
		jsonObject.addProperty("matchId", matchId == null ? "0" : matchId);
		jsonObject.addProperty("perChunk", 100);
		jsonObject.addProperty("betweenChunks", 1000);
		jsonObject.addProperty("cooldown", 28800000);
		jsonObject.addProperty("steamWebAPIKey", CommonGeneral.getInstance().getSteamWebAPIKey());
		
		try {
			URL url = new URL("https://127.0.0.1/");
	        URLConnection urlConnection = url.openConnection();
	        
	        BufferedReader buffered = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        
	        JsonElement response = CommonConst.PARSER.parse(buffered);
	        
	        return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
