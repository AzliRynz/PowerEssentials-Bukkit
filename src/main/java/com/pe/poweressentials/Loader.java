package com.pe.poweressentials;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.pe.poweressentials.commands.FPermCommand;
import com.pe.poweressentials.commands.FlyCommand;
import com.pe.poweressentials.config.Config;
import com.pe.poweressentials.i18n.Lang;
import com.pe.poweressentials.utils.Utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Loader extends JavaPlugin {

    private static Loader instance;
    private final Map<String, CommandExecutor> commandExecutors = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        loadResources();
        registerCommands();
    }

    private void loadResources() {
        Config.init();

        File oldLanguageDir = new File(this.getDataFolder(), "language");
        if (oldLanguageDir.exists() && oldLanguageDir.isDirectory()) {
            Utils.unlinkRecursive(oldLanguageDir);
        }

        try {
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources("language");

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String fileName = new File(resource.getPath()).getName();

                if (!fileName.endsWith("." + Lang.LANGUAGE_EXTENSION)) {
                    continue;
                }

                this.saveResource("lang/" + fileName, false);
            }

            new Lang(this);
            Lang.setConsoleLocale(Config.getLang());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerCommands() {
        commandExecutors.put("fly", new FlyCommand(this));
        commandExecutors.put("fperm", new FPermCommand(this));

        for (Map.Entry<String, CommandExecutor> entry : commandExecutors.entrySet()) {
            if (!Config.isCommandDisabled(entry.getKey())) {
                getCommand(entry.getKey()).setExecutor(entry.getValue());
            }
        }
    }

    public static Loader getInstance() {
        return instance;
    }
}