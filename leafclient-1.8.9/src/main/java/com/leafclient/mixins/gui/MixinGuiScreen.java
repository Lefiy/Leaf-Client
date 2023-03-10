package com.leafclient.mixins.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
	
	@Shadow
	protected Minecraft mc;
	
	@Shadow
	public int width;
	
	@Shadow
	public int height;
	
	@Inject(method = "initGui", at = @At("HEAD"))
	private void setGuiSize(CallbackInfo info) {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
	}
	
	@Inject(method = "drawWorldBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawGradientRect(IIIIII)V", shift = At.Shift.AFTER))
	private void setClientIcon(CallbackInfo info) {
		GlStateManager.color(1, 1, 1, 1);
		int w = ScaleFixer.disW(280);
		int h = ScaleFixer.disH(70);
		int a = ScaleFixer.disW(10);
		mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/clienticon.png"));
		Gui.drawModalRectWithCustomSizedTexture(width - (w + a), height - (h + a), 0.0F, 0.0F, w, h, w, h);
	}
}