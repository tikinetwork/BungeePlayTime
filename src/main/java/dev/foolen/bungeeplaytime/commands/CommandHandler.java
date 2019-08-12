package dev.foolen.bungeeplaytime.commands;

import dev.foolen.bungeeplaytime.BungeePlayTime;
import dev.foolen.bungeeplaytime.commands.executors.PlayTimeCommand;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandHandler {
	
	private BungeePlayTime plugin;
	private PluginManager pm;
	
	public CommandHandler(BungeePlayTime plugin) {
		this.plugin = plugin;
		this.pm = this.plugin.getProxy().getPluginManager();
		
		registerCommands();
	}
	
	private void registerCommands() {
		registerPlayerTimeCommand();
	}
	
	private void registerPlayerTimeCommand() {
		String command = "playtime";
		String permission = "bungeeplayertime.playtime";
		String[] aliases = {"pt"};
		
		pm.registerCommand(plugin, new PlayTimeCommand(command, permission, aliases));
	}
	
}
