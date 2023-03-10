package com.leafclient.mixins.gui;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

@Mixin(ServerListEntryNormal.class)
public class MixinServerListEntryNormal {
	
	@Shadow
	@Final
	private ServerData field_148301_e;
	
	@Shadow
	@Final
	private Minecraft field_148300_d;
	
	@Shadow
	@Final
	private GuiMultiplayer field_148303_c;
	
	@Inject(method = "drawEntry", at = @At("RETURN"))
	private void renderIcon(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_, CallbackInfo info) {
		if(Client.getInstance().downloader.server.contains(field_148301_e.serverIP)) {
			field_148300_d.getTextureManager().bindTexture(new ResourceLocation("leafclient/leaf.png"));
        	Gui.drawModalRectWithCustomSizedTexture(p_148279_2_ - 45, p_148279_3_ - 3, 0.0F, 0.0F, 50, 40, 50, 40);
		}
	}
}