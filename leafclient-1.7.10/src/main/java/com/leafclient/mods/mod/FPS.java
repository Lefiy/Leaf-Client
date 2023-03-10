package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.impl.IMixinFPS;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class FPS extends Mod {
	
	public boolean back;

	public FPS() {
		super("FPS",
				Integer.parseInt(Client.getInstance().setting.reader("FPS", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("FPS", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("FPS", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("FPS", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("FPS", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("FPS", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("FPS", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("FPS", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("FPS", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("FPS", "imagey")));
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String fps = ((IMixinFPS) Minecraft.getMinecraft()).getDebugFPS() + " FPS";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(fps) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(fps, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String fps = ((IMixinFPS) Minecraft.getMinecraft()).getDebugFPS() + " FPS";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(fps) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(fps, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 59;
	}

	@Override
	public int getHeight() {
		return 16;
	}
}
