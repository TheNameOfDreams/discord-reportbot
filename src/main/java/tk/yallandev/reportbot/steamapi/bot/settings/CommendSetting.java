package tk.yallandev.reportbot.steamapi.bot.settings;

import java.util.HashMap;
import java.util.Map;

public class CommendSetting implements Setting {
    
    private int friendly;
    private int teaching;
    private int leader;
    
    public CommendSetting(int botCount) {
    	this.friendly = botCount;
    	this.teaching = botCount;
    	this.leader = botCount;
	}
    
	@Override
	public Map<String, Integer> toMap() {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("friendly", friendly);
		map.put("teaching", teaching);
		map.put("leader", leader);
		
		return map;
	}

}
