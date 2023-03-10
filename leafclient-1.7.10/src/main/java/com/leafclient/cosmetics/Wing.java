package com.leafclient.cosmetics;

import org.lwjgl.opengl.GL11;

import com.leafclient.Client;
import com.leafclient.utils.LeafUser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class Wing {
	
	private ModelRenderer wing;
	private ModelRenderer wingTip;
	private ModelDragonWings modelWing;

	public Wing() {
		
		this.modelWing = new ModelDragonWings();
		
		this.modelWing.setTexture();
        
        int bw = modelWing.textureWidth;
        int bh = modelWing.textureHeight;
        
        modelWing.textureWidth = 256;
        modelWing.textureHeight = 256;
        
        this.wing = new ModelRenderer(modelWing, "wing");
        this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
        this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
        this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
        this.wing.isHidden = true;
        this.wingTip = new ModelRenderer(modelWing, "wingTip");
        this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
        this.wingTip.isHidden = true;
        this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
        this.wing.addChild(wingTip);
        
        modelWing.textureWidth = bw;
        modelWing.textureWidth = bh;
	}
	
	private class ModelDragonWings extends ModelBase {
		
		private void setTexture() {
			modelWing.setTextureOffset("wingTip.bone", 112, 136);
	        modelWing.setTextureOffset("wing.skin", -56, 88);
	        modelWing.setTextureOffset("wing.bone", 112, 88);
	        modelWing.setTextureOffset("wingTip.skin", -56, 144);
		}
		
		private void render(AbstractClientPlayer player, float ageInTicks, float scale) {
			
			if(player.isInvisible()) return;
			
			ResourceLocation location = this.getResourceLoc(player);
			
			if(location == null) return;

			GL11.glPushMatrix();
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(location);
			
			if (player.isSneaking()) {
				GL11.glScaled(0.1D, 0.1D, 0.1D);
				GL11.glTranslated(0.0D, 1.0D, 1.1D);
				GL11.glRotated(10.0F, -10.0F, 0.0F, 0.0F);
			} else {
			    GL11.glScaled(0.1D, 0.1D, 0.1D);
			    GL11.glTranslated(0.0D, -0.3D, 1.1D);
			    GL11.glRotated(10.0F, -10.0F, 0.0F, 0.0F);
			}
			
			for (int i = 0; i < 2; i++) {
				float f6 = (ageInTicks / 10.0F);
				wing.rotateAngleX = 0.125F - (float)Math.cos(f6) * 0.1F;
				wing.rotateAngleY = 0.25F;
				wing.rotateAngleZ = (float)(Math.sin(f6) + 1.225D) * 0.5F;
				wingTip.rotateAngleZ = -((float)(Math.sin((f6 + 2.0F)) + 0.5D)) * 0.75F;
				wing.isHidden = false;
				wingTip.isHidden = false;
				if (!player.isInvisible()) {
					GL11.glPushMatrix();
					GL11.glDisable(GL11.GL_LIGHTING);
					wing.render(scale);
					GL11.glPopMatrix();
				}
				wing.isHidden = false;
				wingTip.isHidden = false;
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

	public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float HeadYaw, float headPitch, float scale) {
        this.modelWing.render(player, ageInTicks, scale);
    }
}