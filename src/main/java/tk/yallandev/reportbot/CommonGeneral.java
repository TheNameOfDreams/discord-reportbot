package tk.yallandev.reportbot;

import lombok.Getter;
import tk.yallandev.reportbot.discord.DiscordGeneral;
import tk.yallandev.reportbot.steamapi.SteamManager;
import tk.yallandev.reportbot.steamapi.check.CheckListener;

@Getter
public class CommonGeneral {
    
    @Getter
    private static CommonGeneral instance;
    
    private DiscordGeneral discordGeneral;
    private SteamManager steamManager;
    
    private CheckListener checkListener;
    
    public String steamWebAPIKey = "";
    
    public CommonGeneral() {
        instance = this;
        
        discordGeneral = new DiscordGeneral();
        steamManager = new SteamManager();
        
        checkListener = new CheckListener();
    }
    
}
