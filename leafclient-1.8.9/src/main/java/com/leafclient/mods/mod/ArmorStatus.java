package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ArmorStatus extends Mod {
	
	public boolean back;

	public ArmorStatus() {
		super("ArmorStatus",
				Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("ArmorStatus", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("ArmorStatus", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("ArmorStatus", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("ArmorStatus", "imagey")));
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
        GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
		int c = hud().getHudY() + 10;
		
		for(int i = 3; i >= 0; i--) {
			if(Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(i) == null) continue;
			Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(i), hud().getHudX() + 10, c);
			Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(i), hud().getHudX() + 10, c);
			GL11.glDisable(GL11.GL_LIGHTING);
			c+=18;
	    }
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        }
        
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_helmet), hud().getHudX() + 10, hud().getHudY() + 10);
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_chestplate), hud().getHudX() + 10, hud().getHudY() + 28);
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_leggings), hud().getHudX() + 10, hud().getHudY() + 46);
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_boots), hud().getHudX() + 10, hud().getHudY() + 64);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 36;
	}

	@Override
	public int getHeight() {
		return 90;
	}
}