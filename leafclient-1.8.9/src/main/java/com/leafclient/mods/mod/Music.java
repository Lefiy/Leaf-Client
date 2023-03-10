package com.leafclient.mods.mod;

import java.io.File;
import java.io.FilenameFilter;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.mods.Mod;
import com.leafclient.utils.MusicUtil;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Music extends Mod {
	
	public int volume_amount;
	private int index;
	
	public boolean switching, back;
	public float volume;
	
	private MusicUtil music;
	
	private File[] files;

	public Music() {
		super("Music",
				Integer.parseInt(Client.getInstance().setting.reader("Music", "x")),
				Integer.parseInt(Client.getInstance().setting.reader("Music", "y")),
				Integer.parseInt(Client.getInstance().setting.reader("Music", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("Music", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("Music", "blue")),
				Integer.valueOf(Client.getInstance().setting.reader("Music", "size")),
				Boolean.valueOf(Client.getInstance().setting.reader("Music", "enable")), true);
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("Music", "background"));
		this.switching = Boolean.valueOf(Client.getInstance().setting.reader("Music", "switch"));
		this.volume_amount = Integer.valueOf(Client.getInstance().setting.reader("Music", "volume"));
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("Music", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("Music", "imagey")));
		this.updateVolume(volume_amount); this.index = 0; this.convert();
	}
	
	private void convert() {
		
		File musicfile = new File(Minecraft.getMinecraft().mcDataDir, "music");
		
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
			    String check = name.substring(name.lastIndexOf("."));
				return check.equalsIgnoreCase(".mp3");
			}
		};
		
		for(File file : musicfile.listFiles(filter)) {
			String name = file.getAbsolutePath(); Converter convert = new Converter();
			String fname = name.substring(0, name.lastIndexOf(".")) + ".wav";
			try {
				convert.convert(name, fname);
				file.delete();
			} catch (JavaLayerException e) {e.printStackTrace();}
		}
		
		files = musicfile.listFiles();
	}
	
	private void play() {
		
		if(files == null) return;
		
		int filec = files.length - 1;
		
		if(index <= filec) {
			play(files[index]);
		}
	}
	
	private void play(File file) {
		
		music = new MusicUtil(file, volume) {
			@Override
			public void callback() {
				super.callback();
				if(switching) {
					int filec = files.length - 1;
					if(filec >= (index + 1)) {
						index = index + 1;
					} else { index = 0; }
				}
				play();
			}
		};
		music.start();
	}
	
	private void next() {
		
		if(music == null) return;
		
		music.stopThread();
		
		int filec = files.length - 1;
		if(filec >= (index + 1)) {
			index = index + 1;
		} else { index = 0; }
		
		play();
		
	}
	
	private void back() {
		
		if(music == null) return;
		
		music.stopThread();
		
		if((index - 1) >= 0) {
			index = index - 1;}
		
		play();
		
	}
	
	public void updateVolume(int amount) {
		float value_f = (float) amount / 51.0F;
		int convert = Math.round(value_f);
		volume = (5.0F - convert) * 10.0F;
		if(music != null) {
			music.updateVolume(volume);
		}
	}
	
	public void mouseClick(int mouseX, int mouseY) {
		int x = hud().getHudX(); int y = hud().getHudY();
		if(mouseX >= x + 10 && mouseX <= x + 20 && mouseY >= y + 30 && mouseY <= y + 40) {
			if(music != null) {
				music.startMusic();
			} else {
				play();
			}
		} else if(mouseX >= x + 30 && mouseX <= x + 40 && mouseY >= y + 30 && mouseY <= y + 40) {
			if(music != null) {
				music.stopMusic();
			}
		} else if(mouseX >= x + 50 && mouseX <= x + 60 && mouseY >= y + 30 && mouseY <= y + 40) {
			if(music != null) {
				back();
			}
		} else if(mouseX >= x + 70 && mouseX <= x + 80 && mouseY >= y + 30 && mouseY <= y + 40) {
			if(music != null) {
				next();
			}
		}
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int x = hud().getHudX(); int y = hud().getHudY();
        int width = getWidth(); int height = getHeight();
        
        if(back) {
        	Gui.drawRect(x, y, x + width, y + height, 1342177280);
        }
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(files[index].getName(), x + 10, y + 10, getColorValue());
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/play.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 10, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/stop.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 30, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/back.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 50, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/next.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 70, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		
		if(music != null) {
			
			String time = music.getMilliTimePos() + " / " + music.getMilliTime();
			int ws = Minecraft.getMinecraft().fontRendererObj.getStringWidth(time);
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(time, (x + width) - (ws + 10), y + 30, getColorValue());
			
		}
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
		
		GL11.glPopMatrix();
	}

	@Override
	public void rendersub(int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glTranslatef(hud().getHudX(), hud().getHudY(), 0.0F);
        GL11.glScalef(getSize(), getSize(), 1.0F);
        GL11.glTranslatef(-(hud().getHudX()), -(hud().getHudY()), 0.0F);
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int x = hud().getHudX(); int y = hud().getHudY();
        int width = getWidth(); int height = getHeight();
        
        if(back) {
        	Gui.drawRect(x, y, x + width, y + height, 1342177280);
        }
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(files[index].getName(), x + 10, y + 10, getColorValue());
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/play.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 10, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/stop.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 30, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/back.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 50, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/next.png"));
		Gui.drawModalRectWithCustomSizedTexture(x + 70, y + 30, 0.0F, 0.0F, 10, 10, 10, 10);
		
		if(music != null) {
			
			String time = music.getMilliTimePos() + " / " + music.getMilliTime();
			int ws = Minecraft.getMinecraft().fontRendererObj.getStringWidth(time);
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(time, (x + width) - (ws + 10), y + 30, getColorValue());
			
		}
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
		
		GL11.glPopMatrix();
		hud().renderSub(mouseX, mouseY);
	}

	@Override
	public int getWidth() {
		return 200;
	}

	@Override
	public int getHeight() {
		return 50;
	}
}
