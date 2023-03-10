package com.leafclient.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.impl.IMixinGui;

import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public class MixinGui implements IMixinGui {
	
	@Shadow
	protected void drawHorizontalLine(int startX, int endX, int y, int color) {}
	
	@Shadow
	protected void drawVerticalLine(int x, int startY, int endY, int color) {}
	
	public void drawHline(int startX, int endX, int y, int color) {
		drawHorizontalLine(startX, endX, y, color);
	}
	
	public void drawVline(int x, int startY, int endY, int color) {
		drawVerticalLine(x, startY, endY, color);
	}
}