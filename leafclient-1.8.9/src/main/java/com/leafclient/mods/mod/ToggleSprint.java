package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ToggleSprint extends Mod {
	
	public boolean flyspeed, sneak, back;
	private boolean press;

	public ToggleSprint() {
		super("ToggleSprint", Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("ToggleSprint", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("ToggleSprint", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("ToggleSprint", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("ToggleSprint", "imagey")));
		this.flyspeed = Boolean.valueOf(Client.getInstance().setting.reader("ToggleSprint", "speedup"));
		this.sneak = Boolean.valueOf(Client.getInstance().setting.reader("ToggleSprint", "sneak"));
		this.press = false;
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String sprint = isToggleSnaek() ? (sneak ? "[Sneaking (Toggled)]" : "[Sneaking (KeyHeld)]") : "[Sprinting (Toggled)]";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(sprint) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
        
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(sprint, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String sprint = isToggleSnaek() ? (sneak ? "[Sneaking (Toggled)]" : "[Sneaking (KeyHeld)]") : "[Sprinting (Toggled)]";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(sprint) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(sprint, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 120;
	}

	@Override
	public int getHeight() {
		return 16;
	}
	
	public boolean isToggleSnaek() {
		if(!sneak) return Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();
		if(!press) {if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()) {press = true;}
		} else {if(Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()) {press = false;}}
		return press;
	}
}
