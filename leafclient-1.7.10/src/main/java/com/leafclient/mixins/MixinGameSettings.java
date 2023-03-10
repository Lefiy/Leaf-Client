package com.leafclient.mixins;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

@Mixin(GameSettings.class)
public class MixinGameSettings {
	
	@Shadow
	public KeyBinding[] keyBindings;
	
	@Inject(method = "loadOptions", at = @At("HEAD"))
	private void addKeyBinds(CallbackInfo info) {
		this.keyBindings = (KeyBinding[])((KeyBinding[])ArrayUtils.add(keyBindings, Client.getInstance().settingKey));
		this.keyBindings = (KeyBinding[])((KeyBinding[])ArrayUtils.add(keyBindings, Client.getInstance().sprintKey));
		this.keyBindings = (KeyBinding[])((KeyBinding[])ArrayUtils.add(keyBindings, Client.getInstance().freeKey));
	}
}