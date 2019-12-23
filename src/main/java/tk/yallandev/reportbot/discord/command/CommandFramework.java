package tk.yallandev.reportbot.discord.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tk.yallandev.reportbot.CommonConst;
import tk.yallandev.reportbot.discord.DiscordGeneral;

public class CommandFramework {

	private final Map<String, Entry<Method, Object>> commandMap = new HashMap<String, Entry<Method, Object>>();
	private DiscordGeneral manager;

	public CommandFramework(DiscordGeneral manager) {
		this.manager = manager;
		this.manager.getJda().addEventListener(new CommandRegister());
    }

    public boolean handleCommand(CommandSender sender, String label, String[] args, MessageChannel textChannel,
            Guild guild) {
        for (int i = args.length; i >= 0; i--) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(label.toLowerCase());

            for (int x = 0; x < i; x++) {
                buffer.append(".").append(args[x].toLowerCase());
            }

            String cmdLabel = buffer.toString();
            String commandPrefix = CommonConst.PREFIX;

            if (commandMap.containsKey(cmdLabel.replace(commandPrefix, ""))) {
                Entry<Method, Object> entry = commandMap.get(cmdLabel.replace(commandPrefix, ""));
                Command command = entry.getKey().getAnnotation(Command.class);

//                if (command.onlyChat())
//                    if (CommonGeneral.getGuildCommon().getGuildConfiguration(guild)
//                            .hasChannel(GuildConfiguration.ChannelType.COMMAND)) {
//                        if (textChannel.getIdLong() != CommonGeneral.getGuildCommon().getGuildConfiguration(guild)
//                                .getChannelId(GuildConfiguration.ChannelType.COMMAND)) {
//                            TextChannel channel = manager.getJda().getTextChannelById(CommonGeneral.getGuildCommon()
//                                    .getGuildConfiguration(guild).getChannelId(GuildConfiguration.ChannelType.COMMAND));
//
//                            if (channel != null) {
//                                // MessageUtils.sendMessage(event.getChannel(), "Você não só pode executar
//                                // comandos no canal ``" + textChannel.getName() + "``.", 5);
//                                return true;
//                            }
//                        }
//                    }

                if (command.runAsync()) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                entry.getKey().invoke(entry.getValue(), new CommandArgs(sender, label, args, cmdLabel
                                        .split("\\.").length - 1, textChannel, guild));
                            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
                } else {
                    try {
                        entry.getKey().invoke(entry.getValue(), new CommandArgs(sender, label, args, cmdLabel
                                .split("\\.").length - 1, textChannel, guild));
                    } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        }

        defaultCommand(new CommandArgs(sender, label, args, 0, textChannel, guild));
        return true;
    }

    public void registerCommands(CommandClass cls) {
        for (Method m : cls.getClass().getMethods()) {
            if (m.getAnnotation(Command.class) != null) {
                Command command = m.getAnnotation(Command.class);

                if (m.getParameterTypes().length > 1 || m.getParameterTypes().length <= 0 || !CommandArgs.class
                        .isAssignableFrom(m.getParameterTypes()[0])) {
                    System.out.println("Unable to register command " + m.getName() + ". Unexpected method arguments");
                    continue;
                }

                registerCommand(command, command.name(), m, cls);

                for (String alias : command.aliases()) {
                    registerCommand(command, alias, m, cls);
                }
            }
        }
    }

    private void registerCommand(Command command, String label, Method m, Object obj) {
        Entry<Method, Object> entry = new AbstractMap.SimpleEntry<Method, Object>(m, obj);
        commandMap.put(label.toLowerCase(), entry);
        System.out.println("The command " + label + " (" + command.name() + ") has been registred!");
    }

    private void defaultCommand(CommandArgs args) {
        // args.getSender().reply("o comando " + args.getLabel() + " não existe!", 5);
    }

    class CommandRegister extends ListenerAdapter {

        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            if (manager.getJda().getSelfUser().getId().equals(event.getAuthor().getId()))
                return;

            String commandPrefix = CommonConst.PREFIX;

            if (!event.getMessage().getContentDisplay().startsWith(commandPrefix))
                return;

            if (event.getChannel() instanceof PrivateChannel) {
//                MessageUtils.sendMessage(event.getChannel(), "Por enquanto não é permitido enviar nenhum comando no meu privado.");
                return;
            }

            String[] txt = event.getMessage().getContentDisplay().trim().split(" ");
            String[] args = new String[txt.length - 1];
            
            for (int i = 1; i < txt.length; i++) {
                args[i - 1] = txt[i];
            }

            handleCommand(new CommandSender(event.getAuthor(), event.getTextChannel(), event.getGuild()), txt[0], args,
                    event.getTextChannel(), event.getGuild());
            super.onMessageReceived(event);
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Command {

        String name();

        String[] aliases() default {};

        String description() default "";

        String usage() default "";

        boolean onlyGuild() default true;

        boolean onlyChat() default true;

        boolean runAsync() default false;

    }
}