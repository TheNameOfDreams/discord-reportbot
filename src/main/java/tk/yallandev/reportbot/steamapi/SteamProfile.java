package tk.yallandev.reportbot.steamapi;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tk.yallandev.reportbot.CommonConst;

@Getter
@AllArgsConstructor
public class SteamProfile {

	private String profileId;
	private String avatarIcon;

	private String profileName;
	
	private int vacBanned;

	public SteamProfile() {
		this.profileId = "00000000000000000";
		this.profileName = "unknow//";
	}

	public String getProfileUrl() {
		return "https://steamcommunity.com/id/" + profileId + "/";
	}

	public static SteamProfile getProfileByUrl(String profileUrl) {

		if (profileUrl.endsWith("/"))
			profileUrl += "?xml=1";
		else
			profileUrl += "/?xml=1";

		try {
			URLConnection connection = new URL(profileUrl).openConnection();

			XmlMapper xmlMapper = new XmlMapper();
			JsonElement jsonElement = CommonConst.PARSER
					.parse(xmlMapper.readTree(connection.getInputStream()).toString());

			if (jsonElement instanceof JsonObject) {
				JsonObject jsonObject = (JsonObject) jsonElement;

				return new SteamProfile(jsonObject.get("steamID64").getAsString(),
						jsonObject.get("avatarFull").getAsString(), jsonObject.get("steamID").getAsString(),
						jsonObject.get("vacBanned").getAsInt());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new SteamProfile();
		}

		return new SteamProfile();
	}

}
