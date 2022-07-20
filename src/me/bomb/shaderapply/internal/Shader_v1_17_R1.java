package me.bomb.shaderapply.internal;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.network.protocol.game.PacketPlayOutAbilities;
import net.minecraft.network.protocol.game.PacketPlayOutCamera;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.level.biome.BiomeManager;

class Shader_v1_17_R1 extends Shader {
	@Override
	public void apply(Player player, ShaderType type) {
		EntityPlayer entityplayer = ((CraftPlayer) player).getHandle();
		Location loc = player.getLocation();
		EntityLiving entity = null;
		switch (type) {
		case GREEN:
			entity = new EntityCreeper(EntityTypes.o, entityplayer.t);
			entity.setLocation(loc.getX(), loc.getY() - 1.445, loc.getZ(), loc.getYaw(), loc.getPitch());
			break;
		case NEGATIVE:
			entity = new EntityEnderman(EntityTypes.w, entityplayer.t);
			entity.setLocation(loc.getX(), loc.getY() - 2.55, loc.getZ(), loc.getYaw(), loc.getPitch());
			break;
		case SPLIT:
			entity = new EntitySpider(EntityTypes.aI, entityplayer.t);
			entity.setLocation(loc.getX(), loc.getY() - 0.65, loc.getZ(), loc.getYaw(), loc.getPitch());
			break;
		default: return;
		}
		PlayerConnection connection = entityplayer.b;
		connection.sendPacket(new PacketPlayOutSpawnEntityLiving(entity));
		connection.sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), false));
		connection.sendPacket(new PacketPlayOutCamera(entity));
		connection.sendPacket(new PacketPlayOutRespawn(entityplayer.t.getDimensionManager(), entityplayer.t.getDimensionKey(),BiomeManager.a(entityplayer.t.ae()), entityplayer.d.getGameMode(), entityplayer.d.c(),entityplayer.getWorldServer().isDebugWorld(), entityplayer.getWorldServer().isFlatWorld(), false));
		connection.sendPacket(new PacketPlayOutEntityDestroy(new int[] {entity.getId()}));
		connection.sendPacket(new PacketPlayOutUpdateHealth(entityplayer.getHealth(), entityplayer.getFoodData().getFoodLevel(), entityplayer.getFoodData().getSaturationLevel()));
		connection.sendPacket(new PacketPlayOutAbilities(entityplayer.getAbilities()));
		Iterator<MobEffect> iterator = entityplayer.getEffects().iterator();
        while (iterator.hasNext()) {
			connection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(),iterator.next()));
        }
		entityplayer.bV.updateInventory();
	}
	@Override
	public void reset(Player player) {
		EntityPlayer entityplayer = ((CraftPlayer) player).getHandle();
		entityplayer.b.sendPacket(new PacketPlayOutCamera(entityplayer));
	}
}
