package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.leafclient.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

@Mixin(RenderBlocks.class)
public class MixinRenderBlocks {
	
	@Inject(method = "renderBlockByRenderType", at = @At("HEAD"), cancellable = true)
	private void clearGlass(Block p_147805_1_, int p_147805_2_, int p_147805_3_, int p_147805_4_, CallbackInfoReturnable<?> info) {
		if(Client.getInstance().modmanager.clearGlass) {
            if(p_147805_1_.getUnlocalizedName().equals("tile.glass") || p_147805_1_.getUnlocalizedName().equals("tile.thinGlass")) {
                info.cancel();
            }
        }
	}
}