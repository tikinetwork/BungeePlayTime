package dev.foolen.bungeeplaytime;

import java.util.ArrayList;

import dev.foolen.bungeeplaytime.commands.PlayTime;
import dev.foolen.bungeeplaytime.config.Config;
import dev.foolen.bungeeplaytime.databases.mysql.MySQL;
import dev.foolen.bungeeplaytime.listeners.OnDisconnect;
import dev.foolen.bungeeplaytime.listeners.OnLogin;
import dev.foolen.bungeeplaytime.objects.Player;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeePlayTimePlugin extends Plugin {

	public static final BaseComponent[] PREFIX = TextComponent.fromLegacyText("§8[§3BungeePlayTime§8]§r ");

	private static BungeePlayTimePlugin instance;
	private static ArrayList<Player> playTimePlayers;
	
	private MySQL mysql;

	@Override
	public void onEnable() {
		instance = this;
		playTimePlayers = new ArrayList<Player>();

		// Load config
		new Config();

		// Open database connection
		mysql = new MySQL();
		
		// Register commands and listeners
		registerCommandsAndListeners();
	}

	@Override
	public void onDisable() {
		// Close database connection
		mysql.close();
	}

	public static BungeePlayTimePlugin getInstance() {
		return instance;
	}
	
	public static ArrayList<Player> getPlayTimePlayers() {
		return playTimePlayers;
	}
	
	private void registerCommandsAndListeners() {
		PluginManager pm = getProxy().getPluginManager();
		
		// Listeners
		pm.registerListener(this, new OnLogin());
		pm.registerListener(this, new OnDisconnect());
		
		// Commands
		pm.registerCommand(this, new PlayTime());
	}
}
