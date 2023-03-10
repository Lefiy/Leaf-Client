package com.leafclient.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.leafclient.Client;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class MixinEntity {
	
	@Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
	private void setBrightness(CallbackInfoReturnable<Float> info) {
		if(Client.getInstance().modmanager.fullbright) {
			info.setReturnValue(1.0F);
		} else {
			info.setReturnValue(info.getReturnValue());
		}
	}
	
	@Inject(method = "getBrightnessForRender", at = @At("RETURN"), cancellable = true)
	private void setBrightnessRender(CallbackInfoReturnable<Integer> info) {
		if(Client.getInstance().modmanager.fullbright) {
			info.setReturnValue(200);
		} else {
			info.setReturnValue(info.getReturnValue());
		}
	}
}