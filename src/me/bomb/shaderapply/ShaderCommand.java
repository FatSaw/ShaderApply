package me.bomb.shaderapply;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.bomb.shaderapply.internal.Shader;
import me.bomb.shaderapply.internal.ShaderType;

public class ShaderCommand implements CommandExecutor {
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
						case "reset":
							shadertype = ShaderType.RESET;
							break;
						}
						if(shadertype==null) {
							sender.sendMessage("Unknown shader name!");
						} else {
							if(sender instanceof Player && ((Player) sender).getUniqueId().equals(target.getUniqueId())) {
								if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.self")) {
									Shader.applyShader(target, shadertype);
									sender.sendMessage("Shader applied!");
								} else {
									sender.sendMessage("No permission to apply shader for self!");
								}
							} else {
								if(sender.hasPermission("shaderapply.shader") || sender.hasPermission("shaderapply.shader.other")) {
									Shader.applyShader(target, shadertype);
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
