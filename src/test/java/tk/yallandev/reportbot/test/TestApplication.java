package tk.yallandev.reportbot.test;

import java.io.OutputStream;

public class TestApplication {
	
	public static void main(String[] args) {
		ProcessBuilder builder = new ProcessBuilder("C:\\Users\\Allan\\Documents\\ShareX\\Tools\\ffmpeg.exe", "-f", "s16be", "-ar", "48k", "-ac", "2", "-i", "pipe:0", "C:\\Users\\Allan\\Desktop\\eae.wav");
		
		try {
			Process process = builder.start();
			OutputStream outputSteam = process.getOutputStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
