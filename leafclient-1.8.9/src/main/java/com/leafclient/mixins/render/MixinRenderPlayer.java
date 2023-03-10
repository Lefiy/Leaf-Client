package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.cosmetics.Hat;
import com.leafclient.cosmetics.Wing;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity<AbstractClientPlayer> {
	
	@Shadow
	public abstract ModelPlayer getMainModel();
	
	public MixinRenderPlayer(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
		super(renderManagerIn, modelBaseIn, shadowSizeIn);
	}

	@Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V", at = @At("RETURN"))
	private void addPlayerLayer(CallbackInfo info) {
		this.addLayer(new Wing());
		this.addLayer(new Hat(getMainModel().bipedHead));
	}
	
	@Inject(method = "rotateCorpse", at = @At("RETURN"))
	private void onDeathEvent(AbstractClientPlayer bat, float p_77043_2_, float p_77043_3_, float partialTicks, CallbackInfo info) {
		if(!Client.getInstance().modmanager.killeffect.bypass) {
			if(bat.deathTime == 1) Client.getInstance().modmanager.killeffect.toggle(bat);
			if(bat.deathTime == 2) Client.getInstance().modmanager.killeffect.killToggle = false;
		}
	}
}