package com.leafclient.mixins.packet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;

@Mixin(S02PacketChat.class)
public class MixinS02PacketChat {
	
	@Redirect(method = "processPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/INetHandlerPlayClient;handleChat(Lnet/minecraft/network/play/server/S02PacketChat;)V"))
	private void callBlockAccess(INetHandlerPlayClient handler, S02PacketChat packet) {
		if(!Client.getInstance().modmanager.chat.access) {
			handler.handleChat(packet);
		}
	}
}