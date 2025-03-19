package com.pe.poweressentials.commands;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.pe.poweressentials.i18n.Lang;

public class FPermCommand extends Commands {

    public FPermCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean run(CommandSender sender, String prefix, Lang lang, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(prefix + ChatColor.YELLOW + "/fperm <plugin>");
            return false;
        }

        String pluginName = args[0];
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            sender.sendMessage(prefix + ChatColor.RED + lang.translateString("messages", "errorPluginNotFound", pluginName, "", ""));
            return false;
        }

        Set<Permission> permissions = new HashSet<>(plugin.getDescription().getPermissions());
        StringBuilder listPerm = new StringBuilder();

        if (permissions.isEmpty()) {
            sender.sendMessage(prefix + ChatColor.RED + lang.translateString("messages", "errorNoPermissions", "", "", ""));
            return true;
        }

        int i = 1;
        for (Permission perm : permissions) {
            listPerm.append("\n").append(ChatColor.GOLD).append(" ")
                    .append(i++).append(". ").append(perm.getName()).append(": ")
                    .append(perm.getDefault());
        }

        sender.sendMessage(prefix + lang.translateString2("messages", "resultFperm",
                plugin.getName(), String.valueOf(permissions.size()), listPerm.toString()));

        return true;
    }
}