package com.leafclient.mixins.gui;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer {
	
	@Shadow
	private ServerList savedServerList;
	
	private int index_server = 0;
	
	@Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;)V", at = @At("RETURN"))
	private void resetUserList(CallbackInfo info) {
		Client.getInstance().users.clear();
		Client.getInstance().modmanager.hypixel.loaded.clear();
	}
	
	@ModifyVariable(method = "selectServer", at = @At("HEAD"), ordinal = 0)
	private int setIndex(int index) {
		index_server = index;
		return index;
	}
	
	@Redirect(method = "selectServer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiButton;enabled:Z", ordinal = 4, opcode = Opcodes.PUTFIELD))
	private void deleteBlock1(GuiButton button, boolean info) {
		if(Client.getInstance().downloader.server.contains(savedServerList.getServerData(index_server).serverIP) && index_server <= (Client.getInstance().downloader.server.size() - 1)) {
			button.enabled = false;
		} else {
			button.enabled = true;
		}
	}
	
	@Redirect(method = "selectServer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiButton;enabled:Z", ordinal = 5, opcode = Opcodes.PUTFIELD))
	private void deleteBlock2(GuiButton button, boolean info) {
		if(Client.getInstance().downloader.server.contains(savedServerList.getServerData(index_server).serverIP) && index_server <= (Client.getInstance().downloader.server.size() - 1)) {
			button.enabled = false;
		} else {
			button.enabled = true;
		}
	}
	
	/**
	 * @author Lefiy
	 * @reason Overwrite func_175392_a Method
	 */
	@Overwrite
	public boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
		return p_175392_2_ > (Client.getInstance().downloader.server.isEmpty() ? 0 : Client.getInstance().downloader.server.size());
	}
}