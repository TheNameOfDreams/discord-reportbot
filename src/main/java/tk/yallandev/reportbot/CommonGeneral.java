package tk.yallandev.reportbot;

import com.google.gson.JsonObject;

import lombok.Getter;
import tk.yallandev.reportbot.common.update.UpdateListener;
import tk.yallandev.reportbot.discord.DiscordGeneral;
import tk.yallandev.reportbot.steamapi.SteamManager;
import tk.yallandev.reportbot.steamapi.bot.BotList;
import tk.yallandev.reportbot.steamapi.check.CheckListener;

@Getter
public class CommonGeneral {
    
    @Getter
    private static CommonGeneral instance;
    
    private JsonObject configuration;
    private UpdateListener updateListener;
    
    private DiscordGeneral discordGeneral;
    private SteamManager steamManager;
    private BotList botList;
    
    private CheckListener checkListener;
    
    private String steamWebAPIKey = "541C674D56313586F7E3A2D6A5EB885C";
    
    public static void main(String[] args) {
		new CommonGeneral(args);
	}
    
    public CommonGeneral(String[] args) {
        instance = this;
        
        configuration = new JsonObject();
        
        JsonObject report = new JsonObject();
        
        report.addProperty("thread-category", 615635554448441355l);
        
        configuration.add("report", report);
        
        (updateListener = new UpdateListener()).start();
        
        discordGeneral = new DiscordGeneral();
        steamManager = new SteamManager();
        updateListener.registerObserver(botList = new BotList());
        
        checkListener = new CheckListener();
    }
    
}
