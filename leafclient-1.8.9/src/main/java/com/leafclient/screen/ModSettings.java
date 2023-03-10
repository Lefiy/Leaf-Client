package com.leafclient.screen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.mods.Mod;
import com.leafclient.screen.ui.ModButton;
import com.leafclient.screen.ui.ScrollBar;
import com.leafclient.screen.ui.SystemButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ModSettings extends GuiScreen {
	
	private ArrayList<SystemButton> system;
	private ArrayList<ModButton> modbutton;
	private ArrayList<Mod> mods;
	private ScrollBar scroll;
	
	@Override
	public void initGui() {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
		CustomFont.getRender().setSize(ScaleFixer.disHB(50));
		system.add(new SystemButton("mod", 430, 250, 170, 106));
		system.add(new SystemButton("cosmetic", 650, 250, 170, 106));
		system.add(new SystemButton("location", 1100, 250, 170, 106));
		system.add(new SystemButton("setting", 1320, 250, 170, 106));
		mods.addAll(Client.getInstance().modmanager.mods);
		mods.add(Client.getInstance().modmanager.killeffect);
		mods.add(Client.getInstance().modmanager.freelook);
		mods.add(Client.getInstance().modmanager.nickhider);
		mods.add(Client.getInstance().modmanager.hitbox);
		mods.add(Client.getInstance().modmanager.nametag);
		mods.add(Client.getInstance().modmanager.motionblur);
		mods.add(Client.getInstance().modmanager.overlay);
		mods.add(Client.getInstance().modmanager.timechanger);
		mods.add(Client.getInstance().modmanager.weatherchanger);
		mods.add(Client.getInstance().modmanager.chat);
		mods.add(Client.getInstance().modmanager.animation);
		int w_index = 0; int y_index = 400; int count = 0;
		for(Mod mod : mods) {
			if(w_index >= 4) {w_index = 0; y_index += 220;} if(count >= 8){w_index = 0; y_index = 400; count = 0;}
			modbutton.add(new ModButton(mod, system.get(w_index).getX(), y_index, 170, 182));
			w_index++; count++;
		}
		scroll = new ScrollBar(mods, 945, 400, 32, 400, 8);
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = sr.getScaledWidth();
		int height = sr.getScaledHeight();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/main.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, width, height);
		for(SystemButton sbutton : system) {
			sbutton.render();}
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			modbutton.get(i).render();}
		scroll.render();
		mouseMove(p_73863_1_, p_73863_2_);
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		for(SystemButton sbutton : system) {
			sbutton.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);}
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			modbutton.get(i).onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);}
	}
	
	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		system = new ArrayList<>();
		modbutton = new ArrayList<>();
		mods = new ArrayList<>();
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int event = Mouse.getEventDWheel();
		if(event < 0) {scroll.onScroll();}
		else if(event > 0) {scroll.onUnScroll();}
	}

	void mouseMove(int mouseX, int mouseY) {
		for(SystemButton sbutton : system) {
			sbutton.onMouseMove(mouseX, mouseY);}
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			modbutton.get(i).onMouseMove(mouseX, mouseY);}
	}
	
	@Override
	public void onGuiClosed() {
		this.mc.currentScreen = null;
	}
}