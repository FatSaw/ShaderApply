package me.bomb.shaderapply.internal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Shader {
	private static final Shader shader;
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
	public static final void applyShader(Player player,ShaderType type) {
		shader.reset(player);
		if(!ShaderType.RESET.equals(type)) shader.apply(player, type);
	}
	protected abstract void apply(Player player, ShaderType type);
	protected abstract void reset(Player player);
}
