package tk.yallandev.reportbot.steamapi.check.manager;

import tk.yallandev.reportbot.common.update.UpdateListener.UpdateEvent;
import tk.yallandev.reportbot.common.update.UpdateListener.UpdateType;

public class CheckManager implements UpdateEvent {
	
	private long currentTick;
	
	public CheckManager() {
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
