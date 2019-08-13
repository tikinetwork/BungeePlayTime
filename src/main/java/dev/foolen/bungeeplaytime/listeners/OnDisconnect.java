package dev.foolen.bungeeplaytime.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dev.foolen.bungeeplaytime.BungeePlayTimePlugin;
import dev.foolen.bungeeplaytime.databases.PlayerDB;
import dev.foolen.bungeeplaytime.objects.Player;
import dev.foolen.bungeeplaytime.utils.Logger;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnDisconnect implements Listener {

	private Player player;

	@EventHandler
	public void onPlayerDisconnectEvent(PlayerDisconnectEvent e) {
		// Get current player from saved data array
		player = null;
		BungeePlayTimePlugin.getPlayTimePlayers().forEach(p -> {
			if (p.getUuid() == e.getPlayer().getUniqueId()) {
				player = p;
				return;
			}
		});

		// Remove player from array
		BungeePlayTimePlugin.getPlayTimePlayers().remove(player);

		// Get time of quit
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String loggedOutAt = formatter.format(new Date());

		// Compare times and calculate seconds between
		try {
			Date d1 = formatter.parse(player.getLastLogin());
			Date d2 = formatter.parse(loggedOutAt);

			long diff = d2.getTime() - d1.getTime(); // in milliseconds
			long diffSeconds = diff / 1000 % 60; // in seconds

			Logger.info(e.getPlayer().getName() + " has been online for a total of " + diffSeconds + " seconds.");

			// Add new onlinetime to total playtime
			PlayerDB.savePlayer(e.getPlayer().getUniqueId(), diffSeconds);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
}
