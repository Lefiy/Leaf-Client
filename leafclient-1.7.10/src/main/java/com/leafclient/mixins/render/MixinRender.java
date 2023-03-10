package com.leafclient.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;
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
public class MixinRender {
	
	private boolean isRanker = false;
	private String icon = "";
	
	@Shadow
	@Final
	protected RenderManager renderManager;
	
	@Redirect(method = "renderLivingLabel", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewY:F", opcode = Opcodes.GETFIELD))
    private float callGL11Yaw(RenderManager render) {
        if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            return renderManager.playerViewY;
        } else {
            return Client.getInstance().modmanager.freelook.getPlayerViewY();
        }
    }

    @Redirect(method = "renderLivingLabel", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewX:F", opcode = Opcodes.GETFIELD))
    private float callGL11Pitch(RenderManager render) {
        if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            return -renderManager.playerViewX;
    	} else {
            return Client.getInstance().modmanager.freelook.getPlayerViewX();
    	}
    }
	
	@Redirect(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;getStringWidth(Ljava/lang/String;)I"))
	private int setBackgroundPos(FontRenderer fontrenderer, String text, Entity entityIn, String str, double x, double y, double z, int maxDistance) {
		if(entityIn instanceof AbstractClientPlayer) {
			if(!Client.getInstance().modmanager.hideIcon) {
				String uuid = entityIn.getUniqueID().toString();
				if(text.contains(entityIn.getFormattedCommandSenderName().getFormattedText()) || text.contains(Client.getInstance().modmanager.nickhider.username)) {
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
	private int callShadowString(FontRenderer fontrenderer, String text, int x, int y, int color, Entity entityIn, String str, double xa, double ya, double za, int maxDistance) {
		if(isRanker) {
			GL11.glColor4f(1, 1, 1, 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/icon/" + icon + ".png"));
        	Gui.drawModalRectWithCustomSizedTexture(x - 1, -2, 11, 11, 11, 11, 11, 11);
		}
		
		if(Client.getInstance().modmanager.nametag.isShadow) {
			return fontrenderer.drawStringWithShadow(text, isRanker ? x + 12 : x, y, color);
		} else {
			return fontrenderer.drawString(text, isRanker ? x + 12 : x, y, color);
		}
	}
	
	@ModifyArg(method = "renderLivingLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;setColorRGBA_F(FFFF)V"), index = 3)
    private float setNametagAlpha(float p_78369_4_) {
        return Client.getInstance().modmanager.nametag.back ? 0.0F : 0.25F;
    }
	
	@ModifyVariable(method = "renderLivingLabel", at = @At("HEAD"), ordinal = 0)
	private String changeName(String str) {
		if(Client.getInstance().modmanager.nickhider.isEnable() && Minecraft.getMinecraft().theWorld != null) {
			if(str.contains(Minecraft.getMinecraft().thePlayer.getCommandSenderName())) {
    			return str.replace(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), Client.getInstance().modmanager.nickhider.username);
    		}
		}
		return str;
	}
	
	@Redirect(method = "doRenderShadowAndFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;canRenderOnFire()Z"))
	private boolean showFire(Entity entity) {
		return Client.getInstance().modmanager.hideFire ? false : entity.canRenderOnFire();
	}
}