package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

public class Animation extends Mod {

	public Animation() {
		super("Old Animation", 0, 0, 0, 0, 0, 0, Boolean.valueOf(Client.getInstance().setting.reader("Old Animation", "enable")), false);
	}
}