package dev.foolen.bungeeplaytime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import dev.foolen.bungeeplaytime.commands.CommandHandler;
import dev.foolen.bungeeplaytime.database.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeePlayTime extends Plugin {

	public static final ComponentBuilder PREFIX = new ComponentBuilder("[").color(ChatColor.DARK_GRAY)
			.append("BungeePlayTime").color(ChatColor.AQUA).append("]").color(ChatColor.DARK_GRAY).append(" ")
			.color(ChatColor.RESET);
	private Configuration configuration;

	@Override
	public void onEnable() {
		// Load configurations
		loadConfiguration();
				
		// Load modules
		new MySQL(this);
		new CommandHandler(this);
	}
	
	public Configuration getConfig() {
		return configuration;
	}
	
	private void loadConfiguration() {
		if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");
   
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
