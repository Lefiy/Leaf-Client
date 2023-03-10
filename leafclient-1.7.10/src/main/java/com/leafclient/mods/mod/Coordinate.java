package com.leafclient.mods.mod;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Coordinate extends Mod {
	
	private int widthv, heightv;
	public boolean showBiome, setDecimal, back;

	public Coordinate() {
		super("Coordinate", Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("Coordinate", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("Coordinate", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("Coordinate", "background"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("Coordinate", "imagey")));
		this.showBiome = Boolean.valueOf(Client.getInstance().setting.reader("Coordinate", "biome"));
		this.setDecimal = Boolean.valueOf(Client.getInstance().setting.reader("Coordinate", "decimal"));
		this.widthv = 138;
		this.heightv = 28;
	}

	@Override
	public void render() {
		String loc = "";
		if(setDecimal) {
        	double x = Math.round(Minecraft.getMinecraft().thePlayer.posX * 100.0) / 100.0;
        	double y = Math.round(Minecraft.getMinecraft().thePlayer.posY * 100.0) / 100.0;
        	double z = Math.round(Minecraft.getMinecraft().thePlayer.posZ * 100.0) / 100.0;
        	loc = "X: " + x + " Y: " + y + " Z: " + z;
        } else {
        	int x = (int) Minecraft.getMinecraft().thePlayer.posX;
        	int y = (int) Minecraft.getMinecraft().thePlayer.posY;
        	int z = (int) Minecraft.getMinecraft().thePlayer.posZ;
        	loc = "X: " + x + " Y: " + y + " Z: " + z;}
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String biome = "Biome: " + Minecraft.getMinecraft().theWorld.getBiomeGenForCoords((int)Minecraft.getMinecraft().thePlayer.posX, (int)Minecraft.getMinecraft().thePlayer.posZ).biomeName;
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        	int locWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(loc);
    		int biomeWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(biome);
    		this.widthv = Math.max(locWidth, biomeWidth) + 10;
        }
        
        this.heightv = (!showBiome) ? 28 : 16;
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(loc, hud().getHudX() + 5, hud().getHudY() + 4, getColorValue());
		if(!showBiome) {Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(biome, hud().getHudX() + 5, hud().getHudY() + 17, getColorValue());}
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		String loc = "";
		if(setDecimal) {
        	double x = Math.round(Minecraft.getMinecraft().thePlayer.posX * 100.0) / 100.0;
        	double y = Math.round(Minecraft.getMinecraft().thePlayer.posY * 100.0) / 100.0;
        	double z = Math.round(Minecraft.getMinecraft().thePlayer.posZ * 100.0) / 100.0;
        	loc = "X: " + x + " Y: " + y + " Z: " + z;
        } else {
        	int x = (int) Minecraft.getMinecraft().thePlayer.posX;
        	int y = (int) Minecraft.getMinecraft().thePlayer.posY;
        	int z = (int) Minecraft.getMinecraft().thePlayer.posZ;
        	loc = "X: " + x + " Y: " + y + " Z: " + z;}
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        String biome = "Biome: " + Minecraft.getMinecraft().theWorld.getBiomeGenForCoords((int)Minecraft.getMinecraft().thePlayer.posX, (int)Minecraft.getMinecraft().thePlayer.posZ).biomeName;
        
        if(back) {
        	Gui.drawRect(hud().getHudX(), hud().getHudY(), hud().getHudX() + getWidth(), hud().getHudY() + getHeight(), 1342177280);
        	int locWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(loc);
    		int biomeWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(biome);
    		this.widthv = Math.max(locWidth, biomeWidth) + 10;
        }
        
        this.heightv = (!showBiome) ? 28 : 16;
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(loc, hud().getHudX() + 5, hud().getHudY() + 4, getColorValue());
		if(!showBiome) {Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(biome, hud().getHudX() + 5, hud().getHudY() + 17, getColorValue());}
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return widthv;
	}

	@Override
	public int getHeight() {
		return heightv;
	}
}
