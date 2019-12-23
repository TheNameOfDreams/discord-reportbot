package tk.yallandev.reportbot.discord;

import javax.security.auth.login.LoginException;

import lombok.Getter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.discord.command.CommandFramework;
import tk.yallandev.reportbot.discord.command.register.SteamCommand;
import tk.yallandev.reportbot.discord.thread.ThreadManager;

@Getter
public class DiscordGeneral {

	private JDA jda;
	
	private ThreadManager threadManager;

	public DiscordGeneral() {

		try {
			jda = new JDABuilder(AccountType.BOT).setToken(CommonConst.BOT_TOKEN).build().awaitReady();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}

		CommandFramework commandFramework = new CommandFramework(this);

		commandFramework.registerCommands(new SteamCommand());
	}

}
