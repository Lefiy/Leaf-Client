package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Ping extends Mod {
	
	public boolean back;

	public Ping() {
		super("Ping", Integer.parseInt(Client.getInstance().setting.reader("Ping", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("Ping", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("Ping", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Ping", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Ping", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("Ping", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("Ping", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("Ping", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("Ping", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("Ping", "imagey")));
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String ping = ((Minecraft.getMinecraft().getCurrentServerData() == null) ? "0" : Minecraft.getMinecraft().getCurrentServerData().pingToServer) + " ms";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(ping) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(ping, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String ping = ((Minecraft.getMinecraft().getCurrentServerData() == null) ? "0" : Minecraft.getMinecraft().getCurrentServerData().pingToServer) + " ms";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(ping) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(ping, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
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
