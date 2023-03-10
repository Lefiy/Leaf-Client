package com.leafclient.screen.ui;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class SessionButton extends UIBase {
	
	private String name;
	private int x, y, head, index, max;
	private ResourceLocation player;
	private boolean isCoverR, isCoverL;
	
	public SessionButton(Session session, int x, int y, ArrayList<String> keys, int index) {
		this.index = index;
		this.max = keys.size();
		this.name = session.getUsername();
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.head = ScaleFixer.disH(80);
		this.isCoverR = false;
		this.isCoverL = false;
		try {
			BufferedImage image = ImageIO.read(new URL("https://minotar.net/avatar/" + session.getUsername()));
			this.player = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(session.getUsername(), new DynamicTexture(image));
		} catch (Exception e) {}
	}

	@Override
	public void render() {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		int width = ScaleFixer.disH(50);
		int namew = (int)CustomFont.getRender().getStringWidth(name) + ScaleFixer.disH(20) + width;
		
		CustomFont.getRender().drawStringWithShadow(name, x + head + width + ScaleFixer.disH(20), y + (head / 2) - ScaleFixer.disH(8), -1);
		
		if(this.player != null) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(player);
			Gui.drawModalRectWithCustomSizedTexture(x + head, y + ScaleFixer.disH(16), 0.0F, 0.0F, width, width, width, width);
		}
		
		if(index > 1) {
			
			GL11.glColor4f(isCoverL ? 0 : 1, isCoverL ? 100 : 1, isCoverL ? 0 : 1, 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/button/arrow_left.png"));
			Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, head, head, head, head);
			
		}
		
		if(index < max) {
		
			GL11.glColor4f(isCoverR ? 0 : 1, isCoverR ? 100 : 1, isCoverR ? 0 : 1, 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/button/arrow_right.png"));
			Gui.drawModalRectWithCustomSizedTexture(x + head + namew, y, 0.0F, 0.0F, head, head, head, head);
			
		}
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		int namew = (int)CustomFont.getRender().getStringWidth(name) + ScaleFixer.disH(20) + ScaleFixer.disH(50);
		int total = head + namew + head;
		if(mouseX >= x && mouseX <= x + total && mouseY >= y && mouseY <= y + head) {
			if(mouseX <= x + head) {
				isCoverL = true;
			} else {
				isCoverL = false;
			}
			if(mouseX >= (x + total) - head) {
				isCoverR = true;
			} else {
				isCoverR = false;
			}
		} else {
			isCoverL = false;
			isCoverR = false;
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int button) {
		int namew = (int)CustomFont.getRender().getStringWidth(name) + ScaleFixer.disH(20) + ScaleFixer.disH(50);
		int total = head + namew + head;
		if(mouseX >= x && mouseX <= x + total && mouseY >= y && mouseY <= y + head) {
			if(mouseX <= x + head) {
				switching(-1);
			}
			if(mouseX >= (x + total) - head) {
				switching(1);
			}
		}
	}
	
	public void switching(int amount) {
		
		if(index + amount >= 1 && index + amount <= max) {
			
			int swit = index + amount;
			final String token = Client.getInstance().getKeys().get(swit - 1);
			final String result = Client.getInstance().tokenSession(token);
			
			if(result != null) {
				
				name = result; index = swit; Client.getInstance().index = index;
				Client.getInstance().setting.writer("Setting", "index", String.valueOf(index));
				
				try {
					BufferedImage image = ImageIO.read(new URL("https://minotar.net/avatar/" + name));
					this.player = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(name, new DynamicTexture(image));
				} catch (Exception e) {}
			}
		}
	}
}
