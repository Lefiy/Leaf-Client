package com.leafclient.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem extends Render {
	
	@Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;getEntityItem()Lnet/minecraft/item/ItemStack;", ordinal = 1, shift = At.Shift.BEFORE))
	private void overlayScale(EntityItem p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_, CallbackInfo info) {
		if(Client.getInstance().modmanager.overlay.isEnable()) {
			int id = Item.getIdFromItem(p_76986_1_.getEntityItem().getItem());
			if(Client.getInstance().modmanager.overlay.isTarget(id)) {
				GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
				GL11.glScalef(2, 2, 2);
				GL11.glTranslatef(-((float)p_76986_2_), -((float)p_76986_4_), -((float)p_76986_6_));
			} else {
				GL11.glScalef(1, 1, 1);
			}
		}
	}
}