package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.impl.IMixinShader;
import com.leafclient.mods.Mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;

public class MotionBlur extends Mod {
	
	public int amount;
	
	private float blur;
	
	private ShaderGroup shader;
	
	private final ResourceLocation LOC = new ResourceLocation("minecraft", "shaders/post/leaf_blur.json");

	public MotionBlur() {
		super("MotionBlur", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("MotionBlur", "enable")), false);
		this.amount = Integer.parseInt(Client.getInstance().setting.reader("MotionBlur", "amount")); updateAmount(amount);
	}
	
	public void updateAmount(int amount) {
		blur = (float) amount / 255.5F;
		this.updateBlurAmount();
	}
	
	public boolean createShader(Minecraft mc) {
		if(shader == null) {
			try {
				shader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), LOC);
				this.updateSize(mc.displayWidth, mc.displayHeight); this.updateBlurAmount(); return true;
			} catch (Exception e) {}
			return false;
		} else {
			return true;
		}
	}
	
	public void loadShader(float partialTicks) {
		if(shader != null) {
			shader.loadShaderGroup(partialTicks);
		}
	}
	
	public void updateSize(int width, int height) {
		if(shader != null) {
			shader.createBindFramebuffers(width, height);
		}
	}
	
	private void updateBlurAmount() {
		if(shader != null) {
			for(Shader shg : ((IMixinShader) shader).getShaders()) {
				ShaderUniform uniform = shg.getShaderManager().getShaderUniform("Amount");
				if(uniform != null) uniform.set(blur);
			}
		}
	}
}