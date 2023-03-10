package com.leafclient.mods.mod;

import org.lwjgl.input.Keyboard;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;

public class FreeLook extends Mod {
	
	private float yaw, pitch;
	private int view;
	
	public boolean press;

	public FreeLook() {
		super("FreeLook", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("FreeLook", "enable")), false);
		
		yaw = 0F;
		pitch = 0F;
		press = false;
	}
	
	public void toggled(int keyCode) {
		if(!isEnable()) return;
		if(Keyboard.getEventKeyState()) {
			this.press = !this.press;
			this.yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
			this.pitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
			if(this.press) {
				this.view = Minecraft.getMinecraft().gameSettings.thirdPersonView;
				Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
			} else {
				Minecraft.getMinecraft().gameSettings.thirdPersonView = this.view;
			}
		} else if(true) {
			this.press = false;
			Minecraft.getMinecraft().gameSettings.thirdPersonView = this.view;
		}
	}
	
	public float getCameraYaw() {
		return press ? yaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
	}
	
	public float getCameraPitch() {
		return press ? pitch : Minecraft.getMinecraft().thePlayer.rotationPitch;
	}
	
	public float getPlayerViewY() {
		return press ? yaw : RenderManager.instance.playerViewY;
	}
	
	public float getPlayerViewX() {
		return press ? pitch : RenderManager.instance.playerViewX;
	}
	
	public boolean canPrevMove() {
			
		if(!press) {
			return true;
		}
		
		Minecraft.getMinecraft().mouseHelper.mouseXYChange();
		float f1 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
		float f2 = (f1 * f1 * f1 * 8.0F);
		float f3 = (float) Minecraft.getMinecraft().mouseHelper.deltaX * f2;
		float f4 = (float) Minecraft.getMinecraft().mouseHelper.deltaY * f2;
		
		yaw += f3 * 0.15F;
		pitch += f4 * 0.15F;
		
		if(pitch < -90) {
			pitch = -90;
		}
		
		if(pitch > 90) {
			pitch = 90;
		}
		
		return false;
	}
}
