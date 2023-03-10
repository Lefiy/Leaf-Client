package com.leafclient.mixins.layer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(LayerHeldItem.class)
public class MixinLayerHeldItem {
	
	@Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 1, shift = At.Shift.AFTER))
	private void setAnimation(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale, CallbackInfo info) {
		if(Client.getInstance().modmanager.animation.isEnable()) {
			if(entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer)entitylivingbaseIn).isBlocking()) {
        		GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
        		GlStateManager.rotate(-25.0F, 0.0F, 1.0F, 0.0F);
        		GlStateManager.rotate(10.0F, 1.0F, 0.0F, 0.0F);
        	}
		}
	}
	
	@ModifyArg(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 3), index = 2)
	private float setSneakAnimation(float z) {
		return Client.getInstance().modmanager.animation.isEnable() ? 0.14F : 0.0F;
	}
}