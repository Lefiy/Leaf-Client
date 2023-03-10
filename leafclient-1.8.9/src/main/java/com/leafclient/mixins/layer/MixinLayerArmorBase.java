package com.leafclient.mixins.layer;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;

@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase<T extends ModelBase> {
	
	@Shadow
	private boolean skipRenderGlint;
	
	@Redirect(method = "renderLayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/layers/LayerArmorBase;skipRenderGlint:Z", opcode = Opcodes.GETFIELD))
	private boolean isRenderGlint(LayerArmorBase<T> layer) {
		return !skipRenderGlint && Client.getInstance().modmanager.hideEncha;
	}
}