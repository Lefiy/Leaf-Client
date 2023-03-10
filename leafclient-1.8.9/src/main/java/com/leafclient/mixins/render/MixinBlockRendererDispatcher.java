package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.leafclient.Client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockRendererDispatcher.class)
public class MixinBlockRendererDispatcher {
	
	@Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
	private void clearGlass(IBlockState state, BlockPos pos, IBlockAccess blockAccess, WorldRenderer worldRendererIn,  CallbackInfoReturnable<?> info) {
		if(Client.getInstance().modmanager.clearGlass) {
			if(state.getBlock().getUnlocalizedName().equals("tile.glass") || state.getBlock().getUnlocalizedName().equals("tile.thinGlass")) {
				info.cancel();
			}
		}
	}
}
