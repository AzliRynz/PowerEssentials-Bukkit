package com.pe.poweressentials.commands;

import com.pe.poweressentials.i18n.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyCommand extends Commands {

	public FlyCommand(JavaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean run(CommandSender sender, String prefix, Lang lang, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				boolean isFlying = player.getAllowFlight();
				player.setAllowFlight(!isFlying);

				player.sendMessage(prefix + (isFlying
						? lang.translateString("disabled", "fly", "", "", "")
						: lang.translateString("enabled", "fly", "", "", "")));
			} else {
				sender.sendMessage(prefix + ChatColor.RED + lang.translateString("errorCommandConsole", "", "", "", ""));
				return false;
			}
		} else {
			Player targetPlayer = sender.getServer().getPlayer(args[0]);

			if (targetPlayer == null) {
				sender.sendMessage(prefix + ChatColor.RED + lang.translateString("errorPlayerNotFound", args[0], "", "", ""));
				return false;
			}

			boolean isFlying = targetPlayer.getAllowFlight();
			targetPlayer.setAllowFlight(!isFlying);

			targetPlayer.sendMessage(prefix + (isFlying
					? lang.translateString("disabled", "fly", "", "", "")
					: lang.translateString("enabled", "fly", "", "", "")));

			sender.sendMessage(prefix + (isFlying
					? lang.translateString("otherDisabled", "fly", targetPlayer.getName(), "", "")
					: lang.translateString("otherEnabled", "fly", targetPlayer.getName(), "", "")));
		}
		return true;
	}
}