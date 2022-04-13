package me.bomb.shaderapply;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.network.protocol.game.PacketPlayOutCamera;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.level.biome.BiomeManager;

class Shader_v1_18_R2 extends Shader {
	@Override
	public void applyShader(Player player, ShaderType type) {
		EntityPlayer entityplayer = ((CraftPlayer) player).getHandle();
		Location loc = player.getLocation();
		PlayerConnection connection = entityplayer.b;
		EntityLiving entity = null;
		switch (type) {
		case GREEN:
			entity = new EntityCreeper(EntityTypes.o, entityplayer.s);
			entity.a(loc.getX(), loc.getY() - 1.445, loc.getZ(), loc.getYaw(), loc.getPitch());
			break;
		case NEGATIVE:
			entity = new EntityEnderman(EntityTypes.w, entityplayer.s);
			entity.a(loc.getX(), loc.getY() - 2.55, loc.getZ(), loc.getYaw(), loc.getPitch());
			break;
		case SPLIT:
			entity = new EntitySpider(EntityTypes.aI, entityplayer.s);
			entity.a(loc.getX(), loc.getY() - 0.65, loc.getZ(), loc.getYaw(), loc.getPitch());
			break;
		}
		if (entity != null) {
			connection.a(new PacketPlayOutSpawnEntityLiving(entity));
			connection.a(new PacketPlayOutEntityMetadata(entity.ae(), entity.ai(), false));
			connection.a(new PacketPlayOutCamera(entity));
			connection.a(new PacketPlayOutRespawn(entityplayer.s.Z(), entityplayer.s.aa(),BiomeManager.a(entityplayer.s.ae()), entityplayer.d.b(), entityplayer.d.c(), false, false, true));
			connection.a(new PacketPlayOutEntityDestroy(new int[] {entity.ae()}));
			entityplayer.bV.b();
		}
	}
}
