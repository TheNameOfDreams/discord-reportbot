package tk.yallandev.reportbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class CommonConst {
    
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonParser PARSER = new JsonParser();
    
    public static final String BOT_TOKEN = "NjE1NjMyNzAyMjU4ODA2ODA5.XgCxhQ.CzfLlwPKPWXzxFICPXx0fn4hOrA";
    
    public static final String PREFIX = "!";
}
