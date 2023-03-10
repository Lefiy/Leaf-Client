package com.leafclient.mixins.gui;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.screen.CosmeticSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;

@Mixin(GuiInventory.class)
public class MixinGuiInventory {

	@Redirect(method = "drawEntityOnScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;renderYawOffset:F", ordinal = 0, opcode = Opcodes.PUTFIELD))
	private static void setYawOffSet(EntityLivingBase entity, float yawOffSet) {
		if(Minecraft.getMinecraft().currentScreen instanceof CosmeticSettings) {
			entity.renderYawOffset = yawOffSet + 180.0F;
		} else {
			entity.renderYawOffset = yawOffSet;
		}
	}
	
	@Redirect(method = "drawEntityOnScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;rotationYaw:F", ordinal = 0, opcode = Opcodes.PUTFIELD))
	private static void setYaw(EntityLivingBase entity, float yaw) {
		if(Minecraft.getMinecraft().currentScreen instanceof CosmeticSettings) {
			entity.rotationYaw = yaw + 180.0F;
		} else {
			entity.rotationYaw = yaw;
		}
	}
}