package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

public class NameTag extends Mod {
	
	public boolean isShadow, back;

	public NameTag() {
		super("NameTag", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("NameTag", "enable")), false);
		
		this.isShadow = Boolean.valueOf(Client.getInstance().setting.reader("NameTag", "shadow"));
		this.back = Boolean.valueOf(Client.getInstance().setting.reader("NameTag", "background"));
	}
}
