package com.leafclient.mods.mod;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CPS extends Mod {
	
	private ArrayList<Long> left = new ArrayList<>();
	private ArrayList<Long> right = new ArrayList<>();
	private boolean lpress, rpress;
	public boolean back;
	private long ltime, rtime;
	
	public boolean showRight;
	
	private final String line = " | ";

	public CPS() {
		super("CPS",
				Integer.parseInt(Client.getInstance().setting.reader("CPS", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("CPS", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("CPS", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("CPS", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("CPS", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("CPS", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("CPS", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("CPS", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("CPS", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("CPS", "imagey")));
		this.showRight = Boolean.valueOf(Client.getInstance().setting.reader("CPS", "right_click"));
	}

	@Override
	public void render() {
		this.setCPS();
		GL11.glPushMatrix();
        GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String cps = (showRight ? getLeftCPS() + line + getRightCPS() : getLeftCPS()) + " CPS";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(cps) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(cps, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		this.setCPS();
		GL11.glPushMatrix();
        GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String cps = (showRight ? getLeftCPS() + line + getRightCPS() : getLeftCPS()) + " CPS";
        int w = (getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(cps) / 2);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(cps, hud().getHudX() + w, hud().getHudY() + 4, getColorValue());
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
	
	void setCPS() {
		final boolean pressed = Mouse.isButtonDown(0);
		if(pressed != this.lpress) {
			this.ltime = System.currentTimeMillis();
			this.lpress = pressed;
			if(pressed) this.left.add(this.ltime);
		}
		
		if(showRight) {
			final boolean rpressed = Mouse.isButtonDown(1);
			if(rpressed != this.rpress) {
				this.rtime = System.currentTimeMillis();
				this.rpress = rpressed;
				if(rpressed) this.right.add(this.rtime);
			}
		}
	}
	
	int getLeftCPS() {
		final long time = System.currentTimeMillis();
		this.left.removeIf(value -> value + 1000 < time);
		return (this.left.size() <= 0) ? 0 : this.left.size();
	}
	
	int getRightCPS() {
		final long time = System.currentTimeMillis();
		this.right.removeIf(value -> value + 1000 < time);
		return (this.right.size() <= 0) ? 0 : this.right.size();
	}
}
