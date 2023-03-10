package com.leafclient.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

	public MixinEntityLivingBase(World worldIn) {
		super(worldIn);
	}
	
	@Inject(method = "getLook", at = @At("HEAD"), cancellable = true)
	private void fix(float partialTicks, CallbackInfoReturnable<Vec3> info) {
		if((Object) this instanceof EntityPlayerSP) {
			info.setReturnValue(super.getLook(partialTicks));
		}
	}
}