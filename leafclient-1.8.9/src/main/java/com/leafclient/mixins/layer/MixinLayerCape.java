package com.leafclient.mixins.layer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.leafclient.Client;
import com.leafclient.utils.LeafUser;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.util.ResourceLocation;

@Mixin(LayerCape.class)
public class MixinLayerCape {
	
	@Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getLocationCape()Lnet/minecraft/util/ResourceLocation;"))
	private ResourceLocation setCapeLocation(AbstractClientPlayer entitylivingbaseIn) {
		if(!Client.getInstance().modmanager.hideCape) {
			String uuid = entitylivingbaseIn.getUniqueID().toString();
			LeafUser user = null;
			if(entitylivingbaseIn instanceof EntityPlayerSP) {
				if(!Client.getInstance().modmanager.cape.equals("None")) {
					String capeID = Client.getInstance().modmanager.cape;
					if(Client.getInstance().downloader.custom.contains(capeID)) {
						int number = Client.getInstance().cos_number;
						String frame = (number == 0) ? "" : String.valueOf(number);
						return Client.getInstance().downloader.data.get(capeID + frame);
					} else {
						return Client.getInstance().downloader.data.get(capeID);
					}
				}
			} else if((user = Client.getInstance().users.isExistUUID(uuid)) != null) {
				if(!user.getCape().equals("None")) {
					String capeID = user.getCape();
					if(Client.getInstance().downloader.custom.contains(capeID)) {
						int number = Client.getInstance().cos_number;
						String frame = (number == 0) ? "" : String.valueOf(number);
						return Client.getInstance().downloader.data.get(capeID + frame);
					} else {
						return Client.getInstance().downloader.data.get(capeID);
					}
				}
			}
		}
		return entitylivingbaseIn.getLocationCape();
	}
}