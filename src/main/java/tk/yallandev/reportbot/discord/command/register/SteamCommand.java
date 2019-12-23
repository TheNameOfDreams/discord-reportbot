package tk.yallandev.reportbot.discord.command.register;

import net.dv8tion.jda.api.entities.User;
import tk.yallandev.reportbot.discord.command.CommandArgs;
import tk.yallandev.reportbot.discord.command.CommandClass;
import tk.yallandev.reportbot.discord.command.CommandFramework.Command;

public class SteamCommand implements CommandClass {

	@Command(name = "report")
	public void steamCommand(CommandArgs cmdArgs) {
		if (!cmdArgs.getSender().isPlayer())
			return;
		
		String[] args = cmdArgs.getArgs();
		User user = cmdArgs.getSender().getUser();
		
		if (args.length == 0) {
			cmdArgs.getSender().reply("Use " + cmdArgs.getPrefix() + "report <steamId64> para reportar um usuário");
			return;
		}
		
		
	}

}
