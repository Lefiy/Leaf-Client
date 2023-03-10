package com.leafclient.mixins.entity;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.utils.DBUtil;
import com.leafclient.utils.HypixelUser;
import com.leafclient.utils.HypixelUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
	
	@Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/World;Lnet/minecraft/client/network/NetHandlerPlayClient;Lnet/minecraft/stats/StatFileWriter;)V", at = @At("RETURN"))
	private void addUserList(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile, CallbackInfo info) {
		Client.getInstance().users.clear();
		Client.getInstance().modmanager.hypixel.loaded.clear();
		String uuid = netHandler.getGameProfile().getId().toString();
		if(uuid != null) {
			HypixelUtil load = new HypixelUtil(uuid) {
				@Override
				public void result(int level, int karma, String rank, int sky, int bed, int uhc, int skywin, int bedwin, int uhcwin, int duelwin) {
					HypixelUser user = new HypixelUser(uuid, level, karma, rank, sky, bed, uhc, skywin, bedwin, uhcwin, duelwin);
					Client.getInstance().modmanager.hypixel.loaded.add(user);
				}
			}; load.start();
			DBUtil database = new DBUtil(uuid);
			database.online();
		}
	}
	
	@Redirect(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;sprintingTicksLeft:I", opcode = Opcodes.GETFIELD))
	private int setSprint(EntityPlayerSP entity) {
		if(Client.getInstance().modmanager.sprint.isEnable()) {
			entity.setSprinting(true);
		}
		return entity.sprintingTicksLeft;
	}
	
	@Redirect(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerCapabilities;allowFlying:Z", ordinal = 1, opcode = Opcodes.GETFIELD))
	private boolean setFlySpeed(PlayerCapabilities player) {
		if(Client.getInstance().modmanager.sprint.flyspeed) {
			player.setFlySpeed(0.1F);
    	} else {
    		player.setFlySpeed(0.05F);
    	}
		return player.allowFlying;
	}
}