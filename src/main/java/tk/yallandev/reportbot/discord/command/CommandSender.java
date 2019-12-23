package tk.yallandev.reportbot.discord.command;

import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

@Getter
public class CommandSender {

	private net.dv8tion.jda.api.entities.User user;
	private MessageChannel messageChannel;
	private Guild guild;

	public CommandSender(User user, MessageChannel messageChannel, Guild guild) {
		this.user = user;
		this.messageChannel = messageChannel;
		this.guild = guild;
	}

	public boolean isPlayer() {
		return !user.isBot();
	}
	
	public Message reply(String string) {
		return reply(string, 0);
	}

	public Message reply(String string, int time) {
		Message message = messageChannel.sendMessage(user.getAsMention() + ", " + string).complete();

		if (time != 0)
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					if (message != null)
						message.delete().complete();
				}
			}, 1000 * time);
		
		return message;
	}

}