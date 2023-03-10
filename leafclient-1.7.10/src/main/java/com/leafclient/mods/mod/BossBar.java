package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;

public class BossBar extends Mod {
	
	public boolean showHealth;

	public BossBar() {
		super("BossBar", Integer.parseInt(Client.getInstance().setting.reader("BossBar", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("BossBar", "y")), 0, 0, 0,
				Integer.valueOf(Client.getInstance().setting.reader("BossBar", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("BossBar", "enable")), true);
		this.showHealth = Boolean.valueOf(Client.getInstance().setting.reader("BossBar", "health"));
	}

	@Override
	public void render() {}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        int x = (hud().getHudX() + (getWidth() / 2)) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth("BossBar") / 2);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("BossBar", x, (hud().getHudY() + (getHeight() / 2)) - 3, -1);
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 182;
	}

	@Override
	public int getHeight() {
		return 20;
	}
}