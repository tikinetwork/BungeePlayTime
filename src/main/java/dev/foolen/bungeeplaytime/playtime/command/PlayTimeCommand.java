package dev.foolen.bungeeplaytime.playtime.command;

import dev.foolen.bungeeplaytime.BungeePlayTime;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class PlayTimeCommand extends Command {

	public PlayTimeCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ComponentBuilder cb = BungeePlayTime.PREFIX.append("Your playtime is: ").color(ChatColor.WHITE).append("1 hour, 24 minutes and 34 seconds").color(ChatColor.GRAY).append(".").color(ChatColor.WHITE);
		sender.sendMessage(cb.create());
	}

}
