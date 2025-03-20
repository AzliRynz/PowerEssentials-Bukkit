package com.pe.poweressentials.listener;

import com.pe.poweressentials.Loader;
import com.pe.poweressentials.manager.UserManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

	private final Loader loader;

	public EventListener(Loader loader) {
		this.loader = loader;
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UserManager.registerUser(this.loader, player);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UserManager.unregisterUser(player);
	}
}
