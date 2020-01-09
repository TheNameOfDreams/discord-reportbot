package tk.yallandev.reportbot.steamapi.check.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.common.update.UpdateListener.UpdateEvent;
import tk.yallandev.reportbot.common.update.UpdateListener.UpdateType;
import tk.yallandev.reportbot.steamapi.SteamProfile;

public class CheckManager implements UpdateEvent {
	
	private List<String> steamUrlList;
	
	private File file;
	private long currentTick;
	
	public CheckManager() {
		steamUrlList = new ArrayList<>();
		
		file = new File("steamToVerify.json");

		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		try {
			JsonArray jsonArray = (JsonArray) CommonConst.PARSER.parse(new FileReader(file));
			
			for (int x = 0; x < jsonArray.size(); x++) {
				JsonObject jsonElement = (JsonObject) jsonArray.get(x);
				steamUrlList.add(jsonElement.get("steamUrl").getAsString());
			}
			
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addSteam(SteamProfile steamProfile) {
		try {
			JsonArray jsonArray = (JsonArray) CommonConst.PARSER.parse(new FileReader(file));
			
			JsonObject jsonObject = new JsonObject();
			
			jsonObject.addProperty("steamUrl", steamProfile.getProfileUrl());
			
			jsonArray.add(jsonObject);
			
			PrintWriter writer = new PrintWriter(file);
			writer.print(CommonConst.GSON.toJson(jsonArray));
			writer.close();
			
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(UpdateType updateType) {
		if (updateType == UpdateType.MINUTE) {
			currentTick++;
			
			if (currentTick % 10 == 0)
				check();
		}
	}
	
	public void check() {
	}
	
}
