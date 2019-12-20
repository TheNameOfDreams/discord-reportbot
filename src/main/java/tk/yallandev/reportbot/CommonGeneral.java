package tk.yallandev.reportbot;

import lombok.Getter;
import lombok.Setter;
import tk.yallandev.reportbot.common.steam.SteamManager;
import tk.yallandev.reportbot.discord.DiscordGeneral;

@Getter
public class CommonGeneral {
    
    @Getter
    private static CommonGeneral instance;
    
    private DiscordGeneral discordGeneral;
    private SteamManager steamManager;
    
    public String steamWebAPIKey = "";
    
    public CommonGeneral() {
        instance = this;
        
        discordGeneral = new DiscordGeneral();
        steamManager = new SteamManager();
    }
    
}
