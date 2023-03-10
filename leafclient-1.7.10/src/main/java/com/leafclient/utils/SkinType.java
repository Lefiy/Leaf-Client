package com.leafclient.utils;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

public class SkinType {
		
	public static final int[] TYPES = new int[Type.values().length];

	static {
		
		try {
			TYPES[Type.SKIN.ordinal()] = 1;
		} catch (NoSuchFieldError var2) {;}

		try{
			TYPES[Type.CAPE.ordinal()] = 2;
		} catch (NoSuchFieldError var1) {;}
	}
}