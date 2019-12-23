package tk.yallandev.reportbot.discord.thread;

import java.util.ArrayList;
import java.util.Arrays;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import tk.yallandev.reportbot.CommonGeneral;

public class Thread {

	private Member member;
	private Guild guild;
	private MessageChannel messageChannel;

	public Thread(Member member, Guild guild, String threadName) {
		this.member = member;
		this.guild = guild;
		
		Category category = guild.getCategoryById(CommonGeneral.getInstance().getConfiguration().get("report").getAsJsonObject().get("thread-category").getAsLong());
		ChannelAction<TextChannel> action = guild.createTextChannel(threadName).setSlowmode(5).setParent(category).addPermissionOverride(member, Arrays.asList(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), new ArrayList<>());
		
		this.messageChannel = action.complete();
		
		CommonGeneral.getInstance().getDiscordGeneral().getJda().addEventListener(new ListenerAdapter() {
			
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				if (event.getChannel().getIdLong() != messageChannel.getIdLong())
					return;
				
//				if ()
			}
			
		});
	}

	public interface ThreadMessageEvent {

		void onMessage();

	}

}
