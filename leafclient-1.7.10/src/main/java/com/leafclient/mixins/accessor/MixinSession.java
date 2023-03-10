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
    
    public Session getSession() {
    	return session;
    }
	
	public void setSession(Session session) {
		this.session = session;
	}
}