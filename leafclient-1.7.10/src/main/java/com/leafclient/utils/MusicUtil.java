package com.leafclient.utils;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import net.minecraft.client.Minecraft;

public class MusicUtil extends Thread {
	
	private File file;
	
	private Clip clip;
	
	private String length;
	
	private float volume;
	
	private boolean isActive;
	
	public MusicUtil(File file, float volume) {
		this.file = file;
		this.volume = volume;
		this.length = null;
		this.isActive = true;
	}
	
	@Override
	public void run() {
		
		try {
			
			AudioInputStream input = AudioSystem.getAudioInputStream(file);
			
			DataLine.Info line = new DataLine.Info(Clip.class, input.getFormat());
			
			clip = (Clip) AudioSystem.getLine(line);
			
			clip.open(input);
			
			FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(-volume);
			
			clip.start();
			
			// Just Wait. There is no other way.
			while(clip.getMicrosecondLength() != clip.getMicrosecondPosition()) {
				if(Minecraft.getMinecraft().theWorld == null || !isActive) {
					stopMusic();
					deleteMusic();
					return;
				}
			}
			
			callback();
			
		} catch (Exception e) {}
	}
	
	public void stopThread() {
		this.isActive = false;
	}
	
	public void stopMusic() {
		if(clip != null) clip.stop();
	}
	
	public void startMusic() {
		if(clip != null) clip.start();
	}
	
	public void deleteMusic() {
		if(clip != null) clip.close();
	}
	
	public void updateVolume(float volume) {
		if(clip != null) {
			FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(-volume);
		}
	}
	
	public void callback() {
		stopMusic();
		deleteMusic();
	}
	
	public String getMilliTime() {
		if(clip != null) {
			if(length != null) {
				return length;
			} else {
				if(clip.getMicrosecondLength() != 0) {
					length = format(clip.getMicrosecondLength());
					return length;
				}
			}
		}
		return "0";
 	}
	
	public String getMilliTimePos() {
		if(clip != null) {
			return format(clip.getMicrosecondPosition());
		}
		return "0";
 	}
	
	private String format(long micro) {
		int seconds = (int) micro / 1000000;
		int hour = seconds / 3600;
		int saveM = (seconds - (hour * 3600));
		int min = saveM / 60;
		int sec = saveM - (min * 60);
		String s = sec < 10 ? "0" + sec : "" + sec;
		if(hour >= 1) {
			return hour + ":" + min + ":" + s;
		}
		if(min >= 1) {
			return min + ":" + s;
		}
		if(sec >= 1) {
			return "0:" + s;
		}
		return "0";
	}
}