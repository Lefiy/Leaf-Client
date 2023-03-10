package com.leafclient.mixins.render;

import java.awt.Color;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;

@Mixin(RenderManager.class)
public class MixinRenderManager {
	
	@Redirect(method = "doRenderEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;debugBoundingBox:Z", opcode = Opcodes.GETFIELD))
	private boolean showHitBox(RenderManager rendermanager) {
		return rendermanager.isDebugBoundingBox() || Client.getInstance().modmanager.hitbox.isEnable();
	}
	
	@Redirect(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V", ordinal = 1))
	private void callAroundHeadNothing(AxisAlignedBB boundingBox, int red, int green, int blue, int alpha) {}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V"), index = 1)
	private int setHitColorRed(int red) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		return Client.getInstance().modmanager.hitbox.isEnable() ? color.getRed() : red;
	}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V"), index = 2)
	private int setHitColorGreen(int green) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		return Client.getInstance().modmanager.hitbox.isEnable() ? color.getGreen() : green;
	}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V"), index = 3)
	private int setHitColorBlue(int blue) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		return Client.getInstance().modmanager.hitbox.isEnable() ? color.getBlue() : blue;
	}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"), index = 0)
	private int showEyeLineRed(int red) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		return Client.getInstance().modmanager.hitbox.isEnable() ? color.getRed() : red;
	}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"), index = 1)
	private int showEyeLineGreen(int green) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		return Client.getInstance().modmanager.hitbox.isEnable() ? color.getGreen() : green;
	}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"), index = 2)
	private int showEyeLineBlue(int blue) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		return Client.getInstance().modmanager.hitbox.isEnable() ? color.getBlue() : blue;
	}
	
	@ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"), index = 3)
	private int showEyeLineAlpha(int alpha) {
		return Client.getInstance().modmanager.hitbox.isEnable() && Client.getInstance().modmanager.hitbox.showDirection ? 255 : 0;
	}
	
	/*
	@ModifyArgs(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V", ordinal = 0))
	private void setHitBoxColor(Args args) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		args.set(1, color.getRed()); args.set(2, color.getGreen()); args.set(3, color.getBlue());
	}
	
	@ModifyArgs(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(IIII)Lnet/minecraft/client/renderer/WorldRenderer;"))
	private void showEyeLineColor(Args args) {
		Color color = new Color(Client.getInstance().modmanager.hitbox.getColorValue());
		args.set(0, color.getRed()); args.set(1, color.getGreen()); args.set(2, color.getBlue());
		args.set(3, Client.getInstance().modmanager.hitbox.lI ? 255 : 0);
	}
	*/
}