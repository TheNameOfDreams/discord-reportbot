package tk.yallandev.reportbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class CommonConst {
    
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonParser PARSER = new JsonParser();
    
    public static final String BOT_TOKEN = "NjE1NjMyNzAyMjU4ODA2ODA5.XgP4_Q.sVgKQjmz42IyNw_YX1g5ssLaVqo";
    
    public static final long BOT_COOLDOWN = 28800000;
    public static final long BOT_BETWEENCHUNKS = 1000;
    public static final long BOT_PERCHUNK = 100;
    
    public static final String LOG_PREFIX = "REPORTBOT";
    
    public static final String PREFIX = "!";
}
