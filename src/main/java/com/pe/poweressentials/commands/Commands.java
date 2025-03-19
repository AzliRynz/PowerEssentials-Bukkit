package com.pe.poweressentials.commands;

import com.pe.poweressentials.i18n.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Commands implements CommandExecutor {

    public static final String PREFIX_PERMISSION = "poweressentials.";
    private String prefix;
    private final JavaPlugin plugin;
    private final Lang lang;

    public Commands(JavaPlugin plugin) {
        this.plugin = plugin;
        this.lang = Lang.fromConsole(plugin);
        this.prefix = ChatColor.GOLD + lang.translateString("prefix", "default", "", "", "");
    }

    public void setPrefix(String sectionLang) {
        this.prefix = ChatColor.GOLD + lang.translateString("prefix", sectionLang, "", "", "");
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PREFIX_PERMISSION + command.getName())) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return false;
        }
        return this.run(sender, this.prefix, lang, args);
    }

    public abstract boolean run(CommandSender sender, String prefix, Lang lang, String[] args);
}