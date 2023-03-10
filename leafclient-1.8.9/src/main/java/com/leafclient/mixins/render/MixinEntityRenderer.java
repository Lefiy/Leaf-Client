package com.leafclient.mixins.render;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.biome.WorldChunkManager;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
	
	@Shadow
	private Minecraft mc;
	
	@Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F", opcode = Opcodes.GETFIELD))
	private float setYaw(Entity entity) {
		return Client.getInstance().modmanager.freelook.getCameraYaw();
	}
	
	@Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F", opcode = Opcodes.GETFIELD))
	private float setPrevYaw(Entity entity) {
		return Client.getInstance().modmanager.freelook.getCameraYaw();
	}
	
	@Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F", opcode = Opcodes.GETFIELD))
	private float setPitch(Entity entity) {
		return Client.getInstance().modmanager.freelook.getCameraPitch();
	}
	
	@Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F", opcode = Opcodes.GETFIELD))
	private float setPrevPitch(Entity entity) {
		return Client.getInstance().modmanager.freelook.getCameraPitch();
	}
	
	@Redirect(method = "updateCameraAndRender", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;inGameHasFocus:Z", opcode = Opcodes.GETFIELD))
	private boolean owMouse(Minecraft mc) {
		return mc.inGameHasFocus && Client.getInstance().modmanager.freelook.canPrevMove();
	}
	
	@Redirect(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/WorldChunkManager;getTemperatureAtHeight(FI)F"))
	private float getTemperature(WorldChunkManager itemstack, float temperature) {
		if(Client.getInstance().modmanager.weatherchanger.isEnable() && Client.getInstance().modmanager.weatherchanger.weather.equals("SNOW")) {
			return 0.0F;
		}
		return temperature;
	}
	
	@ModifyVariable(method = "addRainParticles", at = @At("STORE"), ordinal = 0)
	private float setRain(float f) {
		if(Client.getInstance().modmanager.weatherchanger.isEnable() && Client.getInstance().modmanager.weatherchanger.weather.equals("SNOW")) {
			return 0.0F;
		}
		return f;
	}
	
	@Inject(method = "updateShaderGroupSize", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;createBindEntityOutlineFbs(II)V", shift = At.Shift.BEFORE))
	private void updateMotionblur(int width, int height, CallbackInfo info) {
		if(Client.getInstance().modmanager.motionblur.isEnable()) {
			if(Client.getInstance().modmanager.motionblur.createShader(mc)) {
				Client.getInstance().modmanager.motionblur.updateSize(width, height);
			}
		}
	}
	
	@Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE))
	private void loadMotionblur(float partialTicks, long nanoTime, CallbackInfo info) {
		if(Client.getInstance().modmanager.motionblur.isEnable()) {
			if(Client.getInstance().modmanager.motionblur.createShader(mc)) {
				GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                Client.getInstance().modmanager.motionblur.loadShader(partialTicks);
                GlStateManager.popMatrix();
			}
		}
	}
	
	@Inject(method = "enableLightmap", at = @At("HEAD"), cancellable = true)
	private void setFullBright(CallbackInfo info) {
		if(Client.getInstance().modmanager.fullbright) {
			info.cancel();
		}
	}
	
	@Inject(method = "updateLightmap", at = @At("HEAD"), cancellable = true)
	private void blockUpdate(CallbackInfo info) {
		if(Client.getInstance().modmanager.fullbright) {
			info.cancel();
		}
	}
}