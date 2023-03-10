package com.leafclient.screen.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import org.lwjgl.opengl.GL11;

import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class TextBox extends UIBase {
	
	protected boolean select;
	
	private int x, y, w, h;
	
	private String text, type;
	
	private boolean isPass, copy;
	
	public TextBox(String type, int x, int y, int width, int height, boolean isPass) {
		this.type = type;
		this.text = "";
		this.isPass = isPass;
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.select = false;
	}
	
	public void render() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/field/" + type + ".png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		int space = ScaleFixer.disW(20); String pass = text;
		if(isPass) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/hide.png"));
			Gui.drawModalRectWithCustomSizedTexture(x + (w - h), y, 0.0F, 0.0F, h, h, h, h);
			pass = ""; for(@SuppressWarnings("unused") char c : text.toCharArray()) {pass += "*";}
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/show.png"));
			Gui.drawModalRectWithCustomSizedTexture(x + (w - h), y, 0.0F, 0.0F, h, h, h, h);
		}
		GL11.glDisable(GL11.GL_BLEND);
		if(select) {
			int w = (int) CustomFont.getRender().getStringWidth(pass) + 1;
			Gui.drawRect(x + h + w, y + space, x + h + w + 1, y + h - space, -16119286);
		}
		CustomFont.getRender().drawString(pass, x + h, y + space, 0);
	}
	
	public void onMouseMove(int mouseX, int mouseY) {}
	
	public void onMouseClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			if(mouseX >= x + (w - h)) {
				isPass = !isPass;
			}
			select = true;
		} else {
			doThings();
			select = false;
		}
	}
	
	public void keyTyped(char key, int id) {
		if(!select) return;
		if(id == 14 || key == 211) {
			copy = false;
			if(text.length() == 0) return;
			this.text = text.substring(0, text.length() - 1);
			return;
		} else if(id == 29) {
			copy = true;
			return;
		} else if(id == 47 && copy) {
			String copied = getCopied();
			if(copied != null) {
				text += copied;
			} copy = false; return;
		} else if(id == 28 || id == 42 || id == 54 || id == 57 || id == 200 || id == 203 || id == 205 || id == 208) {
			copy = false;
			return;
		}
		copy = false;
		this.text += key;
	}
	
	public void setText(String text) {
		this.text += text;
	}
	
	public String getPassward() {
		return this.text;
	}
	
	@Override
	public void doThings() {}
	
	private String getCopied() {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();

		try { return (String) clip.getData(DataFlavor.stringFlavor);
		} catch (Exception e) {return null;}
	}
}