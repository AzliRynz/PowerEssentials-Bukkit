package com.pe.poweressentials.i18n;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.pe.poweressentials.utils.StringArrayMultiton;

public class Lang extends StringArrayMultiton {

	public static final String LANGUAGE_EXTENSION = "yml";
	public static final String FALLBACK_LANGUAGE = "en_us";

	private static String consoleLocale = FALLBACK_LANGUAGE;
	private static String defaultSection = "messages";
	private FileConfiguration langConfig;
	private final JavaPlugin plugin;

	public Lang(JavaPlugin plugin) {
		super(consoleLocale);
		this.plugin = plugin;
		loadLanguageFile(consoleLocale);
	}

	private void loadLanguageFile(String locale) {
		File langFile = new File(plugin.getDataFolder(), "lang/" + locale + "." + LANGUAGE_EXTENSION);

		if (!langFile.exists()) {
			plugin.saveResource("lang/" + FALLBACK_LANGUAGE + "." + LANGUAGE_EXTENSION, false);
		}

		this.langConfig = YamlConfiguration.loadConfiguration(langFile);
	}

	public String getLang() {
		return consoleLocale;
	}

	public String translateString(String section, String key, String param1, String param2, String param3) {
		String path = section + "." + key;
		String message = langConfig.getString(path, key);
		if (message != null) {
			message = message.replace("%0", param1 != null ? param1 : "null")
							 .replace("%1", param2 != null ? param2 : "null")
							 .replace("%2", param3 != null ? param3 : "null");
		}
		return message;
	}

	public String translateString2(String section, String key, String... params) {
		String path = section + "." + key;
		String message = langConfig.getString(path, key);
		if (message != null) {
			for (int i = 0; i < params.length; i++) {
				message = message.replace("%" + i, params[i] == null ? "null" : params[i]);
			}
		}
		return message;
	}

	public static void setConsoleLocale(String locale) {
		consoleLocale = locale;
	}

	public static Lang fromConsole(JavaPlugin plugin) {
		Lang instance = (Lang) getInstance(consoleLocale);
		if (instance == null) {
			instance = new Lang(plugin);
		}
		return instance;
	}
}