package com.leafclient.mixins.accessor;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.impl.IMixinRenderItemObj;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.entity.RenderItem;

@Mixin(GuiIngame.class)
public class MixinRenderItemObj implements IMixinRenderItemObj {

    @Shadow
    @Final
    protected static RenderItem itemRenderer;

    @Override
    public RenderItem getRenderItem() {
        return itemRenderer;
    }
}