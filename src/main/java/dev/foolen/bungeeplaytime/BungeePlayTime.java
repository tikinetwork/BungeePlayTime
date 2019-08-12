package dev.foolen.bungeeplaytime;

import dev.foolen.bungeeplaytime.config.Config;
import dev.foolen.bungeeplaytime.database.DatabaseModule;
import dev.foolen.bungeeplaytime.database.mysql.MySQLModule;
import dev.foolen.bungeeplaytime.playtime.PlayTimeModule;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlayTime extends Plugin {

	private static BungeePlayTime instance;
	
	public static final ComponentBuilder PREFIX = new ComponentBuilder("[").color(ChatColor.DARK_GRAY)
			.append("BungeePlayTime").color(ChatColor.AQUA).append("]").color(ChatColor.DARK_GRAY).append(" ")
			.color(ChatColor.RESET);

	@Override
	public void onEnable() {
		instance = this;
		
		// Load configurations
		new Config();
				
		// Load modules
		new DatabaseModule();
		new PlayTimeModule();
	}
	
	public static BungeePlayTime getInstance() {
		return instance;
	}
	
	@Override
	public void onDisable() {
		// Close database connection
		MySQLModule.getInstance().closeConnection();
	}
}
