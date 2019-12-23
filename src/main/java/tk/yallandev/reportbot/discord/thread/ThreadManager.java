package tk.yallandev.reportbot.discord.thread;

import java.util.HashMap;

public class ThreadManager {
	
	private HashMap<Long, Thread> threadMap;
	
	public ThreadManager() {
		threadMap = new HashMap<>();
	}
	
	public void addThread(Long userId, Thread thread) {
		if (!hasThread(userId))
			threadMap.put(userId, thread);
	}
	
	public boolean hasThread(Long userId) {
		return threadMap.containsKey(userId);
	}
	
	public void removeThread(Long userId) {
		if (hasThread(userId))
			threadMap.remove(userId);
	}

}
