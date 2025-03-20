package com.pe.poweressentials.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.pe.poweressentials.Loader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class UserManager {

	private static final Map<String, UserManager> userManagers = new HashMap<>();

	private final Player player;
	private final Loader loader;
	private final FileConfiguration data;
	private final File dataFile;

	private final UserHomeManager userHomeManager;

	public UserManager(Loader loader, Player player) {
		this.loader = loader;
		this.player = player;

		this.dataFile = new File(loader.getDataFolder(), "player/" + player.getName().toLowerCase() + ".yml");
		if (!dataFile.exists()) {
			try {
				dataFile.getParentFile().mkdirs();
				dataFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.data = YamlConfiguration.loadConfiguration(dataFile);

		this.userHomeManager = new UserHomeManager(this);
	}

	public Player getPlayer() {
		return this.player;
	}

	public Loader getLoader() {
		return this.loader;
	}

	public FileConfiguration getData() {
		return this.data;
	}

	public UserHomeManager getHomeManager() {
		return userHomeManager;
	}

	public void saveData() {
		try {
			this.data.save(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static UserManager getUser(Player player) {
		return getUser(player.getName());
	}

	public static UserManager getUser(String playerName) {
		return userManagers.get(playerName.toLowerCase());
	}

	public static void registerUser(Loader loader, Player player) {
		userManagers.put(player.getName().toLowerCase(), new UserManager(loader, player));
	}

	public static void unregisterUser(Player player) {
		userManagers.remove(player.getName().toLowerCase());
	}
}