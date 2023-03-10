package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {
	
	@ModifyVariable(method = "renderStringAtPos", at = @At("HEAD"), ordinal = 0)
	private String changeName(String text) {
		if(Client.getInstance().modmanager.nickhider.isEnable() && Minecraft.getMinecraft().theWorld != null) {
			if(text.contains(Minecraft.getMinecraft().thePlayer.getName())) {
    			return text.replace(Minecraft.getMinecraft().thePlayer.getName(), Client.getInstance().modmanager.nickhider.username);
    		}
		}
		return text;
	}
}