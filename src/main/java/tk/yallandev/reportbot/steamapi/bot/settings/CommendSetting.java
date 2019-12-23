package tk.yallandev.reportbot.steamapi.bot.settings;

import java.util.HashMap;
import java.util.Map;

public class CommendSetting implements Setting {
    
    private int friendly;
    private int teaching;
    private int leader;
    
	@Override
	public Map<String, Integer> toMap() {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("friendly", friendly);
		map.put("teaching", teaching);
		map.put("leader", leader);
		
		return map;
	}

}
