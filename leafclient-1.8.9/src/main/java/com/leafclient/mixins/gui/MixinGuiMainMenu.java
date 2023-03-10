package com.leafclient.mixins.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.impl.IMixinSession;
import com.leafclient.screen.ScaleFixer;
import com.leafclient.screen.ui.SessionButton;
import com.leafclient.screen.ui.SystemButton;
import com.leafclient.screen.ui.UIBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {
	
	@Shadow
	private ResourceLocation backgroundTexture;
	
	@Shadow
	@Final
	private static ResourceLocation[] titlePanoramaPaths;
	
	@Shadow
	private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {};
	
	private ArrayList<UIBase> buttons = new ArrayList<>();
	
	/**
	 * @author Lefiy
	 * @reason Overwrite initGui Method
	 */
	@Overwrite
	public void initGui() {
		
		buttons.clear();
		
		titlePanoramaPaths = new ResourceLocation[] {new ResourceLocation("leafclient/panorama_0.png"), 
				new ResourceLocation("leafclient/panorama_1.png"), new ResourceLocation("leafclient/panorama_2.png"), 
				new ResourceLocation("leafclient/panorama_3.png"), new ResourceLocation("leafclient/panorama_4.png"), new ResourceLocation("leafclient/panorama_5.png")};
		
		this.backgroundTexture = mc.getTextureManager().getDynamicTextureLocation("background", new DynamicTexture(256, 256));
        
        ScaledResolution sr = new ScaledResolution(mc);
		int width = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getWidth();
		int height = (int)((Dimension)Toolkit.getDefaultToolkit().getScreenSize()).getHeight();
		double displayW = ((width / sr.getScaleFactor()) > sr.getScaledWidth()) ? (double)sr.getScaledWidth() / ((double)width / (double)sr.getScaleFactor()) : 1.0;
		double displayH = ((height / sr.getScaleFactor()) > sr.getScaledHeight()) ? (double)sr.getScaledHeight() / ((double)height / (double)sr.getScaleFactor()) : 1.0;
		double abjustW = (double)width / 1920.0; double abjustH = (double)height / 1080.0;
		ScaleFixer.setup(sr.getScaleFactor(), displayW, displayH, abjustW, abjustH);
		CustomFont.getRender().setSize(ScaleFixer.disHB(50));
		
		buttons.add(new SystemButton("single", 760, 550, 400, 80));
		buttons.add(new SystemButton("multi", 760, 670, 400, 80));
		buttons.add(new SystemButton("account", 760, 790, 185, 80));
		buttons.add(new SystemButton("settings", 975, 790, 185, 80));
		buttons.add(new SystemButton("close", 1820, 20, 80, 80) {
			@Override
			public void doThings() {
				Minecraft.getMinecraft().shutdown();
			}
		});
		buttons.add(new SystemButton("discord", 10, 1020, 50, 50));
		buttons.add(new SystemButton("twitter", 70, 1020, 50, 50));
		buttons.add(new SystemButton("youtube", 130, 1020, 50, 50));
        buttons.add(new SessionButton(((IMixinSession)Minecraft.getMinecraft()).getSession(), 0, 5, Client.getInstance().getKeys(), Client.getInstance().index));
        
        Client.getInstance().setStatus();
	}
	
	/**
	 * @author Lefiy
	 * @reason Overwrite drawScreen Method
	 */
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        
        this.mouseMove(mouseX, mouseY);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/leaf.png"));
        Gui.drawModalRectWithCustomSizedTexture((width / 2) - ScaleFixer.disW(200), (height / 2) - ScaleFixer.disH(290), 0.0F, 0.0F, 
        		ScaleFixer.disW(400), ScaleFixer.disH(320), ScaleFixer.disW(400), ScaleFixer.disH(320));

        String var10 = "Copyright Mojang Studio.";
        CustomFont.getRender().drawString(var10, width - CustomFont.getRender().getStringWidth(var10) - 5, height - ScaleFixer.disH(30), -1);
        
        for(UIBase button : buttons) {
        	button.render();
        }
	}
	
	/**
	 * @author Lefiy
	 * @reason Overwrite mouseClick Method
	 */
	@Overwrite
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for(UIBase button : buttons) {
        	button.onMouseClick(mouseX, mouseY, mouseButton);
    	}
	}
	
	private void mouseMove(int mouseX, int mouseY) {
		for(UIBase button : buttons) {
			button.onMouseMove(mouseX, mouseY);
		}
	}
}