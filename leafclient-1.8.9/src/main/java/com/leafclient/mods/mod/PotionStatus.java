package com.leafclient.mods.mod;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionStatus extends Mod {
	
	public boolean back;

	public PotionStatus() {
		super("PotionStatus", Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("PotionStatus", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("PotionStatus", "enable")), true);
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("PotionStatus", "imagey")));
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("PotionStatus", "background"));
	}
	
	@Override
	public void render() {
		
		GL11.glPushMatrix();
        GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
		
		int xpos = hud().getHudX();
		int ypos = hud().getHudY();

        if (!Minecraft.getMinecraft().thePlayer.getActivePotionEffects().isEmpty()) {
        	
        	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            Iterator<PotionEffect> effects = Minecraft.getMinecraft().thePlayer.getActivePotionEffects().iterator();

            while (effects.hasNext()) {
            	
                PotionEffect potioneffect = (PotionEffect)effects.next();
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                
                if(back) {
                	Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(xpos, ypos, 0, 166, 140, 32);
                }
                
                if (potion.hasStatusIcon()) {
                    int index = potion.getStatusIconIndex();
                    int texX = index % 8 * 18; int texY = 198 + index / 8 * 18;
                    Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(xpos + 6, ypos + 7, texX, texY, 18, 18);
                }
                
                String name = I18n.format(potion.getName()) + " ";

                if (potioneffect.getAmplifier() == 1) {
                	name += "II";
                } else if (potioneffect.getAmplifier() == 2) {
                	name += "III";
                } else if (potioneffect.getAmplifier() == 3) {
                	name += "IV";
                } else if (potioneffect.getAmplifier() == 4) {
                	name += "V";
                } else if (potioneffect.getAmplifier() == 5) {
                	name += "VI";
                } else if (potioneffect.getAmplifier() == 6) {
                	name += "VII";
                } else if (potioneffect.getAmplifier() == 7) {
                	name += "VIII";
                } else if (potioneffect.getAmplifier() == 8) {
                	name += "IX";
                } else if (potioneffect.getAmplifier() == 9) {
                	name += "X";
                }

                String duration = Potion.getDurationString(potioneffect);
                if(potioneffect.getDuration() <= 200 && (potioneffect.getDuration() % 20) >= 10) {duration = "";}
                
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(name, xpos + 28, ypos + 6, getColorValue());
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(duration, xpos + 28, ypos + 16, getColorValue());
                
                ypos += 33;
            }
        }
        GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
        GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        int x = (hud().getHudX() + (getWidth() / 2)) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth("PotionStatus") / 2);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("PotionStatus", x, (hud().getHudY() + (getHeight() / 2)) - 3, -1);
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 140;
	}

	@Override
	public int getHeight() {
		return 35;
	}
}