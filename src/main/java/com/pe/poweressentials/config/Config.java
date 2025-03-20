package com.pe.poweressentials.config;

import com.pe.poweressentials.Loader;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

	private static FileConfiguration config;
	private static final String CONFIG_NEW_VERSION = "1.0";

	public static void init() {
		Loader.getInstance().saveDefaultConfig();
		config = Loader.getInstance().getConfig();
	}

	public static String getNewVersion() {
		return CONFIG_NEW_VERSION;
	}

	public static String getCurrentVersion() {
		return config.getString("config-version", "1.0");
	}

	public static String getLang() {
		return config.getString("lang", "en_us");
	}

	public static boolean isCommandDisabled(String command) {
		List<String> disabledCommands = config.getStringList("disabled-commands");
		return disabledCommands.contains(command.toLowerCase());
	}
}