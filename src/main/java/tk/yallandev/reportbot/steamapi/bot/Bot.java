package tk.yallandev.reportbot.steamapi.bot;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.CommonGeneral;
import tk.yallandev.reportbot.steamapi.SteamProfile;
import tk.yallandev.reportbot.steamapi.SteamUser;
import tk.yallandev.reportbot.steamapi.bot.settings.Setting;

@AllArgsConstructor
@Getter
public class Bot {

	private SteamUser fetcher;

	private String serverId;
	private String matchId;

	private SteamProfile target;

	private Setting setting;

	private BotType botType;

	public Bot(SteamUser fetcher, String matchId, SteamProfile target, Setting setting,
			BotType botType) {
		this(fetcher, "0", matchId, target, setting, botType);
	}
	
	public Bot(String serverId, SteamProfile target, Setting setting, BotType botType) {
		this(null, serverId, "0", target, setting, botType);
	}
	
	public Bot(SteamUser fetcher, String matchId, SteamProfile target, BotType botType) {
		this(fetcher, "0", matchId, target, new Setting() {

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

	public JsonObject getJsonObject() {
		JsonObject jsonObject = new JsonObject();

		Map<String, Integer> map = setting.toMap();
		
		JsonObject json = new JsonObject();
		
		if (botType == BotType.REPORT) {
			json.addProperty("aimbot", map.get("aimbot"));
			json.addProperty("wallhack", map.get("wallhack"));
			json.addProperty("speedhack", map.get("speedhack"));
			json.addProperty("teamharm", map.get("teamharm"));
			json.addProperty("textabuse", map.get("textabuse"));
			json.addProperty("voiceabuse", map.get("voiceabuse"));
		} else {
			json.addProperty("friendly", map.get("friendly"));
			json.addProperty("teaching", map.get("teaching"));
			json.addProperty("leader", map.get("leader"));
		}
		
		jsonObject.add(botType == BotType.REPORT ? "report" : "commend", json);	
		
		if (fetcher != null)
			jsonObject.addProperty("fetcher", CommonConst.GSON.toJson(fetcher));
		
		jsonObject.addProperty("type", botType.name());
		jsonObject.addProperty("method", serverId == null ? "AUTO" : "SERVER");
		jsonObject.addProperty("target", target.getProfileUrl());
		jsonObject.addProperty("serverID", serverId);
		jsonObject.addProperty("matchID", matchId == null ? "0" : matchId);
		jsonObject.addProperty("perChunk", 100);
		jsonObject.addProperty("betweenChunks", CommonConst.BOT_BETWEENCHUNKS);
		jsonObject.addProperty("cooldown", CommonConst.BOT_COOLDOWN);
		jsonObject.addProperty("steamWebAPIKey", CommonGeneral.getInstance().getSteamWebAPIKey());
		
		return jsonObject;
	}
	
}
