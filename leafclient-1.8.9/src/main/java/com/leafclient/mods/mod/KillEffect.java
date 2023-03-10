package com.leafclient.mods.mod;

import java.util.List;
import java.util.Random;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFirework.SparkFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class KillEffect extends Mod {
	
	public boolean hide, bypass, killToggle, recent;
	
	public String target, effect;

	public KillEffect() {
		super("KillEffect", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("KillEffect", "enable")), false);
		
		this.target = "";
		this.killToggle = false;
		
		this.hide = Boolean.valueOf(Client.getInstance().setting.reader("KillEffect", "hide_cadaver"));
		this.bypass = Boolean.valueOf(Client.getInstance().setting.reader("KillEffect", "bypass"));
		this.recent = Boolean.valueOf(Client.getInstance().setting.reader("KillEffect", "recentcolor"));
		
		this.effect = Client.getInstance().setting.reader("KillEffect", "effect");
	}
	
	public void toggle(Entity entity) {
		
		if(!isEnable()) return;
		
		if(!isToggle(entity)) return;
			
		if(hide) Minecraft.getMinecraft().getNetHandler().handleDestroyEntities(new S13PacketDestroyEntities(new int[] {entity.getEntityId()}));
			
		switch(effect) {
		case "NONE":
			break;
		case "BLOOD":
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 152);
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 152);
			break;
		case "LIGHTNING":
			Minecraft.getMinecraft().getNetHandler().handleSpawnGlobalEntity(new S2CPacketSpawnGlobalEntity(new EntityLightningBolt(entity.worldObj, entity.posX, entity.posY, entity.posZ)));
			Minecraft.getMinecraft().thePlayer.playSound("ambient.weather.thunder", 1.0F, 1.0F);
			break;
		case "EXPLOSION":
			Minecraft.getMinecraft().thePlayer.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX, entity.posY, entity.posZ, 0.0D, 0.0D, 0.0D);
			Minecraft.getMinecraft().thePlayer.playSound("random.explode", 1.0F, 1.0F);
			break;
		case "METAL":
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 57);
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 41);
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 42);
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 133);
			Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land", 1.0F, 1.0F);
			break;
		case "FIREWORK":
			this.createFirework(entity.worldObj, entity.posX, entity.posY, entity.posZ);
			Minecraft.getMinecraft().thePlayer.playSound("fireworks.largeBlast", 1.0F, 1.0F);
			Minecraft.getMinecraft().thePlayer.playSound("fireworks.twinkle_far", 1.0F, 1.0F);
			break;
		case "BURNING":
			this.createFlame(entity.worldObj, entity.posX, entity.posY, entity.posZ);
			Minecraft.getMinecraft().thePlayer.playSound("mob.blaze.death", 1.0F, 1.0F);
			break;
		case "NATURE":
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 16402);
			Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, entity.getPosition().add(0, 1, 0), 28690);
			Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1.0F, 1.0F);
			break;
		}
			
		this.target = "";
	}
	
	private boolean isToggle(Entity entity) {
		
		if(entity instanceof EntityPlayerSP) return false;
		if(!target.equals(entity.getName())) return false;
		if(bypass) return true;
		if(killToggle) return false;
		
		killToggle = true;
		return true;
	}
	
	private void createFirework(World world, double x, double y, double z) {
		
		double speed = 0.25D; int size = 2;
		final Random random = new Random();
		
		EffectRenderer render = Minecraft.getMinecraft().effectRenderer;
		
		Integer[] colors = {14602026, 11743532, 14188952, 6719955, 15435844, 4312372};
		
		if(recent) {
			List<Integer> list = Client.getInstance().modmanager.recent_color;
			colors = list.toArray(new Integer[list.size()]);
		}
		
		
		for (int xm = -size; xm <= size; ++xm) {
            for (int ym = -size; ym <= size; ++ym) {
                for (int zm = -size; zm <= size; ++zm) {
                    double xv = (double)ym + (random.nextDouble() - random.nextDouble()) * 0.5D;
                    double yv = (double)xm + (random.nextDouble() - random.nextDouble()) * 0.5D;
                    double zv = (double)zm + (random.nextDouble() - random.nextDouble()) * 0.5D;
                    double sp = (double)MathHelper.sqrt_double(xv * xv + yv * yv + zv * zv) / speed + random.nextGaussian() * 0.05D;
                    SparkFX fw = new SparkFX(world, x, y, z, xv / sp, yv / sp, zv / sp, render);
                    fw.setTrail(true); fw.setTwinkle(true); int len = random.nextInt(colors.length);
                    fw.setColour(colors[len]); render.addEffect(fw);
                    if (xm != -size && xm != size && ym != -size && ym != size) {
                        zm += size * 2 - 1;
                    }
                }
            }
        }
	}
	
	private void createFlame(World world, double x, double y, double z) {
		
		final Random random = new Random();
		
		double speed = 0.2;
		
		for (int i = 0; i < 50; i++) {
			
            double d6 = random.nextGaussian() * speed;
            double d7 = random.nextGaussian() * speed;
            double d8 = random.nextGaussian() * speed;
            
            world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, d6, d7, d8);
        }
	}
}