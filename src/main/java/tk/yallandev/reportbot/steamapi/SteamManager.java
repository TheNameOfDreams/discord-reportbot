package tk.yallandev.reportbot.steamapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.CommonGeneral;

public class SteamManager {
    
    public JsonElement request(RequestUrl requestUrl, String steamId) {
        String formattedUrl = "http://api.steampowered.com/" + requestUrl.getUrlComponent() + "?key=" + CommonGeneral.getInstance().getSteamWebAPIKey() + "&steamid=" + steamId;
        
        try {
            URLConnection connection = new URL(formattedUrl).openConnection();
            JsonElement jsonElement = CommonConst.PARSER.parse(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @AllArgsConstructor
    @Getter
    public enum RequestUrl {
        
        PLAYER_SUMMARY("/ISteamUser/GetPlayerSummaries/v0002/");
        
        private String urlComponent;
        
////        private String urlInitial;
//        private String apiVersion;
        
    }

}
