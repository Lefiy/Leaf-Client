package com.leafclient.mixins;

import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.impl.IMixinSession;
import com.leafclient.screen.custom.GuiPassward;
import com.leafclient.utils.DBUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	
	@Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", shift = At.Shift.BEFORE))
	private void init(CallbackInfo info) {
		Client.getInstance().setup();
		Client.getInstance().applyStatus();
	}
	
	@ModifyArg(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"), index = 0)
	private GuiScreen checkPass(GuiScreen guiScreenIn) {
		return new GuiPassward(Client.getInstance().secure);
	}
	
	@Inject(method = "createDisplay", at = @At("RETURN"))
	private void setDisplayName(CallbackInfo info) {
		Display.setTitle("Leaf Client 5.0 | Minecraft 1.8.9");
	}
	
	@Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
	private void shutdown(CallbackInfo info) {
		DBUtil database = new DBUtil(((IMixinSession)Minecraft.getMinecraft()).getSession().getProfile().getId().toString());
		database.offline(true); Client.getInstance().shutdown();
	}
}