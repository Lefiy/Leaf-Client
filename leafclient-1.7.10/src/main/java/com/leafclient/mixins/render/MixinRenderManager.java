package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.Vec3;

@Mixin(RenderManager.class)
public class MixinRenderManager {
	
	@Inject(method = "renderDebugBoundingBox", at = @At("HEAD"), cancellable = true)
	private void block(Entity p_85094_1_, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_, CallbackInfo info) {
		if(p_85094_1_ instanceof EntityPlayerSP || p_85094_1_ instanceof EntityHorse) {
			info.cancel();
		}
	}

    @Inject(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;I)V", shift = At.Shift.AFTER))
    private void showEyeLoc(Entity p_85094_1_, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_, CallbackInfo info) {
        if(Client.getInstance().modmanager.hitbox.showDirection) {
            Tessellator var12 = Tessellator.instance;
            Vec3 vec3 = p_85094_1_.getLookVec();
            var12.startDrawing(1);
            var12.setColorOpaque_I(Client.getInstance().modmanager.hitbox.getColorValue());
            var12.addVertex(p_85094_2_, p_85094_4_ + p_85094_1_.getEyeHeight(), p_85094_6_);
            var12.addVertex(p_85094_2_ + vec3.xCoord * 1.5D, p_85094_4_ + p_85094_1_.getEyeHeight() + vec3.yCoord * 1.5D, p_85094_6_ + vec3.zCoord * 1.5D);
            var12.draw();
        }
    }

    @ModifyArg(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;I)V", ordinal = 0), index = 1)
    private int setHitBoxColor(int color) {
        return Client.getInstance().modmanager.hitbox.isEnable() ? Client.getInstance().modmanager.hitbox.getColorValue() : color;
    }
}