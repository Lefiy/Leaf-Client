package com.leafclient.impl;

import com.mojang.authlib.GameProfile;

public interface IMixinManager {
	
	void loadCustomSkin(GameProfile profile);

}
