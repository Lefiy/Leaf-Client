package com.leafclient.mixins.gui;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.screen.ModPosSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Shadow
	@Final
	protected Minecraft mc;
	
	@Inject(method = "renderGameOverlay", at = @At("RETURN"))
    private void renderMod(CallbackInfo info) {
        if(!(mc.currentScreen instanceof ModPosSettings)) {
            Client.getInstance().modmanager.renderMods();
        }
    }
	
	@Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
	private void setBossBarSizeTop(CallbackInfo info) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			GL11.glPushMatrix();
			GL11.glTranslatef(Client.getInstance().modmanager.bossbar.hud().getHudX(), Client.getInstance().modmanager.bossbar.hud().getHudY(), 0.0F);
			GL11.glScalef(Client.getInstance().modmanager.bossbar.getSize(), Client.getInstance().modmanager.bossbar.getSize(), 1.0F);
			GL11.glTranslatef(-(Client.getInstance().modmanager.bossbar.hud().getHudX()), -(Client.getInstance().modmanager.bossbar.hud().getHudY()), 0.0F);
		} else {
			info.cancel();
		}
	}
	
	@Inject(method = "renderBossHealth", at = @At("RETURN"))
	private void setBossBarSizeEnd(CallbackInfo info) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			GL11.glPopMatrix();
		}
	}
	
	@ModifyVariable(method = "renderBossHealth", at = @At("STORE"), ordinal = 2)
	private int setPosX(int x) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			return Client.getInstance().modmanager.bossbar.hud().getHudX();
		}
		return x;
	}
	
	@ModifyVariable(method = "renderBossHealth", at = @At("STORE"), ordinal = 4)
	private int setPosY(int y) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			return Client.getInstance().modmanager.bossbar.hud().getHudY() + 10;
		}
		return y;
	}
	
	@ModifyArg(method = "renderBossHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I", ordinal = 0), index = 1)
	private float setBossBarText(float x) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			String bossname = BossStatus.bossName;
			int nameWidth =  mc.fontRendererObj.getStringWidth(bossname);
			return (float) ((Client.getInstance().modmanager.bossbar.hud().getHudX() + 91) - nameWidth / 2);
		}
		return x;
	}
	
	@Redirect(method = "renderBossHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V"))
	private void callBossBarShowNothing(GuiIngame guigame, int x, int y, int textureX, int textureY, int width, int height) {
		if(!Client.getInstance().modmanager.bossbar.showHealth) {
			mc.ingameGUI.drawTexturedModalRect(x, y, textureX, textureY, width, height);
		}
	}
	
	/**
	 * @author Lefiy
	 * @reason Overwrite scoreboard Method
	 */
	@Overwrite
	private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
		
		Client.getInstance().modmanager.scoreboard.render(objective, scaledRes);
		
	}
	
	@Inject(method = "updateTick", at = @At("RETURN"))
	private void renderSwing(CallbackInfo info) {
		if(mc.thePlayer != null) {
			this.attemptSwing();
		}
	}
	
	private void attemptSwing() {
		if(Client.getInstance().modmanager.animation.isEnable()) {
			if (this.mc.thePlayer.getItemInUseCount() > 0) {
				boolean mouseDown = this.mc.gameSettings.keyBindAttack.isKeyDown() && this.mc.gameSettings.keyBindUseItem.isKeyDown();
				if (mouseDown && this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
					this.swingItem(this.mc.thePlayer);
				}
			}
		}
	}

	private void swingItem(EntityPlayerSP player) {
		final int animationEnd = player.isPotionActive(Potion.digSpeed) ?
				(6 - (1 + player.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) :
					(player.isPotionActive(Potion.digSlowdown) ? (6 + (1 + player.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
		if (!player.isSwingInProgress || player.swingProgressInt >= animationEnd / 2 || player.swingProgressInt < 0) {
			player.swingProgressInt = -1;
			player.isSwingInProgress = true;
		}
	}
}