package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.screen.CosmeticSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity extends Render {

    @Inject(method = "renderModel", at = @At("RETURN"))
    private void setCosmetic(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_, CallbackInfo info) {
        if(p_77036_1_ instanceof AbstractClientPlayer) {
            if(!Client.getInstance().modmanager.hideWing) {
                Client.getInstance().wing.render((AbstractClientPlayer)p_77036_1_, p_77036_2_, p_77036_3_, 0.0F, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            }
            if(!Client.getInstance().modmanager.hideHat) {
                Client.getInstance().hat.render((AbstractClientPlayer)p_77036_1_, p_77036_2_, p_77036_3_, 0.0F, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            }
        }
    }

    @Inject(method = "passSpecialRender", at = @At("HEAD"))
    private void showNameTag(EntityLivingBase p_77033_1_, double p_77033_2_, double p_77033_4_, double p_77033_6_, CallbackInfo info) {
        if(p_77033_1_ instanceof EntityPlayerSP && Client.getInstance().modmanager.nametag.isEnable()
                && !(Minecraft.getMinecraft().currentScreen instanceof CosmeticSettings)) {
            if(p_77033_1_.isSneaking())
            {
                p_77033_4_ = p_77033_4_ - 0.2;
            }
            this.renderLivingLabel(p_77033_1_, p_77033_1_.getFormattedCommandSenderName().getFormattedText(), p_77033_2_, p_77033_4_, p_77033_6_, 64);
        }
    }
}