package com.leafclient.screen.ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ColorChart extends UIBase {
	
	protected int r, b, g, code, image_x, image_y;
	private int x, y, width, height, i_x, i_y;
	private String name;
	private boolean isCover, isClick, panel;
	
	private int newX, newY, newW, newH;
	
	private BufferedImage image;
	
	public ColorChart(String name, int x, int y, int width, int height, int image_x, int image_y, int code) {
		this.name = name;
		this.code = code;
		this.x = ScaleFixer.disW(710);
		this.y = ScaleFixer.disH(413);
		this.width = ScaleFixer.disW(255);
		this.height = ScaleFixer.disH(255);
		this.i_x = ScaleFixer.disW(image_x);
		this.i_y = ScaleFixer.disH(image_y);
		this.image_x = ScaleFixer.disW(image_x);
		this.image_y = ScaleFixer.disH(image_y);
		this.newX = ScaleFixer.disW(x);
		this.newY = ScaleFixer.disH(y);
		this.newW = ScaleFixer.disW(width);
		this.newH = ScaleFixer.disH(height);
		this.isCover = false; this.isClick = false; this.panel = false;
		try { this.image = getImage(new ResourceLocation("leafclient/pallete.jpg"));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void render() {
		GlStateManager.color(1, 1, 1, 1);
		Gui.drawRect(newX, newY, newX + newW, newY + newH, code);
		CustomFont.getRender().drawString(name, newX - ScaleFixer.disW(250), newY + (newH / 2) - ScaleFixer.disH(10), 0);
		if(panel) {
			int spaceX = width / 4; int spaceY = height / 7; int subX = 0; int plus = 0; int recentW = ScaleFixer.disW(220);
			Gui.drawRect(x - 10, y - 10, x + width + (spaceX / 2) + recentW + 10, y + height + 10, 1342177280);
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableBlend();
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/pallete.jpg"));
			Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/recent.png"));
			Gui.drawModalRectWithCustomSizedTexture(x + width + (spaceX / 2), y, 0.0F, 0.0F, recentW, height, recentW, height);
			GlStateManager.color(isCover ? 0 : 1, isCover ? 100 : 1, isCover ? 0 : 1, 1); int size = ScaleFixer.disH(60);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/bar_point.png"));
			Gui.drawModalRectWithCustomSizedTexture((x + i_x) - (size / 2), (y + i_y) - (size / 2), 0.0F, 0.0F, size, size, size, size);
			for(int colorCode : Client.getInstance().modmanager.recent_color) {
				if(plus >= 5) {plus = 0; subX = spaceX + (spaceX / 2);}
				Gui.drawRect(x + width + spaceX + subX, y + (spaceY * plus) + spaceX, x + width + (spaceX * 2) + subX, y + (spaceY * plus) + (spaceY / 2) + spaceX, colorCode); plus++;
			}
		}
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableBlend();
	}
	
	public void onMouseMove(int mouseX, int mouseY) {
		if(!panel) return;
		if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
			isCover = true;
		} else {
			isCover = false;
		}
		if(isCover && isClick) {
			if(Mouse.isButtonDown(0)) {
				i_x = mouseX - x; i_y = mouseY - y;
				image_x = ScaleFixer.disWB(i_x);
				image_y = ScaleFixer.disWB(i_y);
			} else {
				code = image.getRGB(image_x, image_y); Color color = new Color(code);
				r = color.getRed(); g = color.getGreen(); b = color.getBlue();
				isClick = false;
				this.doThings();
			}
		}
	}
	
	public void onMouseClick(int mouseX, int mouseY, int button) {
		
		if(!panel) {
			if(mouseX >= newX && mouseX <= newX + newW && mouseY >= newY && mouseY <= newY + newH) {
				panel = true;
			}
			return;
		}
		
		int spaceX = width / 4; int spaceY = height / 7; int subX = 0; int plus = 0; int recentW = ScaleFixer.disW(220);
		
		if(mouseX >= x - 10 && mouseX <= x + width + (spaceX / 2) + recentW + 10 && mouseY >= y - 10 && mouseY <= y + height + 10) {
			if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {isClick = true;}
			for(int colorCode : Client.getInstance().modmanager.recent_color) {
				if(plus >= 5) {plus = 0; subX = spaceX + (spaceX / 2);}
				int xp = x + width + spaceX + subX;
				int yp = y + (spaceY * plus) + spaceX;
				int wp = spaceX; int hp = spaceY / 2;
				if(mouseX >= xp && mouseX <= xp + wp && mouseY >= yp && mouseY <= yp + hp) {
					code = colorCode; Color color = new Color(code);
					r = color.getRed(); g = color.getGreen(); b = color.getBlue();
					int[] pos = getXYFromColor(code);
					i_x = ScaleFixer.disW(pos[0]);
					i_y = ScaleFixer.disH(pos[1]);
					image_x = ScaleFixer.disWB(i_x);
					image_y = ScaleFixer.disWB(i_y);
					this.doThings();
				}
				plus++;
			}
		} else {
			panel = false;
		}
	}
	
	@Override
	public void doThings() {}
	
	public boolean isPanelOpen() {
		return panel;
	}
	
	private BufferedImage getImage(ResourceLocation loc) throws IOException {
		InputStream input =  Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
		return ImageIO.read(input);
    }
	
	private int[] getXYFromColor(int colorCode) {
		
		int[] list = new int[2];
		
		for(int x = 0; x < 255; x++) {
			for(int y = 0; y < 255; y++) {
				int code = image.getRGB(x, y);
				if(colorCode == code) {
					list[0] = x;
					list[1] = y;
					return list;
				}
			}
		}
		return list;
	}
}