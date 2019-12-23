package tk.yallandev.reportbot.common.update;

import java.util.ArrayList;
import java.util.List;

public class UpdateListener extends Thread {
	
	private boolean running;
	private List<UpdateEvent> observersMap;
	
	private long currentTick;
	private long lastTick;
	
	public UpdateListener() {
		running = true;
		observersMap = new ArrayList<>();
	}
	
	public void registerObserver(UpdateEvent updateEvent) {
		this.observersMap.add(updateEvent);
	}
	
	public void unregisterObserver(UpdateEvent updateEvent) {
		this.observersMap.remove(updateEvent);
	}
	
	@Override
	public void run() {
		while (running) {
			if (lastTick + 50l < System.currentTimeMillis()) {
				lastTick = System.currentTimeMillis();
				
				currentTick++;
				notifyAll(UpdateType.TICK);
				
				if (currentTick % 20 == 0) {
					notifyAll(UpdateType.SECOND);
				}
				
				if (currentTick % 1200 == 0) {
					notifyAll(UpdateType.MINUTE);
				}
			}
		}
	}
	
	public void notifyAll(UpdateType updateType) {
		observersMap.forEach(updateEvent -> updateEvent.update(updateType));
	}
	
	public interface UpdateEvent {
		
		void update(UpdateType updateType);
		
	}
	
	public enum UpdateType {
		
		TICK, SECOND, MINUTE;
		
	}
	
	

}
