package tk.yallandev.reportbot.discord.log;

import lombok.Getter;
import tk.yallandev.reportbot.CommonConst;

public class LogManager {
	
	@Getter
	private static LogManager instance = new LogManager();
	
	public void log(String message) {
		System.out.println("[" + CommonConst.LOG_PREFIX + " - " + LogType.LOG.name() + "] " + message);
	}
	
	public void warning(String message) {
		System.out.println("[" + CommonConst.LOG_PREFIX + " - " + LogType.WARNING.name() + "] " + message);
	}
	
	public void erro(String message) {
		System.out.println("[" + CommonConst.LOG_PREFIX + " - " + LogType.ERRO.name() + "] " + message);
	}
	
	public enum LogType {
		
		ERRO, WARNING, LOG;
		
	}
	
}
