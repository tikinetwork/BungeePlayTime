package dev.foolen.bungeeplaytime.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import dev.foolen.bungeeplaytime.BungeePlayTimePlugin;
import dev.foolen.bungeeplaytime.objects.Player;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnLogin implements Listener {

	@EventHandler
	public void onPostLoginEvent(PostLoginEvent e) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String lastLogin = formatter.format(new Date());

		BungeePlayTimePlugin.getPlayTimePlayers().add(new Player(e.getPlayer().getUniqueId(), lastLogin));
	}
}
