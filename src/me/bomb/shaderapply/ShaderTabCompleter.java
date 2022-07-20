package me.bomb.shaderapply;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ShaderTabCompleter implements TabCompleter {
	@Override
	public ArrayList<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> tabcomplete = new ArrayList<String>();
		if (command.getName().equalsIgnoreCase("shader")) {
			if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.self") || sender.hasPermission("shaderapply.shader.other")) {
				if (args.length == 1) {
					if ("green".startsWith(args[0].toLowerCase()))
						tabcomplete.add("green");
					if ("negative".startsWith(args[0].toLowerCase()))
						tabcomplete.add("negative");
					if ("split".startsWith(args[0].toLowerCase()))
						tabcomplete.add("split");
					if ("reset".startsWith(args[0].toLowerCase()))
						tabcomplete.add("reset");
				} else if (args.length == 2) {
					if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.other")) {
						for (Player tcplayer : Bukkit.getOnlinePlayers()) {
							if (tcplayer.getName().toLowerCase().startsWith(args[1].toLowerCase())) tabcomplete.add(tcplayer.getName());
						}
					}
				}
			}
		}
		return tabcomplete;
	}
}
