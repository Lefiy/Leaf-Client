package com.leafclient.mixins.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {
	
	private boolean isContains = false;
	
	@Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getUpdatedCounter()I", ordinal = 0))
	private int isContainName(ChatLine chatline) {
		if(Client.getInstance().modmanager.chat.detect) {
			String msg = chatline.getChatComponent().getFormattedText();
			if(msg.contains(Minecraft.getMinecraft().thePlayer.getCommandSenderName())) {
				isContains = true;
			} else {
				isContains = false;
			}
		}
		return chatline.getUpdatedCounter();
	}
	
	@ModifyArg(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0), index = 4)
	private int setColor(int color) {
		if(Client.getInstance().modmanager.chat.isEnable()) {
			if(Client.getInstance().modmanager.chat.detect && isContains) {
				return Client.getInstance().modmanager.chat.getColorValue();
			}
			if(Client.getInstance().modmanager.chat.back) {
				return 0;
			}
		}
		return color;
	}
}