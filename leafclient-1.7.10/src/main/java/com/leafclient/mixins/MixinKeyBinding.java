package com.leafclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.screen.ModSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@Mixin(KeyBinding.class)
public class MixinKeyBinding {
	
	@Inject(method = "setKeyBindState", at = @At("HEAD"))
	private static void onKeyPress(int keyCode, boolean pressed, CallbackInfo info) {
		if(keyCode == Client.getInstance().settingKey.getKeyCode()) {
			if(pressed) {
				Minecraft.getMinecraft().displayGuiScreen(new ModSettings());
			}
		}
		if(keyCode == Client.getInstance().sprintKey.getKeyCode()) {
			if(pressed) {
				Client.getInstance().modmanager.sprint.setEnable();
				Client.getInstance().setting.writer("ToggleSprint", "enable", "true");
			}
		}
		if(keyCode == Client.getInstance().freeKey.getKeyCode()) {
			Client.getInstance().modmanager.freelook.toggled(keyCode);
		}
		if(keyCode == Minecraft.getMinecraft().gameSettings.keyBindTogglePerspective.getKeyCode()) {
			Client.getInstance().modmanager.freelook.press = false;
		}
	}
	
	@Inject(method = "setKeyCode", at = @At("RETURN"))
	private void updateKeyBinds(CallbackInfo info) {
		if(Client.getInstance().modmanager != null) {
			Client.getInstance().modmanager.keystrokes.updateKey();
		}
	}
}