package com.leafclient.mods.mod;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
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
	
	@SuppressWarnings("rawtypes")
	public void render(ScoreObjective p_96136_1_, int p_96136_2_, int p_96136_3_, FontRenderer p_96136_4_) {
		
		if(!isEnable()) return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        
        Scoreboard scoreboard = p_96136_1_.getScoreboard();
        Collection collection = scoreboard.getSortedScores(p_96136_1_);

        if (collection.size() <= 15)
        {
            int k = p_96136_4_.getStringWidth(p_96136_1_.getDisplayName());
            String s;

            for (Iterator iterator = collection.iterator(); iterator.hasNext(); k = Math.max(k, p_96136_4_.getStringWidth(s)))
            {
                Score score = (Score)iterator.next();
                ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
                s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            }
            
            widthv = k + scorep;
			width = hud().getHudX() + k + scorep;
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

            int k1 = collection.size() * p_96136_4_.FONT_HEIGHT;
            
            heightv = k1 + p_96136_4_.FONT_HEIGHT;
			height = k1 +p_96136_4_.FONT_HEIGHT + hud().getHudY();
            
            int l1 = height;
            
            int i2 = hud().getHudX() - space;
            
            int l = 0;
            
            int alpha = (back) ? 0 : 1342177280;
            
            Iterator iterator1 = collection.iterator();

            while (iterator1.hasNext())
            {
                Score score1 = (Score)iterator1.next();
                ++l;
                ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
                String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
                int i1 = l1 - l * p_96136_4_.FONT_HEIGHT;
                
                int j1 = width;
                
                Gui.drawRect(i2 - 2, i1, j1, i1 + p_96136_4_.FONT_HEIGHT, alpha);
                
                if(shadow) {
                	p_96136_4_.drawStringWithShadow(s1, i2, i1, 553648127);
                } else {
                	p_96136_4_.drawString(s1, i2, i1, 553648127);
                }
                
                if(!number) {
                	String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
                	
                	if(shadow) {
                		p_96136_4_.drawStringWithShadow(s2, j1 - p_96136_4_.getStringWidth(s2), i1, 553648127);
                	} else {
                		p_96136_4_.drawString(s2, j1 - p_96136_4_.getStringWidth(s2), i1, 553648127);
                	}
                	
                	scorep = p_96136_4_.getStringWidth(s2);
                } else {
                	scorep = 0;
                }

                if (l == collection.size())
                {
                    String s3 = p_96136_1_.getDisplayName();
                    Gui.drawRect(i2 - 2, i1 - p_96136_4_.FONT_HEIGHT - 1, j1, i1 - 1, alpha);
                    Gui.drawRect(i2 - 2, i1 - 1, j1, i1, alpha);
                    if(shadow) {
                    	p_96136_4_.drawStringWithShadow(s3, i2 + k / 2 - p_96136_4_.getStringWidth(s3) / 2, i1 - p_96136_4_.FONT_HEIGHT, 553648127);
                    } else {
                    	p_96136_4_.drawString(s3, i2 + k / 2 - p_96136_4_.getStringWidth(s3) / 2, i1 - p_96136_4_.FONT_HEIGHT, 553648127);
                    }
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