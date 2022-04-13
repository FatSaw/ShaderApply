package me.bomb.shaderapply;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ShaderApply extends JavaPlugin {
	public static final Shader shader;
	static {
		switch (Bukkit.getServer().getClass().getPackage().getName().substring(23)) {
		case "v1_18_R2":
			shader = new Shader_v1_18_R2();
			break;
		case "v1_17_R1":
			shader = new Shader_v1_17_R1();
			break;
		default:
			shader = null;
		}
	}
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
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("shader")) {
			if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.self") || sender.hasPermission("shaderapply.shader.other")) {
				if (args.length > 0) {
					Player target = null;
					if (args.length > 1) {
						target = Bukkit.getPlayerExact(args[1]);
					} else if (sender instanceof Player) {
						target = (Player) sender;
					} else {
						sender.sendMessage("This command only for players!");
						return true;
					}
					if(target!=null&&target.isOnline()) {
						ShaderType shadertype = null;
						String reqestedshadername = args[0].toLowerCase();
						switch(reqestedshadername) {
						case "green":
							shadertype = ShaderType.GREEN;
							break;
						case "negative":
							shadertype = ShaderType.NEGATIVE;
							break;
						case "split":
							shadertype = ShaderType.SPLIT;
							break;
						}
						if(shadertype==null) {
							sender.sendMessage("Unknown shader name!");
						} else {
							if(sender instanceof Player && ((Player) sender).getUniqueId().equals(target.getUniqueId())) {
								if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.self")) {
									shader.applyShader(target, shadertype);
									sender.sendMessage("Shader applied!");
								} else {
									sender.sendMessage("No permission to apply shader for self!");
								}
							} else {
								if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.other")) {
									shader.applyShader(target, shadertype);
									sender.sendMessage("Shader applied!");
								} else {
									sender.sendMessage("No permission to apply shader for other!");
								}
							}
						}
					} else {
						sender.sendMessage("Target offline");
					}
				} else {
					sender.sendMessage("Usage: /shader <shader name> [player]");
				}
			} else {
				sender.sendMessage("No permission!");
			}
		}
		return true;
	}
}
