package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
	
	@Shadow
	@Final
	private Minecraft mc;
	
	@Shadow
	private ItemStack itemToRender;
	
	private float swingProgressSave = 0;
	
	@Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getSwingProgress(F)F"))
	private float swingProgress(AbstractClientPlayer entity, float partialTicks) {
		float swing = entity.getSwingProgress(partialTicks);
		if(Client.getInstance().modmanager.animation.isEnable()) {
			swingProgressSave = swing;
		}
		return swing;
	}
	
	@ModifyArg(method = "transformFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0), index = 2)
	private float setRodAnimation(float z) {
		if(Client.getInstance().modmanager.animation.isEnable()) {
			if(itemToRender.getItem() instanceof ItemFishingRod) {
				return -0.98F;
			}
		}
		return z;
	}
	
	@ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 1)
	private float setAnimationSword(float swingProgress, float partialTicks) {
		return Client.getInstance().modmanager.animation.isEnable() ? swingProgressSave : swingProgress;
	}
	
	@ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 3), index = 1)
	private float setAnimationBow(float swingProgress, float partialTicks) {
		return Client.getInstance().modmanager.animation.isEnable() ? swingProgressSave : swingProgress;
	}
	
	@ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1), index = 1)
	private float setAnimationDrink(float swingProgress, float partialTicks) {
		return Client.getInstance().modmanager.animation.isEnable() ? swingProgressSave : swingProgress;
	}
	
	@Redirect(method = "renderOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isBurning()Z"))
	private boolean showFireOnFirstPerson(EntityPlayerSP player) {
		return Client.getInstance().modmanager.hideFire ? false : player.isBurning();
	}
}