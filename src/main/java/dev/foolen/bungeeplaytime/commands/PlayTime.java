package dev.foolen.bungeeplaytime.commands;

import java.util.UUID;

import dev.foolen.bungeeplaytime.BungeePlayTimePlugin;
import dev.foolen.bungeeplaytime.databases.PlayerDB;
import dev.foolen.bungeeplaytime.utils.Logger;
import dev.foolen.bungeeplaytime.utils.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PlayTime extends Command {

	private ProxiedPlayer targetPlayer;

	public PlayTime() {
		super("playtime", "bungeeplaytime.playtime", "");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// Check if looking up own playtime
		if (args.length == 0) {
			// Check if console isn't looking up its own playtime
			if (!(sender instanceof ProxiedPlayer)) {
				Logger.info("A console does not have a playtime!");
				Logger.info("If you want to lookup a player playtime specify a playername.");
			} else {
				// Show own total playtime
				ProxiedPlayer p = (ProxiedPlayer) sender;
				long totalSecondsPlayed = PlayerDB.getTotalSecondsPlayed(p.getUniqueId());

				p.sendMessage(
						new ComponentBuilder("").append(BungeePlayTimePlugin.PREFIX).append("You have been online for ")
								.color(ChatColor.AQUA).append(getFormattedPlayTime(totalSecondsPlayed))
								.color(ChatColor.GRAY).append(".").color(ChatColor.RESET).create());
			}
		} else {
			// Check online time from specified player
			String playername = args[0];
			// Check if player is online
			targetPlayer = null;
			BungeePlayTimePlugin.getInstance().getProxy().getPlayers().forEach(pp -> {
				if (pp.getName().equalsIgnoreCase(playername)) {
					targetPlayer = pp;
				}
			});

			// If not online, find uuid from mojang api
			long totalSecondsPlayed = 0;
			boolean validAccount = true;
			if (targetPlayer == null) {
				UUID uuid = UUIDFetcher.getUUID(playername);
				if (uuid != null) {
					totalSecondsPlayed = PlayerDB.getTotalSecondsPlayed(uuid);
				} else {
					validAccount = false;
				}

			} else {
				totalSecondsPlayed = PlayerDB.getTotalSecondsPlayed(targetPlayer.getUniqueId());
			}

			// Check if sender is console
			if (!(sender instanceof ProxiedPlayer)) {
				if (validAccount) {
					Logger.info(playername + " has been online for " + getFormattedPlayTime(totalSecondsPlayed) + ".");
				} else {
					Logger.info(playername + " does not exist!");
				}
			} else {
				ProxiedPlayer p = (ProxiedPlayer) sender;
				if (validAccount) {
					p.sendMessage(new ComponentBuilder("").append(BungeePlayTimePlugin.PREFIX)
							.append(playername + " has been online for ").color(ChatColor.AQUA)
							.append(getFormattedPlayTime(totalSecondsPlayed)).color(ChatColor.GRAY).append(".")
							.color(ChatColor.RESET).create());
				} else {
					p.sendMessage(new ComponentBuilder("").append(BungeePlayTimePlugin.PREFIX)
							.append(playername + " does not exist!").color(ChatColor.RED).create());
				}
			}
		}
	}

	private String getFormattedPlayTime(long totalSecondsPlayed) {
		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int seconds = (int) (totalSecondsPlayed % SECONDS_IN_A_MINUTE);
		int totalMinutes = (int) (totalSecondsPlayed / SECONDS_IN_A_MINUTE);
		int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
		int hours = totalMinutes / MINUTES_IN_AN_HOUR;

		return hours + " hours " + minutes + " minutes " + seconds + " seconds";
	}
}
