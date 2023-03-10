package com.leafclient.mixins.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.gui.GuiChat;

@Mixin(GuiChat.class)
public class MixinGuiChat {
	
	@Inject(method = "mouseClicked", at = @At("RETURN"))
	private void musicMouse(int mouseX, int mouseY, int mouseButton, CallbackInfo info) {
		if(Client.getInstance().modmanager.music.isEnable()) {
			Client.getInstance().modmanager.music.mouseClick(mouseX, mouseY);
		}
	}
	
	@ModifyArg(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;drawRect(IIIII)V"), index = 4)
	private int setAlpha(int color) {
		return Client.getInstance().modmanager.chat.back ? 0 : color;
	}
}