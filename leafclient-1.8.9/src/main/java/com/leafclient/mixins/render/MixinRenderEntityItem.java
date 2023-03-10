package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.leafclient.Client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem extends Render<EntityItem> {
	
	protected MixinRenderEntityItem(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityItem entity) {
		return TextureMap.locationBlocksTexture;
	}
	
	@Redirect(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0))
	private void callItemRender(float angle, float x, float y, float z) {
		if(Client.getInstance().modmanager.customItem) {
			GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		} else {
			GlStateManager.rotate(angle, x, y, z);
		}
	}
	
	@Inject(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/IBakedModel;isGui3d()Z", shift = At.Shift.BEFORE))
	private void overlayScale(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable<?> info) {
		if(Client.getInstance().modmanager.overlay.isEnable()) {
			int id = Item.getIdFromItem(itemIn.getEntityItem().getItem());
			if(Client.getInstance().modmanager.overlay.isTarget(id)) {
				GlStateManager.translate(p_177077_2_, p_177077_4_, p_177077_6_);
				GlStateManager.scale(2, 2, 2);
				GlStateManager.translate(-(p_177077_2_), -(p_177077_4_), -(p_177077_6_));
			} else {
				GlStateManager.scale(1, 1, 1);
			}
		}
	}
}