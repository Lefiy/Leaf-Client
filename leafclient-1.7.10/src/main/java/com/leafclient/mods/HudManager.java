package com.leafclient.mods;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.impl.IMixinGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class HudManager {
	
	private Mod mod;
	
    private int mouseX, mouseY;

    private boolean isHold, isHoldCheck, center, wcenter, modcenter, modtop, modbuttom;

    public HudManager(Mod mod) {
        this.mod = mod;
        this.isHold = false;
        this.isHoldCheck = false;
        this.center = false;
        this.wcenter = false;
        this.modcenter = false;
        this.modtop = false;
        this.modbuttom = false;
    }

    public int getHudX() {
        return mod.getX() / getScale();
    }

    public int getHudY() {
        return mod.getY() / getScale();
    }
    
    public int getHudW() {
        return (int)((float)mod.getWidth() * mod.getSize());
    }

    public int getHudH() {
    	return (int)((float)mod.getHeight() * mod.getSize());
    }
    
    public int getScale() {
		return (Minecraft.getMinecraft().gameSettings.guiScale == 0) ? 4 : Minecraft.getMinecraft().gameSettings.guiScale;
	}
    
    private boolean isCanHold(Mod mod, int mouseX, int mouseY) {
    	return mouseX >= mod.hud().getHudX() && mouseX <= mod.hud().getHudX() + mod.hud().getHudW()
    	&& mouseY >= mod.hud().getHudY() && mouseY <= mod.hud().getHudY() + mod.hud().getHudH();
    }
    
    private boolean isNotOverScreen(int newMouseX, int newMouseY) {
    	Minecraft mc = Minecraft.getMinecraft();
    	ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	int x = newMouseX; int y = newMouseY; int w = getHudW() * getScale(); int h = getHudH() * getScale();
    	int maxX = sr.getScaledWidth() * sr.getScaleFactor(); int maxY = sr.getScaledHeight() * sr.getScaleFactor();
    	if(mod.getName().equals("ScoreBoard")) {
    		if((maxX - (x + w)) < 5) {
    			Client.getInstance().modmanager.scoreboard.setSide(true);
    			Client.getInstance().setting.writer("ScoreBoard", "side", "true");
    		} else {
    			Client.getInstance().modmanager.scoreboard.setSide(false);
    			Client.getInstance().setting.writer("ScoreBoard", "side", "false");
    		}
    	}
    	return x > 0 && x + w < maxX && y > 0 && y + h < maxY;
    }
    
    private void adjust(int mouseX, int mouseY) {
    	
    	Minecraft mc = Minecraft.getMinecraft();
    	ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	
    	if(isHold) {
    		
    		int centerX = sr.getScaledWidth() / 2 - 20;
    		int centerY = sr.getScaledHeight() / 2 - 20;
    		int centerW = centerX + 40;
    		int centerH = centerY + 40;
    		
    		if(mouseX >= centerX && mouseX <= centerW) {
    			wcenter = true;
    			int mCenterX = ((sr.getScaledWidth() / 2) - (getHudW() / 2)) * getScale();
    			int ySub = (mouseY * getScale()) + this.mouseY;
    			mod.setX(mCenterX); mod.setY(ySub);
    			((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawVline((sr.getScaledWidth() / 2), 0, sr.getScaledHeight(), -16711936);
    		} else { wcenter = false; }
    		
    		if(mouseX >= centerX && mouseX <= centerW && mouseY >= centerY && mouseY <= centerH) {
    			center = true;
    			int mCenterX = ((sr.getScaledWidth() / 2) - (getHudW() / 2)) * getScale();
    			int mCenterY = ((sr.getScaledHeight() / 2) - (getHudH() / 2)) * getScale();
    			mod.setX(mCenterX); mod.setY(mCenterY);
    			((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawHline(0, sr.getScaledWidth(), (sr.getScaledHeight() / 2), -16711936);
        		((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawVline((sr.getScaledWidth() / 2), 0, sr.getScaledHeight(), -16711936);
    		} else { center = false; }
    		
    		int modX = 0; int topY = 0; int buttomY = 0;
    		
    		for(Mod mods : Client.getInstance().modmanager.mods) {
    			
    			if(!mods.getName().equals(mod.getName()) && mods.isEnable() && mods.isShowHUD()) {
    				
    				modX = (mods.hud().getHudX() + (mods.hud().getHudW() / 2));
    				int mCenterXSub = modX - 20; int mCenterWSub = modX + 20;
    				
    				if(mouseX >= mCenterXSub && mouseX <= mCenterWSub) {
    					modcenter = true; break;
    				} else { modcenter = false; }
    			}
    		}
    		
    		for(Mod mods : Client.getInstance().modmanager.mods) {
    			
    			if(!mods.getName().equals(mod.getName()) && mods.isEnable() && mods.isShowHUD()) {
    				
    				topY = mods.hud().getHudY();
    				int topYSub = topY - 20;
    					
    				if(mouseY >= topYSub && mouseY <= topY) {
    					modtop = true; break;
    				} else { modtop = false; }
    				
    			}
    		}
    		
    		for(Mod mods : Client.getInstance().modmanager.mods) {
    			
    			if(!mods.getName().equals(mod.getName()) && mods.isEnable() && mods.isShowHUD()) {
    				
    				buttomY = mods.hud().getHudY() + mods.hud().getHudH();
    				int buttomYSub2 = buttomY + 20;
    				
    				if(mouseY >= buttomY && mouseY <= buttomYSub2) {
    					modbuttom = true; break;
    				} else { modbuttom = false; }
    			}
    		}
    		
    		if(modcenter) {
    			int modCenterX = (modX - (getHudW() / 2)) * getScale();
    			int ySub = (mouseY * getScale()) + this.mouseY;
    			if(isNotOverScreen(modCenterX, ySub)) {
    				mod.setX(modCenterX); mod.setY(ySub);
    			}
    			((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawVline(modX, 0, sr.getScaledHeight(), -16711936);
    		}
    		
    		if(modtop) {
    			int modTopY = (topY - getHudH()) * getScale();
    			int xSub = (mouseX * getScale()) + this.mouseX;
    			if(isNotOverScreen(xSub, modTopY)) {
    				mod.setX(xSub); mod.setY(modTopY);
    			}
    			((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawHline(0, sr.getScaledWidth(), topY, -16711936);
    		}
    		
    		if(modbuttom) {
    			int modButtomY = buttomY * getScale();
    			int xSub = (mouseX * getScale()) + this.mouseX;
    			if(isNotOverScreen(xSub, modButtomY)) {
    				mod.setX(xSub); mod.setY(modButtomY);
    			}
    			((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawHline(0, sr.getScaledWidth(), buttomY, -16711936);
    		}
    	}
    }
    
    private boolean isAllowMove(String name, int mouseX, int mouseY) {
    	ArrayList<String> mods = new ArrayList<>();
    	for(Mod mod : Client.getInstance().modmanager.mods) {
    		if(mod.isEnable() && mod.hud().isCanHold(mod, mouseX, mouseY)) {
    			mods.add(mod.getName());}}
    	if(mods.isEmpty()) {return false;}
    	if(mods.get(0).equals(mod.getName())) {return true;}
    	return false;
    }
    
    public void renderSub(int mouseX, int mouseY) {
        fixMouse(mouseX, mouseY);
        GL11.glColor4f(1, 1, 1, 1);
        Gui.drawRect(this.getHudX(), this.getHudY(), this.getHudX() + this.getHudW(), this.getHudY() + this.getHudH(), 685563100);
        ((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawHline(this.getHudX(), this.getHudX() + this.getHudW(), this.getHudY(), (this.isHold) ? -256 : -16711936);
        ((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawHline(this.getHudX(), this.getHudX() + this.getHudW(), this.getHudY() + this.getHudH(), (this.isHold) ? -256 : -16711936);
        ((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawVline(this.getHudX(), this.getHudY() + this.getHudH(), this.getHudY(), (this.isHold) ? -256 : -16711936);
        ((IMixinGui) Minecraft.getMinecraft().ingameGUI).drawVline(this.getHudX() + this.getHudW(), this.getHudY() + this.getHudH(), this.getHudY(), (this.isHold) ? -256 : -16711936);
        if(isCanHold(mod, mouseX, mouseY)) { this.mouseX = mod.getX() - (mouseX * getScale()); this.mouseY = mod.getY() - (mouseY * getScale());}
    }

    private void fixMouse(int mouseX, int mouseY) {
    	
    	boolean isAllow = isAllowMove(mod.getName(), mouseX, mouseY);
    	
    	if(isAllow && !isHold && !isHoldCheck) {if(!Mouse.isButtonDown(0)) {isHoldCheck = true;}
    	} else if(!isAllow && !isHold && isHoldCheck) {isHoldCheck = false;
    	} else if(isAllow && !isHold && isHoldCheck) {if(Mouse.isButtonDown(0)) {isHold = true;}}
    	
        if (this.isHold && this.isHoldCheck) {
        	
        	if(!Client.getInstance().modmanager.abjust) { this.adjust(mouseX, mouseY);
        	} else { this.center = false; this.wcenter = false; this.modcenter = false;
        	this.modtop = false; this.modbuttom = false; }
        	
        	if(!center && !wcenter && !modcenter && !modtop && !modbuttom) {
        		int newMouseX = (mouseX * getScale()) + this.mouseX;
        		int newMouseY = (mouseY * getScale()) + this.mouseY;
        		if(isNotOverScreen(newMouseX, newMouseY)) {
        			mod.setX(newMouseX); mod.setY(newMouseY);
        		}
        	}
        	if(!Mouse.isButtonDown(0)) {
        		this.isHold = false; this.isHoldCheck = false; this.center = false; this.wcenter = false;
        		this.modcenter = false; this.modtop = false; this.modbuttom = false;
        		Client.getInstance().setting.writer(mod.getName(), "x", String.valueOf(mod.getX()));
        		Client.getInstance().setting.writer(mod.getName(), "y", String.valueOf(mod.getY()));
        	}
        }
    }
}