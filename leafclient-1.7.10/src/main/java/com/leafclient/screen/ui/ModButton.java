package com.leafclient.screen.ui;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.font.CustomFont;
import com.leafclient.mods.Mod;
import com.leafclient.screen.ModDetailSettings;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class ModButton extends UIBase {
	
	private int x, y, w, h;
	private Mod mod;
	private boolean isCover, isSubCover;

	public ModButton(Mod mod, int x, int y, int width, int height) {
		this.mod = mod;
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.isCover = false;
	}

	@Override
	public void render() {
		GL11.glColor4f(isCover ? 0 : mod.isEnable() ? 1 : 100, isCover ? 100 : mod.isEnable() ? 1 : 0, isCover ? 0 : mod.isEnable() ? 1 : 0, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/mod.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		if(!mod.getName().equals("FreeLook")) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/gear_small.png"));
			int p = isSubCover ? ScaleFixer.disW(2) : 0; int plus = isSubCover ? ScaleFixer.disW(4) : 0;
			Gui.drawModalRectWithCustomSizedTexture(x + ScaleFixer.disW(60) - (p), y + ScaleFixer.disH(110) - (p), 0.0F, 0.0F, 
					ScaleFixer.disW(50) + plus, ScaleFixer.disH(50) + plus, ScaleFixer.disW(50) + plus, ScaleFixer.disH(50) + plus);
		}
		CustomFont.getRender().drawCenteredString(mod.getName(), x + (w / 2), y + (h / 4), 0);
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + (h / 2)) {
			this.isCover = true;} else {this.isCover = false;
		}
		if(mouseX >= x && mouseX <= x + w && mouseY >= y + (h / 2) && mouseY <= y + h) {
			this.isSubCover = true;} else {this.isSubCover = false;
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + (h / 2)) {
			this.mod.setEnable();
			Client.getInstance().setting.writer(mod.getName(), "enable", String.valueOf(mod.isEnable()));
			if(mod.getName().equals("HitBox")) {
				RenderManager.debugBoundingBox = !RenderManager.debugBoundingBox;
			}
		} else if(mouseX >= x && mouseX <= x + w && mouseY >= y + (h / 2) && mouseY <= y + h
				&& !(mod.getName().equals("FreeLook"))) {
			Minecraft.getMinecraft().displayGuiScreen(new ModDetailSettings(mod));
		}
	}
}
