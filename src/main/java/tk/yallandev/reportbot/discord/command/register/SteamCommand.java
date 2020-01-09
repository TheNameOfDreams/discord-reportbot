package tk.yallandev.reportbot.discord.command.register;

import java.awt.Color;
import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import tk.yallandev.reportbot.CommonGeneral;
import tk.yallandev.reportbot.discord.DiscordGeneral;
import tk.yallandev.reportbot.discord.command.CommandArgs;
import tk.yallandev.reportbot.discord.command.CommandClass;
import tk.yallandev.reportbot.discord.command.CommandFramework.Command;
import tk.yallandev.reportbot.steamapi.SteamAPI;
import tk.yallandev.reportbot.steamapi.SteamProfile;
import tk.yallandev.reportbot.steamapi.bot.Bot;
import tk.yallandev.reportbot.steamapi.bot.BotType;
import tk.yallandev.reportbot.steamapi.bot.settings.CommendSetting;
import tk.yallandev.reportbot.steamapi.bot.settings.ReportSetting;

public class SteamCommand implements CommandClass {

	private DiscordGeneral manager;

	public SteamCommand() {
		this.manager = CommonGeneral.getInstance().getDiscordGeneral();
	}

	@Command(name = "steambot")
	public void steamCommand(CommandArgs cmdArgs) {
		if (!cmdArgs.getSender().isPlayer())
			return;

		String[] args = cmdArgs.getArgs();

		if (args.length <= 3) {
			cmdArgs.getSender().reply("utilize " + cmdArgs.getPrefix()
					+ "steambot <steamId64> <serverId> <report:commend> <botCount> para reportar um usuário");
			return;
		}

		if (!Arrays.asList("report", "commend").contains(args[2].toLowerCase())) {
			cmdArgs.getSender().reply("utilize report/commend como tipo de bot.");
			return;
		}

		String profileUrl = SteamAPI.getUrlFromArgument(args[0]);

		String serverId = args[1];

		// TODO CHECK IF SERVERID IS CORRECT

		BotType botType = args[2].equalsIgnoreCase("report") ? BotType.REPORT : BotType.COMMEND;

		Integer botCount = null;

		try {
			botCount = Integer.valueOf(args[3]);
		} catch (Exception ex) {
			return;
		}

		if (botCount == null || botCount <= 0) {
			cmdArgs.getSender().reply("botCount inválido!");
			return;
		}

		Message message = cmdArgs.getTextChannel().sendMessage(new EmbedBuilder().setAuthor("ReportBOT - Query")
				.setDescription("Verificando dados da conta...").setColor(Color.YELLOW).build()).complete();

		SteamProfile steamProfile = SteamProfile.getProfileByUrl(profileUrl);

		if (steamProfile.getProfileId().equalsIgnoreCase("00000000000000000")) {
			message.editMessage(new EmbedBuilder().setAuthor("ReportBOT - Query")
					.setDescription("Não foi possível indentificar a conta inserida!").setColor(Color.RED).build())
					.complete();
			return;
		}

		if (steamProfile.getVacBanned() >= 1) {
			cmdArgs.getSender().reply("Um erro aconteceu!");
			return;
		}

		if (botType == BotType.REPORT) {
			if (botCount > 20)
				botCount = 20;

			CommonGeneral.getInstance().getBotList().addToTask(new Bot(serverId, steamProfile,
					(botType == BotType.REPORT ? new ReportSetting(botCount) : new CommendSetting(botCount)), botType));

			message.editMessage(
					new EmbedBuilder().setAuthor("ReportBOT - Sucesso").appendDescription("Steam Nickname: " + steamProfile.getProfileName() + "\nSteamID: "
									+ steamProfile.getProfileId() + "\n")
							.appendDescription(
									CommonGeneral.getInstance().getBotList().canRequest() ? "Os reports estão sendo enviados!"
											: "Os reports serão enviados assim que possível!")
							.setColor(steamProfile.getVacBanned() == 0 ? Color.GREEN : Color.RED).build())
					.complete();
		} else if (botType == BotType.COMMEND) {
			if (botCount > 20) {
				int times = botCount % 20 == 0 ? botCount / 20 : (botCount / 20) + 1;

				for (int x = 0; x < times; x++) {
					if (botCount >= 20) {
						CommonGeneral.getInstance().getBotList().addToTask(new Bot(serverId, steamProfile,
								(botType == BotType.REPORT ? new ReportSetting(20) : new CommendSetting(20)), botType));
						botCount -= 20;
					} else {
						CommonGeneral.getInstance().getBotList().addToTask(
								new Bot(serverId, steamProfile, (botType == BotType.REPORT ? new ReportSetting(botCount)
										: new CommendSetting(botCount)), botType));
					}
				}
				
				message.editMessage(
						new EmbedBuilder().setAuthor("ReportBOT - Sucesso").appendDescription("Steam Nickname: " + steamProfile.getProfileName() + "\nSteamID: "
										+ steamProfile.getProfileId() + "\n")
								.appendDescription("Todos os seus " + botCount + " elogios serão enviados 20 por 20 a cada 5 minutos.")
								.setColor(steamProfile.getVacBanned() == 0 ? Color.GREEN : Color.RED).build())
						.complete();

			} else {
				CommonGeneral.getInstance().getBotList().addToTask(new Bot(serverId, steamProfile,
						(botType == BotType.REPORT ? new ReportSetting(botCount) : new CommendSetting(botCount)),
						botType));
				
				message.editMessage(
						new EmbedBuilder().setAuthor("ReportBOT - Sucesso").appendDescription("Steam Nickname: " + steamProfile.getProfileName() + "\nSteamID: "
										+ steamProfile.getProfileId() + "\n")
								.appendDescription(
										CommonGeneral.getInstance().getBotList().canRequest() ? "Os elogios estão sendo enviados!"
												: "Os elogios serão enviados assim que possível!")
								.setColor(steamProfile.getVacBanned() == 0 ? Color.GREEN : Color.RED).build())
						.complete();
			}
		}
	}

}
