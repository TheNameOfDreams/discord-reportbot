package tk.yallandev.reportbot.steamapi.bot.settings;

import java.util.HashMap;
import java.util.Map;

public class ReportSetting implements Setting {
    
    /**
     * 
     * Hack Setting
     * 
     */
    
    private int aimbot;
    private int wallhack;
    private int speedhack;
    
    /**
     * 
     * Other Setting
     * 
     */
    
    private int teamharm;
    private int textabuse;
    private int voiceabuse;
    
    public ReportSetting(int botCount) {
    	aimbot = botCount;
    	wallhack = botCount;
    	speedhack = botCount;
    	teamharm = botCount;
    	textabuse = botCount;
    	voiceabuse = botCount;
	}
    
	@Override
	public Map<String, Integer> toMap() {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("aimbot", aimbot);
		map.put("wallhack", wallhack);
		map.put("speedhack", speedhack);
		
		map.put("teamharm", teamharm);
		map.put("textabuse", textabuse);
		map.put("voiceabuse", voiceabuse);
		
		return map;
	}
    
}
