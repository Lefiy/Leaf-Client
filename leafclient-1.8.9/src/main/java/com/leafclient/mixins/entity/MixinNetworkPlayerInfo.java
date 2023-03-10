package com.leafclient.mixins.entity;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

@Mixin(NetworkPlayerInfo.class)
public class MixinNetworkPlayerInfo {
	
	@Shadow
	private ResourceLocation locationSkin;
	
	@Shadow
	private boolean playerTexturesLoaded;
	
	@Redirect(method = "getLocationSkin", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/NetworkPlayerInfo;locationSkin:Lnet/minecraft/util/ResourceLocation;", ordinal = 0, opcode = Opcodes.GETFIELD))
	private ResourceLocation setSkin(NetworkPlayerInfo playerinfo) {
		if(Client.getInstance().modmanager.nickhider.isEnable()) {
			if(Client.getInstance().modmanager.nickhider.skin) {
				if(playerinfo.getGameProfile().getId().toString().equals(Minecraft.getMinecraft().thePlayer.getUniqueID().toString())) {
					if(Client.getInstance().modmanager.nickhider.update) {
						this.playerTexturesLoaded = false;
						Client.getInstance().modmanager.nickhider.update = false;
						return null;
					}
				}
			}
		}
		return this.locationSkin;
	}
}