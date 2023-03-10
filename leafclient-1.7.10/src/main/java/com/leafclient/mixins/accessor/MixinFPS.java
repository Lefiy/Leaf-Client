package com.leafclient.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.impl.IMixinFPS;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;

@Mixin(Minecraft.class)
public class MixinFPS implements IMixinFPS {

    @Shadow
    private static int debugFPS;
    
    @Shadow
    private SkinManager skinManager;

    @Override
    public int getDebugFPS() {
        return debugFPS;
    }
}