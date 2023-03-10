package com.leafclient.screen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.lwjgl.input.Mouse;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.screen.ui.CosmeticButton;
import com.leafclient.screen.ui.ScrollBar;
import com.leafclient.screen.ui.SelectButton;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.utils.DBUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CosmeticSettings extends GuiScreen {
	
	private ArrayList<SystemButton> system;
	private ArrayList<CosmeticButton> cosmetic;
	private SelectButton type;
	private ScrollBar scroll;
	private String typeName;
	
	public CosmeticSettings(String lIl) {
		this.typeName = lIl;}
	
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
		ArrayList<String> cosmetics = new ArrayList<>();
		cosmetics.add("None");
		for(Map.Entry<String, String> entry : Client.getInstance().cosmes.entrySet()) {
			if(entry.getKey().equals(typeName.toLowerCase())) {
				cosmetics.addAll(Arrays.asList(entry.getValue().split(",")));
			}
		}
		int y_index = 400; int count = 0;
		for(String name : cosmetics) {
			if(count >= 3) {y_index = 400; count = 0;}
			cosmetic.add(new CosmeticButton(name, 480, y_index, 300, 90, 
				name.equals(Client.getInstance().setting.reader("Setting", typeName.toLowerCase()))) {
					@Override
					public void doThings() {
						Client.getInstance().setting.writer("Setting", typeName.toLowerCase(), name);
						if(typeName.equals("Cape")) {
							Client.getInstance().modmanager.cape = name;
						} else if(typeName.equals("Wing")) {
							Client.getInstance().modmanager.wing = name;
						} else if(typeName.equals("Hat")) {
							Client.getInstance().modmanager.hat = name;}
						setWorldAndResolution(Minecraft.getMinecraft(), 
						Minecraft.getMinecraft().currentScreen.width, 
						Minecraft.getMinecraft().currentScreen.height);
						DBUtil database = new DBUtil(mc.thePlayer.getUniqueID().toString());
						database.update(typeName.toLowerCase(), name);
					}
				});
			y_index+=100; count++;}
		type = new SelectButton("", 480, 700, 300, 90, Arrays.asList("Cape", "Wing", "Hat"), typeName) {
			@Override
			public void doThings() {
				Minecraft.getMinecraft().displayGuiScreen(new CosmeticSettings(list.get(index)));
			}
		};
		scroll = new ScrollBar(cosmetics, 945, 400, 32, 400, 3);
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
			cosmetic.get(i).render();}
		type.render();
		scroll.render();
		float mouseX = -(float)((sr.getScaledWidth() / 2) - p_73863_1_);
		float mouseY = (float)((sr.getScaledHeight() / 2) - p_73863_2_);
		if(mouseY >= 30.0F) {mouseY = 30.0F;} else if(mouseY <= -30.0F) {mouseY = -30.0F;}
		GuiInventory.drawEntityOnScreen(ScaleFixer.disW(1300), ScaleFixer.disH(800), ScaleFixer.disW(200), mouseX, mouseY, mc.thePlayer);
		mouseMove(p_73863_1_, p_73863_2_);
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		for(SystemButton sbutton : system) {
			sbutton.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);}
		for(int i = scroll.getIndex(); scroll.isScrollAble(i); i++) {
			cosmetic.get(i).onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);}
		type.onMouseClick(p_73864_1_, p_73864_2_, p_73864_3_);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		system = new ArrayList<>();
		cosmetic = new ArrayList<>();
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
			cosmetic.get(i).onMouseMove(mouseX, mouseY);}
		type.onMouseMove(mouseX, mouseY);
	}
	
	@Override
	public void onGuiClosed() {
		this.mc.currentScreen = null;
	}
}