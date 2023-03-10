package com.leafclient.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.leafclient.Client;
import com.leafclient.utils.DBUtil;
import com.leafclient.utils.LeafUser;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Mixin(EntityOtherPlayerMP.class)
public abstract class MixinEntityOtherPlayerMP extends AbstractClientPlayer {
	
	public MixinEntityOtherPlayerMP(World worldIn, GameProfile playerProfile) {
		super(worldIn, playerProfile);
	}
	
	@Inject(method = "<init>(Lnet/minecraft/world/World;Lcom/mojang/authlib/GameProfile;)V", at = @At("RETURN"))
	private void addUserList(World worldIn, GameProfile gameProfileIn, CallbackInfo info) {
		String uuid = gameProfileIn.getId().toString();
		if(uuid != null) {
			DBUtil database = new DBUtil(uuid) {
				@Override
				public void result(LeafUser user) {
					Client.getInstance().users.setData(user);
				}
			}; database.player();
		}
	}

	/**
	 * @author: Lefiy
	 * @reason: Overwrite attackEntityFrom Method
	 */
	@Overwrite
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(Client.getInstance().modmanager.killeffect.isEnable()) {
    		if(source.getEntity() != null) {
    			if(source.getEntity().getCommandSenderName().equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName())) {
    				Client.getInstance().modmanager.killeffect.target = this.getCommandSenderName();
    			}
    		}
    	}
		return true;
	}
}