package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Saturation extends Mod {
	
	public boolean back;

	public Saturation() {
		super("Saturation", Integer.parseInt(Client.getInstance().setting.reader("Saturation", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("Saturation", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("Saturation", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Saturation", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Saturation", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("Saturation", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("Saturation", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("Saturation", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("Saturation", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("Saturation", "imagey")));
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String saturation = String.valueOf(Math.round(Minecraft.getMinecraft().thePlayer.getFoodStats().getSaturationLevel()));
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(saturation) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(saturation, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String saturation = String.valueOf(Math.round(Minecraft.getMinecraft().thePlayer.getFoodStats().getSaturationLevel()));
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(saturation) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(saturation, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 30;
	}

	@Override
	public int getHeight() {
		return 16;
	}
}
