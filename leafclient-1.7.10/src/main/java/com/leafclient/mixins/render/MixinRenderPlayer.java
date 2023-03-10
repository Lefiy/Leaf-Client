package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.cosmetics.Hat;
import com.leafclient.cosmetics.Wing;
import com.leafclient.utils.LeafUser;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.util.ResourceLocation;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity {
	
	@Shadow
    public ModelBiped modelBipedMain;
	
	private ResourceLocation location;

    public MixinRenderPlayer(ModelBase p_i1261_1_, float p_i1261_2_) {
        super(p_i1261_1_, p_i1261_2_);
    }

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void addPlayerLayer(CallbackInfo info) {
        Client.getInstance().wing = new Wing();
        Client.getInstance().hat = new Hat(this.modelBipedMain);
    }
    
    @Redirect(method = "renderEquippedItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;hasCape()Z"))
    private boolean setCapeLoc(AbstractClientPlayer p_77029_1_) {
    	if(!Client.getInstance().modmanager.hideCape) {
        	String uuid = p_77029_1_.getUniqueID().toString();
			LeafUser user = null;
			if(p_77029_1_ instanceof EntityPlayerSP) {
				if(!Client.getInstance().modmanager.cape.equals("None")) {
					String capeID = Client.getInstance().modmanager.cape;
					if(Client.getInstance().downloader.custom.contains(capeID)) {
						int number = Client.getInstance().cos_number;
						String frame = (number == 0) ? "" : String.valueOf(number);
						location = Client.getInstance().downloader.data.get(capeID + frame);
					} else {
						location = Client.getInstance().downloader.data.get(capeID);
					}
					return true;
				}
			} else if((user = Client.getInstance().users.isExistUUID(uuid)) != null) {
				if(!user.getCape().equals("None")) {
					String capeID = user.getCape();
					if(Client.getInstance().downloader.custom.contains(capeID)) {
						int number = Client.getInstance().cos_number;
						String frame = (number == 0) ? "" : String.valueOf(number);
						location = Client.getInstance().downloader.data.get(capeID + frame);
					} else {
						location = Client.getInstance().downloader.data.get(capeID);
					}
					return true;
				}
			}
			location = null;
			return p_77029_1_.hasCape();
        } else {
        	location = null;
        	return p_77029_1_.hasCape();
        }
    }

    @Redirect(method = "renderEquippedItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getLocationCape()Lnet/minecraft/util/ResourceLocation;"))
    protected ResourceLocation setCape(AbstractClientPlayer p_77029_1_) {
        if(location != null) {
        	return location;
        }
        return p_77029_1_.getLocationCape();
    }
	
	@Inject(method = "rotateCorpse", at = @At("HEAD"))
	private void onDeathEvent(AbstractClientPlayer bat, float p_77043_2_, float p_77043_3_, float partialTicks, CallbackInfo info) {
		if(!Client.getInstance().modmanager.killeffect.bypass) {
			if(bat.deathTime == 1) Client.getInstance().modmanager.killeffect.toggle(bat);
			if(bat.deathTime == 2) Client.getInstance().modmanager.killeffect.killToggle = false;
		}
	}
}