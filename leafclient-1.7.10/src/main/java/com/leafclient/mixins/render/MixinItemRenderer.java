package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
	
	@Redirect(method = "renderOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityClientPlayerMP;isBurning()Z"))
    private boolean showFireOnFirstPerson(EntityClientPlayerMP player) {
        return Client.getInstance().modmanager.hideFire ? false : player.isBurning();
    }
}