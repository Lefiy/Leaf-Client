package com.leafclient.mixins.accessor;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.impl.IMixinSession;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

@Mixin(Minecraft.class)
public class MixinSession implements IMixinSession {

    @Shadow
    @Final
    private Session session;
    
    @Shadow
    private void resize(int width, int height) {};
    
    public Session getSession() {
    	return session;
    }
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public void windowResize(int width, int height) {
		this.resize(width, height);
	}
}