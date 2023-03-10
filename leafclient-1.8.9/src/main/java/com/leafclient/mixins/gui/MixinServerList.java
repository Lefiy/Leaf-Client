package com.leafclient.mixins.gui;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

@Mixin(ServerList.class)
public class MixinServerList {
	
	@Shadow
	@Final
	private List<ServerData> servers;
	
	@Shadow
	public void addServerData(ServerData server) {}
	
	private boolean isTarget = false;
	
	@Inject(method = "loadServerList", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompressedStreamTools;read(Ljava/io/File;)Lnet/minecraft/nbt/NBTTagCompound;", shift = At.Shift.BEFORE))
	private void addCustomServers(CallbackInfo info) {
		if(!Client.getInstance().downloader.server.isEmpty()) {
			for(String ip : Client.getInstance().downloader.server) {
				addServerData(new ServerData("Partner Server", ip, false));
			}
		}
	}
	
	@Redirect(method = "saveServerList", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ServerData;getNBTCompound()Lnet/minecraft/nbt/NBTTagCompound;"))
	private NBTTagCompound isAppendServer(ServerData serverdata) {
		if(Client.getInstance().downloader.server.contains(serverdata.serverIP)) {
			isTarget = true;
		} else {
			isTarget = false;
		}
		return serverdata.getNBTCompound();
	}
	
	@Redirect(method = "saveServerList", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagList;appendTag(Lnet/minecraft/nbt/NBTBase;)V"))
	private void saveServers(NBTTagList nbttaglist, NBTBase nbt) {
		if(!isTarget) nbttaglist.appendTag(nbt);
	}
}