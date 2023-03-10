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
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.scoreboard.ScoreObjective;

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
	
	@ModifyVariable(method = "renderBossHealth", at = @At("STORE"), ordinal = 1)
	private int setPosX(int x) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			return Client.getInstance().modmanager.bossbar.hud().getHudX();
		}
		return x;
	}
	
	@ModifyVariable(method = "renderBossHealth", at = @At("STORE"), ordinal = 0)
	private byte setPosY(byte y) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			return (byte)(Client.getInstance().modmanager.bossbar.hud().getHudY() + 10);
		}
		return y;
	}
	
	@ModifyArg(method = "renderBossHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;III)I", ordinal = 0), index = 1)
	private int setBossBarText(int x) {
		if(Client.getInstance().modmanager.bossbar.isEnable()) {
			String bossname = BossStatus.bossName;
			int nameWidth =  mc.fontRendererObj.getStringWidth(bossname);
			return (Client.getInstance().modmanager.bossbar.hud().getHudX() + 91) - nameWidth / 2;
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
	protected void renderScoreboard(ScoreObjective p_96136_1_, int p_96136_2_, int p_96136_3_, FontRenderer p_96136_4_) {
		
		Client.getInstance().modmanager.scoreboard.render(p_96136_1_, p_96136_2_, p_96136_3_, p_96136_4_);
		
	}
}