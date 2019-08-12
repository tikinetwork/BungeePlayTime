package dev.foolen.bungeeplaytime.playtime;

import dev.foolen.bungeeplaytime.BungeePlayTime;
import dev.foolen.bungeeplaytime.playtime.command.PlayTimeCommand;
import net.md_5.bungee.api.plugin.PluginManager;

public class PlayTimeModule {

	public PlayTimeModule() {
		registerCommand();
	}
	
	private void registerCommand() {
		BungeePlayTime plugin = BungeePlayTime.getInstance();
		PluginManager pm = plugin.getProxy().getPluginManager();
		
		pm.registerCommand(plugin, new PlayTimeCommand("playtime", "bungeeplaytime.playtime", new String[]{"pt"}));
	}
	
}
