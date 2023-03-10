package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

@Mixin(RenderItem.class)
public class MixinRenderItem {
	
	@Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"))
	private boolean isShowEnchant(ItemStack itemstack) {
		return Client.getInstance().modmanager.hideEncha ? false : itemstack.hasEffect();
	}
}