package tk.yallandev.reportbot.steamapi.bot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.common.update.UpdateListener.UpdateEvent;
import tk.yallandev.reportbot.common.update.UpdateListener.UpdateType;
import tk.yallandev.reportbot.discord.log.LogManager;

public class BotList implements UpdateEvent {
	
	private static final long TIME = 5000;

	private List<Bot> botList;
	private File jsonConfig;

	private long lastTime;

	public BotList() {
		botList = new ArrayList<Bot>();

		File file = new File("config.json");

		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		jsonConfig = file;

		lastTime = System.currentTimeMillis();
	}

	public void addToTask(Bot bot) {
		this.botList.add(bot);
		LogManager.getInstance().log("Added report to " + bot.getTarget().getProfileId());
		
		if (lastTime + TIME < System.currentTimeMillis())
			next();
	}

	@Override
	public void update(UpdateType updateType) {
		if (updateType == UpdateType.MINUTE) {
			if (lastTime + TIME < System.currentTimeMillis()) {
				next();
			}
		}
	}

	private void next() {
		if (botList.size() == 0)
			return;

		Bot bot = botList.get(0);

		try {

			PrintWriter writer = new PrintWriter(jsonConfig);
			writer.print(CommonConst.GSON.toJson(bot.getJsonObject()));
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ProcessBuilder pb = new ProcessBuilder("node", "report.js");

			pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			pb.redirectError(ProcessBuilder.Redirect.INHERIT);

			Process p = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		botList.remove(bot);
		lastTime = System.currentTimeMillis();
	}

	public boolean canRequest() {
		return botList.size() == 0 && lastTime + TIME < System.currentTimeMillis();
	}

}
