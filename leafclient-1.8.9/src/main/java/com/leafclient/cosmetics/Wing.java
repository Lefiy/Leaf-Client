package com.leafclient.cosmetics;

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

public class Wing implements LayerRenderer<AbstractClientPlayer> {
	
	private ModelRenderer modelWing;
	private ModelRenderer modelWingTip;
	private ModelDragonWings wing;

	public Wing() {
		
		this.wing = new ModelDragonWings();
		
		this.wing.setTexture();
        
        int bw = wing.textureWidth;
        int bh = wing.textureHeight;
        
        wing.textureWidth = 256;
        wing.textureHeight = 256;
        
        this.modelWing = new ModelRenderer(wing, "wing");
        this.modelWing.setRotationPoint(-12.0F, 5.0F, 2.0F);
        this.modelWing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
        this.modelWing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
        this.modelWing.isHidden = true;
        this.modelWingTip = new ModelRenderer(wing, "wingTip");
        this.modelWingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
        this.modelWingTip.isHidden = true;
        this.modelWingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
        this.modelWingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
        this.modelWing.addChild(modelWingTip);
        
        wing.textureWidth = bw;
        wing.textureWidth = bh;
	}
	
	private class ModelDragonWings extends ModelBase {
		
		private void setTexture() {
			wing.setTextureOffset("wingTip.bone", 112, 136);
	        wing.setTextureOffset("wing.skin", -56, 88);
	        wing.setTextureOffset("wing.bone", 112, 88);
	        wing.setTextureOffset("wingTip.skin", -56, 144);
		}
		
		private void render(AbstractClientPlayer player, float ageInTicks, float scale) {
			
			if(player.isInvisible()) return;
			
			ResourceLocation location = this.getResourceLoc(player);
			
			if(location == null) return;

			GL11.glPushMatrix();
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(location);
			
			if (player.isSneaking()) {
				GL11.glScaled(0.1D, 0.1D, 0.1D);
				GL11.glTranslated(0.0D, 1.8D, 1.1D);
				GL11.glRotated(10.0F, -10.0F, 0.0F, 0.0F);
			} else {
			    GL11.glScaled(0.1D, 0.1D, 0.1D);
			    GL11.glTranslated(0.0D, -0.3D, 1.1D);
			    GL11.glRotated(10.0F, -10.0F, 0.0F, 0.0F);
			}
			
			for (int i = 0; i < 2; i++) {
				float f6 = (ageInTicks / 10.0F);
				modelWing.rotateAngleX = 0.125F - (float)Math.cos(f6) * 0.1F;
				modelWing.rotateAngleY = 0.25F;
				modelWing.rotateAngleZ = (float)(Math.sin(f6) + 1.225D) * 0.5F;
				modelWingTip.rotateAngleZ = -((float)(Math.sin((f6 + 2.0F)) + 0.5D)) * 0.75F;
				modelWing.isHidden = false;
				modelWingTip.isHidden = false;
				if (!player.isInvisible()) {
					GL11.glPushMatrix();
					GL11.glDisable(GL11.GL_LIGHTING);
					modelWing.render(scale);
					GL11.glPopMatrix();
				}
				modelWing.isHidden = false;
				modelWingTip.isHidden = false;
				if (i == 0) GL11.glScaled(-1.0F, 1.0F, 1.0F); 
			}
			GL11.glPopMatrix();
		}
		
		private ResourceLocation getResourceLoc(AbstractClientPlayer player) {
			String uuid = player.getUniqueID().toString();
			LeafUser user = null;
			if(player instanceof EntityPlayerSP) {
				if(!Client.getInstance().modmanager.wing.equals("None")) {
					String wingID = Client.getInstance().modmanager.wing;
					return Client.getInstance().downloader.data.get(wingID);
				}
			} else if((user = Client.getInstance().users.isExistUUID(uuid)) != null) {
				if(!user.getWing().equals("None")) {
					String wingID = user.getWing();
					return Client.getInstance().downloader.data.get(wingID);
				}
			}
			return null;
		}
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
		if(!Client.getInstance().modmanager.hideWing) wing.render(entitylivingbaseIn, ageInTicks, scale);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}