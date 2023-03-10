package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.Client;

import net.minecraft.client.renderer.InventoryEffectRenderer;

@Mixin(InventoryEffectRenderer.class)
public class MixinInventoryEffectRenderer {

	@Shadow
	private boolean hasActivePotionEffects;
	
	/**
	 * @author Lefiy
	 * @reason Overwrite updateActivePotionEffects Method
	 */
	@Overwrite
	protected void updateActivePotionEffects() {
		hasActivePotionEffects = !Client.getInstance().modmanager.potion.isEnable();
	}
}