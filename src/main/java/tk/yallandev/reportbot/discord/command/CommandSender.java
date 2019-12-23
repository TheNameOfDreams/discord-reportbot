package tk.yallandev.reportbot.discord.command;

import lombok.Getter;

@Getter
public class CommandSender {

	private User user;
	private MessageChannel messageChannel;
	private Guild guild;

	public CommandSender(User user, MessageChannel messageChannel, Guild guild) {
		this.user = user;
		this.messageChannel = messageChannel;
		this.guild = guild;
	}

	public boolean isPlayer() {
		return !user.get
	}


}