package com.leafclient.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.impl.IMixinManager;
import com.leafclient.utils.SkinType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;

@Mixin(AbstractClientPlayer.class)
public class MixinManager implements IMixinManager, SkinManager.SkinAvailableCallback {
	
	@Shadow
	private ResourceLocation locationSkin;
	
	@Shadow
    private ResourceLocation locationCape;

	@Override
	public void loadCustomSkin(GameProfile profile) {
		Minecraft.getMinecraft().getSkinManager().func_152790_a(profile, this, true);
	}
	
	@Override
	public void onSkinAvailable(Type skinPart, ResourceLocation skinLoc) {
		
        switch (SkinType.TYPES[skinPart.ordinal()]) {
            case 1:
                this.locationSkin = skinLoc;
                break;
            case 2:
                this.locationCape = skinLoc;
        }
    }
}