package me.bomb.shaderapply;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ShaderApply extends JavaPlugin {
	public void onEnable() {
		PluginCommand playscenecommand = getCommand("shader");
		playscenecommand.setExecutor(new ShaderCommand());
		playscenecommand.setTabCompleter(new ShaderTabCompleter());
	}
}
