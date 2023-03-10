package com.leafclient.screen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.impl.IMixinSession;
import com.leafclient.screen.ui.ScrollBar;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.screen.ui.ToggleButton;
import com.leafclient.screen.ui.UIBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ClientSettings extends GuiScreen {
	
	private SystemButton home;
	private ArrayList<UIBase> base;
	private ScrollBar scroll;
	
	private int w_screen, h_screen;
	
	public static int w_save = 0;
	public static int h_save = 0;
	
	@Override
	public void initGui() {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth(); this.w_screen = width;
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight(); this.h_screen = height;
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
		CustomFont.getRender().setSize(ScaleFixer.disHB(50));
		home = new SystemButton("home", 640, 220, 80, 80);
		base.add(new ToggleButton("Enable FullBright", 1090, 310, 100, 60, Client.getInstance().modmanager.fullbright) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "fullbright", String.valueOf(select));
				Client.getInstance().modmanager.fullbright = select;
			}
		});
		base.add(new ToggleButton("Enable Clear Glass", 1090, 410, 100, 60, Client.getInstance().modmanager.clearGlass) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "clear_glass", String.valueOf(select));
				Client.getInstance().modmanager.clearGlass = select;
				Minecraft.getMinecraft().renderGlobal.loadRenderers();}
		});
		base.add(new ToggleButton("Hide Entity Burning", 1090, 510, 100, 60, Client.getInstance().modmanager.hideFire) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "hide_fire", String.valueOf(select));
				Client.getInstance().modmanager.hideFire = select;}
		});
		base.add(new ToggleButton("Disable Enchantment Glint", 1090, 610, 100, 60, Client.getInstance().modmanager.hideEncha) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "hide_enchant", String.valueOf(select));
				Client.getInstance().modmanager.hideEncha = select;}
		});
		base.add(new ToggleButton("Enable Animation of Dropped Items", 1090, 710, 100, 60, Client.getInstance().modmanager.customItem) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "item", String.valueOf(select));
				Client.getInstance().modmanager.customItem = select;}
		});
		base.add(new ToggleButton("Hide NameTag Icon", 1090, 310, 100, 60, Client.getInstance().modmanager.hideIcon) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "hide_icon", String.valueOf(select));
				Client.getInstance().modmanager.hideIcon = select;}
		});
		base.add(new ToggleButton("Hide Cape Cosmetics", 1090, 410, 100, 60, Client.getInstance().modmanager.hideCape) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "hide_cape", String.valueOf(select));
				Client.getInstance().modmanager.hideCape = select;}
		});
		base.add(new ToggleButton("Hide Wing Cosmetics", 1090, 510, 100, 60, Client.getInstance().modmanager.hideWing) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "hide_wing", String.valueOf(select));
				Client.getInstance().modmanager.hideWing = select;}
		});
		base.add(new ToggleButton("Hide Hat Cosmetics", 1090, 610, 100, 60, Client.getInstance().modmanager.hideHat) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "hide_hat", String.valueOf(select));
				Client.getInstance().modmanager.hideHat = select;}
		});
		base.add(new ToggleButton("Borderless Screen", 1090, 710, 100, 60, Client.getInstance().modmanager.borderless) {
			@Override
			public void doThings() {
				Client.getInstance().setting.writer("Setting", "borderless", String.valueOf(select));
				Client.getInstance().modmanager.borderless = select;
				if(select) {
					w_save = Minecraft.getMinecraft().displayWidth;
					h_save = Minecraft.getMinecraft().displayHeight;
					System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
					try {
						Display.setFullscreen(false);
					    Display.setResizable(true);
					    Display.setDisplayMode(new DisplayMode(w_screen, h_screen));
					    ((IMixinSession) Minecraft.getMinecraft()).windowResize(w_screen, h_screen);
					} catch (LWJGLException e) {}
				} else {
					if(w_save == 0 || h_save == 0) return;
					System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
					try {
						Display.setFullscreen(false);
					    Display.setResizable(true);
					    Display.setDisplayMode(new DisplayMode(w_save, h_save));
					    ((IMixinSession) Minecraft.getMinecraft()).windowResize(w_save, h_save);
					} catch (LWJGLException e) {}
				}
			}
		});
		scroll = new ScrollBar(base, 1230, 310, 32, 460, 5);
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		ScaledResolution sr = new ScaledResolution(mc);
		int width = sr.getScaledWidth();
		int height = sr.getScaledHeight();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/main_mod.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, width, height);
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			base.get(i).render();}
		scroll.render();
		home.render();
		mouseMove(p_73863_1_, p_73863_2_);
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			base.get(i).onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);}
		home.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		base = new ArrayList<>();
		home = null;
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
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			base.get(i).onMouseMove(mouseX, mouseY);}
		home.onMouseMove(mouseX, mouseY);
	}
	
	@Override
	public void onGuiClosed() {
		this.mc.currentScreen = null;
	}
}
