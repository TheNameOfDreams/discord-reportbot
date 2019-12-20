package tk.yallandev.reportbot.common.steam;

import lombok.AllArgsConstructor;
import tk.yallandev.reportbot.common.steam.report.ReportSetting;

@AllArgsConstructor
public class SteamUser {
    
    /**
     * Default user settings
     */
    
    private String userName;
    private String password;
    private String sharedSecret;
    
    /**
     * If the User is a fetcher
     */
    
    private boolean askSteamGuard;
    private int maxTries;
    private long tryDelay;
    
    public SteamUser(String userName, String password) {
        this(userName, password, null, false, 0, 0l);
    }
    
    public void login() {
        
    }
    
    public void report(ReportSetting reportSetting) {
        
    }

}
