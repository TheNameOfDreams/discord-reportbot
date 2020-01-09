package tk.yallandev.reportbot.discord.thread;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import tk.yallandev.reportbot.CommonGeneral;

public class Thread {

	private Member member;
	private Guild guild;
	private MessageChannel messageChannel;
	private ListenerAdapter listenerAdapter;

	private ThreadMessageEvent threadMessageEvent;
	private Message lastMessage;
	private int times = 1;

	public Thread(Member member, Guild guild, String threadName, String message,
			ThreadMessageEvent threadMessageEvent) {
		this.member = member;
		this.guild = guild;
		this.threadMessageEvent = threadMessageEvent;

		Category category = guild.getCategoryById(CommonGeneral.getInstance().getConfiguration().get("report")
				.getAsJsonObject().get("thread-category").getAsLong());
		ChannelAction<TextChannel> action = guild.createTextChannel(threadName).setSlowmode(5).setParent(category)
				.addPermissionOverride(member, Arrays.asList(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE,
						Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY), new ArrayList<>());

		this.messageChannel = action.complete();
		this.lastMessage = this.messageChannel.sendMessage(message).complete();

		CommonGeneral.getInstance().getDiscordGeneral().getJda()
				.addEventListener(listenerAdapter = new ListenerAdapter() {

					@Override
					public void onMessageReceived(MessageReceivedEvent event) {
						if (event.getChannel().getIdLong() != messageChannel.getIdLong())
							return;
						
						if (event.getAuthor().getIdLong() != member.getUser().getIdLong())
							return;
						
						if (event.getMessage().getContentDisplay().equalsIgnoreCase("cancel")
								|| event.getMessage().getContentDisplay().equalsIgnoreCase("cancelar")) {
							event.getTextChannel().sendMessage("").complete();
							return;
						}
						
						Response response = threadMessageEvent.onMessage(event.getMessage(), lastMessage,
								event.getAuthor(), event.getChannel(), event.getGuild(), times);
						
						if (response.isStopThread()) {
							stopThread(false);
							return;
						}
						
						if (response.isCancelThread()) {
							event.getMessage().delete().complete();
						} else {
							event.getMessage().delete().complete();
							lastMessage.editMessage(response.getMessage()).complete();
							times += 1;
						}
					}

				});

		CommonGeneral.getInstance().getDiscordGeneral().getThreadManager().addThread(member.getIdLong(), this);
	}

	public MessageChannel stopThread(boolean deleteChannel) {
		if (deleteChannel && messageChannel != null) {
			guild.getGuildChannelById(messageChannel.getIdLong()).delete().complete();
			messageChannel = null;
		}

		if (listenerAdapter != null)
			CommonGeneral.getInstance().getDiscordGeneral().getJda().removeEventListener(messageChannel);

		return messageChannel;
	}
	
	public void clear() {
		for (Message message : messageChannel.getHistory().getRetrievedHistory()) {
			message.delete().complete();
		}
	}
	
	@AllArgsConstructor
	@Getter
	public static class Response {

		private String message;
		private boolean cancelThread;
		private boolean stopThread;
		private boolean removePermission;
		
		public Response(String message, boolean cancelThread, boolean stopThread) {
			this.message = message;
			this.cancelThread = cancelThread;
			this.stopThread = stopThread;
		}
		
		public Response(String message, boolean cancelThread) {
			this.message = message;
			this.cancelThread = cancelThread;
		}

	}

	public interface ThreadMessageEvent {

		Response onMessage(Message message, Message lastMessage, User author, MessageChannel channel, Guild guild,
				int times);

	}

}
