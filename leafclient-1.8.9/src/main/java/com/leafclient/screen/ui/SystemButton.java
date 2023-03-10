package com.leafclient.screen.ui;

import java.awt.Desktop;
import java.net.URI;

import com.leafclient.Client;
import com.leafclient.screen.ClientSettings;
import com.leafclient.screen.CosmeticSettings;
import com.leafclient.screen.ModPosSettings;
import com.leafclient.screen.ModSettings;
import com.leafclient.screen.ScaleFixer;
import com.leafclient.screen.custom.GuiSession;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SystemButton extends UIBase {
	
	private int x, x1, y, w, h;
	private String run;
	private boolean isCover;

	public SystemButton(String run, int x, int y, int width, int height) {
		this.run = run;
		this.x1 = x;
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.isCover = false;
	}

	@Override
	public void render() {
		GlStateManager.color(isCover ? 0 : 1, isCover ? 100 : 1, isCover ? 0 : 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/button/" + run + ".png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			this.isCover = true;
		} else {
			this.isCover = false;
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
			if(run.equals("mod")) {
				Minecraft.getMinecraft().displayGuiScreen(new ModSettings());
			} else if(run.equals("cosmetic")) {
				Minecraft.getMinecraft().displayGuiScreen(new CosmeticSettings("Cape"));
			} else if(run.equals("location")) {
				Minecraft.getMinecraft().displayGuiScreen(new ModPosSettings());
			} else if(run.equals("setting")) {
				Minecraft.getMinecraft().displayGuiScreen(new ClientSettings());
			} else if(run.equals("single")) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
			} else if(run.equals("multi")) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
			} else if(run.equals("account")) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiSession(Client.getInstance().secure, true));
			} else if(run.equals("settings")) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(null, Minecraft.getMinecraft().gameSettings));
			} else if(run.equals("home")) {
				Minecraft.getMinecraft().displayGuiScreen(new ModSettings());
			} else if(run.equals("discord")) {
				try {
					Desktop.getDesktop().browse(new URI("https://discord.gg/eJtRwnhxdY"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if(run.equals("twitter")) {
				try {
					Desktop.getDesktop().browse(new URI("https://twitter.com/Leaf_Client"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if(run.equals("youtube")) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.youtube.com/c/Lefiy_MC"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.doThings();
		}
	}
	
	public int getX() {
		return this.x1;
	}
}