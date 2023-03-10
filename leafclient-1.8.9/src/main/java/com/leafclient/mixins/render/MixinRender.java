package com.leafclient.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.screen.CosmeticSettings;
import com.leafclient.utils.LeafUser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@Mixin(Render.class)
public class MixinRender<T extends Entity> {
	
	private boolean isRanker = false;
	private String icon = "";
	
	@Shadow
	@Final
	protected RenderManager renderManager;
	
	@Shadow
	protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {};

	@Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0))
	private void callGL11Yaw(float viewY, float v1, float v2, float v3) {
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
			GL11.glRotatef(viewY, v1, v2, v3);
		} else {
			GL11.glRotatef(-Client.getInstance().modmanager.freelook.getPlayerViewY(), v1, v2, v3);
		}
	}
	
	@Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1))
	private void callGL11Pitch(float viewX, float v1, float v2, float v3) {
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
			GL11.glRotatef(-viewX, v1, v2, v3);
		} else {
			GL11.glRotatef(Client.getInstance().modmanager.freelook.getPlayerViewX(), v1, v2, v3);
		}
	}
	
	@Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;getStringWidth(Ljava/lang/String;)I"))
	private int setBackgroundPos(FontRenderer fontrenderer, String text, T entityIn, String str, double x, double y, double z, int maxDistance) {
		if(entityIn instanceof AbstractClientPlayer) {
			if(!Client.getInstance().modmanager.hideIcon) {
				String uuid = entityIn.getUniqueID().toString();
				if(text.contains(entityIn.getDisplayName().getFormattedText()) || text.contains(Client.getInstance().modmanager.nickhider.username)) {
					LeafUser user = null;
					if(entityIn instanceof EntityPlayerSP) {
						icon = Client.getInstance().modmanager.rank; isRanker = true;
						return fontrenderer.getStringWidth(text) + 14;
					} else if((user = Client.getInstance().users.isExistUUID(uuid)) != null) {
						icon = user.getRank();
						isRanker = true;
						return fontrenderer.getStringWidth(text) + 14;
					}
				}
			}
		}
		isRanker = false;
		return fontrenderer.getStringWidth(text);
	}
	
	@Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
	private int callShadowString(FontRenderer fontrenderer, String text, int x, int y, int color, T entityIn, String str, double xa, double ya, double za, int maxDistance) {
		if(isRanker) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/icon/" + icon + ".png"));
        	Gui.drawModalRectWithCustomSizedTexture(x - 1, -2, 11, 11, 11, 11, 11, 11);
		}
		
		int tagColor = (maxDistance == 20 && color != 553648127) ? Client.getInstance().modmanager.hypixel.getColorValue() : color;
		
		if(Client.getInstance().modmanager.nametag.isShadow) {
			return fontrenderer.drawStringWithShadow(text, isRanker ? x + 12 : x, y, tagColor);
		} else {
			return fontrenderer.drawString(text, isRanker ? x + 12 : x, y, tagColor);
		}
	}
	
	@ModifyArg(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;"), index = 3)
	private float setNametagAlpha(float alpha) {
		return Client.getInstance().modmanager.nametag.back ? 0.0F : 0.25F;
	}
	
	@ModifyVariable(method = "renderLivingLabel", at = @At("HEAD"), ordinal = 0)
	private String changeName(String str) {
		if(Client.getInstance().modmanager.nickhider.isEnable() && Minecraft.getMinecraft().theWorld != null) {
			if(str.contains(Minecraft.getMinecraft().thePlayer.getName())) {
    			return str.replace(Minecraft.getMinecraft().thePlayer.getName(), Client.getInstance().modmanager.nickhider.username);
    		}
		}
		return str;
	}
	
	@Inject(method = "doRender", at = @At("RETURN"))
	private void showNameTag(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
		if(entity instanceof EntityPlayerSP && Client.getInstance().modmanager.nametag.isEnable() 
				&& !(Minecraft.getMinecraft().currentScreen instanceof CosmeticSettings)) {
        	if(entity.isSneaking())
        	{
        		y = y - 0.2;
        	}
        	renderLivingLabel(entity, Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText(), x, y, z, 64);
        	if(Client.getInstance().modmanager.hypixel.isEnable()) {
        		y = y + 0.3; String text = null;
        		if((text = Client.getInstance().modmanager.hypixel.getText(entity.getUniqueID().toString())) != null) {
        			renderLivingLabel(entity, text, x, y, z, 20);
        		}
        	}
        }
	}
	
	@Redirect(method = "doRenderShadowAndFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;canRenderOnFire()Z"))
	private boolean showFire(Entity entity) {
		return Client.getInstance().modmanager.hideFire ? false : entity.canRenderOnFire();
	}
	
	@Inject(method = "renderOffsetLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/Render;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.AFTER))
	private void renderLabel(T entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_, CallbackInfo info) {
		if(Client.getInstance().modmanager.hypixel.isEnable() && entityIn instanceof AbstractClientPlayer) {
			if(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(entityIn.getUniqueID()) != null) {
				y = y + 0.3; String text = null;
				if((text = Client.getInstance().modmanager.hypixel.getText(entityIn.getUniqueID().toString())) != null) {
					renderLivingLabel(entityIn, text, x, y, z, 20);
				}
			}
		}
	}
}