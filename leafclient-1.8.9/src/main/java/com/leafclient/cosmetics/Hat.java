package com.leafclient.cosmetics;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.utils.LeafUser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class Hat implements LayerRenderer<AbstractClientPlayer> {
	
	private ModelHat modelHat;
	private ModelRenderer render;

	public Hat(ModelRenderer lIIl) {
		
		this.render = lIIl;
		this.modelHat = new ModelHat();
		
	}

	private class ModelHat extends ModelBase {
		
		private ModelRenderer model1;
		private ModelRenderer model2;
		
		private void render(AbstractClientPlayer player, float ageInTicks, float scale) {
			
			if(player.isInvisible()) return;
			
			if(player.inventory.armorInventory[3] != null) return;
			
			ResourceLocation location = this.getResourceLoc(player);
			
			if(location == null) return;

			GL11.glPushMatrix();
			
			List<String> list = Client.getInstance().downloader.subData.get(location);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(location);
			
			modelHat.textureWidth = Integer.parseInt(list.get(0));
			modelHat.textureHeight = Integer.parseInt(list.get(0));
			
			model1 = new ModelRenderer(modelHat, 0, 0);
			model1.addBox(Float.parseFloat(list.get(1)), Float.parseFloat(list.get(2)), Float.parseFloat(list.get(3)), 
					Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)), Integer.parseInt(list.get(6)));
			
			model2 = new ModelRenderer(modelHat, 0, Integer.parseInt(list.get(7)));
			model2.addBox(Float.parseFloat(list.get(8)), Float.parseFloat(list.get(9)), Float.parseFloat(list.get(10)), 
					Integer.parseInt(list.get(11)), Integer.parseInt(list.get(12)), Integer.parseInt(list.get(13)));
			
			if(player.isSneaking()) {
				GL11.glTranslated(0, 0.25D, 0);
			}
			
			float lIII = Float.parseFloat(list.get(14));

			GL11.glScalef(lIII, lIII, lIII);
			model1.rotateAngleX = render.rotateAngleX;
			model1.rotateAngleY = render.rotateAngleY;
			model1.rotationPointX = 0.0F;
			model1.rotationPointY = 0.0F;
			model1.render(scale);
			
			model2.rotateAngleX = render.rotateAngleX;
			model2.rotateAngleY = render.rotateAngleY;
			model2.rotationPointX = 0.0F;
			model2.rotationPointY = 0.0F;
			model2.render(scale);
			
			GL11.glPopMatrix();
		}
		
		private ResourceLocation getResourceLoc(AbstractClientPlayer player) {
			String uuid = player.getUniqueID().toString();
			LeafUser user = null;
			if(player instanceof EntityPlayerSP) {
				if(!Client.getInstance().modmanager.hat.equals("None")) {
					String hatID = Client.getInstance().modmanager.hat;
					return Client.getInstance().downloader.data.get(hatID);
				}
			} else if((user = Client.getInstance().users.isExistUUID(uuid)) != null) {
				if(!user.getHat().equals("None")) {
					String hatID = user.getHat();
					return Client.getInstance().downloader.data.get(hatID);
				}
			}
			return null;
		}
	}
	
	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
		if(!Client.getInstance().modmanager.hideHat) modelHat.render(entitylivingbaseIn, ageInTicks, scale);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}