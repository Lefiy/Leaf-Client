package com.leafclient.mixins.entity;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.utils.DBUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.Session;
import net.minecraft.world.World;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
	
	@Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/World;Lnet/minecraft/util/Session;I)V", at = @At("RETURN"))
	private void addUserList(Minecraft p_i1238_1_, World p_i1238_2_, Session p_i1238_3_, int p_i1238_4_, CallbackInfo info) {
		String uuid = p_i1238_3_.getProfile().getId().toString();
		if(uuid != null) {
			DBUtil database = new DBUtil(uuid);
			database.online();
			if(Client.getInstance().modmanager.hitbox.isEnable()) {
				RenderManager.debugBoundingBox = true;
			}
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