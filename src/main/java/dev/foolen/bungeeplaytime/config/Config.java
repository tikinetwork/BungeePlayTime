package dev.foolen.bungeeplaytime.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import dev.foolen.bungeeplaytime.BungeePlayTime;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
	
	private static Configuration config;
	
	public Config() {
		loadConfiguration();
	}

	public static Configuration getConfig() {
		return config;
	}
	
	private void loadConfiguration() {
		BungeePlayTime plugin = BungeePlayTime.getInstance();
		
		if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        File file = new File(plugin.getDataFolder(), "config.yml");
   
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
