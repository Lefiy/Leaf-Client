package com.leafclient.mods.mod;

import com.leafclient.Client;
import com.leafclient.mods.Mod;

public class HitBox extends Mod {
	
	public boolean showDirection;

	public HitBox() {
		super("HitBox", 0, 0, 
				Integer.parseInt(Client.getInstance().setting.reader("HitBox", "red")),
				Integer.parseInt(Client.getInstance().setting.reader("HitBox", "green")),
				Integer.parseInt(Client.getInstance().setting.reader("HitBox", "blue")),
				0,
				Boolean.valueOf(Client.getInstance().setting.reader("HitBox", "enable")), false);
		setImageX(Integer.parseInt(Client.getInstance().setting.reader("HitBox", "imagex")));
		setImageY(Integer.parseInt(Client.getInstance().setting.reader("HitBox", "imagey")));
		this.showDirection = Boolean.valueOf(Client.getInstance().setting.reader("HitBox", "look_direction"));
	}
}
