package com.leafclient.mixins.gui;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ResourceLocation;

@Mixin(ServerListEntryNormal.class)
public class MixinServerListEntryNormal {
	
	@Shadow
	@Final
	private ServerData server;
	
	@Shadow
	@Final
	private Minecraft mc;
	
	@Shadow
	@Final
	private GuiMultiplayer owner;
	
	private int index = 0;
	
	@Inject(method = "drawEntry", at = @At("RETURN"))
	private void renderIcon(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo info) {
		index = slotIndex;
		if(Client.getInstance().downloader.server.contains(server.serverIP)) {
			mc.getTextureManager().bindTexture(new ResourceLocation("leafclient/leaf.png"));
        	Gui.drawModalRectWithCustomSizedTexture(x - 45, y - 3, 0.0F, 0.0F, 50, 40, 50, 40);
		}
	}
	
	@Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;drawModalRectWithCustomSizedTexture(IIFFIIFF)V", ordinal = 3))
	private void showArrow1(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		if(!Client.getInstance().downloader.server.contains(server.serverIP) || index > (Client.getInstance().downloader.server.size() - 1)) {
			Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
	
	@Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;drawModalRectWithCustomSizedTexture(IIFFIIFF)V", ordinal = 4))
	private void showArrow2(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		if(!Client.getInstance().downloader.server.contains(server.serverIP) || index > (Client.getInstance().downloader.server.size() - 1)) {
			Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
	
	@Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;drawModalRectWithCustomSizedTexture(IIFFIIFF)V", ordinal = 5))
	private void showArrow3(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		if(!Client.getInstance().downloader.server.contains(server.serverIP) || index > (Client.getInstance().downloader.server.size() - 1)) {
			Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
	
	@Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;drawModalRectWithCustomSizedTexture(IIFFIIFF)V", ordinal = 6))
	private void showArrow4(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		if(!Client.getInstance().downloader.server.contains(server.serverIP) || index > (Client.getInstance().downloader.server.size() - 1)) {
			Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
		}
	}
	
	@Redirect(method = "mousePressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;func_175391_a(Lnet/minecraft/client/gui/ServerListEntryNormal;IZ)V", ordinal = 0))
	private void showArrowMove1(GuiMultiplayer guimulti, ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
		if(!Client.getInstance().downloader.server.contains(server.serverIP) || index > (Client.getInstance().downloader.server.size() - 1)) {
			owner.func_175391_a(p_175391_1_, p_175391_2_, p_175391_3_);
		}
	}
	
	@Redirect(method = "mousePressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;func_175393_b(Lnet/minecraft/client/gui/ServerListEntryNormal;IZ)V", ordinal = 0))
	private void showArrowMove2(GuiMultiplayer guimulti, ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
		if(!Client.getInstance().downloader.server.contains(server.serverIP) || index > (Client.getInstance().downloader.server.size() - 1)) {
			owner.func_175393_b(p_175393_1_, p_175393_2_, p_175393_3_);
		}
	}
}