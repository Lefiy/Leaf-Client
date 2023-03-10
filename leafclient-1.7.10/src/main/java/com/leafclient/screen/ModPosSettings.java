package com.leafclient.screen;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.screen.ui.ToggleButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ModPosSettings extends GuiScreen {
	
	private SystemButton home;
	private ToggleButton abjust;
	
	@Override
	public void initGui() {
		home = new SystemButton("home", 920, 500, 80, 80);
		abjust = new ToggleButton("Disable Mod Position Adjustment", 1800, 15, 100, 60, Client.getInstance().modmanager.abjust) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "abjust", String.valueOf(select));
				Client.getInstance().modmanager.abjust = select;}
		};
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		
		this.drawDefaultBackground();
		
		for(Mod m : Client.getInstance().modmanager.mods) {
			if(m.isEnable())
				m.rendersub(p_73863_1_, p_73863_2_);
		}
		
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		
		home.render();
		abjust.render();
		
		mouseMove(p_73863_1_, p_73863_2_);
		
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(Client.getInstance().modmanager.music.isEnable()) {
			Client.getInstance().modmanager.music.mouseClick(mouseX, mouseY);
		}
		home.onMouseClick(mouseX, mouseY, mouseButton);
		abjust.onMouseClick(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		home = null;
		abjust = null;
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}
	
	void mouseMove(int mouseX, int mouseY) {
		home.onMouseMove(mouseX, mouseY);
		abjust.onMouseMove(mouseX, mouseY);
	}
}