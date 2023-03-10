package com.leafclient.mixins.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.impl.IMixinShader;

import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;

@Mixin(ShaderGroup.class)
public class MixinShader implements IMixinShader {
	
	@Shadow
	@Final
	private List<Shader> listShaders;

	public List<Shader> getShaders() {
		return listShaders;
	}
}