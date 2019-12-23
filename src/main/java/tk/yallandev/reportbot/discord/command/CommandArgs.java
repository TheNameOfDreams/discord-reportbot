package tk.yallandev.reportbot.discord.command;

import java.awt.Color;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.TextChannel;
import tk.brooklynofplugins.alphabot.DiscordBOT;
import tk.brooklynofplugins.common.guild.GuildConfiguration.ChannelType;
import tk.brooklynofplugins.common.guild.player.Player;
import tk.yallandev.reportbot.CommonGeneral;

@Getter
public class CommandArgs {

	private final CommandSender sender;
	private final String label;
	private final String[] args;
	private final MessageChannel textChannel;
	private final Guild guild;

	protected CommandArgs(CommandSender sender, String label, String[] args, int subCommand, MessageChannel textChannel, Guild guild) {
		String[] modArgs = new String[args.length - subCommand];
		System.arraycopy(args, subCommand, modArgs, 0, args.length - subCommand);

		StringBuilder buffer = new StringBuilder();
		buffer.append(label);

		for (int x = 0; x < subCommand; x++) {
			buffer.append(".").append(args[x]);
		}

		String cmdLabel = buffer.toString();
		this.sender = sender;
		this.label = cmdLabel;
		this.args = modArgs;
		this.textChannel = textChannel;
		this.guild = guild;
	}

}