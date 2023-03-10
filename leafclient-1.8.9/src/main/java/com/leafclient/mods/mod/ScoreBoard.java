package com.leafclient.mods.mod;

import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

public class ScoreBoard extends Mod {
	
	public boolean number, side, back, shadow;
	
	public int widthv;
	public int heightv;
	
	private int space = 0;
	private int width = 0;
	private int height = 0;
	private int scorep = 0;

	public ScoreBoard() {
		super("ScoreBoard", Integer.parseInt(Client.getInstance().setting.reader("ScoreBoard", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("ScoreBoard", "y")), 0, 0, 0,
				Integer.valueOf(Client.getInstance().setting.reader("ScoreBoard", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("ScoreBoard", "enable")), true);
		this.number = Boolean.valueOf(Client.getInstance().setting.reader("ScoreBoard", "number"));
		this.side = Boolean.valueOf(Client.getInstance().setting.reader("ScoreBoard", "side"));
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("ScoreBoard", "background"));
		this.shadow = Boolean.valueOf(Client.getInstance().setting.reader("ScoreBoard", "shadow"));
		this.widthv = 100;
		this.heightv = 100;
	}
	
	public void render(ScoreObjective objective, ScaledResolution scaledRes) {
		
		if(!isEnable()) return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>()
        {
            public boolean apply(Score p_apply_1_)
            {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15)
        {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        }
        else
        {
            collection = list;
        }

        int i = fontRenderer.getStringWidth(objective.getDisplayName());

        for (Score score : collection)
        {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, fontRenderer.getStringWidth(s));
        }
        
        widthv = i + scorep;
		width = hud().getHudX() + i + scorep;
		int max = Minecraft.getMinecraft().displayWidth / hud().getScale();
		int rwidth = hud().getHudX() + hud().getHudW();
		if(rwidth > max) {
			space = rwidth - max;
			int x = getX();
			setX(x - space);
		} else {
			space = 0;
			if(side) {
				if((max - rwidth) > 2) {
					int newX = max - rwidth;
					int x = getX();
					setX(x + newX);
				}
			}
		}

        int i1 = collection.size() * fontRenderer.FONT_HEIGHT;
        
        heightv = i1 + fontRenderer.FONT_HEIGHT;
		height = i1 + fontRenderer.FONT_HEIGHT + hud().getHudY();
        
        int j1 = height;
        
        int l1 = hud().getHudX() - space;
        
        int j = 0;
        
        int alpha = (back) ? 0 : 1342177280;

        for (Score score1 : collection)
        {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            
            int k = j1 - j * fontRenderer.FONT_HEIGHT;
            
            int l = width;
            
            Gui.drawRect(l1 - 2, k, l, k + fontRenderer.FONT_HEIGHT, alpha);
            
            if(shadow) {
            	fontRenderer.drawStringWithShadow(s1, l1, k, 553648127);
            } else {
            	fontRenderer.drawString(s1, l1, k, 553648127);
            }
            
            if(!number) {
            	String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            	
            	if(shadow) {
            		fontRenderer.drawStringWithShadow(s2, l - fontRenderer.getStringWidth(s2), k, 553648127);
            	} else {
            		fontRenderer.drawString(s2, l - fontRenderer.getStringWidth(s2), k, 553648127);
            	}
            	
            	scorep = fontRenderer.getStringWidth(s2);
            } else {
            	scorep = 0;
            }

            if (j == collection.size())
            {
                String s3 = objective.getDisplayName();
                Gui.drawRect(l1 - 2, k - fontRenderer.FONT_HEIGHT - 1, l, k - 1, alpha);
                Gui.drawRect(l1 - 2, k - 1, l, k, alpha);
                if(shadow) {
                	fontRenderer.drawStringWithShadow(s3, l1 + i / 2 - fontRenderer.getStringWidth(s3) / 2, k - fontRenderer.FONT_HEIGHT, 553648127);
                } else {
                	fontRenderer.drawString(s3, l1 + i / 2 - fontRenderer.getStringWidth(s3) / 2, k - fontRenderer.FONT_HEIGHT, 553648127);
                }
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
        int x = (hud().getHudX() + (getWidth() / 2)) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth("ScoreBoard") / 2);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("ScoreBoard", x, (hud().getHudY() + (getHeight() / 2)) - 3, -1);
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
	
	public void setSide(boolean side) {
		this.side = side;
	}
}