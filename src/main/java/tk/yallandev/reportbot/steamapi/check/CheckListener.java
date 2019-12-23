package tk.yallandev.reportbot.steamapi.check;

import java.util.ArrayList;
import java.util.List;

import tk.yallandev.reportbot.steamapi.SteamProfile;

public class CheckListener {
	
	private List<CheckEvent> observersMap;
	
	public CheckListener() {
		this.observersMap = new ArrayList<>();
	}
	
	public void registerObserver(CheckEvent checkEvent) {
		this.observersMap.add(checkEvent);
	}
	
	public void unregisterObserver(CheckEvent checkEvent) {
		this.observersMap.remove(checkEvent);
	}
	
	public void call(SteamProfile steamProfile) {
		this.observersMap.forEach(checkEvent -> checkEvent.checking(steamProfile));
	}
	
	public static interface CheckEvent {
		
		void checking(SteamProfile steamProfile);
		
	}
	
}
